package controleur;

import java.util.*;
import modele.*;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
public interface InterfacePlay {
    
    /**
     * Retrourne la liste des actions possible 
     * @param plateau
     * @return
     */
    public EnumAction choixJeu(PlateauPuzzle plateau);

    /**
     * Retourne le choix du joueur
     * @param borneInf
     * @param borneSup
     * @return
     */
    public int choix(int borneInf, int borneSup);

    /**
     * Retoourne les coordonnéees que le joueur à choisi 
     * @param largeurPlateau
     * @param longueurPlateau
     * @return
     */
    public ArrayList<Integer> selectCoordonnees(int largeurPlateau, int longueurPlateau);

    /**
     * Sélectionne la pièce en fonction des coordonnées 
     * @param largeur
     * @param longueur
     * @param plateau
     * @return
     */
    public ArrayList<Integer> selectPiece(int largeur, int longueur, PlateauPuzzle plateau);

    /**
     * Retroune les coordonnées où la pièce doit se déplacer
     * @param largeur
     * @param longueur
     * @param plateau
     * @return
     */
    public ArrayList<ArrayList<Integer>> choixDeplacement(int largeur, int longueur, PlateauPuzzle plateau);

    /**
     * Retroune les coordonnées où la pièce doit se placer
     * @param largeur
     * @param longueur
     * @param plateau
     * @return
     */
    public ArrayList<ArrayList<Integer>> choixAjout(int largeur, int longueur, PlateauPuzzle plateau);

    
}
