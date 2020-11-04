package controleur;

import file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modele.PlateauPuzzle;
import vue.*;


public class PlayConsole implements ActionListener{

	
	private PlateauPuzzle plateauConsole;
	

	private ArrayList<String> pieceString = new ArrayList<String>(Arrays.asList("PieceH", "PieceL", "PieceRectangle", "PieceT"));
	private int largeurPlateauX, longueurPlateauY = 0;

	private boolean explicationRot = true;
	private boolean end = false;

	private String pseudo = null;
	private ScoreFile sauvegardeScore = new ScoreFile();
	
	private InterfaceGraphique vue;
	
	public PlayConsole(/*InterfaceGraphique vue*/){
		//this.vue = vue;
		menu();
	}
	
//######## Menu de chargement ########
	private void menu(){
		boolean reinitialiser = true;
		System.out.println("--------------------------------------------");
		System.out.println("| ## Bienvenue dans le jeu Assemblage ! ## |");
		System.out.println("--------------------------------------------");
		while(!this.end){
			System.out.println();
			System.out.println("----- Menu -----");
			System.out.println("1- Vue Conssole");
			System.out.println("2- Vue Graphique");
			int start = choixValide(1, 4, "Que voulez vous faire ?");
			if(start == 1){
				System.out.println();
				System.out.println("----- Menu -----");
				System.out.println("1- Nouvelle partie");
				System.out.println("2- Charger la dernière partie");
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
					regle();
				}
				else if(choix == 4){
					afficheScore();
				}
						}
			if(start == 2){                            
				while (reinitialiser) {
					vue.start(this);
					 try {
						wait();
					}
					 catch (InterruptedException e)  {
						 System.out.println("jsp ce qui c'est passé");
					 }
					 reinitialiser = false;
				 }
				reinitialiser = true;
				while(reinitialiser){
					initialisationPlateau();
					creationPieceRandom();
					etatPlateau();
					reinitialiser = false;
				}
				vue.afficheGrille();
				/*if(partie fini)
					reinistialisé*/
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
					if(this.pseudo == null)
						this.pseudo();
					sauvegarderPartie();
					end = choixYesNo("Voulez vous arretez la partie ?");
				}
				else if(choix==6){
					end = score();
				}
			}
			etatPlateau();
		}
		finDePartie();
	}
	
	
//######## Nouvelle partie ########	
	
	private void initialisationPlateau(){
		if(this.largeurPlateauX == 0 && this.longueurPlateauY == 0){
                    System.out.println("Veuillez entrer la grandeur du plateau au niveau largeur: ");
                    this.largeurPlateauX = choixValide(5,20,"Le nombre doit être au minimum de 5 et au maximum de 20");
                    System.out.println("Veuillez entrer la grandeur du plateau au niveau longueur: ");
                    this.longueurPlateauY = choixValide(5,20,"Le nombre doit être au minimum de 5 et au maximum de 20");
                }
		this.plateauConsole = new PlateauPuzzle(this.largeurPlateauX,this.longueurPlateauY);
	}
	
	
	private void creationPieceRandom(){
		int randPiece = difZero((this.largeurPlateauX*this.longueurPlateauY)/this.largeurPlateauX);

                int largeur = 0;
                int longueur = 0;
                int max =  this.largeurPlateauX /2 + 2 ;
                
                if(max > 5){
                    max = 5;
                }
                
		for(int i = 0; i <= randPiece; i++){

			String piece = this.pieceString.get(new Random().nextInt(4));
			if(piece.equals("PieceH")){
				System.out.println("H");
				largeur = rdmMinimum(3,max);
				longueur = rdmMinimum(3,max);
			}else if(piece.equals("PieceL")){
				System.out.println("L");
				largeur = rdmMinimum(2,max);
				longueur = rdmMinimum(2,max);
			}else if(piece.equals("PieceRectangle")){
				System.out.println("R");
				largeur = rdmMinimum(1,max-1);
				longueur = rdmMinimum(1,max-1);
			}else if(piece.equals("PieceT")){
				System.out.println("T");
				largeur = rdmMinimum(2,max);
				longueur = rdmMinimum(3,max);
			}
			plateauConsole.newPiece(piece, largeur, longueur);
		}
	}
	
