package piecesPuzzle.pieces;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
public class PieceRectangle extends AbstractPiece{
	
    /**
     *
     * @param x
     * @param y
     */
    public PieceRectangle(int x, int y){
		super(x,y,0);
	}
	
    /**
     *
     * @param x
     * @param y
     * @param rotation
     */
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