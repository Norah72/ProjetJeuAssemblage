package piecesPuzzle.pieces;

import java.util.ArrayList;
import util.Coordonnees;

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
	}
        
	/**
	 * Construction du tableau de la piece en fonction de la rotationActuel
	 */
	public abstract void pieceGrid();
	
	public void createPiece() {
		createPiece(0);
	}
	
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
        
}