//######## Charger/sauvegarder partie ########	
	
	private void sauvegarderPartie(){
		/*SauvegardeFichier sauvegarde = new SauvegardeFichier(this);
		try{
			sauvegarde.ecrire();
		}
		catch(Exception e){
			System.out.println("Impossible de sauvegarder");
		}*/
	}
	
	private void chargerPartie(){
		/*ChargerPartie charger = new ChargerPartie(this);
		try{
			charger.chargerSauvegarde();
		}
		catch(Exception e){
		System.out.println("Impossible de charger le fichier");
		}*/
	}
	
//######## Méthode de jeu ########		
	
	private void ajoutePiece(){	
		System.out.println("Que pièce voulez vous ajouter ? (Veuillez indiqué le numéro de la pièce)");
		int choixPiece = choixValide(1, this.plateauConsole.getPieceAJouer().size(),"Cette pièce n'existe pas");
		
		System.out.println("Indiquer les coordonnées où vous voulez placer la partie haut gauche de la pièce (Exemple: 2,3)");
		if(this.plateauConsole.addPiece(this.plateauConsole.getPieceAJouer().get(choixPiece-1), valideCoordonnees())){
			//this.piecePlacer.add(this.plateauConsole.getPieceAJouer().get(choixPiece-1));
			//this.plateauConsole.getPieceAJouer().remove(choixPiece-1);
			System.out.println("Piece ajouter");
		}else{
			System.out.println("Piece non ajouter par manque de place");
		}
	}
	
	private void deplacementPiece(){
		
		System.out.println("Que pièce voulez vous déplacer ? (Veuillez indiqué une de ses coordonnées en format 2,3)");
		ArrayList coo = selectPieceValide();
		
		System.out.println("Indiquer les coordonnées où vous voulez placer la partie haut gauche de la pièce (Exemple: 2,3)");
		if(this.plateauConsole.movePiece(this.plateauConsole.getPiece(coo),valideCoordonnees()))
			System.out.println("Piece déplacer");
		else
			System.out.println("Piece non déplacer par manque de place");
		
	}
	
	private void supprimerPiece(){
		System.out.println("Que pièce voulez vous supprimer ? (Veuillez indiqué une de ses coordonnées en format 2,3)");
		ArrayList coo = selectPieceValide();
		this.plateauConsole.removePiece(this.plateauConsole.getPiece(coo));
		//this.plateauConsole.setPieceAJouer();pieceAJouer.add(pieceASupprimer);
		//this.piecePlacer.remove(pieceASupprimer);
		System.out.println("Piece supprimer");
	}
		
	
	private void rotationPiece(){
		if(explicationRot){
			explicationRotation();
		}
		
		System.out.println("Que pièce voulez vous effectuer une rotation ? (Veuillez indiqué une de ses coordonnées en format 2,3)");
		ArrayList coo = selectPieceValide();
		System.out.println("Quel rotation ? Rappel: Choix 0 à 3");
		if(this.plateauConsole.rotationPiece(this.plateauConsole.getPiece(coo), choixValide(0,3,"Choix non accepter: 0 à 3")))
			System.out.println("Rotation effectuer");
		else
			System.out.println("Rotation non effectuer par manque de place");
	}
	
