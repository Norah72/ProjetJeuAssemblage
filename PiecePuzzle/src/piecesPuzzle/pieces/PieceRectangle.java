package piecesPuzzle.pieces;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
public class PieceRectangle extends AbstractPiece{
	
    /**
     * Constructeur (rotation 0 par défaut)
     * @param x coorosonées x de la pièce
     * @param y coorosonées y de la pièce
     */
    public PieceRectangle(int x, int y){
		super(x,y,0);
	}
	
    /**
     * Constructeur
     * @param x coorosonées x de la pièce
     * @param y coorosonées y de la pièce
     * @param rotation rotation de la pièce
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