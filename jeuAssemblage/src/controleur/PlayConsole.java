package controleur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import modele.PlateauPuzzle;
import piecesPuzzle.pieces.*;

public class PlayConsole {
	
	PlateauPuzzle plateauConsole;
	int largeurPlateauX, longueurPlateauY;
	ArrayList<PiecesPuzzle> pieceAJouer = new ArrayList<PiecesPuzzle>();
	
	public PlayConsole(){
		play();
	}
	
	public void play(){
		boolean end = false;
		this.largeurPlateauX = 0;
		this.longueurPlateauY = 0;
		
		initialisationPlateau();
		creationPieceRandom();
		while(end == false){
			System.out.println("Voici vos pièce: ");
			for(int i = 0 ; i <= this.pieceAJouer.size()-1; i++){
				System.out.print("Piece "+(i+1)+":");
				affichePiece(this.pieceAJouer.get(i));
			}
			System.out.println(this.pieceAJouer);
			end=true;
		}
	}
		
	private void initialisationPlateau(){
		System.out.println("Veuillez entrer la grandeur du plateau au niveau largeur: ");
		while(this.largeurPlateauX < 5 || this.largeurPlateauX > 20){
			this.largeurPlateauX = dimensionPlateauValider();
		}
		
		System.out.println("Veuillez entrer la grandeur du plateau au niveau longueur: ");
		while(this.longueurPlateauY < 5 || this.longueurPlateauY > 20){
			this.longueurPlateauY = dimensionPlateauValider();
		}
		
		this.plateauConsole = new PlateauPuzzle(this.largeurPlateauX,this.longueurPlateauY);
		
		System.out.println("Voici le plateau:");
		affichePlateau();
	}
	
	private int dimensionPlateauValider(){
		System.out.println("Le nombre doit être au minimum de 5 et au maximum de 20");
		return (new Scanner(System.in)).nextInt();
	}
	
	private void creationPieceRandom(){
		int randPiece = difZero((this.largeurPlateauX*this.longueurPlateauY)/this.largeurPlateauX);

		for(int i = 0; i <= randPiece; i++){
			int piece = new Random().nextInt(3);
			int largeur = difZero((this.largeurPlateauX)/2);
			int longueur = difZero((this.longueurPlateauY)/2);

			if(piece == 0){
				this.pieceAJouer.add((new PieceH(largeur+1,longueur+1)));
			}else if(piece == 1){
				this.pieceAJouer.add(new PieceL(largeur+1,longueur+1));
			}else if(piece == 2){
				this.pieceAJouer.add(new PieceRectangle(largeur,longueur));
			}else if(piece == 3){
				this.pieceAJouer.add(new PieceT(largeur+1,longueur+1));
			}
			this.pieceAJouer.get(i).createPiece();
		}
	}
	
	private int difZero(int randPiece){
		int rand = 0;
		while(rand < 1)
			rand = new Random().nextInt(randPiece);
		return rand;
	}
	
	private void affichePlateau(){
		for(int i = 0; i<=this.largeurPlateauX-1; i++){
			for(int j = 0; j<=this.longueurPlateauY-1; j++){
				if(this.plateauConsole.getPlateau().get(new ArrayList<Integer>(Arrays.asList(i,j))) != null){
					System.out.print("■");
				}else{
					System.out.print("-");
				}
			}
			System.out.println();
		}
	}
	
	private void affichePiece(PiecesPuzzle piece){
		boolean[][] grid = piece.getGrid();
		for(int i = 0 ; i < piece.getLargeurX() ; i++) {
			for(int j= 0 ; j < piece.getLongueurY(); j++) {
				if(grid[i][j] == true){
					System.out.print("■");
				} else {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
}