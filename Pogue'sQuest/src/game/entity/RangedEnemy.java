package game.entity;

import java.util.ArrayList;

public class RangedEnemy extends Enemy {
    private long lastShotTime;
    private final long shootCooldown = 2000;  // 2-second cooldown between shots

    public RangedEnemy(int x, int y) {
        super("Ranged Enemy", x, y, 40, 2);  // Ranged enemies have moderate health and speed
        this.lastShotTime = 0;
    }

    // Method for ranged enemy to shoot at the player
    public void shootAtPlayer(int playerX, int playerY, ArrayList<Projectile> enemyProjectiles) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= shootCooldown) {
            // Shoot a projectile towards the player
            enemyProjectiles.add(new Projectile(x, y, playerX, playerY));
            lastShotTime = currentTime;
        }
    }

    @Override
    public void moveTowardsPlayer(int playerX, int playerY) {
        // Ranged enemies might keep their distance, so let's override this method if necessary
        // You can customize their movement to stay a certain distance away from the player
    }
}
