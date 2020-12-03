package controleur;

import java.util.*;
import modele.*;

public interface InterfacePlay {
    
	public int choixJeu(PlateauPuzzle plateau);
    public int choix(int borneInf, int borneSup);
    public ArrayList<Integer> selectCoordonnees(int largeurPlateau, int longueurPlateau);
    public ArrayList<Integer> selectPiece(int largeur, int longueur, PlateauPuzzle plateau);
	public ArrayList<ArrayList<Integer>> choixDeplacement(int largeur, int longueur, PlateauPuzzle plateau);
	public ArrayList<ArrayList<Integer>> choixAjout(int largeur, int longueur, PlateauPuzzle plateau);

    
}
