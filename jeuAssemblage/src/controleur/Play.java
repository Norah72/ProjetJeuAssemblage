package controleur;

import file.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import modele.*;
import vue.*;
import file.*;

public class Play {
    //Attibuts
    
    public InterfacePlay joueurActuel;
    public boolean affichageGraph;
    
    private PlateauPuzzle plateau;
    
    private boolean montreMessage;
    private boolean endPlay;
    
    private int largeurPlateau =5;
    private int longueurPlateau =5;
    private boolean ia = false;
    private boolean explicationRot = true;
    
    private String pseudo = null;
    private ScoreFile sauvegardeScore = new ScoreFile();
    
    private boolean stop = false;
    
     
    //Constructeur
    
    public Play(boolean affichageGraph){
        this.affichageGraph = affichageGraph;
        this.joueurActuel = new PlayJoueur();
        if(this.affichageGraph){
            affiche("Méthode d'affichage graphique à implémenter");
        }else{
            this.menu();
        }
    }
    
    private void menu(){
        this.montreMessage = true;
        while(!this.stop){
        
            affiche("\n----- Menu -----");
            affiche("1- Nouvelle partie");
            affiche("2- Charger la dernière partie");
            affiche("3- Règle de jeu");
            affiche("4- Score de jeu\n");

            affiche("Que voulez vous faire ?");
            int choix = this.joueurActuel.choix(1, 4);

            if (choix == 1){
                boolean reinitialiser = true;

                boolean ia = false;

                while(reinitialiser){

                    affiche("Voulez vous que l'ordinateur joue cette partie ? 0-Oui / 1-Non");
                    int choixIA = joueurActuel.choix(0,1);

                    this.ia = choixIA==0;

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
                nouvellePartie();
                this.endPlay=false;
                jeu();

            }else if (choix == 2){
                chargerPartie();
                System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                System.out.println("[---- Content de vous revoir "+this.pseudo+" ! ----]");
                System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
                etatPlateau();
                jeu();
            }else if (choix == 3){
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
            }else if (choix == 4){
                try{
                    sauvegardeScore.affiche();
                }catch(Exception e){

                }
            }
        }
    }
    
    //Méthodes de jeu
    
    public void nouvellePartie(){
        if(this.ia){
            this.plateau = new PlateauPuzzle(this.largeurPlateau,this.longueurPlateau);
            creationPieceRandom();
        }else{
            boolean reinitialiser = true;
            while(reinitialiser){
                this.plateau = new PlateauPuzzle(this.largeurPlateau,this.longueurPlateau);
                creationPieceRandom();
                etatPlateau();
                printPiece();
                
                affiche("\nCette configuraion vous convient-elle ? 0-Oui / 1-Non : ");
                if(this.joueurActuel.choix(0,1)==0)
                        reinitialiser = false;
            }
        }
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
        if(ia){
            this.montreMessage = false;
            this.joueurActuel = new PlayIA();
        }
        while(!this.endPlay){
            this.etatPlateau();
            this.printPiece();
            
            int choix = choixJeu();
            
            if(choix == 1)
                choixAjoutPiece();
            else if(choix == 2)
                choixDeplacementPiece();
            else if(choix == 3)
                choixSupprimerPiece();
            else if(choix == 4)
                choixRotationPiece();
            else if(choix == 5)
                sauvegarderPartie();
            else if(choix==6)
                this.endPlay = score();
                
        }
        finDePartie();
    }
    
    private boolean score(){
        System.out.println("\nScore : "+this.plateau.getScore() + "pts");
        affiche("Voulez vous arretez la partie ? 0-Oui / 1-Non : ");
        return (this.joueurActuel.choix(0,1)==0);
    }
    
    private void finDePartie(){
        if(!ia){
            pseudo();
            affiche("\nVoulez-vous sauvegarder votre score ? 0-Oui / 1-Non :");
            if(this.joueurActuel.choix(0,1)==0){
                try{
                    sauvegardeScore.write(this);
                }
                catch(Exception e){
                    System.out.println("Impossible de sauvegarder le score");
                }
            }
            
        }else{
            this.pseudo = "Computer";
            try{
                    sauvegardeScore.write(this);
                }catch(Exception e){
                    System.out.println("Impossible de sauvegarder le score");
            }
        }
        
        System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("[---- Merci d'avoir jouer ! ----]");
        System.out.println("[--------- A bientôt ! ---------]");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
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
        int nbrChoix = (this.joueurActuel instanceof PlayIA ? 1 : 2);;
		
        if(!this.plateau.getPiecePlacer().isEmpty())
            nbrChoix = (this.joueurActuel instanceof PlayIA ? 4 : 5);

        if(this.plateau.getPieceAJouer().isEmpty())
            nbrChoix = (this.joueurActuel instanceof PlayIA ? 5 : 6);
        
        affiche("1- Placer une pièce");
        if(nbrChoix >= 5){
            affiche("2- Déplacer une pièce");
            affiche("3- Supprimer une pièce");
            affiche("4- Rotation d'une pièce");
            affiche("5- Sauvegarder la partie");
        }
        if(nbrChoix == 6){
            affiche("6- Score/Fin");
        }
        else if(nbrChoix == 2){
            affiche("2- Sauvegarder la partie");
        }
        
        int choix = this.joueurActuel.choix(1,nbrChoix);
        
        if(this.plateau.getPiecePlacer().isEmpty() && choix == 2){
            choix = 5;
        }
        if(this.joueurActuel instanceof PlayIA && choix == 5){
            choix = 6;
        }
        return choix;
        
        
    }
    
    private void choixAjoutPiece(){
        affiche("Quel pièce voulez vous ajouter ? (Veuillez indiqué le numéro de la pièce)");
        
        int choixPiece = this.joueurActuel.choix(1, this.plateau.getPieceAJouer().size());
        
        affiche("Indiquer les coordonnées où vous voulez placer la partie haut gauche de la pièce (Exemple: 2,3)");
        
        ArrayList<Integer> choixCoo = this.joueurActuel.selectCoordonnees(this.largeurPlateau,this.longueurPlateau);
        
        boolean valide = ajoutPiece(choixPiece, choixCoo);
        
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
        
        ArrayList<Integer> choixCoo = this.joueurActuel.selectCoordonnees(this.largeurPlateau,this.longueurPlateau);
        
        boolean valide = supprimerPiece(choixCoo);
        
        if(valide){
            affiche("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            affiche("[---- Piece supprimer ----]");
            affiche("=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
        }else{
            affiche("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            affiche("[---- Il n'y a pas de pièce ici ----]");
            affiche("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
        }
    }

    private void choixDeplacementPiece(){
        affiche("Que pièce voulez vous déplacer ? (Veuillez indiqué une de ses coordonnées en format 2,3)");
        ArrayList<Integer> choixCooPiece = this.joueurActuel.selectCoordonnees(this.largeurPlateau,this.longueurPlateau);

        affiche("Indiquer les coordonnées où vous voulez placer la partie haut gauche de la pièce (Exemple: 2,3)");
        ArrayList<Integer> choixCoo = this.joueurActuel.selectCoordonnees(this.largeurPlateau,this.longueurPlateau);
        
        boolean valide = deplacementPiece(choixCooPiece,choixCoo);
        
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
        ArrayList<Integer> coo = this.joueurActuel.selectCoordonnees(this.largeurPlateau,this.longueurPlateau);

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
    
    // Méthodes sur plateau
    
    public boolean ajoutPiece(int piece, ArrayList<Integer> coordonnees){
        return this.plateau.addPiece(this.plateau.getPieceAJouer().get(piece-1), coordonnees);
    }
    
    public boolean supprimerPiece(ArrayList<Integer> coordonneesPiece){
        return this.plateau.removePiece(this.plateau.getPiece(coordonneesPiece));
    }
    
    public boolean deplacementPiece(ArrayList<Integer> anciennesCoordonnees, ArrayList<Integer> nouvellesCoordonnees){
        return this.plateau.movePiece(this.plateau.getPiece(anciennesCoordonnees),nouvellesCoordonnees);
    }
    
    public boolean rotationPiece(ArrayList<Integer> coordonneesPiece, int rotation){
        return this.plateau.rotationPiece(this.plateau.getPiece(coordonneesPiece),rotation);
    }
    
    //Méthode d'affichage
    
    private void etatPlateau(){
        System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("Voici le plateau:");
        System.out.println(this.plateau);
        
    }
    
    private void printPiece(){
        System.out.println("Voici vos pièce: ");
        for(int i = 0 ; i <= this.plateau.getPieceAJouer().size()-1; i++){
                System.out.println("Piece "+(i+1)+":");
                System.out.println(this.plateau.getPieceAJouer().get(i));
        }
    }
    
    private void affiche(String texte){
        if(montreMessage){
            System.out.println(texte);
        }
    }
    
    //Méthodes de gestion des parties
    
    private void sauvegarderPartie(){
        SauvegardeFichier sauvegarde = new SauvegardeFichier(this);
        try{
            sauvegarde.ecrire();
        }
        catch(Exception e){
            System.out.println("Impossible de sauvegarder");
        }
    }
	
    private void chargerPartie(){
        ChargerPartie charger = new ChargerPartie(this);
        try{
            charger.chargerSauvegarde();
        }
        catch(Exception e){
            System.out.println("Impossible de charger le fichier");
        }
    }
    
    //Autres Méthodes
    
    private int rdmMin(int min, int max){
        int rand = 0;
        while(rand < min)
            rand = new Random().nextInt(max+1);
        return rand;
    }
    
    //Getter et Setter
    
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
