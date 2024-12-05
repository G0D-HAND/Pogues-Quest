package game.entity;

public class Upgrade {
    public enum UpgradeType { HEALTH, ATTACK, SPEED, MAGNET }

    private UpgradeType type;
    private String description;

    public Upgrade(UpgradeType type, String description) {
        this.type = type;
        this.description = description;
    }

    // Getters for upgrade type and description
    public UpgradeType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    // Apply the upgrade to the player based on the type
    public void applyUpgrade(Player player) {
        switch (type) {
            case HEALTH:
                player.increaseMaxHealth(20);  // Increase max health by 20
                break;
            case ATTACK:
                player.increaseAttackPower(5);  // Increase attack power by 5
                break;
            case SPEED:
                player.increaseSpeed(1);  // Increase speed by 1
                break;
            case MAGNET:
                player.increaseMagnetRadius(50);  // Increase item attraction radius by 50 pixels
                break;
        }
    }
}
