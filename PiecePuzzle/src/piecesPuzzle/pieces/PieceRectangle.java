package piecesPuzzle.pieces;

import java.util.ArrayList;

public class PieceRectangle implements PiecesPuzzle{

	public int largeurX;
	public int longueurY;
	public boolean[][] grid;
	public int rotationActuel;	
	public int x,y;
	
	public PieceRectangle(int x, int y){
		this.largeurX = x;
		this.longueurY = y;
		this.rotationActuel = 0;
	}
	
	public PieceRectangle(int x, int y, int rotation){
		this.largeurX = x;
		this.longueurY = y;
		this.rotationActuel = rotation;
	}

	/**
	 * Créer la grille de la pièce
	 */
	public void pieceGrid(){
		choiceRotation(this.rotationActuel);
	}
	
	public void pieceGrid(int rotationNum){
		grid = new boolean[x][y];
		for(int i = 0 ; i < x ; i++) {
			for(int j= 0 ; j < y ; j++) {
				grid[i][j]=true;
			}
			System.out.println();
		}
	}

	/**
	 * Permet de changer la longuer/largeur de la pièce, changer le grille de la pièce,ainsi que de prévenir l'observeur qu'il ya un changement de rotation
	 * @param rotationNum 
	 */
	public void choiceRotation(int rotationNum) {
		if(this.rotationActuel == 0 || this.rotationActuel == 2){
			x = largeurX;
			y = longueurY;
		}else if(this.rotationActuel == 1 || this.rotationActuel == 3){
			x = longueurY;
			y = largeurX;
		}
		pieceGrid(rotationNum);
		
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
