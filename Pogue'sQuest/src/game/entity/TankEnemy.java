package game.entity;

public class TankEnemy extends Enemy {
    public TankEnemy(int x, int y) {
        super("Tank Enemy", x, y, 100, 1);  // Tank enemies are slower but have high health
    }

    // Tank enemies move slower, no need to override move, they will be naturally slower due to low speed
}
