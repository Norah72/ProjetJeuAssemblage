package piecesPuzzle.pieces;

import java.util.ArrayList;

public class PieceRectangle extends AbstractPiece{
	
	public PieceRectangle(int x, int y){
		super(x,y,0);
	}
	
	public PieceRectangle(int x, int y, int rotation){
		super(x,y,rotation);
	}
	
	public void pieceGrid(){
		grid = new boolean[largeurXActuel][longueurYActuel];
		for(int i = 0 ; i < largeurXActuel ; i++) {
			for(int j= 0 ; j < longueurYActuel ; j++) {
				grid[i][j]=true;
			}
		}
	}
}