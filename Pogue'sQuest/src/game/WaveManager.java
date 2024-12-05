package game;

import game.entity.*;
import java.util.ArrayList;

public class WaveManager {
    private int waveNumber;
    private final int frameWidth = 800;  // Width of the game frame
    private final int frameHeight = 600; // Height of the game frame

    public WaveManager() {
        this.waveNumber = 1;
    }

    // Spawn a new wave of mixed enemy types outside the screen
    public ArrayList<Enemy> spawnWave(ArrayList<Projectile> enemyProjectiles) {
        ArrayList<Enemy> newEnemies = new ArrayList<>();
        int numberOfEnemies = waveNumber * 3;  // Increase the number of enemies each wave

        for (int i = 0; i < numberOfEnemies; i++) {
            int x = 0, y = 0;

            // Spawn enemies outside the frame (on any side: top, bottom, left, or right)
            int side = (int) (Math.random() * 4);  // Choose a random side (0 = top, 1 = right, 2 = bottom, 3 = left)
            switch (side) {
                case 0:  // Top of the frame (outside)
                    x = (int) (Math.random() * frameWidth);
                    y = -50;  // Spawn just above the screen
                    break;
                case 1:  // Right of the frame (outside)
                    x = frameWidth + 50;
                    y = (int) (Math.random() * frameHeight);
                    break;
                case 2:  // Bottom of the frame (outside)
                    x = (int) (Math.random() * frameWidth);
                    y = frameHeight + 50;
                    break;
                case 3:  // Left of the frame (outside)
                    x = -50;
                    y = (int) (Math.random() * frameHeight);
                    break;
            }

            // Spawn a mixture of different enemy types
            if (waveNumber % 3 == 0) {
                newEnemies.add(new FastEnemy(x, y));  // Fast enemies
            } else if (waveNumber % 2 == 0) {
                newEnemies.add(new TankEnemy(x, y));  // Tank enemies
            } else {
                newEnemies.add(new RangedEnemy(x, y));  // Ranged enemies
            }
        }

        waveNumber++;  // Increment wave number
        return newEnemies;
    }

    // Get the current wave number (for display purposes)
    public int getWaveNumber() {
        return waveNumber;
    }
}
