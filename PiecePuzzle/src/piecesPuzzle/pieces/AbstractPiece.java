package piecesPuzzle.pieces;

import util.Coordonnees;
import java.util.HashMap;

public abstract class AbstractPiece implements PiecesPuzzle{
	protected int x,y;
	protected int largeurXActuel, longueurYActuel;
	protected boolean[][] grid;
	protected int rotationActuel;	
        protected HashMap<Coordonnees,Coordonnees> coordonnees;

	
	public AbstractPiece(int x, int y, int rotation){
		this.x = x;
		this.y = y;
		this.rotationActuel = rotation;
	}
        
        public void newCoordonnees(Coordonnees cooplateau){
            int i = 0;
            int j = 0;
            Coordonnees test2=new Coordonnees(cooplateau.setX(cooplateau.getX()+i),cooplateau.setY(cooplateau.getY()+j));
            for(i=0 ; i<this.largeurXActuel; i++){
                for(j=0 ; j<longueurYActuel; j++){
                    this.coordonnees.put(test2,new Coordonnees(i,j));
                }
            }
            
        }
	
        public void updateCoordonnees(Coordonnees cooplateau){
            this.coordonnees.clear();
            this.newCoordonnees(cooplateau);
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
	
}
