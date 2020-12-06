package piecesPuzzle.pieces;

import java.util.ArrayList;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
public interface PiecesPuzzle {

    /**
     *
     */
    public void pieceGrid();

    /**
     *
     * @param i
     */
    public void createPiece(int rotationNum);

    /**
     *
     * @return
     */
    public boolean[][]  getGrid();

    /**
     *
     * @return
     */
    public int getLargeurX();

    /**
     *
     * @return
     */
    public int getLongueurY();

    /**
     *
     * @return
     */
    public ArrayList<Integer> getCoo();
 
    /**
     *
     * @return
     */
    public int getRotation(); 

    /**
     *
     * @param al
     */
    public void updateCoordonnees(ArrayList<Integer> coo);

    /**
     *
     * @return
     */
    public int getX();

    /**
     *
     * @return
     */
    public int getY();
}
