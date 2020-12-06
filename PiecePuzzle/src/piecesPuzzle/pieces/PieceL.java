package piecesPuzzle.pieces;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
public class PieceL extends AbstractPiece{
	
    /**
     *
     * @param x
     * @param y
     */
    public PieceL(int x, int y){
		super(x,y,0);
	}
	
    /**
     *
     * @param x
     * @param y
     * @param rotation
     */
    public PieceL(int x, int y, int rotation){
		super(x,y,rotation);
	}
		
	public void pieceGrid(){
		grid = new boolean[largeurXActuel][longueurYActuel];
		for(int i = 0 ; i < largeurXActuel ; i++) {
			for(int j = 0 ; j < longueurYActuel ; j++) {
                            grid[i][j] = ((this.rotationActuel == 0) && (i+1==largeurXActuel || j==0)) 
                                    || ((this.rotationActuel == 1) && (i==0 || j==0)) || ((this.rotationActuel == 2) && (i==0 || j==longueurYActuel-1)) 
                                    || ((this.rotationActuel == 3) && (i==largeurXActuel-1 || j==longueurYActuel-1));
			}
		}
	}
}