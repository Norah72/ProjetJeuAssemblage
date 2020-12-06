package piecesPuzzle.pieces;

public class PieceT extends AbstractPiece{
	
	public PieceT(int x, int y){
		super(x,y,0);
	}
	
	public PieceT(int x, int y, int rotation){
		super(x,y,rotation);
	}
		
        public void pieceGrid(){

		grid = new boolean[largeurXActuel][longueurYActuel];
		double yDiv = longueurYActuel/2;
		int mod = 0;
		if(this.rotationActuel == 1){
			yDiv = largeurXActuel/2;
		}else if(this.rotationActuel == 3){
			yDiv = largeurXActuel/2;
		}
		if( ((this.longueurYActuel%2 == 0) && (this.rotationActuel==2)) || ((this.largeurXActuel%2 == 0) && (this.rotationActuel==3)) ){
			mod = 1;
		}

		for(int i = 0 ; i < largeurXActuel ; i++) {
			for(int j = 0 ; j < longueurYActuel ; j++) {
				if(((this.rotationActuel == 0) && (i==0 || (j+1 > yDiv && j-1 < yDiv)))
						|| ((this.rotationActuel == 1) && (((i+1 > yDiv) && (i-1 < yDiv)) || (j==longueurYActuel-1)))
						|| ((this.rotationActuel == 2) && ((i==largeurXActuel-1) || ((j+1 > yDiv-mod) && (j-1 < yDiv-mod))))
						|| ((this.rotationActuel == 3) && (((i+1 > yDiv-mod) && (i-1 < yDiv-mod)) || (j==0)))
						){
					grid[i][j] = true;
				}else{
					grid[i][j] = false;
				}
			}
		}

	}		

	//Couleur cyan
	@Override
	public String getColor() {
		return  "\u001B[36m";
	}
}
