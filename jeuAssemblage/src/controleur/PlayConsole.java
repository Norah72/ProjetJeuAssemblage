package controleur;

import file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import static java.lang.Thread.sleep;
import java.util.HashMap;
import javax.swing.JPanel;

import modele.PlateauPuzzle;
import vue.*;


public class PlayConsole extends MouseAdapter implements ActionListener{

	
	private PlateauPuzzle plateauConsole;
	
	private int largeurPlateauX, longueurPlateauY = 0;

	private boolean explicationRot = true;
	private boolean end = false;
	private boolean stop = false;
	private boolean endplay = false;

	private String pseudo = null;
	private ScoreFile sauvegardeScore = new ScoreFile();
	private InterfaceGraphique vue;
        private MouseClicker laPetiteSouris;
		private InterfacePlay play;
		private PlayJoueur joueur;
		private boolean montreMessage = true;
		
		private ArrayList choixIa;
	
	public PlayConsole(InterfaceGraphique vue, PlateauPuzzle plateauConsole){
		this.vue = vue;
        this.plateauConsole = plateauConsole;
		this.play = new PlayJoueur();	
				
		menu();
	}
	
//######## Menu de chargement ########
private void menu(){
		boolean reinitialiser = true;
		afficheMessageJoueur("--------------------------------------------");
		afficheMessageJoueur("| ## Bienvenue dans le jeu Assemblage ! ## |");
		afficheMessageJoueur("--------------------------------------------");
		while(!this.end){
			afficheMessageJoueur("");
			afficheMessageJoueur("----- Menu -----");
			afficheMessageJoueur("1- Vue Console");
			afficheMessageJoueur("2- Vue Graphique");
			
			//A REVOIR://
			int start = play.choix(1, 2);
			
			if(start == 1){
				while(!this.end){
					System.out.println();
					afficheMessageJoueur("----- Menu -----");
					afficheMessageJoueur("1- Nouvelle partie");
					afficheMessageJoueur("2- Charger la dernière partie");
					afficheMessageJoueur("3- Règle de jeu");
					afficheMessageJoueur("4- Score de jeu\n");					
					
					int choix = play.choix(1, 4);
					
					if(choix == 1){
						
						while(reinitialiser){
							int choixConf = -2;
							initialisationPlateau();
							creationPieceRandom();
							etatPlateau();
							
							afficheMessageJoueur("Cette configuraion vous convient-elle ? 0-Oui / 1-Non");
							while(choixConf == -2)
								choixConf = play.choix(0,1);
							
							if(choixConf == 0)
								reinitialiser = false;
						}
						initialisationJeu();
					}

					else if(choix == 2){
						chargerPartie("src/file/partie/partie.txt");
						afficheMessageJoueur("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
						afficheMessageJoueur("[---- Content de vous revoir "+this.pseudo+" ! ----]");
						afficheMessageJoueur("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
						etatPlateau();
						initialisationJeu();
					}
					else if(choix == 3){
						regle();
					}
					else if(choix == 4){
						afficheScore();
					}
				}
			}
			if(start == 2){
				playVue();
			}
		}
	}


	public void initialisationJeu(){
		afficheMessageJoueur("\n Voulez vous que l'ordinateur joue cette partie ? 0-Oui / 1-Non / r-retour\n");
		int choix = play.choix(0, 1);
		if(choix == 0){
			int minScore = -1;
			ArrayList choixIaMinScore = new ArrayList();
			montreMessage = false;
			this.play = new PlayIa();
			this.choixIa = new ArrayList();
			
			sauvegarderPartie("src/file/partie/partieIA.txt");
			
			for(int i = 0; i < 100; i++){
				//créer nouvelle instance Play avec ia avec 1 fourmis
				//instance.charger partie
				//instance.play()
				//instance.score et si score inf a score min d'ici alors  de cout
				chargerPartie("src/file/partie/partieIA.txt");
				try{
					play();
				}
				catch(Exception e){
					System.out.println("bug");
				}
				if(minScore < 0 || this.plateauConsole.getScore() < minScore){
					choixIaMinScore = this.choixIa;
					minScore = this.plateauConsole.getScore();
					System.out.println(minScore);
				}
				this.end = endplay;//ca voir si c'était le bug car j'avais peur que ca fasse le end dans le menu
			}
			
			//ici faire jouer l'ia avec arrayList
		}
		
		if(choix == 1)
			try{
			play();
			}catch(Exception e){
				
			}
			this.end = endplay;
	}
	
	
//######## Jouer ########
	public void play() throws InterruptedException{					

		while(!this.endplay){
			int choix = choix();
			System.out.println("choix "+choix);

				if(choix==1)
					ajoutePiece();
				else if(choix==2)
					deplacementPiece();
				else if(choix==3)
					supprimerPiece();
				else if(choix==4)
					rotationPiece();
				else if(choix==5){
					afficheMessageJoueur("Voulez vous arretez la partie après cette sauvegarde ? 0-Oui / 1-Non / r-retour");
					int choixEnd = play.choix(0,1);
					this.stop =(choixEnd == 0 ? true : false);
					
					if(choixEnd != -2){
						pseudo();
						sauvegarderPartie("src/file/partie/partie.txt");
					}
					
					
					if(stop)
						this.endplay = true;
				}
				else if(choix==6){
					this.endplay = score();
				}
			
			if(!endplay)
				etatPlateau();
		}
		finDePartie();
	}
	
	private void playVue(){
            boolean reinitialiser = true;
            try{
		synchronized (this) {
                    vue.start(this);
                    for(int i=0 ; i < vue.getListeBouton().size() ; i++)
			vue.getListeBouton().get(i).addActionListener(this);
                    wait();
		}
		while(reinitialiser){
                    initialisationPlateau();
                    creationPieceRandom();
                    etatPlateau();
                    reinitialiser = false;
		}
		vue.afficheGrille();
                reinitialiser = true;
                laPetiteSouris = new MouseClicker(vue);
                while(reinitialiser){
                    synchronized (this) {
                        System.out.println("test1");
                        wait();
                    }
                    System.out.println("test2");
                    addPieceListener(this.vue.getListePieceForClick());
                    while(!laPetiteSouris.verif()){
                        System.out.print("");
                        /*voila voila voila... on attend... que MONSIEUR daigne appuyé... car sinon ca va etre long... TRES LONG!!!! DEPECHE PTN!!                                                                                                                                                              coucou tu m'as vu mi homo <3 */
                    }
                    laPetiteSouris.setVerif(false);
                    removePieceListener(vue.getListePieceForClick());
                    System.out.println("test3");
                    addCaseListener(vue.getListeCaseForClick());
                    while(!laPetiteSouris.verif()){
                        System.out.print("");
                        /*et encore.... ca devient relou la par contre... ECOUTE SI T'ES NUL TU POSES AU PIF ET TU FAIS PAS CHIER!!                                                                                                                                                                             je suis toujours là mi homo ;) */
                    }
                    laPetiteSouris.setVerif(false);
                    removeCaseListener(vue.getListeCaseForClick());
                    System.out.println("test4");
                    this.plateauConsole.addPiece(this.plateauConsole.getPieceAJouer().get(laPetiteSouris.getPieceSelectionné()), laPetiteSouris.getCaseSelectionné());
                    System.out.println(this.plateauConsole);
					vue.setModele();
                }
            }catch(Exception e){
                    System.out.println("Impossible de charger la vue: "+e);
		}
        }
        private void removeCaseListener(HashMap<ArrayList<Integer>,JPanel> woula){
            for(ArrayList<Integer> i : woula.keySet()){
                woula.get(i).removeMouseListener(laPetiteSouris);
            }
        }
        private void addCaseListener(HashMap<ArrayList<Integer>,JPanel> woula){
            for(ArrayList<Integer> i : woula.keySet()){
                woula.get(i).addMouseListener(laPetiteSouris);
            }
        }
        private void removePieceListener(ArrayList<JPanel> woula){
            for(int i=0 ; i < woula.size() ; i++)
                        woula.get(i).removeMouseListener(laPetiteSouris);
        }
        private void addPieceListener(ArrayList<JPanel> woula){
            for(int i=0 ; i < woula.size() ; i++)
                        woula.get(i).addMouseListener(laPetiteSouris);
        }
//######## Nouvelle partie ########	
	
	private void initialisationPlateau(){
		//A REVOIR://
		if(this.largeurPlateauX == 0 && this.longueurPlateauY == 0){
			afficheMessageJoueur("Veuillez entrer la grandeur du plateau au niveau largeur: ");
			this.largeurPlateauX = play.choix(5,20);
			afficheMessageJoueur("Veuillez entrer la grandeur du plateau au niveau longueur: ");
			this.longueurPlateauY = play.choix(5,20);
                }
		this.plateauConsole.setXY(this.largeurPlateauX,this.longueurPlateauY);
	}
	
	
	private void creationPieceRandom(){
		int largeur = 0;
		int longueur = 0;

		// minimum et maximum pour la taille des pièces
		int max =  (this.largeurPlateauX + longueurPlateauY) /4 ;

		// On ne veux pas de pièce trop grandes, on borne donc leur taille à 5, pour permettre d'avoir 
		//une grande grille de petites pièces, et non des pièces de taille relatives à la taille du plateau.
		if (max < 3) {
			max = 3;
		}else if(max > 5){
			max = 5;
		}

		// minimum et maximum pour le nombre de pièce, puis aléatoire entre ces deux valeurs
		int minPiece = (this.largeurPlateauX + longueurPlateauY) /3;
		int maxPiece = (this.largeurPlateauX+this.longueurPlateauY)/2;
		int randPiece = new Random().nextInt(maxPiece - minPiece ) + minPiece;
                
		for(int i = 0; i <= randPiece; i++){

			String piece = this.plateauConsole.getPieceString().get(new Random().nextInt(this.plateauConsole.getPieceString().size()));
			
			if(piece.equals("PieceH")){
				largeur = rdmMinimum(3,max);
				longueur = rdmMinimum(3,max);
			}else if(piece.equals("PieceL")){
				largeur = rdmMinimum(2,max);
				longueur = rdmMinimum(2,max);
			}else if(piece.equals("PieceRectangle")){
				largeur = rdmMinimum(1,max-1);
				longueur = rdmMinimum(1,max-1);
			}else if(piece.equals("PieceT")){
				largeur = rdmMinimum(2,max);
				longueur = rdmMinimum(3,max);
			}
			plateauConsole.newPiece(piece, largeur, longueur);
		}
	}
	
//######## Charger/sauvegarder partie ########	
	
	private void sauvegarderPartie(String file){
		SauvegardeFichier sauvegarde = new SauvegardeFichier(this, file);
		try{
			sauvegarde.ecrire();
		}
		catch(Exception e){
			System.out.println("Impossible de sauvegarder");
		}
	}
	
	private void chargerPartie(String file){
		ChargerPartie charger = new ChargerPartie(this, file);
		try{
			charger.chargerSauvegarde();
		}
		catch(Exception e){
		System.out.println("Impossible de charger le fichier");
		}
	}
	
//######## Méthode de jeu ########		
	
	private void ajoutePiece(){	
		System.out.println("SIZEZZEEZ : "+this.plateauConsole.getPieceAJouer().size());
		int choixPiece = play.selectAjoutePiece(this.plateauConsole.getPieceAJouer().size());
		
		if(choixPiece != -2){
			ArrayList coo = play.coordonnees(largeurPlateauX, longueurPlateauY);
			
			System.out.println("Piece a ajouter : "+(choixPiece-1)+" au coo "+ coo);
			
			if(this.plateauConsole.addPiece(this.plateauConsole.getPieceAJouer().get(choixPiece-1), coo)){
				afficheMessageJoueur("\n=-=-=-=-=-=-=-=-=-=-=-=-=\n[---- Piece ajouter ----]\n=-=-=-=-=-=-=-=-=-=-=-=-=\n");

			}else{
				afficheMessageJoueur("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n[---- Piece non ajouter par manque de place ----]\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
			}
		}
	}
	
	private void deplacementPiece(){
		afficheMessageJoueur("Que pièce voulez vous déplacer ? (Veuillez indiqué une de ses coordonnées en format 2,3)");
		
		ArrayList cooPieceSelect = play.selectPieceValide(largeurPlateauX, longueurPlateauY, this.plateauConsole);
		ArrayList cooPieceDeplace = play.coordonnees(largeurPlateauX, longueurPlateauY);
		
		System.out.println("Piece déplacer : "+(cooPieceSelect)+" aux coo : "+cooPieceDeplace);
		
		if(this.plateauConsole.movePiece(this.plateauConsole.getPiece(cooPieceSelect),cooPieceDeplace)){
			afficheMessageJoueur("\n=-=-=-=-=-=-=-=-=-=-=-=-=\n[---- Piece déplacer ----]\n=-=-=-=-=-=-=-=-=-=-=-=-=\n");
		}
		else{
			afficheMessageJoueur("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n[---- Piece non déplacer par manque de place ----]\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
		}
		
	}
	
	private void supprimerPiece(){
		afficheMessageJoueur("Que pièce voulez vous supprimer ? (Veuillez indiqué une de ses coordonnées en format 2,3)");
		
		ArrayList coo = play.selectPieceValide(largeurPlateauX, longueurPlateauY, plateauConsole);
		
		this.plateauConsole.removePiece(this.plateauConsole.getPiece(coo));
		System.out.println("Supprime : "+coo);
		afficheMessageJoueur("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=\n[---- Piece supprimer ----]\n=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
	}
		
	
	private void rotationPiece(){
		afficheMessageJoueur("Quelle pièce voulez vous effectuer une rotation ? (Veuillez indiqué une de ses coordonnées en format 2,3)");
		if(explicationRot && montreMessage)
			explicationRotation();
		
		
		ArrayList cooSelectPiece = play.selectPieceValide(largeurPlateauX, longueurPlateauY, plateauConsole);
		
		afficheMessageJoueur("Quel rotation ? Rappel: Choix 0 à 3");
		
		int choix = play.choix(0, 3);
		System.out.println("Rottation : "+cooSelectPiece + " a la rot "+ choix);
		if(this.plateauConsole.rotationPiece(this.plateauConsole.getPiece(cooSelectPiece), choix)){
			afficheMessageJoueur("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n[---- Rotation effectuer ----]\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
        }else{
			afficheMessageJoueur("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n[---- Rotation non effectuer par manque de place ----]\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
        }
	}
	
//######## Regle ########

	
	private void regle(){
        
		System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
		System.out.println("Le but du jeu est qu'il y est le minimum d'aire entre les pièces.");
		System.out.println("Bien sûr, il faut que toutes les pièces disponible soit placer.");
		System.out.println("Vous pouvez calculer votre score que si vous avez posé toutes les pièces sur le plateau.");
		System.out.println("Pour ce faire, vous pouvez placer, déplacer, supprimer ou encore effectuer une rotation des pièces placer.");
		System.out.println();
		System.out.println("Il y a 4 pièces différentes: ");
		
		PlateauPuzzle plateauRegle = new PlateauPuzzle(10,10);
		System.out.println(plateauRegle.createNewPiece(plateauRegle.getPieceString().get(0), 4, 3, 0));
		System.out.println();
		System.out.println(plateauRegle.createNewPiece(plateauRegle.getPieceString().get(1), 3, 4, 0));
		System.out.println();
		System.out.println(plateauRegle.createNewPiece(plateauRegle.getPieceString().get(2), 3, 4, 0));
		System.out.println();
		System.out.println(plateauRegle.createNewPiece(plateauRegle.getPieceString().get(3), 3, 3, 0));
		System.out.println();
		
		System.out.println("Bien sûr, vous pouvez sauvegarder votre partie pour la reprendre plus tard");
        System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
	}
	
	private void explicationRotation(){
		afficheMessageJoueur("Voulez vous des explication sur la rotation ? 0-Oui / 1-Non");
		int choix = -2;
		while(choix == -2)
			choix = play.choix(0,1);
		
		if(choix == 0){
            afficheMessageJoueur("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
			afficheMessageJoueur("Les rotations se font dans le sens horaires.");
			afficheMessageJoueur("Il y a donc quatre prossibilité: du choix 0 au choix 3, comme suite:");
			for(int i = 0 ; i < 4; i++){
				afficheMessageJoueur("--"+i+"--");
				afficheMessageJoueur(""+this.plateauConsole.createNewPiece(this.plateauConsole.getPieceString().get(1), 4, 3, i));
			}
            
			afficheMessageJoueur("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
		}
		afficheMessageJoueur("Voulez vous avoir des explications la prochaine fois ? 0-Oui / 1-Non");
		
		if(play.choix(0,1) == 1)
			explicationRot = false;
		
	}
	
//######## Fin/quitter jeu ########
	private boolean score(){
		if(montreMessage)
			afficheMessageJoueur("\n Votre score : "+this.plateauConsole.getScore() + "pts");
		
		return play.score();
	}
	
	private void pseudo(){
		if(pseudo != null){
			afficheMessageJoueur("Votre pseudo : "+pseudo+" Voulez vous le changer ?- 0-Oui / 1-Non");
			int choix = -2;
			while(choix == -2)
				choix = play.choix(0,1);
			if(choix == 0)
				this.pseudo = null;
		}
		if(pseudo == null){
			afficheMessageJoueur("Inscrivez votre pseudo: ");
			this.pseudo = play.finDePartie();
		}
	}
	
	
	private void finDePartie(){
        afficheMessageJoueur("");
		if(!stop){
			afficheMessageJoueur("Voulez vous sauvegardez votre score ? 0-Oui / 1-Non");
			int choix = -2;
			while(choix == -2)
				choix = play.choix(0, 1);
			if(choix == 0){
				pseudo();

				try{
					sauvegardeScore.write(this);
				}
				catch(Exception e){
					System.out.println("Impossible de sauvegarder le score");
				}
			}
		}
		
		System.out.println("Score: "+this.plateauConsole.getScore());
		
		afficheMessageJoueur("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		afficheMessageJoueur("[---- Merci d'avoir jouer ! ----]");
		if(stop)
			afficheMessageJoueur("[--------- A bientôt ! ---------]");
		afficheMessageJoueur("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
	}
	
//######## Validation/effectuer des choix ########	
	
	private int choix(){
		int nbrChoix = (this.play instanceof PlayIa ? 1 : 2);;
		
		if(!this.plateauConsole.getPiecePlacer().isEmpty())
			nbrChoix = (this.play instanceof PlayIa ? 4 : 5);
			
		if(this.plateauConsole.getPieceAJouer().isEmpty())
			nbrChoix = (this.play instanceof PlayIa ? 5 : 6);
		
		int choix = play.choixJeu(nbrChoix);
		
		if(this.plateauConsole.getPiecePlacer().isEmpty() && choix == 2)
			choix = 5;
		
		if(this.play instanceof PlayIa && choix == 5)
			choix = 6;
		
		return choix;
	}		
	
	private int rdmMinimum(int min, int randPiece ){
		int rand = 0;
		while(rand < min)
			rand = new Random().nextInt(randPiece+1);
		return rand;
	}

//######## Affichage ########

	private void etatPlateau(){
                System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
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
	
	private void afficheMessageJoueur(String texte){
		if(montreMessage)
			System.out.println(texte);
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
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source == vue.getListeBouton().get(0)){
			if(vue.getLigne().getSelectedIndex()!=0 && vue.getColonne().getSelectedIndex()!=0){
				this.largeurPlateauX = vue.getLigne().getSelectedIndex()+4;
				this.longueurPlateauY = vue.getColonne().getSelectedIndex()+4;
				synchronized(this){
                                notify();
                                }
			}
			else{
				System.out.println("Choisis un chiffre cou***** ( il faut être poli voyons ! )");
			}
                        
		}
                if(source == vue.getListeBouton().get(1)){
                    synchronized(this){
                        notify();
                        }
                }
    }
}