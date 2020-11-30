package piecesPuzzle.pieces;

import java.util.ArrayList;

public interface PiecesPuzzle {

	public void pieceGrid();
	public void createPiece(int rotationNum);
	public boolean[][]  getGrid();
	public int getLargeurX();
	public int getLongueurY();
        public ArrayList<Integer> getCoo();
	public int getRotation(); 
        public void updateCoordonnees(ArrayList<Integer> coo);
	public int getX();
	public int getY();
}
