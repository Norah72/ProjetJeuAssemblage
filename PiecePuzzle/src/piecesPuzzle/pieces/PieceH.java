package piecesPuzzle.pieces;

public class PieceH implements PiecesPuzzle{
	
	public int largeurX;
	public int longueurY;
	public boolean[][] grid;
	public int rotationActuel;	
	private int x,y;
	
	public PieceH(int x, int y){
		this.x = x;
		this.y = y;
		this.rotationActuel = 0;
	}
	
	public PieceH(int x, int y, int rotation){
		this.x = x;
		this.y = y;
		this.rotationActuel = rotation;
	}

	public void pieceGrid() {
		choiceRotation(this.rotationActuel);
	}
		
	public void pieceGrid(int rotationNum){
		grid = new boolean[largeurX][longueurY];
		double yDiv = largeurX/2;
		for(int i = 0 ; i < largeurX ; i++) {
			for(int j = 0 ; j < longueurY ; j++) {
				if(((this.rotationActuel == 0 || this.rotationActuel == 2) && (j==0 || j==longueurY-1 ||(i+1 > yDiv && i-1 < yDiv))) || ((this.rotationActuel == 1 || this.rotationActuel == 3) && (i==0 || i==largeurX-1 ||(j+1 > yDiv && j-1 < yDiv)))){
					grid[i][j] = true;
				}else{
					grid[i][j] = false;
				}
			}
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
