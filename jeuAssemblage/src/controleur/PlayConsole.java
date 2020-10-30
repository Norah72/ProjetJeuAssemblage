package controleur;

import file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import modele.PlateauPuzzle;
import piecesPuzzle.pieces.*;

public class PlayConsole{
	
	private PlateauPuzzle plateauConsole;
	private int largeurPlateauX, longueurPlateauY;
	private ArrayList<PiecesPuzzle> pieceAJouer;
	private ArrayList<PiecesPuzzle> piecePlacer = new ArrayList<PiecesPuzzle>();
	private boolean explicationRot = true;
	private String pseudo;
	private boolean end = false;
	private ScoreFile sauvegardeScore = new ScoreFile();
	
	public PlayConsole(){
		menu();
	}
	
//######## Menu de chargement ########
	private void menu(){
		boolean reinitialiser = true;
		System.out.println("--------------------------------------------");
		System.out.println("| ## Bienvenue dans le jeu Assemblage ! ## |");
		System.out.println("--------------------------------------------");
		while(!this.end == false);{
			System.out.println();
			System.out.println("----- Menu -----");
			System.out.println("1- Nouvelle partie");
			System.out.println("2- Charger partie");
			System.out.println("3- Règle de jeu");
			System.out.println("4- Score de jeu");
			int choix = choixValide(1, 4, "Que voulez vous faire ?");

			if(choix == 1){
				while(reinitialiser){
					initialisationPlateau();
					creationPieceRandom();
					etatPlateau();
					if(!choixYesNo("Voulez vous une nouvelle configuration ?"))
						reinitialiser = false;
				}
				play();
			}
			else if(choix == 2){
				chargerPartie();
				System.out.println("Content de vous revoir "+this.pseudo+" !");
				etatPlateau();
				play();
			}
			else if(choix == 3){
				
			}
			else if(choix == 4){
				afficheScore();
			}
	}
	}
	
//######## Jouer ########
	public void play(){		
		//POUR TEST://
		/*System.out.println("Ajout d'une pièce");
		this.plateauConsole.addPiece(this.pieceAJouer.get(1), new ArrayList<Integer>(Arrays.asList(2, 4)));
		this.piecePlacer.add(this.pieceAJouer.get(1));
		this.pieceAJouer.remove(1);*/
		//FIN TEST//
			
		while(!this.end){
			int choix = choix();
			if(choixYesNo("Etes vous sûr de ce choix ?")){
				if(choix==1)
					ajoutePiece();
				else if(choix==2)
					deplacementPiece();
				else if(choix==3)
					supprimerPiece();
				else if(choix==4)
					rotationPiece();
				else if(choix==5){
					end = score();
				}
				else if(choix==6){
					pseudo();
					sauvegarderPartie();
				}
			}
			etatPlateau();
		}
		finDePartie();
	}
	
	
//######## Nouvelle partie ########	
	
