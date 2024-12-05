package game.entity;

public class FastEnemy extends Enemy {
    public FastEnemy(int x, int y) {
        super("Fast Enemy", x, y, 30, 5);  // Fast enemies have less health but higher speed
    }

    // Optional: Customize movement behavior if needed
    @Override
    public void moveTowardsPlayer(int playerX, int playerY) {
        if (x < playerX) {
            x += speed * 1.5;  // Move faster
        } else if (x > playerX) {
            x -= speed * 1.2;
        }

        if (y < playerY) {
            y += speed * 1.5;
        } else if (y > playerY) {
            y -= speed * 1.2;
        }
    }
}
