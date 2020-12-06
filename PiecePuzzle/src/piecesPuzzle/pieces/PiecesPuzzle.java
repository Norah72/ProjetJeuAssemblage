package piecesPuzzle.pieces;

import java.util.ArrayList;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
public interface PiecesPuzzle {

    /**
	 * Construction du tableau de la piece en fonction de la rotationActuel
    */
    public void pieceGrid();

    /**
	 * Choix de la rotation dans le sens horaire
	 * @param rotationNum 
    */
    public void createPiece(int rotationNum);

    /**
     *
     * @return les endroits physique de la pièce ou non
     */
    public boolean[][]  getGrid();

    /**
     *
     * @return Largueur de la pièce
     */
    public int getLargeurX();

    /**
     *
     * @return Largueur de la pièce
     */
    public int getLongueurY();

    /**
     *
     * @return coordonées de la pièce
     */
    public ArrayList<Integer> getCoo();
 
    /**
     *
     * @return rotation de la pièce
     */
    public int getRotation(); 

    /**
     * Met a jour les coordonées dans la pièce
     * @param coo
     */
    public void updateCoordonnees(ArrayList<Integer> coo);

    /**
     *
     * @return Coordonnées y
     */
    public int getX();

    /**
     *
     * @return Coordonnées x
     */
    public int getY();
    
}
