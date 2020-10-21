package piecesPuzzle.pieces;

import java.util.ArrayList;

public interface PiecesPuzzle {

	public void pieceGrid();
	public void createPiece();
	public void createPiece(int rotationNum);
	public boolean[][]  getGrid();
	public int getLargeurX();
	public int getLongueurY();
}
