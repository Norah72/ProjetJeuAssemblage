package piecesPuzzle.pieces;

import java.util.ArrayList;

public abstract class AbstractPiece implements PiecesPuzzle{
	protected int x,y;
	protected int largeurXActuel, longueurYActuel;
	protected boolean[][] grid;
	protected int rotationActuel;
    protected ArrayList<Integer> coordonnees;
	
	public AbstractPiece(int x, int y, int rotation){
		this.x = x;
		this.y = y;
		this.rotationActuel = rotation;
        this.coordonnees = null;
		
		createPiece(this.rotationActuel);
	}
        
	/**
	 * Construction du tableau de la piece en fonction de la rotationActuel
	 */
	public abstract void pieceGrid();
	
	
	/**
	 * Choix de la rotation dans le sens horaire
	 * @param rotationNum 
	 */
	public void createPiece(int rotationNum) {
		this.rotationActuel = rotationNum;
		if(this.rotationActuel == 0 || this.rotationActuel == 2){
			this.largeurXActuel = x;
			this.longueurYActuel = y;
		}else if(this.rotationActuel == 1 || this.rotationActuel == 3){
			this.largeurXActuel = y;
			this.longueurYActuel = x;
		}
		pieceGrid();
	}
	
	public abstract String getColor();
	
	public boolean[][] getGrid() {
		return this.grid;
	}

	public int getLargeurX() {
		return this.largeurXActuel;
	}

	public int getLongueurY() {
		return this.longueurYActuel;
	}
	
	public ArrayList<Integer> getCoo(){
		return this.coordonnees;
	}
	public int getRotation(){
		return this.rotationActuel;
	}

	public void updateCoordonnees(ArrayList<Integer> coo){
		this.coordonnees = coo;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	@Override
	public String toString(){
		for(int i = 0 ; i < this.largeurXActuel; i++) {
			for(int j= 0 ; j < this.longueurYActuel; j++) {
				if(grid[i][j] == true){
					System.out.print(getColor()+"■ "+getColor());
				} else {
					System.out.print(getColor()+"  "+getColor());
				}
			}
			System.out.println(getColor()+""+getColor());
		}
		return "";
	}
        
}
