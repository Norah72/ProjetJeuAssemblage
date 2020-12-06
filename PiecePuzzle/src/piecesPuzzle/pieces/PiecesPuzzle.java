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
	 * @param rotationNum rotation de la pièce
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
     * @param coo nouvelle coordonées
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
    

	 /**
	 * Permet de changer la couleur de la pièce
	 * @return Couleur bleu
	 */
	public String getColor();
}
