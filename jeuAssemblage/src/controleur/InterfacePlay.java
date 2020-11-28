package controleur;

import java.util.*;
import modele.*;

public interface InterfacePlay {
    
    public int choix(int borneInf, int borneSup);
    public ArrayList<Integer> selectCoordonnees(int largeurPlateau, int longueurPlateau);
}
