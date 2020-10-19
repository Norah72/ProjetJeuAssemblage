package piecesPuzzle.pieces;

import java.util.ArrayList;

public interface PiecesPuzzle {

	public void pieceGrid();
	public void choiceRotation(int rotationNum);
	public boolean[][]  getGrid();
	public int getLargeurX();
	public int getLongueurY();
}
