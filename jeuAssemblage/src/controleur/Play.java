package controleur;

import file.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import modele.*;
import vue.*;
import file.*;
import java.util.concurrent.TimeUnit;

public class Play {
    //Attibuts
    
    private InterfacePlay joueurActuel;
    private boolean affichageGraph;
    
    private PlateauPuzzle plateau;
	private PlateauPuzzle bestPlateau;
    
    private boolean montreMessage = true;
    private boolean endPlay;
    
    private int largeurPlateau =5;
    private int longueurPlateau =5;
    private boolean ia = false;
	private boolean fourmis = false;
    private boolean explicationRot = true;
    
    private String pseudo = null;
    private ScoreFile sauvegardeScore = new ScoreFile();
    
    private boolean stop = false;
	
	private String fileIa = "src/file/partie/partieIa.txt";
	private String fileJoueur = "src/file/partie/partie.txt";
   
     
    //Constructeur
    
    public Play(boolean affichageGraph){
        this.affichageGraph = affichageGraph;
        this.joueurActuel = new PlayJoueur();
        if(this.affichageGraph){
			//InterfaceGraphique vueGraph = new InterfaceGraphique();
            affiche("Méthode d'affichage graphique à implémenter");
        }else{
            this.menu();
        }
    }
    private void menu(){
        while(!this.stop){
				
            affiche("\n----- Menu -----");
            affiche("1- Nouvelle partie");
            affiche("2- Charger la dernière partie");
            affiche("3- Règle de jeu");
            affiche("4- Score de jeu");
            affiche("\n0- Quitter");
            affiche("----------------");
            
            affiche("Que voulez vous faire ?");
            int choix = this.joueurActuel.choix(0, 4);
			if(choix == 1 || choix ==2)
				this.ia = choixIa();

            if (choix == 1){
                boolean reinitialiser = true;

                while(reinitialiser){

                    affiche("Le plateau peut faire entre 5 et 20 cases de côté");
                    affiche("Veuillez entrer la largeur du plateau : ");
                    this.largeurPlateau = this.joueurActuel.choix(5,20);
                    affiche("Veuillez entrer la longueur du plateau : ");
                    this.longueurPlateau = this.joueurActuel.choix(5,20);

                    affiche("\nCette configuraion vous convient-elle ? 0-Oui / 1-Non : ");
                    String str = "==> ";
                    if (this.ia){
                        str += "L'ordinateur va jouer";
                    }else{
                        str += "Vous allez jouer";
                    }
                    affiche(str+" sur un plateau "+this.largeurPlateau+" x "+this.longueurPlateau+"\n");
					
                    if(this.joueurActuel.choix(0,1)==0)
                        reinitialiser = false;
                }
                
				reinitialiser = true;
				while (reinitialiser){
					nouvellePartie(this.largeurPlateau, this.longueurPlateau, this.ia);
					
					affiche("Ce jeu vous convient-il ? 0-Oui / 1-Non : ");
					
					if(this.joueurActuel.choix(0,1)==0)
						reinitialiser = false;
				}
                
                this.endPlay=false;
				if(!this.ia){
					jeu();
				}else{
					affiche("L'ordinateur va jouer");
					playIa();
				}

            }else if (choix == 2){
				chargerLaPartie(this.fileJoueur);				

                affiche("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                affiche("[---- Content de vous revoir "+this.pseudo+" ! ----]");
                affiche("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
				
				afficheJeu();
				
				this.endPlay=false;
				if(this.ia){
					affiche("L'ordinateur va jouer");
					playIa();
				}else{				
					jeu();
				}
				
            }else if (choix == 3){
                affiche("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
                affiche("Le but du jeu est qu'il y est le minimum d'aire entre les pièces.");
                affiche("Bien sûr, il faut que toutes les pièces disponible soit placer.");
                affiche("Vous pouvez calculer votre score que si vous avez posé toutes les pièces sur le plateau.");
                affiche("Pour ce faire, vous pouvez placer, déplacer, supprimer ou encore effectuer une rotation des pièces placer.\n");

                affiche("Il y a 4 pièces différentes: ");

                PlateauPuzzle plateauRegle = new PlateauPuzzle(10,10);
                affiche(""+plateauRegle.createNewPiece(plateauRegle.getPieceString().get(0), 4, 3, 0)+"\n");

                affiche(""+plateauRegle.createNewPiece(plateauRegle.getPieceString().get(1), 3, 4, 0)+"\n");

                affiche(""+plateauRegle.createNewPiece(plateauRegle.getPieceString().get(2), 3, 4, 0)+"\n");

                affiche(""+plateauRegle.createNewPiece(plateauRegle.getPieceString().get(3), 3, 3, 0)+"\n");

                affiche("Bien sûr, vous pouvez sauvegarder votre partie pour la reprendre plus tard");
                affiche("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
            }else if (choix == 4){
                try{
                    sauvegardeScore.affiche();
                }catch(Exception e){

                }
            }else if (choix == 0){
                this.stop=true;
            }
        }
    }
	
	private boolean choixIa(){
		affiche("Voulez vous que l'ordinateur joue cette partie ? 0-Oui / 1-Non");
        return (joueurActuel.choix(0,1)) == 0;
	}
	
	public void playIa(){
		affiche("Merci de patientez quelque instant..");
		this.joueurActuel = new PlayIA();
		this.montreMessage = false;
		this.fourmis = true;
		this.pseudo = "Computer";
		
		ArrayList<PlateauPuzzle> best = new ArrayList<PlateauPuzzle>();
		int scoreMin = -1;
		
		sauvegarderLaPartie(this.fileIa);

		for(int i = 0; i < 100 ; i++){
			this.endPlay=false;
			chargerLaPartie(this.fileIa);
			jeu();
			if(this.plateau.getScore() > scoreMin){
				scoreMin = this.plateau.getScore();
				try{
				this.bestPlateau = (PlateauPuzzle) ((PlateauPuzzle) this.plateau).clone();
				}catch(Exception e){
					System.out.println("Impossible de cloner"+e);
				}
			}
		}
		this.fourmis = false;
		
		chargerLaPartie(this.fileIa); // remet a zéro

		jeuIa();

		this.montreMessage = true;
		affiche("Score obtenue : "+this.plateau.getScore());

		this.endPlay = true;
		this.joueurActuel = new PlayJoueur();
	}
    
	//###############
    //Méthodes de jeu
	//###############
	
	//Méthode commune pour créer une nouvelle partie
    public void nouvellePartie(int largeurPlateau,int longueurPlateau, boolean ia){
        this.plateau = new PlateauPuzzle(largeurPlateau,longueurPlateau);
        creationPieceRandom();
		
		afficheJeu();
    }

    private void creationPieceRandom(){
        int largeur = 0;
        int longueur = 0;
        
        int max = (this.largeurPlateau + this.longueurPlateau) /4 ;
        if(max  < 3 )
            max = 3;
        else if (max > 5)
            max = 5;
        
        int minPiece = (this.largeurPlateau + this.longueurPlateau)/3;
        int maxPiece = (this.largeurPlateau + this.longueurPlateau)/2;
        int randPiece = new Random().nextInt(maxPiece-minPiece) + minPiece;
        
        ArrayList<String> pieceString = this.plateau.getPieceString();
        
        for(int i = 0; i <= randPiece; i++){

            String piece = pieceString.get(new Random().nextInt(pieceString.size()));
            
            if(piece.equals("PieceH")){
                largeur = rdmMin(3,max);
                longueur = rdmMin(3,max);
            }else if(piece.equals("PieceL")){
                largeur = rdmMin(2,max);
                longueur = rdmMin(2,max);
            }else if(piece.equals("PieceRectangle")){
                largeur = rdmMin(1,max-1);
                longueur = rdmMin(1,max-1);
            }else if(piece.equals("PieceT")){
                largeur = rdmMin(2,max);
                longueur = rdmMin(3,max);
            }
            this.plateau.newPiece(piece, largeur, longueur);
        }
    }
    
    private void jeu(){
        while(!this.endPlay){
            int choix = choixJeu();
            
            if(choix == 1)
                choixAjoutPiece();
            else if(choix == 2)
                choixDeplacementPiece();
            else if(choix == 3)
                choixSupprimerPiece();
            else if(choix == 4)
                choixRotationPieceDisponible();
            else if(choix == 5)
                choixRotationPiece();
            else if(choix == 6)
                sauvegarderPartie();
            else if(choix==7){
				if(!this.ia){
					this.endPlay = score();
					finDePartie();
				}else{
					this.endPlay = true;
				}
            }else if(choix==0)
                this.endPlay = true;
        }
        
    }
	
	//Affichage du meilleur plateau trouver par les fourmis
	private void jeuIa(){
		
		//Déplacement des pièces déjà sur le plateau si c'est une partie charger et donc déjà commencée
		for(int i = 0; i < this.plateau.getPiecePlacer().size(); i++){

			//Recherche d'une pièce déjà placer
			int pieceAJouer = -1;
			for(int j = 0; j < this.bestPlateau.getPiecePlacer().size(); j++){
					if( (this.plateau.getPiecePlacer().get(i).getClass().equals(this.bestPlateau.getPiecePlacer().get(j).getClass()) )
							&& (this.plateau.getPiecePlacer().get(i).getX() == (this.bestPlateau.getPiecePlacer().get(j).getX()) )
							&& (this.plateau.getPiecePlacer().get(i).getY() == (this.bestPlateau.getPiecePlacer().get(j).getY()) )
							){
						pieceAJouer = j;
					}
				}
			
			
			if(pieceAJouer != -1){
				boolean rotation = false;
				boolean placement = false;
				
				//Si la pièce a une rotation différente
				if(this.plateau.getPiecePlacer().get(i).getRotation() != this.bestPlateau.getPiecePlacer().get(pieceAJouer).getRotation()){
					rotation = true;
				}
				
				//Si la pièce a été déplacé
				if(this.plateau.getPiecePlacer().get(i).getCoo() != this.bestPlateau.getPiecePlacer().get(pieceAJouer).getCoo()){
					placement = true;
				}

				//Application des méthodes avec suppression de la pièce pour éviter de changer ses coordoonées si rotation
				
				if(rotation){
					ArrayList<Integer> cooPiece = selectPiece(this.plateau.getPiecePlacer().get(i).getCoo(), i);
					supprimerPiece(cooPiece);

					rotationPieceDisponible((this.plateau.getPieceAJouer().size()-1), this.bestPlateau.getPiecePlacer().get(pieceAJouer).getRotation());
				}
				
				if(placement){
					if(!rotation){// si n'a pas été dans la rotation, je supprime piece
						ArrayList<Integer> cooPiece = selectPiece(this.plateau.getPiecePlacer().get(i).getCoo(), i);
						supprimerPiece(cooPiece);
					}

					if(ajoutPiece((this.plateau.getPieceAJouer().size()-1), this.bestPlateau.getPiecePlacer().get(pieceAJouer).getCoo()))
						placement=false;
				}
			}
			
			
			
		}
			
			
		//Placement des pièces disponibles
		for(int i = 0; i < this.bestPlateau.getPiecePlacer().size(); i++){
			
			boolean pieceDejaPlacer = false;
			
			//Vérification si la pièce a déjà été placer ou non
			for(int j = 0; j < this.plateau.getPiecePlacer().size(); j++){
				if( (this.bestPlateau.getPiecePlacer().get(i).getCoo()).equals(this.plateau.getPiecePlacer().get(j).getCoo()) ){
					pieceDejaPlacer = true;
				}
			}
				
				
			if(!pieceDejaPlacer){
				
				//Selectionner la pièce a jouer
				int pieceAJouer = -1;
				for(int j = 0; j < this.plateau.getPieceAJouer().size(); j++){
						if( (this.bestPlateau.getPiecePlacer().get(i).getClass().equals(this.plateau.getPieceAJouer().get(j).getClass()) )
								&& (this.bestPlateau.getPiecePlacer().get(i).getX() == (this.plateau.getPieceAJouer().get(j).getX()) )
								&& (this.bestPlateau.getPiecePlacer().get(i).getY() == (this.plateau.getPieceAJouer().get(j).getY()) )
								){
							pieceAJouer = j;
						}
					}

				if(pieceAJouer != -1){

					//Vérification de la rotation
					if(this.bestPlateau.getPiecePlacer().get(i).getRotation() != this.plateau.getPieceAJouer().get(pieceAJouer).getRotation()){
						rotationPieceDisponible(pieceAJouer,  this.bestPlateau.getPiecePlacer().get(i).getRotation());
						ajoutPiece(pieceAJouer, this.bestPlateau.getPiecePlacer().get(i).getCoo());
					}
					//Sinon ajout de la pièce sans rotation
					else{
						ajoutPiece(pieceAJouer, this.bestPlateau.getPiecePlacer().get(i).getCoo());
					}
				}
			}	
		}
	}
	
	private ArrayList<Integer> selectPiece(ArrayList<Integer> cooPiece, int numPiece){
		boolean valide = false;
		int i=0;
		while(!valide){
			cooPiece.set(1, cooPiece.get(1)+i);
			valide = plateau.getPlateau().get(cooPiece) == plateau.getPiecePlacer().get(numPiece) ? true : false;
			i=+1;
		}
		return cooPiece;
	}
    
    private boolean score(){
        affiche("\nScore : "+this.plateau.getScore() + "pts");
        affiche("Voulez vous arretez la partie ? 0-Oui / 1-Non : ");
        return (this.joueurActuel.choix(0,1)==0);
    }
    
    private void finDePartie(){
        if(!this.ia){
            affiche("\nVoulez-vous sauvegarder votre score ? 0-Oui / 1-Non :");
            if(this.joueurActuel.choix(0,1)==0){
                pseudo();
                try{
                    sauvegardeScore.write(this);
                }
                catch(Exception e){
                    affiche("Impossible de sauvegarder le score");
                }
            }
            
        }else{
            this.pseudo = "Computer";
            try{
                    sauvegardeScore.write(this);
                }catch(Exception e){
                    affiche("Impossible de sauvegarder le score");
            }
            this.joueurActuel = new PlayJoueur();
        }
        
        affiche("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        affiche("[---- Merci d'avoir jouer ! ----]");
        affiche("[--------- A bientôt ! ---------]");
        affiche("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
    }
    
    private void pseudo(){
        if(pseudo != null){
            affiche("Votre pseudo est "+pseudo+". Voulez-vous le changer ? 0-Oui / 1-Non :");
            if(this.joueurActuel.choix(0,1)==0)
                this.pseudo = null;
        }
        if(pseudo == null){
            affiche("Inscrivez votre pseudo: ");
            Scanner pseudoScan = new Scanner(System.in);
            this.pseudo = pseudoScan.next();
        }
    }
    
	//#################
    //Méthodes de choix
	//#################
	
    private int choixJeu(){
        return this.joueurActuel.choixJeu(this.plateau);
    }
    
    private void choixAjoutPiece(){
		ArrayList<ArrayList<Integer>> choix = this.joueurActuel.choixAjout(this.largeurPlateau, this.longueurPlateau, this.plateau);

		boolean valide = false;
		if(choix.get(1) != null)//au cas où, si l'ia n'a aucune possibilité
			valide = ajoutPiece(choix.get(0).get(0), choix.get(1));
		
		if(valide){
			affiche("\n=-=-=-=-=-=-=-=-=-=-=-=-=");
			affiche("[---- Piece ajouter ----]");
			affiche("=-=-=-=-=-=-=-=-=-=-=-=-=\n");
		}else{
			affiche("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
			affiche("[---- Piece non ajouter par manque de place ----]");
			affiche("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
		}
    }

    private void choixSupprimerPiece(){
        affiche("Quel pièce voulez vous supprimer ? (Veuillez indiqué une de ses coordonnées en format 2,3)");
        
        ArrayList<Integer> choixCoo = this.joueurActuel.selectPiece(this.largeurPlateau,this.longueurPlateau,this.plateau);

        supprimerPiece(choixCoo);

        affiche("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        affiche("[---- Piece supprimer ----]");
        affiche("=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
    }

    private void choixDeplacementPiece(){
		ArrayList<ArrayList<Integer>> choix = this.joueurActuel.choixDeplacement(this.largeurPlateau, this.longueurPlateau, this.plateau);
		
		boolean valide = false;
		if(choix.get(1) != null)//au cas où, si l'ia n'a aucune possibilité
			valide = deplacementPiece(choix.get(0),choix.get(1));

        if(valide){
            affiche("\n=-=-=-=-=-=-=-=-=-=-=-=-=");
            affiche("[---- Piece déplacer ----]");
            affiche("=-=-=-=-=-=-=-=-=-=-=-=-=\n");
        }
        else{
            affiche("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            affiche("[---- Piece non déplacer par manque de place ----]");
            affiche("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
        }
    }
    
    private void choixRotationPiece(){
        /*if(!this.ia && explicationRot){
            explicationRotation();
        }
		*/
        affiche("Quelle pièce voulez vous effectuer une rotation ? (Veuillez indiqué une de ses coordonnées en format 2,3)");
        ArrayList<Integer> coo = this.joueurActuel.selectPiece(this.largeurPlateau,this.longueurPlateau,this.plateau);

        affiche("Quel rotation ? Rappel: Choix 0 à 3");
        int rotation = this.joueurActuel.choix(0,3);
		
        
        boolean valide = rotationPiece(coo,rotation);

        if(valide){
            affiche("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            affiche("[---- Rotation effectuer ----]");
            affiche("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
        }else{
            affiche("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            affiche("[---- Rotation non effectuer par manque de place ----]");
            affiche("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
        }
    }
    
    private void choixRotationPieceDisponible(){
            affiche("Que pièce voulez vous effectuer une rotation ? (Veuillez indiqué le numéro de la pièce)");
            int choixPiece = this.joueurActuel.choix(1, this.plateau.getPieceAJouer().size());
            affiche("Quel rotation ? Rappel: Choix 0 à 3");
            int rotation = this.joueurActuel.choix(0,3);
            
            rotationPieceDisponible(choixPiece-1, rotation);
			
			affiche("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
            affiche("[---- Rotation effectuer sur la piece numéro "+choixPiece+"----]");
            affiche("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
    }

    
	//#################
    // Méthodes commune
    //#################
    public boolean ajoutPiece(int piece, ArrayList<Integer> coordonnees){
        boolean actionValide = this.plateau.addPiece(this.plateau.getPieceAJouer().get(piece), coordonnees);
		afficheJeu();
				
		return actionValide;
    }
    
    public boolean supprimerPiece(ArrayList<Integer> coordonneesPiece){
        boolean actionValide = this.plateau.removePiece(this.plateau.getPiece(coordonneesPiece));
		afficheJeu();
		return actionValide;
    }
    
    public boolean deplacementPiece(ArrayList<Integer> anciennesCoordonnees, ArrayList<Integer> nouvellesCoordonnees){
        boolean actionValide = this.plateau.movePiece(this.plateau.getPiece(anciennesCoordonnees),nouvellesCoordonnees);
		afficheJeu();
		return actionValide;
    }
    
    public boolean rotationPiece(ArrayList<Integer> coordonneesPiece, int rotation){
        boolean actionValide = this.plateau.rotationPiece(this.plateau.getPiece(coordonneesPiece),rotation);
		afficheJeu();
		
		return actionValide;
    }
    
    public void rotationPieceDisponible(int piece, int rotation){
        this.plateau.rotationPieceDisponible(piece,rotation);
		afficheJeu();
    }
    
	//###################
    //Méthode d'affichage
	//###################
	
	private void afficheJeu(){
		if(this.affichageGraph && !this.fourmis){
			;//mise a jour de la vue du plateau et pieces dispo
		}else if(this.joueurActuel instanceof PlayJoueur && !this.affichageGraph){
			etatPlateau();
			printPiece();
		}else if(!this.affichageGraph && !this.fourmis && this.joueurActuel instanceof PlayIA){
			montreMessage = true;
			etatPlateau();
			printPiece();
			
			//attente afin de laisser le temps de prendre connaissance du nouveau plateau
			try{
				TimeUnit.SECONDS.sleep(2);
			}catch(Exception e){
				affiche("Attente non effectuer");
			}
			
			montreMessage = false;
		}
			
	}
    
    private void etatPlateau(){
        affiche("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        affiche("Voici le plateau:");
        affiche(""+this.plateau);
        
    }
    
    private void printPiece(){
        affiche("Voici vos pièce: ");
        for(int i = 0 ; i <= this.plateau.getPieceAJouer().size()-1; i++){
            affiche("Piece "+(i+1)+":");
            affiche(""+this.plateau.getPieceAJouer().get(i));
        }
    }
    
    private void affiche(String texte){
        if(montreMessage){
            System.out.println(texte);
        }
    }
    
	//###############################
    //Méthodes de gestion des parties
	//###############################
	
	private void sauvegarderPartie(){
		pseudo();
		sauvegarderLaPartie(fileJoueur);
	}

	//Méthode commune de sauvegarde
    public void sauvegarderLaPartie(String file){
        SauvegardeFichier sauvegarde = new SauvegardeFichier(this, file);
        try{
            sauvegarde.ecrire();
        }
        catch(Exception e){
            affiche("Impossible de sauvegarder");
        }
    }
	
	//Méthode commune de chargement de partie
	public void chargerPartie(){
		chargerLaPartie(fileJoueur);
		afficheJeu();
	}
	
    private void chargerLaPartie(String file){
        ChargerPartie charger = new ChargerPartie(this, file);
        try{
            charger.chargerSauvegarde();
        }
        catch(Exception e){
            affiche("Impossible de charger le fichier");
        }
    }
    
	//###############
    //Autres Méthodes
	//###############
    
    private int rdmMin(int min, int max){
        int rand = 0;
        while(rand < min)
            rand = new Random().nextInt(max+1);
        return rand;
    }
    
	//################
    //Getter et Setter
	//################
    
    public int getLargeur(){
        return this.largeurPlateau;
    }
    
    public int getLongueur(){
        return this.longueurPlateau;
    }
    
    public String getPseudo(){
        return this.pseudo;
    }
    
    public PlateauPuzzle getPlateau(){
        return this.plateau;
    }
    
    public boolean getExplicationRot (){
        return this.explicationRot;
    }
	
	public boolean getIa(){
		return this.ia;
	}
    
	public boolean getAfficheGraph(){
		return this.affichageGraph;
	}
	
    public void setLargeur(int largeur){
        this.largeurPlateau = largeur;
    }
    
    public void setLongueur(int longueur){
        this.longueurPlateau = longueur;
    }
    
    public void setPseudo(String pseudo){
        this.pseudo = pseudo;
    }
    
    public void setPlateau(PlateauPuzzle plateau){
        this.plateau = plateau;
    }
    
    public void setExplicationRot(boolean explication){
        this.explicationRot = explication;
    }
}
