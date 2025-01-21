package bricker.gameobjects;

/**
 * interface for the life counters for the 'Bricker' game
 */
public interface LifeCounters {

    /**
     * increase the life counter in one
     */
    void addLife();

    /**
     * decrease the life counter in one
     */
    void removeLife();
}
