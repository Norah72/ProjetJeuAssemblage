package piecesPuzzle.pieces;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
public class PieceL extends AbstractPiece{
	
    /**
     * Constructeur (rotation 0 par défaut)
     * @param x coorosonées x de la pièce
     * @param y coorosonées y de la pièce
     */
    public PieceL(int x, int y){
		super(x,y,0);
	}
	
    /**
     * Constructeur
     * @param x coorosonées x de la pièce
     * @param y coorosonées y de la pièce
     * @param rotation rotation de la pièce
     */
    public PieceL(int x, int y, int rotation){
		super(x,y,rotation);
	}
	
	@Override
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

	//Couleur vert
	@Override
	public String getColor() {
		return "\u001B[32m";
	}
	
	
}