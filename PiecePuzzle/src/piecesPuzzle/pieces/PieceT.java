package piecesPuzzle.pieces;

public class PieceT extends AbstractPiece{
	
	public PieceT(int x, int y){
		super(x,y,0);
	}
	
	public PieceT(int x, int y, int rotation){
		super(x,y,rotation);
	}
		
	public void pieceGrid(){
		System.out.println(this.longueurYActuel);
		System.out.println(this.largeurXActuel);
		grid = new boolean[largeurXActuel][longueurYActuel];
		double yDiv = longueurYActuel/2;
		int mod = 0;
		if((largeurXActuel%2 == 2)&& (this.rotationActuel == 1)){
			mod = 1;
		}else if((largeurXActuel%2 == 2)&& (this.rotationActuel == 2)){
			mod = 1;
		}
		for(int i = 0 ; i < largeurXActuel ; i++) {
			for(int j = 0 ; j < longueurYActuel ; j++) {
				if(((this.rotationActuel == 0) && (i==0 || (j+1 > yDiv && j-1 < yDiv)))
						|| ((this.rotationActuel == 1) && (((i+1 > yDiv+mod) && (i-1 < yDiv+mod)) || (j==longueurYActuel-1)))
						|| ((this.rotationActuel == 2) && ((i==largeurXActuel-1) || ((j+1 > yDiv-mod) && (j-1 < yDiv-mod))))
						|| ((this.rotationActuel == 3) && (((i+1 > yDiv) && (i-1 < yDiv)) || (j==0)))
						){
					grid[i][j] = true;
				}else{
					grid[i][j] = false;
				}
			}
		}
	}	
}
