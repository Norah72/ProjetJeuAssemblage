package controleur;

import java.util.*;
import modele.*;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
public interface InterfacePlay {
    
    /**
     * 
     * @param plateau plateau de jeu
     * @return liste des actions possible 
     */
    public EnumAction choixJeu(PlateauPuzzle plateau);

    /**
     * 
     * @param borneInf choix minimum possible
     * @param borneSup choix maximum possible
     * @return choix du joueur
     */
    public int choix(int borneInf, int borneSup);

    /**
     *  
     * @param largeurPlateau largeur du plateau
     * @param longueurPlateau longueur du plateau
     * @return coordonnéees que le joueur à choisi
     */
    public ArrayList<Integer> selectCoordonnees(int largeurPlateau, int longueurPlateau);

    /**
     *  
     * @param largeur coordonée X
     * @param longueur coordonée Y
     * @param plateau plateau de jeu
     * @return pièce en fonction des coordonnées 
     */
    public ArrayList<Integer> selectPiece(int largeur, int longueur, PlateauPuzzle plateau);

    /**
     * @param largeur coordonée X
     * @param longueur coordonée Y
     * @param plateau plateau de jeu
     * @return coordonnées où la pièce doit se déplacer
     */
    public ArrayList<ArrayList<Integer>> choixDeplacement(int largeur, int longueur, PlateauPuzzle plateau);

    /**
     * @param largeur coordonée X
     * @param longueur coordonée Y
     * @param plateau plateau de jeu
     * @return coordonnées où la pièce doit se placer
     */
    public ArrayList<ArrayList<Integer>> choixAjout(int largeur, int longueur, PlateauPuzzle plateau);

    
}
