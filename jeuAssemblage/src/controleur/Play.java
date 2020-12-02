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
				
				if(this.ia)
					this.montreMessage = false;
                
                this.endPlay=false;
				if(!this.ia)
					jeu();
				else
					playIa();

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
		this.joueurActuel = new PlayIA();
		this.montreMessage = false;
		this.fourmis = true;
		this.pseudo = "Computer";
		
		int scoreMin = -1;
		sauvegarderLaPartie(this.fileIa);
		
		PlateauPuzzle bestPlateau;
		
		for(int i = 0; i < 100 ; i++){
			this.endPlay=false;
			chargerLaPartie(this.fileIa);
			jeu();
			if(this.plateau.getScore() > scoreMin){
				scoreMin = this.plateau.getScore();
				bestPlateau = this.plateau;
			}
			System.out.println("fin - "+this.plateau.getScore());
		}
		this.fourmis = false;
		this.endPlay = true;
		this.stop = true;
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
            //System.out.println(this.plateau.getPieceAJouer().size());
			//etatPlateau();
			afficheJeu();
        }
        
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
    
    //Méthodes de choix
	
	
    private int choixJeu(){
        return this.joueurActuel.choixJeu(this.plateau);
    }
    
    private void choixAjoutPiece(){
		
        affiche("Quel pièce voulez vous ajouter ? (Veuillez indiqué le numéro de la pièce)");
        
        int choixPiece = this.joueurActuel.choix(1, this.plateau.getPieceAJouer().size());
        
		//affiche("piece ajout : " +this.plateau.getPieceAJouer().get(choixPiece-1));

		affiche("Indiquer les coordonnées où vous voulez placer la partie haut gauche de la pièce (Exemple: 2,3)");

		ArrayList<ArrayList<Integer>> listPossibilite = new ArrayList<ArrayList<Integer>>();
		if(this.ia){
			for (int i = 0; i < this.largeurPlateau; i++){
				for (int j = 0; j < this.longueurPlateau; j++){
					if(this.plateau.validePlacement(this.plateau.getPieceAJouer().get(choixPiece-1), new ArrayList<Integer>(Arrays.asList(i, j))))
						listPossibilite.add(new ArrayList<Integer>(Arrays.asList(i, j)));
					
				}
			}	
		}
		
		ArrayList<Integer> choixCoo = null;
		if(this.ia)
			choixCoo = listPossibilite.size() != 0 ? listPossibilite.get(this.joueurActuel.choix(1,listPossibilite.size())-1) : null;
		else
			choixCoo = this.joueurActuel.selectCoordonnees(this.largeurPlateau,this.longueurPlateau);

		boolean valide = false;
		if(choixCoo != null)
			valide = ajoutPiece(choixPiece, choixCoo);

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
        choixCoo.remove(2);
        supprimerPiece(choixCoo);

        affiche("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        affiche("[---- Piece supprimer ----]");
        affiche("=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
    }

    private void choixDeplacementPiece(){
					//etatPlateau();
        affiche("Que pièce voulez vous déplacer ? (Veuillez indiqué une de ses coordonnées en format 2,3)");
        ArrayList<Integer> choixCooPiece = this.joueurActuel.selectPiece(this.largeurPlateau,this.longueurPlateau,this.plateau);

        affiche("Indiquer les coordonnées où vous voulez placer la partie haut gauche de la pièce (Exemple: 2,3)");
		ArrayList<ArrayList<Integer>> listPossibilite = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> cooAvant = new ArrayList<Integer>();
		//System.out.println(this.ia);
		if(this.ia){

		int c = choixCooPiece.get(1);
		//System.out.println("choixCooPiece"+choixCooPiece);
		//System.out.println(this.plateau.getPlateau().get(new ArrayList<Integer>(Arrays.asList(choixCooPiece.get(0), choixCooPiece.get(1)))));

		choixCooPiece.remove(1);
		
		cooAvant = new ArrayList<Integer>(Arrays.asList(choixCooPiece.get(0), choixCooPiece.get(1))); //sauvegarde des ancienne coordonnées
		
		
		choixCooPiece.set(1, c);
		supprimerPiece(choixCooPiece); //supprime la pièce
		
		//System.out.println(" c cooAvant "+cooAvant+"c choixCooPiece"+choixCooPiece);
			for (int i = 0; i < this.largeurPlateau; i++){
				for (int j = 0; j < this.longueurPlateau; j++){
					//System.out.println(cooAvant +" i "+i +" j "+j+" this.largeurPlateau "+this.largeurPlateau+" this.longueurPlateau "+this.longueurPlateau);
					ArrayList<Integer> coo = new ArrayList<Integer>(Arrays.asList(i, j));
					//System.out.println(coo +" "+cooAvant);
					if(this.plateau.validePlacement(this.plateau.getPieceAJouer().get(this.plateau.getPieceAJouer().size()-1), coo) && (coo != cooAvant))
						listPossibilite.add(new ArrayList<Integer>(Arrays.asList(i, j)));
					
				}
			}	
		ajoutPiece(this.plateau.getPieceAJouer().size(), cooAvant);
		}
		//etatPlateau();
		
		//System.out.println("fin list");
		ArrayList<Integer> choixCoo = null;
		if(this.ia)
			choixCoo = listPossibilite.size() >= 1 ? (listPossibilite.get(this.joueurActuel.choix(1,listPossibilite.size())-1)) : null;
		else
			choixCoo = this.joueurActuel.selectCoordonnees(this.largeurPlateau,this.longueurPlateau);
		/*try{
					Thread.sleep(40);
					System.out.println();
				}catch(Exception e){

				}
		if(this.plateau.getPiece(choixCooPiece) == null || choixCoo == null){
			try{
					Thread.sleep(100);
					System.out.println("ATTTTTTENNNNNTTTTTTTTTIIIIIIIIIIIIOOOOOOOONNNNNNNNNNN");
				}catch(Exception e){

				}
		}*/
			
		boolean valide = false;
		if(choixCoo != null) // si aucune possibilité de déplacement
			valide = deplacementPiece(choixCooPiece,choixCoo);

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
        /*if(explicationRot){
            explicationRotation();
        }*/

        affiche("Quelle pièce voulez vous effectuer une rotation ? (Veuillez indiqué une de ses coordonnées en format 2,3)");
        ArrayList<Integer> coo = this.joueurActuel.selectPiece(this.largeurPlateau,this.longueurPlateau,this.plateau);
		coo.remove(2);
        affiche("Quel rotation ? Rappel: Choix 0 à 3");
        int rotation = this.joueurActuel.choix(0,3);
		
        
        boolean valide = rotationPiece(coo,rotation);
		//System.out.println("rotation est "+valide+" de la piece en coo "+coo);
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
        boolean actionValide = this.plateau.addPiece(this.plateau.getPieceAJouer().get(piece-1), coordonnees);
		afficheJeu();
		//System.out.println("aj "+actionValide);
		return actionValide;
    }
    
    public boolean supprimerPiece(ArrayList<Integer> coordonneesPiece){
        boolean actionValide = this.plateau.removePiece(this.plateau.getPiece(coordonneesPiece));
		afficheJeu();
		return actionValide;
    }
    
    public boolean deplacementPiece(ArrayList<Integer> anciennesCoordonnees, ArrayList<Integer> nouvellesCoordonnees){
		//System.out.println("dep "+anciennesCoordonnees);
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
		}else if(!this.affichageGraph && !this.fourmis){
			etatPlateau();
			printPiece();
		}else if(this.fourmis){
			//etatPlateau();
			//printPiece();
			try{
				//TimeUnit.SECONDS.sleep(2);
				//Thread.sleep(60);
			}catch(Exception e){
				affiche("pas de waait");
			}
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
