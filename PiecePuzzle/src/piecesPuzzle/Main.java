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
					System.out.print("■ ");
				} else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
		System.out.println("Next:");
		PieceL l = new PieceL(5,4);
		l.pieceGrid();
		boolean[][] gridL = l.getGrid();
		for(int i = 0 ; i < l.getLargeurX() ; i++) {
			for(int j= 0 ; j < l.getLongueurY(); j++) {
				if(gridL[i][j] == true){
					System.out.print("■ ");
				} else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
		System.out.println();
		l.choiceRotation(1);
		gridL = l.getGrid();
		for(int i = 0 ; i < l.getLargeurX() ; i++) {
			for(int j= 0 ; j < l.getLongueurY(); j++) {
				if(gridL[i][j] == true){
					System.out.print("■ ");
				} else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
		System.out.println();
		l.choiceRotation(2);
		gridL = l.getGrid();
		for(int i = 0 ; i < l.getLargeurX() ; i++) {
			for(int j= 0 ; j < l.getLongueurY(); j++) {
				if(gridL[i][j] == true){
					System.out.print("■ ");
				} else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
		System.out.println();
		l.choiceRotation(3);
		gridL = l.getGrid();
		for(int i = 0 ; i < l.getLargeurX() ; i++) {
			for(int j= 0 ; j < l.getLongueurY(); j++) {
				if(gridL[i][j] == true){
					System.out.print("■ ");
				} else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
	}
}