//######## Regle ########
	private void explicationRotation(){
		if(choixYesNo("Explication rotation ?")){
			System.out.println("Les rotations se font dans le sens horaires.");
			System.out.println("Il y a donc quatre prossibilité: du choix 0 au choix 3, comme suite:");
			for(int i = 0 ; i < 4; i++){
				System.out.println("--"+i+"--");
				System.out.println(this.plateauConsole.createNewPiece(this.pieceString.get(1), 4, 3, i));
			}			
		}
		if(!choixYesNo("Voulez vous avoir des explications la prochaine fois ?"))
			explicationRot = false;
		
	}
	
	private void regle(){
		System.out.println("Le but du jeu est qu'il y est le minimum d'aire entre les pièces.");
		System.out.println("Bien sûr, il faut que toutes les pièces disponible soit placer.");
		System.out.println("Vous pouvez calculer votre score que si vous avez posé toutes les pièces sur le plateau.");
		System.out.println("Pour ce faire, vous pouvez placer, déplacer, supprimer ou encore effectuer une rotation des pièces placer.");
		System.out.println();
		System.out.println("Il y a 4 pièces différentes: ");
		
		System.out.println(this.plateauConsole.createNewPiece(this.pieceString.get(0), 4, 3, 0));
		System.out.println();
		System.out.println(this.plateauConsole.createNewPiece(this.pieceString.get(1), 3, 4, 0));
		System.out.println();
		System.out.println(this.plateauConsole.createNewPiece(this.pieceString.get(2), 3, 4, 0));
		System.out.println();
		System.out.println(this.plateauConsole.createNewPiece(this.pieceString.get(3), 3, 3, 0));
		System.out.println();
		
		System.out.println("Bien sûr, vous pouvez sauvegarder votre partie ou charger la dernière partie sauvegarder");
	}
	
	
//######## Fin/quitter jeu ########
	private boolean score(){
		System.out.println("Votre score : "+this.plateauConsole.getScore());
		return choixYesNo("Voulez vous arretez la partie ?");
	}
	
	private void pseudo(){
		System.out.println("Quel est votre pseudo ?");
		Scanner pseudoScan = new Scanner(System.in);
		this.pseudo = pseudoScan.next();
	}
	
	private void finDePartie(){
		if(choixYesNo("Voulez vous sauvegardez votre score ?")){
			if(pseudo == null)
				pseudo();
			else
				if(choixYesNo("Votre pseudo : "+pseudo+" Voulez vous le changer ?"))
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
		if(!this.plateauConsole.getPiecePlacer().isEmpty()){
			nbrChoix = 6;
			System.out.println("2- Déplacer une pièce");
			System.out.println("3- Supprimer une pièce");
			System.out.println("4- Rotation d'une pièce");
			System.out.println("5- Sauvegarder la partie");
			if(this.plateauConsole.getPieceAJouer().isEmpty())
				System.out.println("6- Score/Fin");
		}else{
			System.out.println("2- Sauvegarder la partie");
		}

		int choix = choixValide(1,nbrChoix, "Choix invalide");
		
		if(this.plateauConsole.getPiecePlacer().isEmpty() && choix == 2)
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
		int choix = -1;
		while(choix == -1){
			Scanner choixScan = new Scanner(System.in);
			try{
				choix = choixScan.nextInt();
				while(choix < borneInf || choix > borneSup){
					System.out.println(texte);
					choix = choixScan.nextInt();
				}
			}
			catch(Exception e){
				System.out.println("Choix invalide");
			}
		}
		return choix;
	}
	
	
	private ArrayList selectPieceValide(){
		boolean valide = false;
		ArrayList coo = null;
		while(!valide){
			coo = valideCoordonnees();
			valide = this.plateauConsole.selectPiece(coo);
			if(!valide)
				System.out.println("Il n'y a pas de pièce ici");
		}
		return coo;
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
        private int rdmMinimum(int min, int randPiece ){
            int rand = 0;
            while(rand < min)
                rand = new Random().nextInt(randPiece+1);
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
		for(int i = 0 ; i <= this.plateauConsole.getPieceAJouer().size()-1; i++){
			System.out.println("Piece "+(i+1)+":");
			System.out.println(this.plateauConsole.getPieceAJouer().get(i));
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

	public void setExplicationRot(boolean explicationRot) {
		this.explicationRot = explicationRot;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}


 //####Affichage grille (vue) ############//
    @Override
	public synchronized void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source == vue.getValide()){
			if(vue.getLigne().getSelectedIndex()!=0 && vue.getColonne().getSelectedIndex()!=0){
				this.largeurPlateauX = vue.getLigne().getSelectedIndex()+4;
				this.longueurPlateauY = vue.getColonne().getSelectedIndex()+4;
				notify();
			}
			else{
				System.out.println("Choisis un chiffre couillon");
			}
                        
		}
                
    }
}