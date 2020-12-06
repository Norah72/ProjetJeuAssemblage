package controleur;

import modele.*;
import vue.*;
import file.*;

import java.util.*;
import java.util.Random;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
public class Play {
    //Attibuts
    
    private InterfacePlay joueurActuel;
    private final boolean affichageGraph;
    
    private PlateauPuzzle plateau;
    private PlateauPuzzle bestPlateau;
    private InterfaceGraphique vueGraph;
    private MouseClicker eventSouris;
    private ActionGraphique actionBouton;
    
    private boolean montreMessage = true;
    private boolean endPlay;
    private boolean afficheIa = true;
    
    private int largeurPlateau =5;
    private int longueurPlateau =5;
    private boolean ia = false;
    private boolean fourmis = false;
    private boolean explicationRot = true;
    
    private String pseudo = null;
    private final ScoreFile sauvegardeScore = new ScoreFile();
    
    private boolean stop = false;
	
    private final String fileIa = "src/file/partie/partieIa.txt";
    private final String fileJoueur = "src/file/partie/partie.txt";
   

    /**
     * Constructeur
     * @param affichageGraph présence InterfaceGraphique ou non 
     */
    
    public Play(boolean affichageGraph){
        this.affichageGraph = affichageGraph;
        this.joueurActuel = new PlayJoueur();
        if(this.affichageGraph){
            this.vueGraph = new InterfaceGraphique(plateau);
            this.eventSouris = new MouseClicker(vueGraph);
            this.actionBouton = new ActionGraphique(this,vueGraph,eventSouris);
            menuGraph();
        }else{
            menu();
        }
    }
    /**
     * affichage du menu
     */
    private void menu(){
        while(!this.stop){
				
            affiche("\n----- Menu -----");
            affiche("1- Nouvelle partie");
            affiche("2- Charger la dernière partie");
            affiche("3- Règle de jeu");
            affiche("4- Score de jeu");
            affiche("\n0- Quitter");                        //On analyse le choix du joueur et on lance different prog en fonction de ce qu'il veut
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
					nouvellePartie(this.largeurPlateau, this.longueurPlateau);
					
					affiche("Ce jeu vous convient-il ? 0-Oui / 1-Non : ");
					
					if(this.joueurActuel.choix(0,1)==0)
						reinitialiser = false;
				}
                
                this.endPlay=false;
				if(!this.ia){
					jeuConsole();
				}else{
					affiche("L'ordinateur va jouer");
					jeuIa();
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
					jeuIa();
				}else{				
					jeuConsole();
				}
				
            }else if (choix == 3){
                affiche("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
                affiche("Le but du jeu est qu'il y est le minimum d'aire entre les pièces.");
                affiche("Bien sûr, il faut que toutes les pièces disponible soit placer.");
                affiche("Vous pouvez calculer votre score que si vous avez posé toutes les pièces sur le plateau.");
                affiche("Vous pouvez réglé la difficultés en changeant le nombre de pièce en fonction de la grandeur du plateau. En effet, plus vous avez de pièces, plus la difficultés augmentera mais plus votre score sera grand.");
                affiche("Une option vous permet de faire jouer l'ordinateur. Si jamais vous êtes bloqué sur une partie, vous pouvez la sauvegarder et voir comment l'ordinateur joue a cette partie.\n");

                affiche("Plusieurs actions sont disponibles, vous pouvez placer, déplacer, supprimer ou encore, effectuer une rotation des pièces placer ou non placer.");
                affiche("Il y a 4 pièces différentes: ");

                PlateauPuzzle plateauRegle = new PlateauPuzzle(2,2);
                affiche(""+plateauRegle.createNewPiece(plateauRegle.getPieceString().get(0), 4, 3, 0)+"\n");
                affiche(""+plateauRegle.createNewPiece(plateauRegle.getPieceString().get(1), 3, 4, 0)+"\n");
                affiche(""+plateauRegle.createNewPiece(plateauRegle.getPieceString().get(2), 3, 4, 0)+"\n");
                affiche(""+plateauRegle.createNewPiece(plateauRegle.getPieceString().get(3), 3, 3, 0)+"\n");
                affiche("");

                affiche("Quand vous placer une pièce, les coordonnées sélectionner placeront la case en haut, et la plus a gauche, de la pièce.");
                affiche("Par exemple, si vous avez décider de placer une pièce à la case (0,1) , voici comment elle se place:");

                plateauRegle.addPiece(plateauRegle.createNewPiece(plateauRegle.getPieceString().get(1), 2, 2, 3), new ArrayList<Integer>(Arrays.asList(0,1)));
                affiche(plateauRegle+"\n");

                affiche("Bien sûr, vous pouvez sauvegarder votre partie pour la reprendre plus tard.");
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
	/**
         * menu Principal de la vue
         */
	private void menuGraph(){
            while(!this.endPlay){
                this.pseudo=null;
		try{
			synchronized(this) {
				this.vueGraph.start(this.actionBouton);
				for(int i=0 ; i < vueGraph.getListeBouton().size() ; i++)
					this.vueGraph.getListeBouton().get(i).addActionListener(actionBouton);
				wait();
			}
                   int compo = 0;
                   if(this.actionBouton.getChoix()==1){                 //On modifie l'affichage en fonction du bouton activé
                       nouvellePartie(largeurPlateau,longueurPlateau);
                   }
                   else if(this.actionBouton.getChoix()==6){
                       chargerPartie();
                       compo=1;
                   }   
		   while(compo==0){
			   compo = this.vueGraph.ouiNon("Voulez-vous changer de composition ?", "Que voulez-vous faire ?");
			   if(compo==0){
				   nouvellePartie(largeurPlateau,longueurPlateau);
			   }
		   }
			if(this.vueGraph.ouiNon("Voulez vous que l'ordinateur joue cette partie ?", "Que voulez-vous faire ?")==0){
				this.ia = true;
				jeuIa();
				jeuVue();
			}
			else{
				jeuVue();
			}
		}
		catch(Exception e){
			System.out.println("Impossible de charger la vue: "+e);
		}
		
		if(!this.endPlay){
			this.vueGraph.dispose();
			this.plateau = null;                                //On remet tout à 0 pour la pochaine partie
			this.vueGraph = new InterfaceGraphique(plateau);
			this.eventSouris = new MouseClicker(vueGraph);
			this.actionBouton = new ActionGraphique(this,vueGraph,eventSouris);
		}
            }
	}
	/**
         * Demande à l'utilisateur si il veut que l'ia joue
         * @return 
         */
	private boolean choixIa(){
		affiche("Voulez vous que l'ordinateur joue cette partie ? 0-Oui / 1-Non");
        return (joueurActuel.choix(0,1)) == 0;
	}
    
	//###########################
    //Méthodes de création partie
	//###########################

    /**
     * Creation d'une nouvelle partie
     * @param largeurPlateau largeur du Plateau
     * @param longueurPlateau longueur du Plateau
     */
    public void nouvellePartie(int largeurPlateau,int longueurPlateau){
        this.plateau = new PlateauPuzzle(largeurPlateau,longueurPlateau);
        creationPieceRandom();
	afficheJeu();
    }
    
    /**
     * Creation des pièces à jouer en fonction de la taille de la grille
     */
    private void creationPieceRandom(){
        int largeur = 0;
        int longueur = 0;
        int max = (this.largeurPlateau + this.longueurPlateau) /4 ;
        if(max  < 3 )
            max = 3;                //On gère le nombre de pièce en fonction de la taille de la grille
        else if (max > 5)
            max = 5;
        
        int minPiece = (this.largeurPlateau + this.longueurPlateau)/3;
        int maxPiece = (this.largeurPlateau + this.longueurPlateau)/2;
        int randPiece = new Random().nextInt(maxPiece-minPiece) + minPiece;
        
        ArrayList<String> pieceString = this.plateau.getPieceString();
        
        for(int i = 0; i <= randPiece; i++){

            String piece = pieceString.get(new Random().nextInt(pieceString.size()));
            
            if(piece.equals("PieceH")){              //On gère la taille de la pièce en fonction de la taille de la grille
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
	
	//#############################
	//Méthodes de lancement du jeu
	//#############################
    
    /**
     * Gère les actions en fonction du choix des joueurs
     * 
     */
    private void jeuConsole(){
        while(!this.endPlay){
                EnumAction choix = choixJeu();
                switch(choix)
                        {
                            case QUITTER :
                                    this.endPlay = true;
                                    break;
                            case PLACER :
                                    choixAjoutPiece();
                                    break;
                            case DEPLACER :
                                    choixDeplacementPiece();
                                    break;
                            case SUPPRIMER :
                                    choixSupprimerPiece();
                                    break;
                            case ROTATION_PIECEAJOUER :
                                    choixRotationPieceDisponible();
                                    break;
                            case ROTATION_PIECEPLACER :
                                    choixRotationPiece();
                                    break;
                            case SAUVEGARDER:
                                    sauvegarderPartie();
                                    break;
                            case FIN_DE_PARTIE :
                                    if(!this.ia){
                                            this.endPlay = score();
                                            finDePartie();
                                    }else{
                                            this.endPlay = true;
                                    }
                                    break;
                    }
            }
        }
	
    /**
     * Géneration du meilleur score selon l'ia
     * 
     */
    public void jeuIa(){
		affiche("\nChargement en cours...", true);
		this.joueurActuel = new PlayIA();
		this.montreMessage = false;
		this.fourmis = true;
		this.pseudo = "Computer";

		int scoreMin = -1;

		sauvegarderLaPartie(this.fileIa);

		ArrayList<String> barreChargement = new ArrayList<String>();
			barreChargement.add("\r|=-=-=-=-=-                                                   |");
			barreChargement.add("\r|=-=-=-=-=-=-=-=-=-=-                                         |");
			barreChargement.add("\r|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-                               |");
			barreChargement.add("\r|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-                     |");
			barreChargement.add("\r|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-           |");
			barreChargement.add("\r|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-|");
		
		int nbfourmis = 500;                                  //nombre de partie jouer par l'ia de maniere aléatoire
		int modulo = nbfourmis/(barreChargement.size()-1);
		
		if(this.affichageGraph) 
			this.vueGraph.barreChargement(0, nbfourmis);
		
		final long start = System.currentTimeMillis();
		for(int i = 0; i < nbfourmis ; i++){

			if(i!=0 && !this.affichageGraph){ //Barre de chargement en attendant la fin des fourmis
				if((i%modulo) == 0){
					System.out.print(barreChargement.get(i/modulo));
				}
			}
			
			if(this.affichageGraph)
				this.vueGraph.updateBar(i);
			
			this.endPlay=false;
			chargerLaPartie(this.fileIa); //On charge la meilleur partie de l'ia (grace à une sauvegarde temporaire

			jeuConsole();

			if(this.plateau.getScore() > scoreMin){
				scoreMin = this.plateau.getScore();
				try{
				this.bestPlateau = (PlateauPuzzle) ((PlateauPuzzle) this.plateau).clone();

				}catch(Exception e){
					affiche("Impossible de cloner"+e, true);
				}
			}
		}
		affiche(barreChargement.get(barreChargement.size()-1), true); 
		final long durationInMilliseconds = System.currentTimeMillis()-start;
		affiche("Temps d'execution: " + durationInMilliseconds + "ms", true);
		
		this.fourmis = false;
		
		chargerLaPartie(this.fileIa); // remet a zéro

		if(!this.affichageGraph)
			montreMessage = true;
		
		affiche("L'ordinateur va maintenant jouer la partie  sous vos yeux", true);
		
		try{
			TimeUnit.SECONDS.sleep(2); // Un décalage de 2 sec entre chaque action pour laissez le temps au joueur de comprendre
		}catch(Exception e){
			affiche("Attente non effectuer", true);
		}
		
		affichageJeuIa();

		affiche("Score obtenue : "+this.plateau.getScore(), true);

		this.endPlay = true;
		this.joueurActuel = new PlayJoueur();
		
		supprimerPartie(this.fileIa); //Supression de la sauvegarde temporaire
                this.ia = false;
	}
	/**
         * Gère les actions du joueur en fonction de son choix 
         * @throws IOException
         * @throws InterruptedException 
         */
	private void jeuVue() throws IOException, InterruptedException{
		boolean finTour = false;
		while(!finTour){
			if(this.plateau.getPieceAJouer().isEmpty()){
				int rep = this.vueGraph.ouiNon("Il n'y a plus de piece. \n Avez-vous fini?","C'est fini !");
				if(rep==0){
                                    if(pseudo==null)
                                            this.pseudo = this.vueGraph.pseudo();
                                    if(pseudo!=null){
                                            sauvegardeScore.write(this);
                                            sauvegardeScore.affiche();
                                            this.vueGraph.tableauScore(this.sauvegardeScore);
                                            synchronized(this) {
                                                    wait(); //Affichage des scores jusqu'au prochain bouton activé
                                            }
                                            if(this.actionBouton.getChoix()==9  ){
                                                    this.endPlay=true;
                                            }
                                            else{
                                                    this.endPlay = false;
                                            }
                                    }
                                    else{
                                            rep = this.vueGraph.ouiNon("Votre score ne sera pas enregistrer ! \n Etes-vous sur ?","ATTENTION");
                                    }
                                    if(rep==1){
                                            finTour = !finTour; //Si score non enregistrer retour au menu principale
                                    }
				finTour = !finTour;
				}
			}
			if(!finTour){
				synchronized(this) {
					wait();         //Attend que le joueur clique sur un bouton
				}
				if(this.actionBouton.getChoix()==1){
					this.actionBouton.placementPieceVue();
				}
				else if(this.actionBouton.getChoix()==2){
					this.actionBouton.deplacementPieceVue();
				}
				else if (this.actionBouton.getChoix()==3)
					this.actionBouton.supprimerPieceVue();
				else if (this.actionBouton.getChoix()==4)
					finTour = true;
			}
		}
	}
	
	//##########################
	//Méthodes de fin de parties
	//##########################
    
    /**
     * Affichage du score
     * @return 
     */
    private boolean score(){
        affiche("\nScore : "+this.plateau.getScore() + "pts");
        affiche("Voulez vous arretez la partie ? 0-Oui / 1-Non : ");
        return (this.joueurActuel.choix(0,1)==0);
    }
    /**
     * Affichage de fin de partie
     */
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
    /**
     * Sélection du pseudo
     */
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
    
    /**
     *
     * @return choix du joueur
     */
    private EnumAction choixJeu(){
        return this.joueurActuel.choixJeu(this.plateau);
    }
    /**
     * Ajout de la pièce en console
     */
    private void choixAjoutPiece(){
		ArrayList<ArrayList<Integer>> choix = this.joueurActuel.choixAjout(this.largeurPlateau, this.longueurPlateau, this.plateau);

		boolean valide = false;
		if(choix.get(1) != null)    //au cas où, si l'ia n'a aucune possibilité
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
    /**
     * Suppression de la piece dans la console
     */
    private void choixSupprimerPiece(){
        affiche("Quel pièce voulez vous supprimer ? (Veuillez indiqué une de ses coordonnées en format 2,3)");
        
        ArrayList<Integer> choixCoo = this.joueurActuel.selectPiece(this.largeurPlateau,this.longueurPlateau,this.plateau);

        supprimerPiece(choixCoo);

        affiche("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        affiche("[---- Piece supprimer ----]");
        affiche("=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
    }
    
    /**
     * deplacement de la piece dans la console
     */
    private void choixDeplacementPiece(){
		ArrayList<ArrayList<Integer>> choix = this.joueurActuel.choixDeplacement(this.largeurPlateau, this.longueurPlateau, this.plateau);
		
		boolean valide = false;
		if(choix.get(1) != null)    //au cas où, si l'ia n'a aucune possibilité
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
    /**
     * Rotation de la piece dans la console
     */
    private void choixRotationPiece(){
        if(!this.ia && explicationRot){
            explicationRotation();
        }
		
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
    
    /**
     * Affichage des rotations de la piece possible 
     */
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

    /**
     * @param piece pièce sélectionné
     * @param coordonnees coordonées de placement de la pièce
     * @return la piece peut etre placer ou non
     */
	
    public boolean ajoutPiece(int piece, ArrayList<Integer> coordonnees){
        boolean actionValide = this.plateau.addPiece(this.plateau.getPieceAJouer().get(piece), coordonnees);
        afficheJeu();

        return actionValide;
    }
    
    /**
     * @param coordonneesPiece coordonnees de la pièce
     * @return la piece peut etre supprimer ou non
     */
    public boolean supprimerPiece(ArrayList<Integer> coordonneesPiece){
        boolean actionValide = this.plateau.removePiece(this.plateau.getPiece(coordonneesPiece));
        afficheJeu();

        return actionValide;
    }
    
    /**
     * @param anciennesCoordonnees anciennes coordonnees de la pièce
     * @param nouvellesCoordonnees nouvelle coordonnees de la pièce
     * @return la piece peut etre déplacer ou non
     */
    public boolean deplacementPiece(ArrayList<Integer> anciennesCoordonnees, ArrayList<Integer> nouvellesCoordonnees){
        boolean actionValide = this.plateau.movePiece(this.plateau.getPiece(anciennesCoordonnees),nouvellesCoordonnees);
        afficheJeu();

        return actionValide;
    }
    
    /**
     * @param coordonneesPiece coordonnees de la piece
     * @param rotation rotation de la pièce
     * @return la piece peut etre tourner ou non
     */
    public boolean rotationPiece(ArrayList<Integer> coordonneesPiece, int rotation){
        boolean actionValide = this.plateau.rotationPiece(this.plateau.getPiece(coordonneesPiece),rotation);
        afficheJeu();

        return actionValide;
    }
    
    /**
     *
     * @param piece rotation actuel de la pièce
     * @param rotation futur rotation de la pièce
     */
    public void rotationPieceDisponible(int piece, int rotation){
        this.plateau.rotationPieceDisponible(piece,rotation);

        afficheJeu();
    }
    
    //###################
    //Méthode d'affichage
    //###################
	
    /**
     * Affiche l'explication de la rotation dans la console
     */
    private void explicationRotation(){
            affiche("Voulez vous des explication sur la rotation ? 0-Oui / 1-Non");
            int choix = -2;
            while(choix == -2)
                    choix = this.joueurActuel.choix(0,1);

            if(choix == 0){
                affiche("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
                affiche("Les rotations se font dans le sens horaires.");
                affiche("Il y a donc quatre prossibilité: du choix 0 au choix 3, comme suite:");
                for(int i = 0 ; i < 4; i++){
                        affiche("--"+i+"--");
                        affiche(""+this.plateau.createNewPiece(this.plateau.getPieceString().get(1), 4, 3, i));
                }

                affiche("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
            }
            affiche("Voulez vous avoir des explications la prochaine fois ? 0-Oui / 1-Non");

            if(this.joueurActuel.choix(0,1) == 1)
                explicationRot = false;
	}
    /**
     * Mise à jour de la grille et de la vue apres l'action du joueur
     */
    private void afficheJeu(){
        if(this.affichageGraph && !this.fourmis){
            this.vueGraph.chargerModele(this.plateau);
            this.vueGraph.afficheGrille();
                if(this.ia){
                        try{
                                TimeUnit.SECONDS.sleep(2);
                        }catch(InterruptedException e){
                                affiche("Attente non effectuer");
                        }
                }
        }else if(this.joueurActuel instanceof PlayJoueur && !this.affichageGraph){
                        etatJeu();
        }else if(!this.affichageGraph && !this.fourmis && this.joueurActuel instanceof PlayIA){
            if(this.afficheIa)
                    etatJeu();

            //attente afin de laisser le temps de prendre connaissance du nouveau plateau
            try{
                    TimeUnit.SECONDS.sleep(2);
            }catch(InterruptedException e){
                    affiche("Attente non effectuer");
            }
        }
    }
    /**
     * Affiche le plateau et les pièces à jouer
     */
    private void etatJeu(){
        etatPlateau();
        printPiece();
    }
    /**
     * Affiche le plateau
     */
    private void etatPlateau(){
        affiche("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        affiche("Voici le plateau:");
        affiche(""+this.plateau);
        
    }
    /**
     * Affiche les pièce à jouer
     */
    private void printPiece(){
        affiche("Voici vos pièce: ");
        for(int i = 0 ; i <= this.plateau.getPieceAJouer().size()-1; i++){
            affiche("Piece "+(i+1)+":");
            affiche(""+this.plateau.getPieceAJouer().get(i));
        }
    }
    /**
     * affiche par défaut le string 
     * @param texte 
     */
    private void affiche(String texte){
	affiche(texte, false);
    }
    /**
     * affichage differents pour l'ia 
     * @param texte
     * @param afficheIa 
     */
    private void affiche(String texte, boolean afficheIa){
        if((montreMessage || afficheIa) && !this.affichageGraph){
            System.out.println(texte);
        }
    }
    /**
     * Affichage du meilleur plateau trouver par les fourmis
     */
    private void affichageJeuIa(){

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
                                affiche("Supprime pièce aux coordonnées "+this.plateau.getPiece(cooPiece).getCoo(), true);
                                supprimerPiece(cooPiece);

                                affiche("Rotation dans la liste des pièces - pièce numéro "+(this.plateau.getPieceAJouer().size())+" - rotation "+this.bestPlateau.getPiecePlacer().get(pieceAJouer).getRotation(), true);
                                rotationPieceDisponible((this.plateau.getPieceAJouer().size()-1), this.bestPlateau.getPiecePlacer().get(pieceAJouer).getRotation());
                        }

                        if(placement){
                                if(!rotation){// si n'a pas été dans la rotation, je supprime piece
                                        ArrayList<Integer> cooPiece = selectPiece(this.plateau.getPiecePlacer().get(i).getCoo(), i);
                                        affiche("Supprime pièce aux coordonnées "+this.plateau.getPiece(cooPiece).getCoo(), true);
                                        supprimerPiece(cooPiece);
                                }

                                affiche("Ajoute pièce numéro "+(this.plateau.getPieceAJouer().size())+" aux coordonnées "+this.bestPlateau.getPiecePlacer().get(pieceAJouer).getCoo(), true);

                                ArrayList<Integer> coo = new ArrayList<Integer>(this.bestPlateau.getPiecePlacer().get(pieceAJouer).getCoo());
                                int posY = 0;
                                int xx = 0;
                                int yy = 0;
                                while(!((this.plateau.getPieceAJouer().get(this.plateau.getPieceAJouer().size()-1)).getGrid()[xx][yy+posY])){
                                        coo.set(1, (int) coo.get(1)+1);
                                        posY += 1;
                                }
                                afficheIa = true;
                                if(ajoutPiece((this.plateau.getPieceAJouer().size()-1), coo))
                                        placement=false;
                                afficheIa = false;
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
                                        affiche("Rotation dans la liste des pièces - pièce numéro "+(pieceAJouer+1)+" - rotation "+this.bestPlateau.getPiecePlacer().get(i).getRotation(), true);
                                        rotationPieceDisponible(pieceAJouer,  this.bestPlateau.getPiecePlacer().get(i).getRotation());

                                        affiche("Ajoute pièce numéro "+(pieceAJouer+1)+" aux coordonnées "+this.bestPlateau.getPiecePlacer().get(i).getCoo(),true);
                                        ArrayList<Integer> coo = new ArrayList<Integer>(this.bestPlateau.getPiecePlacer().get(i).getCoo());
                                        int posY = 0;
                                        int xx = 0;
                                        int yy = 0;
                                        while(!((this.plateau.getPieceAJouer().get(pieceAJouer)).getGrid()[xx][yy+posY])){
                                                coo.set(1, (int) coo.get(1)+1);
                                                posY += 1;
                                        }

                                        afficheIa = true;
                                        ajoutPiece(pieceAJouer, coo);
                                        afficheIa = false;
                                }
                                //Sinon ajout de la pièce sans rotation
                                else{
                                        affiche("Ajoute pièce numéro "+(pieceAJouer+1)+" aux coordonnées "+this.bestPlateau.getPiecePlacer().get(i).getCoo(), true);

                                        ArrayList<Integer> coo = new ArrayList<Integer>(this.bestPlateau.getPiecePlacer().get(i).getCoo());
                                        int posY = 0;
                                        int xx = 0;
                                        int yy = 0;
                                        while(!((this.plateau.getPieceAJouer().get(pieceAJouer)).getGrid()[xx][yy+posY])){
                                                coo.set(1, (int) coo.get(1)+1);
                                                posY += 1;
                                        }

                                        afficheIa = true;
                                        ajoutPiece(pieceAJouer, coo);
                                        afficheIa = false;
                                }
                        }
                }
        }
    }
    
    //###############################
    //Méthodes de gestion des fichiers
    //###############################
    
    /**
     * Sauvegarde de la partie
     */
    private void sauvegarderPartie(){
        pseudo();
        sauvegarderLaPartie(fileJoueur);
    }

    /**
     * accesseur de sauvagardePartie
     * @param file nom du fichier
     */
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

    /**
     * Chargement de la partie
     */
    public void chargerPartie(){
        chargerLaPartie(fileJoueur);
        afficheJeu();
    }
    
    /**
     * accesseur de chargePartie
     * @param file 
     */
    private void chargerLaPartie(String file){
        ChargerPartie charger = new ChargerPartie(this, file);
        try{
            charger.chargerSauvegarde();
        }
        catch(Exception e){
            affiche("Impossible de charger le fichier");
        }
    }
    /**
     * Suppression de la partie
     * @param file 
     */
    private void supprimerPartie(String file){
        DeleteFile delete = new DeleteFile(file);
        try{
            delete.supprimerFile();
        }
        catch(Exception e){
            affiche("Impossible de charger le fichier");
        }
    }
    
    //###############
    //Autres Méthodes
    //###############
    /**
     * Random utilisé pour la génération des pièces
     * @param min
     * @param max
     * @return 
     */
    private int rdmMin(int min, int max){
        int rand = 0;
        while(rand < min)
            rand = new Random().nextInt(max+1);
        return rand;
    }
    
    /**
     * Notifie les attentes 
     */
    public void clickNotify(){
        synchronized(this){
            notify();               //utilisé pour les autres class qui interagit avec le wait
        }
    }
    /**
     * Sélectionne la premiere coordonnées true de la pièce
     * @param cooPiece
     * @param numPiece
     * @return 
     */
    private ArrayList<Integer> selectPiece(ArrayList<Integer> cooPiece, int numPiece){
        boolean valide = false;
        int i=0;
        while(!valide){
                cooPiece.set(1, cooPiece.get(1)+i);
                valide = plateau.getPlateau().get(cooPiece) == plateau.getPiecePlacer().get(numPiece);
                i=+1;
        }
        return cooPiece;
    }
    
    //################
    //Getter et Setter
    //################

    /**
     *
     * @return largeur du plateau
     */
    
    public int getLargeur(){
        return this.largeurPlateau;
    }
    
    /**
     *
     * @return largeur du plateau
     */
    public int getLongueur(){
        return this.longueurPlateau;
    }
    
    /**
     *
     * @return pseudo du joueur
     */
    public String getPseudo(){
        return this.pseudo;
    }
    
    /**
     * 
     * @return plateau de jeu
     */
    public PlateauPuzzle getPlateau(){
        return this.plateau;
    }
    
    /**
     * @return le joueur a besoin des explications ou non
     */
    public boolean getExplicationRot (){
        return this.explicationRot;
    }
	
    /**
     *
     * @return ia active ou non
     */
    public boolean getIa(){
        return this.ia;
    }

    /**
     * 
     * @return vue graphiquee active ou non
     */
    public boolean getAfficheGraph(){
        return this.affichageGraph;
    }
	
    /**
     * Modifie la largueur du plateau
     * @param largeur largeur du plateau
     */
    public void setLargeur(int largeur){
        this.largeurPlateau = largeur;
    }
    
    /**
     * Modifie la longueur du plateau
     * @param longueur longueur du plateau
     */
    public void setLongueur(int longueur){
        this.longueurPlateau = longueur;
    }
    
    /**
     * Modifie le pseudo du joueur
     * @param pseudo pseudo du joueur
     */
    public void setPseudo(String pseudo){
        this.pseudo = pseudo;
    }
    
    /**
     * Modifie le plateau de jeu
     * @param plateau plateau de jeu
     */
    public void setPlateau(PlateauPuzzle plateau){
        this.plateau = plateau;
    }
    
    /**
     * Modifie les explications de la rotation
     * @param explication explications de la rotation
     */
    public void setExplicationRot(boolean explication){
        this.explicationRot = explication;
    }
}
