package piecesPuzzle;

import piecesPuzzle.pieces.*;

public class Main {

	public static void main(String[] args){
		PieceRectangle rec = new PieceRectangle(5,4);
		rec.pieceGrid();
		boolean[][] grid = rec.getGrid();
		for(int i = 0 ; i < rec.getLargeurX() ; i++) {
			for(int j= 0 ; j < rec.getLongueurY(); j++) {
				if(grid[i][j] == true){
					System.out.print("true");
				} else {
					System.out.print("false");
				}
			}
			System.out.println();
		}
	}
}
