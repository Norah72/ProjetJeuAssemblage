package piecesPuzzle.pieces;

import java.util.ArrayList;

public class PiecesPuzzle {
	private int x;
	private int y;
	
	public PiecesPuzzle(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString(){
		ArrayList<String> grid = new ArrayList<String>();
		for(int i = 0 ; i < y ; i++) {
			for(int z = 0 ; z < x*2 ; z++) {
				System.out.print("-");
			}
			System.out.println("");
			for(int j = 0 ; j < x ; j++) {
				System.out.print("|");
				System.out.print(" ");
			}
			System.out.print("|");
			System.out.println("");
		}
		for(int z = 0 ; z < x*2 ; z++) {
				System.out.print("-");
			}
			System.out.println("");
		return "";
	}
}
