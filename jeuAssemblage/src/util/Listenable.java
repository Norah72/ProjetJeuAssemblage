package util;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
public interface Listenable {
    
    /**
     * 
     * @param listener
     */
    public void addListener(Listener listener);
    
    /**
     *
     * @param listener
     */
    public void removeListener(Listener listener);
    
    /**
     *
     */
    public void fireChangement();
    
}