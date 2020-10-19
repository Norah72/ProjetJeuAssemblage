package piecesPuzzle.pieces;

public class PieceL implements PiecesPuzzle{
	
	public int largeurX;
	public int longueurY;
	public boolean[][] grid;
	public int rotationActuel;	
	private int x,y;
	
	public PieceL(int x, int y){
		this.x = x;
		this.y = y;
		this.rotationActuel = 0;
	}
	
	public PieceL(int x, int y, int rotation){
		this.x = x;
		this.y = y;
		this.rotationActuel = rotation;
	}

	public void pieceGrid() {
		choiceRotation(this.rotationActuel);
	}
		
	public void pieceGrid(int rotationNum){
		grid = new boolean[largeurX][longueurY];
		for(int i = 0 ; i < largeurX ; i++) {
			for(int j = 0 ; j < longueurY ; j++) {
				if(((this.rotationActuel == 0) && (i+1==largeurX || j==0)) || ((this.rotationActuel == 1) && (i==0 || j==0)) || ((this.rotationActuel == 2) && (i==0 || j==longueurY-1)) || ((this.rotationActuel == 3) && (i==largeurX-1 || j==longueurY-1))){
					grid[i][j]=true;
					//System.out.print("true");
				}else{
					grid[i][j]=false;
					//System.out.print("false");
				}
			}
			//System.out.println();
		}
	}

	public void choiceRotation(int rotationNum) {
		this.rotationActuel = rotationNum;
		if(this.rotationActuel == 0 || this.rotationActuel == 2){
			this.largeurX = x;
			this.longueurY = y;
		}else if(this.rotationActuel == 1 || this.rotationActuel == 3){
			this.largeurX = y;
			this.longueurY = x;
		}
		pieceGrid(this.rotationActuel);
	}

	public boolean[][] getGrid() {
		return this.grid;
	}

	public int getLargeurX() {
		return this.largeurX;
	}

	public int getLongueurY() {
		return this.longueurY;
	}
	
}
