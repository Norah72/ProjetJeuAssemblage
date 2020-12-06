package piecesPuzzle.pieces;

import java.util.ArrayList;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
public abstract class AbstractPiece implements PiecesPuzzle{

    /**
     *
     */
    protected int x,

    /**
     *
     */
    y;

    /**
     *
     */
    protected int largeurXActuel,

    /**
     *
     */
    longueurYActuel;

    /**
     *
     */
    protected boolean[][] grid;

    /**
     *
     */
    protected int rotationActuel;

    /**
     *
     */
    protected ArrayList<Integer> coordonnees;

    /**
     *
     * @param i
     * @param i1
     * @param i2
     */
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
	
    /**
     *
     * @return
     */
    public boolean[][] getGrid() {
		return this.grid;
	}

    /**
     *
     * @return
     */
    public int getLargeurX() {
		return this.largeurXActuel;
	}

    /**
     *
     * @return
     */
    public int getLongueurY() {
		return this.longueurYActuel;
	}
	
    /**
     *
     * @return
     */
    public ArrayList<Integer> getCoo(){
		return this.coordonnees;
	}

    /**
     *
     * @return
     */
    public int getRotation(){
		return this.rotationActuel;
	}

    /**
     *
     * @param coo
     */
    public void updateCoordonnees(ArrayList<Integer> coo){
		this.coordonnees = coo;
	}
	
    /**
     *
     * @return
     */
    public int getX(){
		return this.x;
	}
	
    /**
     *
     * @return
     */
    public int getY(){
		return this.y;
	}
	
	@Override
	public String toString(){
		for(int i = 0 ; i < this.largeurXActuel; i++) {
			for(int j= 0 ; j < this.longueurYActuel; j++) {
				if(grid[i][j] == true){
					System.out.print("■ ");
				} else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
		return "";
	}
        
}
