package game;

import game.entity.*;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {
    private Thread gameThread;
    private boolean running = false;
    private final int FPS = 60;
    private final long frameTime = 1000 / FPS;

    private Player player;
    private ArrayList<Projectile> playerProjectiles;
    private ArrayList<Projectile> enemyProjectiles;
    private ArrayList<Enemy> enemies;
    private ArrayList<XPOrb> xpOrbs;
    private ArrayList<PowerUp> powerUps;  // Power-ups on the field
    private WaveManager waveManager;

    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private Random random;

    // Timer variables for each wave
    private long waveStartTime;
    private long waveInterval = 20000;  // Start with 20 seconds for the first wave

    public GamePanel() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(java.awt.Color.BLACK);
        this.setDoubleBuffered(true);

        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
        this.setFocusable(true);

        player = new Player(100, 100, 100, 5);  // Create player with health 100, speed 5
        playerProjectiles = new ArrayList<>();
        enemyProjectiles = new ArrayList<>();
        enemies = new ArrayList<>();
        xpOrbs = new ArrayList<>();
        powerUps = new ArrayList<>();  // Initialize power-ups list
        waveManager = new WaveManager();  // Manage enemy waves

        random = new Random();
        waveStartTime = System.currentTimeMillis();  // Record the start time of the first wave
    }

    public void startGameThread() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (running) {
            long startTime = System.currentTimeMillis();

            update();
            repaint();

            long endTime = System.currentTimeMillis();
            long sleepTime = frameTime - (endTime - startTime);

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void update() {
        int dx = 0, dy = 0;

        // Handle player movement
        if (keyHandler.upPressed) dy = -1;
        if (keyHandler.downPressed) dy = 1;
        if (keyHandler.leftPressed) dx = -1;
        if (keyHandler.rightPressed) dx = 1;

        player.move(dx, dy);  // Move the player

        // Fire player projectiles if the mouse is pressed and player can shoot
        if (mouseHandler.mousePressed && player.canShoot()) {
            // Continuously fire in the direction of the current mouse position
            playerProjectiles.add(new Projectile(player.getX(), player.getY(), mouseHandler.mouseX, mouseHandler.mouseY));
        }

        // Update player projectiles
        ArrayList<Projectile> projectilesToRemove = new ArrayList<>();
        for (Projectile p : playerProjectiles) {
            p.update();
            for (Enemy e : enemies) {
                if (p.getBounds().intersects(e.getBounds())) {
                    e.takeDamage(10);  // Deal 10 damage to enemy
                    projectilesToRemove.add(p);  // Remove projectile after hitting an enemy
                }
            }
        }
        playerProjectiles.removeAll(projectilesToRemove);  // Remove used projectiles

        // Check if a power-up is spawned randomly when an enemy dies
        for (Enemy e : enemies) {
            if (!e.isAlive()) {
                // 10% chance to drop a random power-up on enemy death
                if (random.nextFloat() < 0.10) {
                    int x = e.getX();
                    int y = e.getY();

                    // Randomly select the type of power-up to drop
                    if (random.nextBoolean()) {
                        powerUps.add(new HealthPowerUp(x, y));  // Drop a health power-up
                    } else {
                        powerUps.add(new DamageBoostPowerUp(x, y));  // Drop a damage boost power-up
                    }
                }
            }
        }

        // Check if the player collects a power-up
        ArrayList<PowerUp> powerUpsToRemove = new ArrayList<>();
        for (PowerUp powerUp : powerUps) {
            if (powerUp.getBounds().intersects(player.getBounds())) {
                powerUp.applyEffect(player);  // Apply the power-up effect to the player
                powerUpsToRemove.add(powerUp);  // Remove the power-up after it's collected
            }
        }
        powerUps.removeAll(powerUpsToRemove);  // Remove collected power-ups

        // Check if the wave interval has passed to spawn the next wave
        long currentTime = System.currentTimeMillis();
        if (currentTime - waveStartTime >= waveInterval) {
            enemies.addAll(waveManager.spawnWave(enemyProjectiles));  // Add new enemies from the wave
            waveStartTime = currentTime;  // Reset the wave start time
            waveInterval *= 1.5;  // Increase wave interval for the next wave
        }

        // Move enemies and handle ranged enemy projectiles
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
        for (Enemy e : enemies) {
            e.moveTowardsPlayer(player.getX(), player.getY());

            // Ranged enemies shoot projectiles at the player
            if (e instanceof RangedEnemy) {
                ((RangedEnemy) e).shootAtPlayer(player.getX(), player.getY(), enemyProjectiles);  // Ranged enemies shoot
            }

            // Check if the enemy collides with the player and apply damage
            if (e.getBounds().intersects(player.getBounds()) && player.canTakeDamage()) {
                player.takeDamage(5);  // Player takes 5 damage per hit
            }

            if (!e.isAlive()) {
                // Handle enemy death logic (e.g., dropping XP orbs)
                xpOrbs.add(new XPOrb(e.getX(), e.getY(), e.getXPValue()));
                enemiesToRemove.add(e);  // Remove dead enemy
            }
        }
        enemies.removeAll(enemiesToRemove);  // Remove dead enemies

        // Check if the player collects XP orbs
        ArrayList<XPOrb> xpOrbsToRemove = new ArrayList<>();
        for (XPOrb orb : xpOrbs) {
            if (orb.getBounds().intersects(player.getBounds())) {
                player.gainXP(orb.getXPValue());  // Player collects XP from the orb
                xpOrbsToRemove.add(orb);  // Remove collected orb
            }
        }
        xpOrbs.removeAll(xpOrbsToRemove);  // Remove collected orbs

        // Update enemy projectiles and check if they hit the player
        ArrayList<Projectile> enemyProjectilesToRemove = new ArrayList<>();
        for (Projectile ep : enemyProjectiles) {
            ep.update();
            if (ep.getBounds().intersects(player.getBounds()) && player.canTakeDamage()) {
                player.takeDamage(5);  // Player takes damage from enemy projectiles
                enemyProjectilesToRemove.add(ep);  // Remove projectile after hitting player
            }
        }
        enemyProjectiles.removeAll(enemyProjectilesToRemove);  // Remove used enemy projectiles

        // Check if the player is still alive
        if (!player.isAlive()) {
            System.out.println("Game Over! Player has died.");
            running = false;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);  // Draw player

        // Draw all enemies
        for (Enemy e : enemies) {
            e.draw(g);
        }

        // Draw all player projectiles
        for (Projectile p : playerProjectiles) {
            p.draw(g);
        }

        // Draw all enemy projectiles
        for (Projectile ep : enemyProjectiles) {
            ep.draw(g);
        }

        // Draw all XP orbs
        for (XPOrb orb : xpOrbs) {
            orb.draw(g);
        }

        // Draw all power-ups
        for (PowerUp powerUp : powerUps) {
            powerUp.draw(g);
        }

        // Display the current wave number
        g.setColor(java.awt.Color.WHITE);
        g.drawString("Wave: " + waveManager.getWaveNumber(), 10, 20);

        // Display the remaining time for the next wave
        long timeRemaining = (waveInterval - (System.currentTimeMillis() - waveStartTime)) / 1000;
        g.drawString("Next Wave In: " + timeRemaining + " seconds", 10, 40);
    }
}
