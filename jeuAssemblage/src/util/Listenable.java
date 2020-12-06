package util;


public interface Listenable {
    
    public void addListener(Listener listener);
    
    public void removeListener(Listener listener);
    
    public void fireChangement();
    
}