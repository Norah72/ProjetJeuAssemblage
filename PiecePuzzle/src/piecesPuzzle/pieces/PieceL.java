package piecesPuzzle.pieces;

public class PieceL extends AbstractPiece{
	
	public PieceL(int x, int y){
		super(x,y,0);
	}
	
	public PieceL(int x, int y, int rotation){
		super(x,y,rotation);
	}
	
	@Override
	public void pieceGrid(){
		grid = new boolean[largeurXActuel][longueurYActuel];
		for(int i = 0 ; i < largeurXActuel ; i++) {
			for(int j = 0 ; j < longueurYActuel ; j++) {
				if(((this.rotationActuel == 0) && (i+1==largeurXActuel || j==0)) || ((this.rotationActuel == 1) && (i==0 || j==0)) || ((this.rotationActuel == 2) && (i==0 || j==longueurYActuel-1)) || ((this.rotationActuel == 3) && (i==largeurXActuel-1 || j==longueurYActuel-1))){
					grid[i][j]=true;
				}else{
					grid[i][j]=false;
				}
			}
		}
	}

	//Couleur vert
	@Override
	public String getColor() {
		return "\u001B[32m";
	}
	
	
}