	private void initialisationPlateau(){
		this.largeurPlateauX = 0;
		this.longueurPlateauY = 0;
		
		System.out.println("Veuillez entrer la grandeur du plateau au niveau largeur: ");
		this.largeurPlateauX = choixValide(5,20,"Le nombre doit être au minimum de 5 et au maximum de 20");
		System.out.println("Veuillez entrer la grandeur du plateau au niveau longueur: ");
		this.longueurPlateauY = choixValide(5,20,"Le nombre doit être au minimum de 5 et au maximum de 20");
		
		this.plateauConsole = new PlateauPuzzle(this.largeurPlateauX,this.longueurPlateauY);
	}
	
	
	private void creationPieceRandom(){
		this.pieceAJouer = new ArrayList<PiecesPuzzle>();
		int randPiece = difZero((this.largeurPlateauX*this.longueurPlateauY)/this.largeurPlateauX);

		for(int i = 0; i <= randPiece; i++){
			int piece = new Random().nextInt(4);
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
	
//######## Charger/sauvegarder partie ########	
	
	private void sauvegarderPartie(){
		SauvegardeFichier sauvegarde = new SauvegardeFichier();
		try{
			sauvegarde.write(this);
		}
		catch(Exception e){
		System.out.println("Impossible de sauvegarder");
		}
	}
	
	private void chargerPartie(){
		ChargerPartie charger = new ChargerPartie();
		try{
			charger.write(this);
		}
		catch(Exception e){
		System.out.println("Impossible de charger le fichier");
		}
	}
	
//######## Méthode de jeu ########		
	
	private void ajoutePiece(){	
		System.out.println("Que pièce voulez vous ajouter ? (Veuillez indiqué le numéro de la pièce)");
		int choixPiece = choixValide(1, this.pieceAJouer.size(),"Cette pièce n'existe pas");
		
		System.out.println("Indiquer les coordonnées où vous voulez placer la partie haut gauche de la pièce (Exemple: 2,3)");
		if(this.plateauConsole.addPiece(this.pieceAJouer.get(choixPiece-1), valideCoordonnees())){
			this.piecePlacer.add(this.pieceAJouer.get(choixPiece-1));
			this.pieceAJouer.remove(choixPiece-1);
			System.out.println("Piece ajouter");
		}else{
			System.out.println("Piece non ajouter par manque de place");
		}
	}
	
	private void deplacementPiece(){
		
		System.out.println("Que pièce voulez vous déplacer ? (Veuillez indiqué une de ses coordonnées en format 2,3)");
		PiecesPuzzle pieceADeplacer = selectPiece();
		
		System.out.println("Indiquer les coordonnées où vous voulez placer la partie haut gauche de la pièce (Exemple: 2,3)");
		if(this.plateauConsole.movePiece(pieceADeplacer,valideCoordonnees()))
			System.out.println("Piece déplacer");
		else
			System.out.println("Piece non déplacer par manque de place");
		
	}
	
	private void supprimerPiece(){
		System.out.println("Que pièce voulez vous supprimer ? (Veuillez indiqué une de ses coordonnées en format 2,3)");
		PiecesPuzzle pieceASupprimer = selectPiece();
		this.plateauConsole.removePiece(pieceASupprimer);
		this.pieceAJouer.add(pieceASupprimer);
		this.piecePlacer.remove(pieceASupprimer);
		System.out.println("Piece supprimer");
	}
		
	
	private void rotationPiece(){
		if(explicationRot){
			explicationRotation();
		}
		
		System.out.println("Que pièce voulez vous effectuer une rotation ? (Veuillez indiqué une de ses coordonnées en format 2,3)");
		PiecesPuzzle pieceATourner = selectPiece();
		System.out.println("Quel rotation ? Rappel: Choix 0 à 3");
		if(this.plateauConsole.rotationPiece(pieceATourner, choixValide(0,3,"Choix non accepter: 0 à 3")))
			System.out.println("Rotation effectuer");
		else
			System.out.println("Rotation non effectuer par manque de place");
	}
	
	private void explicationRotation(){
		if(choixYesNo("Explication rotation ?")){
			System.out.println("Les rotations se font dans le sens horaires.");
			System.out.println("Il y a donc quatre prossibilité: du choix 0 au choix 3, comme suite:");
			PiecesPuzzle pieceExplication = new PieceL(4,3);
			for(int i = 0 ; i < 4; i++){
				pieceExplication.createPiece(i);
				System.out.println("--"+i+"--");
				System.out.println(pieceExplication);
			}			
		}
		if(!choixYesNo("Voulez vous avoir des explications la prochaine fois ?"))
			explicationRot = false;
		
	}
	
	
//######## Fin/quitter jeu ########
	private boolean score(){
		System.out.println("Votre score : "+this.plateauConsole.getScore());
		return choixYesNo("Voulez vous arretez la partie ?");
	}
	
	private void pseudo(){
		System.out.println("Quel est votre pseudo ?");
		Scanner pseudoScan = new Scanner(System.in);
		pseudo = pseudoScan.next();
	}
	
	private void finDePartie(){
		if(choixYesNo("Voulez vous sauvegardez votre score ?")){
			pseudo();
			try{
				sauvegardeScore.write(this);
			}
			catch(Exception e){
				System.out.println("Impossible de sauvegarder le score");
			}
		}
		System.out.println("Merci d'avoir jouer !");
	}
	
//######## Validation/effectuer des choix ########	
	
	private int choix(){
		int nbrChoix=2;
		
		System.out.println("Que voulez vous faire ?");
		System.out.println("1- Placer une pièce");
		if(!piecePlacer.isEmpty()){
			nbrChoix = 6;
			System.out.println("2- Déplacer une pièce");
			System.out.println("3- Supprimer une pièce");
			System.out.println("4- Rotation d'une pièce");
			System.out.println("5- Score/Fin");
			System.out.println("6- Sauvegarder la partie");
		}else{
			System.out.println("2- Sauvegarder la partie");
		}

		int choix = choixValide(1,nbrChoix, "Choix invalide");
		
		if(piecePlacer.isEmpty() && choix == 2)
			choix = 6;
		
		return choix;		
	}

	
	private boolean choixYesNo(String texte){
		System.out.println(texte+" 0-non / 1-Oui");
		if(choixValide(0,1,"Choix non accepter") == 0)
			return false;
		return true;
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
	
	
	private PiecesPuzzle selectPiece(){
		boolean valide = false;
		PiecesPuzzle pieceSelect = null;
		while(!valide){
			pieceSelect = (PiecesPuzzle)this.plateauConsole.getPlateau().get(valideCoordonnees());
			if(pieceSelect == null)
				System.out.println("Il n'y a pas de pièce ici");
			else
				valide = true;
		}
		return pieceSelect;
	}

	
	private ArrayList valideCoordonnees() throws InputMismatchException{
		int cooPlaceX =-1;
		int cooPlaceY =-1;
		boolean valide = false;
		
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
				System.out.println("Vous devez saisir sous le format 2,3 !");
				continue;
            }

		}
		return new ArrayList<Integer>(Arrays.asList((Integer)cooPlaceX, (Integer)cooPlaceY));
	}
		
	
	private int difZero(int randPiece){
		int rand = 0;
		while(rand < 1)
			rand = new Random().nextInt(randPiece);
		return rand;
	}
	
	

//######## Affichage ########

	private void etatPlateau(){
		System.out.println("Voici le plateau:");
		System.out.println(this.plateauConsole);
		printPiece();
	}
	
	private void printPiece(){
		System.out.println("Voici vos pièce: ");
		for(int i = 0 ; i <= this.pieceAJouer.size()-1; i++){
			System.out.println("Piece "+(i+1)+":");
			System.out.println(this.pieceAJouer.get(i));
		}
	}
	
	private void afficheScore(){
		try{
			sauvegardeScore.affiche();
		}catch(Exception e){
			
		}
	}

	
//######## get pour sauvegarde ########
	public PlateauPuzzle getPlateauConsole(){
		return this.plateauConsole;
	}
	public int getlargeurPlateauX(){
		return this.largeurPlateauX;
	}
	public int getlongueurPlateauY(){
		return this.longueurPlateauY;
	}
	public ArrayList getpieceAJouer(){
		return this.pieceAJouer;
	}
	public ArrayList getpiecePlacer(){
		return this.piecePlacer;
	}
	public boolean getexplicationRot(){
		return this.explicationRot;
	}
	public String getpseudo(){
		return this.pseudo;
	}
	
//######## set pour chargement ########

	public void setPlateauConsole(PlateauPuzzle plateauConsole) {
		this.plateauConsole = plateauConsole;
	}

	public void setLargeurPlateauX(int largeurPlateauX) {
		this.largeurPlateauX = largeurPlateauX;
	}

	public void setLongueurPlateauY(int longueurPlateauY) {
		this.longueurPlateauY = longueurPlateauY;
	}

	public void setPieceAJouer(ArrayList<PiecesPuzzle> pieceAJouer) {
		this.pieceAJouer = pieceAJouer;
	}

	public void setPiecePlacer(ArrayList<PiecesPuzzle> piecePlacer) {
		this.piecePlacer = piecePlacer;
	}

	public void setExplicationRot(boolean explicationRot) {
		this.explicationRot = explicationRot;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	
}
