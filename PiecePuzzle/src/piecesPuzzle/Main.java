package piecesPuzzle;

import piecesPuzzle.pieces.*;

public class Main {

	public static void main(String[] args){
		int plateauX = 20;
		int plateauY = 20;
	
		PiecesPuzzle plateau = new PiecesPuzzle(plateauX, plateauY);
		System.out.println(plateau);
	}
}
