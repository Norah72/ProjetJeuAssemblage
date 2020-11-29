package piecesPuzzle.pieces;

public class PieceH extends AbstractPiece{
	
	public PieceH(int x, int y){
		super(x,y,0);
	}
	
	public PieceH(int x, int y, int rotation){
		super(x,y,rotation);
	}

		
	public void pieceGrid(){	
		grid = new boolean[largeurXActuel][longueurYActuel];
		double yDiv = largeurXActuel/2;
		int mod = 0;
		
		if(this.rotationActuel == 1){
			yDiv = longueurYActuel/2;
		}else if(this.rotationActuel == 3){
			yDiv = longueurYActuel/2;
		}
		
		if( ( ((this.longueurYActuel%2 == 0) && (this.rotationActuel==1)) || (this.largeurXActuel%2 == 0) && (this.rotationActuel==2)) ){
			mod = 1;
		}
		
		for(int i = 0 ; i < largeurXActuel ; i++) {
			for(int j = 0 ; j < longueurYActuel ; j++) {
				if(((this.rotationActuel == 0) && (j==0 || j==longueurYActuel-1 ||(i+1 > yDiv && i-1 < yDiv)))
						|| ((this.rotationActuel == 1) &&  (i==0 || i==largeurXActuel-1 ||(j+1 > yDiv-mod && j-1 < yDiv-mod)))
						|| ((this.rotationActuel == 2) && (j==0 || j==longueurYActuel-1 ||(i+1 > yDiv-mod && i-1 < yDiv-mod)))
						|| ((this.rotationActuel == 3) && (i==0 || i==largeurXActuel-1 ||(j+1 > yDiv && j-1 < yDiv)))
						){
				
					grid[i][j] = true;
				}else{
					grid[i][j] = false;
				}
			}
		}
	}
}