package piecesPuzzle.pieces;

import java.util.ArrayList;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
public abstract class AbstractPiece implements PiecesPuzzle{

    /**
     * Coordonnées x
     */
    protected int x,

    /**
     * Coordonées y
     */
    y;

    /**
     * Largueur de la pièce
     */
    protected int largeurXActuel,

    /**
     * Longueur de la pièce
     */
    longueurYActuel;

    /**
     * Indique les endroits physique de la pièce
     */
    protected boolean[][] grid;

    /**
     * rotation de lapièce
     */
    protected int rotationActuel;

    /**
     * Coordonées x,y dans la pièce
     */
    protected ArrayList<Integer> coordonnees;

    /**
     * Constucteur
     * @param x coorosonées x de la pièce
     * @param y coorosonées y de la pièce
     * @param rotation rotation de la pièce
     */
    public AbstractPiece(int x, int y, int rotation){
		this.x = x;
		this.y = y;
		this.rotationActuel = rotation;
        this.coordonnees = null;
		
		createPiece(this.rotationActuel);
	}
        
	
	public abstract void pieceGrid();
	
	
	
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
	
    
    public int getX(){
		return this.x;
	}
	
    
    public int getY(){
		return this.y;
	}
    /**
     * Modifictaion de l'affichage d'une pièce
     * @return la pièce
     */
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
