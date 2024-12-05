package game.entity;

import java.awt.Color;
import java.awt.Graphics;

public class HealthPowerUp extends PowerUp {
    private int healAmount;

    public HealthPowerUp(int x, int y) {
        super(x, y);
        this.healAmount = 20;  // Heal amount (can be adjusted)
    }

    @Override
    public void applyEffect(Player player) {
        player.restoreHealth(healAmount);  // Restore player's health by the healAmount
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);  // Health power-up shown in green
        g.fillRect(x, y, width, height);
    }
}
