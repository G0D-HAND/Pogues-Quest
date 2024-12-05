package game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends Entity {
    private long lastDamageTime;
    private final long damageCooldown = 1000;  // 1-second cooldown between damage
    private int maxHealth;
    private int attackPower;
    private int baseAttackPower;  // Base attack power before any boosts
    private int magnetRadius;

    private long lastShotTime;
    private final long shootCooldown = 500;

    // XP and leveling system
    private int experience;
    private int level;
    private int xpToNextLevel;

    // Attack power boost
    private long boostEndTime;  // Time when the attack boost ends
    private boolean attackBoosted;

    public Player(int x, int y, int health, int speed) {
        super(x, y, health, speed);
        this.maxHealth = health;
        this.attackPower = 10;
        this.baseAttackPower = 10;  // Default base attack power
        this.magnetRadius = 100;
        this.lastDamageTime = 0;
        this.lastShotTime = 0;
        this.experience = 0;
        this.level = 1;
        this.xpToNextLevel = 100;
        this.boostEndTime = 0;
        this.attackBoosted = false;  // Whether attack is currently boosted
    }

    // Method to move the player based on directional input (dx, dy)
    public void move(int dx, int dy) {
        this.x += dx * speed;
        this.y += dy * speed;
    }

    // Method to restore health
    public void restoreHealth(int healAmount) {
        this.health += healAmount;
        if (this.health > maxHealth) {
            this.health = maxHealth;  // Ensure health doesn't exceed max health
        }
        System.out.println("Health restored by " + healAmount + ". Current health: " + this.health);
    }

    // Method to boost attack power for a limited time
    public void boostAttackPower(int boostAmount, long duration) {
        this.attackPower += boostAmount;  // Increase attack power by boostAmount
        this.attackBoosted = true;
        this.boostEndTime = System.currentTimeMillis() + duration;  // Set when the boost ends
        System.out.println("Attack power boosted by " + boostAmount + " for " + (duration / 1000) + " seconds.");
    }

    // Method to reset attack power if the boost has expired
    public void checkAttackBoost() {
        if (attackBoosted && System.currentTimeMillis() > boostEndTime) {
            this.attackPower = baseAttackPower;  // Reset attack power to base value
            this.attackBoosted = false;
            System.out.println("Attack boost expired. Attack power is now: " + attackPower);
        }
    }

    // Method to increase the player's maximum health
    public void increaseMaxHealth(int amount) {
        this.maxHealth += amount;
        this.health = maxHealth;  // Restore health to full when max health increases
        System.out.println("Max health increased by " + amount + ". New max health: " + maxHealth);
    }

    // Method to increase the player's attack power
    public void increaseAttackPower(int amount) {
        this.attackPower += amount;
        this.baseAttackPower += amount;  // Increase base attack power
        System.out.println("Attack power increased by " + amount + ". New attack power: " + baseAttackPower);
    }

    // Method to increase the player's speed
    public void increaseSpeed(int amount) {
        this.speed += amount;
        System.out.println("Speed increased by " + amount + ". New speed: " + speed);
    }

    // Method to increase the player's magnet radius for item attraction
    public void increaseMagnetRadius(int amount) {
        this.magnetRadius += amount;
        System.out.println("Magnet radius increased by " + amount + ". New magnet radius: " + magnetRadius);
    }

    // Method to check if the player can take damage
    public boolean canTakeDamage() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastDamageTime >= damageCooldown);
    }

    // Method for the player to take damage and reduce health
    public void takeDamage(int damage) {
        if (canTakeDamage()) {
            health -= damage;
            if (health < 0) {
                health = 0;  // Ensure health doesn't go below zero
            }
            lastDamageTime = System.currentTimeMillis();  // Reset the damage cooldown timer
            System.out.println("Player took " + damage + " damage! Health is now: " + health);
        }
    }

    // Method to check if the player can shoot
    public boolean canShoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= shootCooldown) {
            lastShotTime = currentTime;
            return true;
        }
        return false;
    }

    // Check if the player is still alive
    public boolean isAlive() {
        return health > 0;
    }

    // Gain experience points
    public void gainXP(int xp) {
        experience += xp;
        System.out.println("Gained " + xp + " XP. Total XP: " + experience + "/" + xpToNextLevel);

        // Check if the player has enough XP to level up
        if (experience >= xpToNextLevel) {
            levelUp();
        }
    }

    // Level up the player, increase stats, and reset XP
    private void levelUp() {
        level++;
        experience -= xpToNextLevel;  // Carry over extra XP
        xpToNextLevel += 50;  // Increase XP required for the next level

        // Increase player stats on level up
        maxHealth += 20;  // Increase max health by 20
        health = maxHealth;  // Restore health to full
        baseAttackPower += 5;  // Increase base attack power
        speed += 1;  // Increase speed slightly
        System.out.println("Level Up! You are now level " + level + ". Max health: " + maxHealth + ", Attack: " + baseAttackPower + ", Speed: " + speed);
    }

    // Check if the attack power boost has expired, and reset if needed
    public void update() {
        checkAttackBoost();  // Reset attack power if boost has expired
    }

    // Draw the player along with their health bar
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 40, 40);  // Draw player as a blue square

        // Draw health bar above the player
        g.setColor(Color.GREEN);
        g.fillRect(x, y - 10, (int) ((double) health / maxHealth * 40), 5);  // Health bar proportional to max health
    }

    // Get player bounding box for collision detection
    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 40);
    }
}
