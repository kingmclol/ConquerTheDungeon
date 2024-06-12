/**
 * Interface for anything that is damageable (has hp and can lose hp)
 */
public interface Damageable {
    /**
     * Deals damage to the damageable
     * @param damage The damage to take.
     * @return how much damage was taken.
     */
    public int damage(int damage);
}