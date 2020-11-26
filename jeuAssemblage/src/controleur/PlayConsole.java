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

    private String pseudo = null;
    private ScoreFile sauvegardeScore = new ScoreFile();
    private InterfaceGraphique vue;
    private MouseClicker laPetiteSouris;
    private int choix;

    public PlayConsole(InterfaceGraphique vue, PlateauPuzzle plateauConsole){
            this.vue = vue;
            this.plateauConsole = plateauConsole;
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
                    System.out.println("1- Vue Console");
                    System.out.println("2- Vue Graphique");
                    int start = choixValide(1, 4, "Que voulez vous faire ?");
                    if(start == 1){
                            while(!this.end){
                                    System.out.println();
                                    System.out.println("----- Menu -----");
                                    System.out.println("1- Nouvelle partie");
                                    System.out.println("2- Charger la dernière partie");
                                    System.out.println("3- Règle de jeu");
                                    System.out.println("4- Score de jeu\n");
                                    int choix = choixValide(1, 4, "Que voulez vous faire ?");

                                            if(choix == 1){
                                                    while(reinitialiser){
                                                            initialisationPlateau();
                                                            creationPieceRandom();
                                                            etatPlateau();
                                                            if(choixYesNo("Cette configuraion vous convient-elle ?"))
                                                                    reinitialiser = false;
                                            }

                                            System.out.println();
                                            play();
                                    }

                                    else if(choix == 2){
                                            chargerPartie();
                                            System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                                            System.out.println("[---- Content de vous revoir "+this.pseudo+" ! ----]");
                                            System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
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
                    }
                    if(start == 2){
                            playVue();
                    }
            }
    }


//######## Jouer ########
    public void play(){		

            while(!this.end){
                    choix = choix();
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
                                    pseudo();
                                    sauvegarderPartie();
                                    this.stop = choixYesNo("Voulez vous arretez la partie ?");
                                    if(stop)
                                            this.end = true;
                            }
                            else if(choix==6){
                                    this.end = score();
                            }
                    }
                    if(!end)
                            etatPlateau();
            }
            finDePartie();
    }

    private void playVue(){
        boolean reinitialiser = true;
        try{
            synchronized (this) {
                this.vue.start(this);
                for(int i=0 ; i < vue.getListeBouton().size() ; i++)
                    this.vue.getListeBouton().get(i).addActionListener(this);
                wait();
            }
            initialisationPlateau();
            creationPieceRandom();
            etatPlateau();
            this.vue.afficheGrille();
            laPetiteSouris = new MouseClicker(vue);
            while(reinitialiser){
                synchronized (this){
                    wait();
                }
                if(choix==1)
                    placementPieceVue();
                else if(choix==2)
                    deplacementPieceVue();
                else if (choix==3)
                    supprimerPieceVue();
                else if (choix==4){
                    System.out.print("test1");
                    rotationPieceVue();
                }
            }
        }
        catch(Exception e){
                System.out.println("Impossible de charger la vue: "+e);
            }
    }

    private void placementPieceVue(){
        this.vue.texteInformation("Sélectionnez une pièce");
        addPieceListener(this.vue.getListePieceForClick());
        while(!laPetiteSouris.verif()){
            System.out.print("");
                    /*voila voila voila... on attend... que MONSIEUR daigne appuyé... car sinon ca va etre long... TRES LONG!!!! DEPECHE PTN!!                                                                                                                                                              coucou tu m'as vu mi homo <3 */
        }
        laPetiteSouris.setVerif(false);
        removePieceListener(this.vue.getListePieceForClick());
        this.vue.visualisationRotation(this.plateauConsole.getPieceAJouer().get(laPetiteSouris.getPieceSelectionné()));
        this.vue.texteInformation("Sélectionnez une case (partie haut gauche de la pièce)");
        addCaseListener(this.vue.getListeCaseForClick());
        while(!laPetiteSouris.verif()){
            System.out.print("");
                    /*et encore.... ca devient relou la par contre... ECOUTE SI T'ES NUL TU POSES AU PIF ET TU FAIS PAS CHIER!!                                                                                                                                                                             je suis toujours là mi homo ;) */
        }
        laPetiteSouris.setVerif(false);
        removeCaseListener(this.vue.getListeCaseForClick());
	int test=0;
        for(int i=0; i<4; i++){
            if(this.vue.getListeRotation().get(i).isSelected()){
                test=i;
            }    
        }
        this.plateauConsole.getPieceAJouer().get(laPetiteSouris.getPieceSelectionné()).createPiece(test);
        this.plateauConsole.addPiece(this.plateauConsole.getPieceAJouer().get(laPetiteSouris.getPieceSelectionné()), laPetiteSouris.getCaseSelectionné());
        System.out.println(this.plateauConsole);
        this.vue.setModele();
    }

    private void deplacementPieceVue(){
        this.vue.texteInformation("Sélectionnez une pièce");
        addCaseListener(this.vue.getListeCaseForClick());
        ArrayList<Integer> pieceSelectionne = new ArrayList();
        ArrayList<Integer> deplacementSelectionne = new ArrayList();
        while(!laPetiteSouris.verif()){
            if(this.plateauConsole.selectPiece(laPetiteSouris.getCaseSelectionné()))
                pieceSelectionne = laPetiteSouris.getCaseSelectionné();
            else
                this.laPetiteSouris.setVerif(false);
        }
        this.vue.texteInformation("Sélectionnez une case (partie haut gauche de la pièce)");
        laPetiteSouris.setVerif(false);                                                     
        while(!laPetiteSouris.verif()){
            System.out.print("");
            deplacementSelectionne = laPetiteSouris.getCaseSelectionné();
        }
        laPetiteSouris.setVerif(false);
        this.plateauConsole.movePiece(this.plateauConsole.getPiece(pieceSelectionne), deplacementSelectionne);
        System.out.println(this.plateauConsole);
        removeCaseListener(this.vue.getListeCaseForClick());
        this.vue.setModele();
    }

    private void supprimerPieceVue(){
        this.vue.texteInformation("Sélectionnez une pièce");
        addCaseListener(this.vue.getListeCaseForClick());
        ArrayList<Integer> pieceSelectionne = new ArrayList();
        while(!laPetiteSouris.verif()){
            if(this.plateauConsole.selectPiece(laPetiteSouris.getCaseSelectionné()))
                pieceSelectionne = laPetiteSouris.getCaseSelectionné();
            else
                this.laPetiteSouris.setVerif(false);
        }
        laPetiteSouris.setVerif(false);
        this.plateauConsole.removePiece(this.plateauConsole.getPiece(pieceSelectionne));
        System.out.println(this.plateauConsole);
        removeCaseListener(this.vue.getListeCaseForClick());
        this.vue.setModele();
    }

    private void rotationPieceVue(){
        this.vue.texteInformation("Sélectionnez une pièce");
        addCaseListener(this.vue.getListeCaseForClick());
        ArrayList<Integer> pieceSelectionne = new ArrayList();
        while(!laPetiteSouris.verif()){
            if(this.plateauConsole.selectPiece(laPetiteSouris.getCaseSelectionné()))
                pieceSelectionne = laPetiteSouris.getCaseSelectionné();
            else
                this.laPetiteSouris.setVerif(false);
        }
        /*this.vue.visualisationRotation(this.plateauConsole.getPiece(pieceSelectionne));
        int test=0;
        for(int i=0; i<4; i++){
            if(this.vue.getListeRotation().get(i).isSelected()){
                test=i;
            }    
        }*/
        laPetiteSouris.setVerif(false);
        this.plateauConsole.rotationPiece(this.plateauConsole.getPiece(pieceSelectionne), (this.plateauConsole.getPiece(pieceSelectionne).getRotation()+1)%4);
        System.out.println(this.plateauConsole);
        this.vue.setModele();
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
            if(this.largeurPlateauX == 0 && this.longueurPlateauY == 0){
                System.out.println("Veuillez entrer la grandeur du plateau au niveau largeur: ");
                this.largeurPlateauX = choixValide(5,20,"Le nombre doit être au minimum de 5 et au maximum de 20");
                System.out.println("Veuillez entrer la grandeur du plateau au niveau longueur: ");
                this.longueurPlateauY = choixValide(5,20,"Le nombre doit être au minimum de 5 et au maximum de 20");
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

//######## Méthode de jeu ########		

    private void ajoutePiece(){	
            System.out.println("Que pièce voulez vous ajouter ? (Veuillez indiqué le numéro de la pièce)");
            int choixPiece = choixValide(1, this.plateauConsole.getPieceAJouer().size(),"Cette pièce n'existe pas");

            System.out.println("Indiquer les coordonnées où vous voulez placer la partie haut gauche de la pièce (Exemple: 2,3)");
            if(this.plateauConsole.addPiece(this.plateauConsole.getPieceAJouer().get(choixPiece-1), valideCoordonnees())){
                    //this.piecePlacer.add(this.plateauConsole.getPieceAJouer().get(choixPiece-1));
                    //this.plateauConsole.getPieceAJouer().remove(choixPiece-1);
                    System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=");
                    System.out.println("[---- Piece ajouter ----]");
                    System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=\n");
            }else{
        System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                    System.out.println("[---- Piece non ajouter par manque de place ----]");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
            }
    }

    private void deplacementPiece(){

            System.out.println("Que pièce voulez vous déplacer ? (Veuillez indiqué une de ses coordonnées en format 2,3)");
            ArrayList coo = selectPieceValide();

            System.out.println("Indiquer les coordonnées où vous voulez placer la partie haut gauche de la pièce (Exemple: 2,3)");
            if(this.plateauConsole.movePiece(this.plateauConsole.getPiece(coo),valideCoordonnees())){
                    System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=");
                    System.out.println("[---- Piece déplacer ----]");
                    System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=\n");
            }
            else{
                    System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                    System.out.println("[---- Piece non déplacer par manque de place ----]");
                    System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
            }

    }

    private void supprimerPiece(){
            System.out.println("Que pièce voulez vous supprimer ? (Veuillez indiqué une de ses coordonnées en format 2,3)");
            ArrayList coo = selectPieceValide();
            this.plateauConsole.removePiece(this.plateauConsole.getPiece(coo));
            //this.plateauConsole.setPieceAJouer();pieceAJouer.add(pieceASupprimer);
            //this.piecePlacer.remove(pieceASupprimer);
    System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            System.out.println("[---- Piece supprimer ----] ");
    System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
    }


    private void rotationPiece(){
            if(explicationRot){
                    explicationRotation();
            }

            System.out.println("Quelle pièce voulez vous effectuer une rotation ? (Veuillez indiqué une de ses coordonnées en format 2,3)");
            ArrayList coo = selectPieceValide();

            System.out.println("Quel rotation ? Rappel: Choix 0 à 3");
            if(this.plateauConsole.rotationPiece(this.plateauConsole.getPiece(coo), choixValide(0,3,"/!\\ Choix non accepter, vous devez choisir une valeur entre 0 et 3 /!\\"))){
                    System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                    System.out.println("[---- Rotation effectuer ----]");
                    System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
    }else{
                    System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                    System.out.println("[---- Rotation non effectuer par manque de place ----]");
                    System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
    }
    }

//######## Regle ########
    private void explicationRotation(){
            if(choixYesNo("Explication rotation ?")){
                    System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
                    System.out.println("Les rotations se font dans le sens horaires.");
                    System.out.println("Il y a donc quatre prossibilité: du choix 0 au choix 3, comme suite:");
                    for(int i = 0 ; i < 4; i++){
                            System.out.println("--"+i+"--");
                            System.out.println(this.plateauConsole.createNewPiece(this.plateauConsole.getPieceString().get(1), 4, 3, i));
                    }
                    System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
            }
            if(!choixYesNo("Voulez vous avoir des explications la prochaine fois ?"))
                    explicationRot = false;

    }

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


//######## Fin/quitter jeu ########
    private boolean score(){
            System.out.println();
            System.out.println("Votre score : "+this.plateauConsole.getScore() + "pts");
            return choixYesNo("Voulez vous arretez la partie ?");
    }

    private void pseudo(){
            if(pseudo != null){
                    if(choixYesNo("Votre pseudo : "+pseudo+" Voulez vous le changer ?"))
                            this.pseudo = null;
            }
            if(pseudo == null){
                    System.out.println("Inscrivez votre pseudo: ");
                    Scanner pseudoScan = new Scanner(System.in);
                    this.pseudo = pseudoScan.next();
            }
    }

    private void finDePartie(){
    System.out.println();
            if(!stop){
                    if(choixYesNo("Voulez vous sauvegardez votre score ?")){
                            pseudo();

                            try{
                                    sauvegardeScore.write(this);
                            }
                            catch(Exception e){
                                    System.out.println("Impossible de sauvegarder le score");
                            }
                    }
            }
            System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            System.out.println("[---- Merci d'avoir jouer ! ----]");
            if(stop)
                    System.out.println("[--------- A bientôt ! ---------]");
            System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
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
                    choix = 5;

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
                choix=1;
                synchronized(this){
                    notify();
                }
            }
            if(source == vue.getListeBouton().get(2)){
                choix=2;
                synchronized(this){
                    notify();
                }
            }
            if(source == vue.getListeBouton().get(3)){
                choix=3;
                synchronized(this){
                    notify();
                }
            }
            if(source == vue.getListeBouton().get(4)){
                choix=4;
                synchronized(this){
                    notify();
                }
            }
}
}