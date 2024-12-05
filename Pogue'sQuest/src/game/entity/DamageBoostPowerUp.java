package game.entity;

import java.awt.Color;
import java.awt.Graphics;

public class DamageBoostPowerUp extends PowerUp {
    private int boostAmount;
    private long duration;  // Duration of the boost in milliseconds

    public DamageBoostPowerUp(int x, int y) {
        super(x, y);
        this.boostAmount = 5;  // Boost attack power by 5
        this.duration = 10000;  // 10-second boost duration
    }

    @Override
    public void applyEffect(Player player) {
        player.boostAttackPower(boostAmount, duration);  // Apply temporary damage boost
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);  // Damage boost power-up shown in red
        g.fillRect(x, y, width, height);
    }
}
