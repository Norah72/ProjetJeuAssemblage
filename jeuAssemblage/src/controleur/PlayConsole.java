package controleur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import static jdk.nashorn.tools.ShellFunctions.input;
import modele.PlateauPuzzle;
import piecesPuzzle.pieces.*;

public class PlayConsole {
	
	PlateauPuzzle plateauConsole;
	int largeurPlateauX, longueurPlateauY;
	ArrayList<PiecesPuzzle> pieceAJouer = new ArrayList<PiecesPuzzle>();
	ArrayList<PiecesPuzzle> piecePlacer = new ArrayList<PiecesPuzzle>();
	
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
			printPiece();
			int choix = choix();
			if(choix==1)
				ajoutePiece();
			
			affichePlateau();
			end=true;
		}
	}
	
	private void ajoutePiece() throws InputMismatchException{
		int cooPlaceX =-1;
		int cooPlaceY =-1;
		boolean valide = false;
		
		System.out.println("Que pièce voulez vous ajouter ? (Veuillez indiqué le numéro de la pièce)");
		int choixPiece = choixValide(1, this.pieceAJouer.size(),"Cette pièce n'existe pas");
		
		System.out.println("Indiquer les coordonnées où vous voulez placer la partie haut gauche de la pièce (Exemple: 2,3)");
		
		while(!valide) {
			Scanner sc = new Scanner(System.in);
			
			try{
				String cooScan = sc.next();

				Scanner scanVirgule = new Scanner(cooScan).useDelimiter(",");
				cooPlaceX = scanVirgule.nextInt();
				cooPlaceY = scanVirgule.nextInt();
				scanVirgule.close();
				
				if(((cooPlaceX < 0) || (cooPlaceX > this.largeurPlateauX-1)) || ((cooPlaceY < 0) || (cooPlaceY > this.longueurPlateauY-1)))
					System.out.println("Coordonnées non valide : vous avez entrée des coordonnées inférieur a zero ou supérieur a la grandeur du plateau");
				else
					valide = true;
			}
			catch (Exception e) {
				System.out.println("Vous devez saisir sous le format 2,4 !");
				continue;
            }

		}
		
		
		ArrayList<Integer> cooList = new ArrayList<Integer>(Arrays.asList(cooPlaceX, cooPlaceY));
		this.plateauConsole.addPiece(this.pieceAJouer.get(choixPiece-1), cooList);
		this.piecePlacer.add(this.pieceAJouer.get(choixPiece-1));
		this.pieceAJouer.remove(choixPiece-1);
		
		System.out.println("PieceAjouter");
	}
	
	private int choix(){
		int nbrChoix=1;
		
		System.out.println("Que voulez vous faire ?");
		System.out.println("1- Placer une pièce");
		if(!piecePlacer.isEmpty()){
			nbrChoix = 4;
			System.out.println("2- Déplacer une pièce");
			System.out.println("3- Supprimer une pièce");
			System.out.println("4- Rotation d'une pièce");
		}

		int choix = choixValide(1,nbrChoix, "Choix invalide");
		
		return choix;		
	}
		
	private void initialisationPlateau(){
		System.out.println("Veuillez entrer la grandeur du plateau au niveau largeur: ");
		this.largeurPlateauX = choixValide(5,20,"Le nombre doit être au minimum de 5 et au maximum de 20");
		System.out.println("Veuillez entrer la grandeur du plateau au niveau longueur: ");
		this.longueurPlateauY = choixValide(5,20,"Le nombre doit être au minimum de 5 et au maximum de 20");
		
		this.plateauConsole = new PlateauPuzzle(this.largeurPlateauX,this.longueurPlateauY);
		
		System.out.println("Voici le plateau:");
		affichePlateau();
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
	
		
	private int choixValide(int borneInf, int borneSup, String texte){
		Scanner choixScan = new Scanner(System.in);
		int choix = choixScan.nextInt();
		while(choix < borneInf || choix > borneSup){
			System.out.println(texte);
			choix = choixScan.nextInt();
		}
		return choix;
	}
	
		
	
	private void printPiece(){
		System.out.println("Voici vos pièce: ");
		for(int i = 0 ; i <= this.pieceAJouer.size()-1; i++){
			System.out.println("Piece "+(i+1)+":");
			affichePiece(this.pieceAJouer.get(i));
		}
		System.out.println(this.pieceAJouer);
	}
	
	
	private void affichePlateau(){
		System.out.print("   ");
		for(int z = 0; z<=this.longueurPlateauY-1; z++){
			System.out.print(" "+z+" ");
		}
		System.out.println();
		
		for(int i = 0; i<=this.largeurPlateauX-1; i++){
			if(i<10)
				System.out.print(i+"  ");
			else
				System.out.print(i+" ");
			for(int j = 0; j<=this.longueurPlateauY-1; j++){
				if(this.plateauConsole.getPlateau().get(new ArrayList<Integer>(Arrays.asList(i,j))) != null){
					if(j<10)
						System.out.print(" ■ ");
					else
						System.out.print("  ■ ");
				}else{
					if(j<10)
						System.out.print(" + ");
					else
						System.out.print("  + ");
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