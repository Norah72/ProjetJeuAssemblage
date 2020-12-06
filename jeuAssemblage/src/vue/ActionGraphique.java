/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;
import controleur.Play;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
public class ActionGraphique implements ActionListener{
    
    private final Play play;
    private final InterfaceGraphique vue;
    private int choix = 0;
    private final MouseClicker eventSouris;

    /**
     * Constructeur des interaction sur la vue graphique
     * 
     * @param play
     * @param vue
     * @param eventSouris
     */
    public ActionGraphique(Play play, InterfaceGraphique vue, MouseClicker eventSouris){
        this.play = play;
        this.vue = vue;
        this.eventSouris = eventSouris;
    }
    
    /**
     * Placement de la pièce selectionné grace aux coordonées où le joueur a cliquer
     *
     */
    public void placementPieceVue(){
        this.vue.texteInformation("Sélectionnez une pièce");
        addPieceListener(this.vue.getListePieceForClick());
        while(!eventSouris.verif()){
            if(choix==5){                                               //On attend que le joueur sélectionne une piece
                break;
            }
            System.out.print("");
        }
        if(choix!=5){   //Si il ne clique pas sur ANNULER, alors on continue le processus
            removePieceListener(this.vue.getListePieceForClick());                                                              //On gère les Listener afin que le joueure ne puisse pas cliquer
            this.vue.visualisationRotation(this.play.getPlateau().getPieceAJouer().get(eventSouris.getPieceSelectionné()));     //sur une autre pièce, il peut seulement cliquer sur le plateau
            this.vue.texteInformation("Sélectionnez une case (partie haut gauche de la pièce)");
            addCaseListener(this.vue.getListeCaseForClick());
            while(!eventSouris.verif()){
                if(choix==5){
                    break;
                }                                                       //On attend que le joueur sélectionne une case sur la grille pour placer sa pièce
                System.out.print("");
            }
            if(choix!=5){
                removeCaseListener(this.vue.getListeCaseForClick());    //On supprime tout les Listener pour revenir au statut initiale
                int rotation=0;
                for(int i=0; i<4; i++){
                    if(this.vue.getListeRotation().get(i).isSelected()){ //On récupere la rotation selectionné par le joueur
                        rotation=i;
                    }    
                }
                this.play.getPlateau().getPieceAJouer().get(eventSouris.getPieceSelectionné()).createPiece(rotation);
                boolean res = this.play.ajoutPiece(eventSouris.getPieceSelectionné(), eventSouris.getCaseSelectionné()); // On place la pièce grâce au coordonées récuperé
                if(!res)
                    JOptionPane.showMessageDialog(this.vue,"PLacement impossible par manque de place");
            }
        }
        vue.getListeBouton().get(1).setText("PLACER");
        if(choix==5)
            this.vue.afficheGrille(); // Si le joueur annule on mouvement, on retourne à l'Etat du début
    }
    
    /**
     * Deplacement de la pièce grâce aux coordonées récuperé 
     *
     */
    public void deplacementPieceVue(){
	boolean ok = false;
        this.vue.texteInformation("Sélectionnez une pièce");
        addCaseListener(this.vue.getListeCaseForClick());
        ArrayList<Integer> pieceSelectionne = new ArrayList();
        ArrayList<Integer> deplacementSelectionne = new ArrayList();
        while(!ok){
            while(!this.eventSouris.verif()){   //Même fonctionnement que pour PlacementVue
                    System.out.print("");
                    if(choix==5){
                            break;
                    }
                    if(this.eventSouris.getValide()){ //On vérifie bien qu'une pièec a été récuperé
                        if(this.play.getPlateau().selectPiece(this.eventSouris.getCaseSelectionné())){
                                pieceSelectionne = this.play.getPlateau().getPiece(this.eventSouris.getCaseSelectionné()).getCoo();
                                ok = true;
                        }else{
                                this.vue.texteInformation("Il n'y a pas de pièce ici");
                        }
                    }
            }
            if(choix==5){
                break;
            }
        }
        if(ok && choix!=5){
            while(play.getPlateau().getPlateau().get(pieceSelectionne) == null){
		pieceSelectionne.set(1, pieceSelectionne.get(1)+1);
            }
            int err = (int) this.play.getPlateau().getPiece(pieceSelectionne).getRotation();
            this.vue.visualisationRotation(this.play.getPlateau().getPiece(pieceSelectionne));
            this.vue.texteInformation("Sélectionnez une case (partie haut gauche de la pièce)");
            while(!this.eventSouris.verif()){
                if(choix==5){
                    break;
                }
                System.out.print("");
                deplacementSelectionne = this.eventSouris.getCaseSelectionné();
            }
            if(choix!=5){
                int rotation=0;
                for(int i=0; i<4; i++){
                    if(this.vue.getListeRotation().get(i).isSelected()){
                        rotation=i;
                    }    
                }                                                                                                                   //Deplacement de la vue en 3 étape :
                this.play.supprimerPiece(pieceSelectionne);                                                                         // 1- Suppression de la pièce sélectionné
                this.play.rotationPieceDisponible((this.play.getPlateau().getPieceAJouer().size()-1), rotation);                    // 2- Rotation de la pièce en fontion du choix du joueur                    
                boolean res = this.play.ajoutPiece((this.play.getPlateau().getPieceAJouer().size()-1), deplacementSelectionne);     // 3- Ajout de la pièce aux coorodonées sélectionné
                if(!res){
                    this.play.rotationPieceDisponible((this.play.getPlateau().getPieceAJouer().size()-1), err);          //Si placment impossible, on place la pièce à son emplacement initial
                    this.play.ajoutPiece((this.play.getPlateau().getPieceAJouer().size()-1), pieceSelectionne);
                    JOptionPane.showMessageDialog(this.vue,"Deplacement impossible par manque de place");
                }
                removeCaseListener(this.vue.getListeCaseForClick());
            }
        }
	this.vue.getListeBouton().get(2).setText("DEPLACER");
        if(choix==5){
            this.vue.afficheGrille();
        }
    }

    /**
     * Suppresion de la pièce sélectionné sur la grille
     *
     */
    public void supprimerPieceVue(){
        boolean ok = false;
        this.vue.texteInformation("Sélectionnez une pièce");
        addCaseListener(this.vue.getListeCaseForClick());
        ArrayList<Integer> pieceSelectionne = new ArrayList();
        while(!ok){
            while(!this.eventSouris.verif()){   
                System.out.print("");                               //On procède de la même maniere que pour les autres méthodes
                if(choix==5){                                       //tout en gérant les listener
                    break;
                }
                if(this.eventSouris.getValide()){
                    if(this.play.getPlateau().selectPiece(this.eventSouris.getCaseSelectionné())){
                        pieceSelectionne = this.eventSouris.getCaseSelectionné();
                        ok = true;
                    }
                    else{
                        this.vue.texteInformation("Il n'y a pas de pièce ici");
                    }
                }
            }
            if(choix==5){
                break;
            }
        }
        if(choix!=5){
            this.play.supprimerPiece(pieceSelectionne);
            removeCaseListener(this.vue.getListeCaseForClick());
        }
        this.vue.getListeBouton().get(3).setText("SUPRIMER");
        if(choix==5)
            this.vue.afficheGrille();
		
    }
    /**
     * Enlève les Mouselistener de la grille
     * @param liste 
     */
    private void removeCaseListener(HashMap<ArrayList<Integer>,JPanel> liste){
        for(ArrayList<Integer> i : liste.keySet()){
            liste.get(i).removeMouseListener(eventSouris);
        }
    }
    /**
     * Ajoute les Mouselistener de la grille
     * @param liste 
     */
    private void addCaseListener(HashMap<ArrayList<Integer>,JPanel> liste){
        for(ArrayList<Integer> i : liste.keySet()){
            liste.get(i).addMouseListener(eventSouris);
        }
    }
    /**
     * Enlève les Mouselistener des pièces qui reste à placer
     * @param liste 
     */
    private void removePieceListener(ArrayList<JPanel> liste){
        for(int i=0 ; i < liste.size() ; i++)
                    liste.get(i).removeMouseListener(eventSouris);
    }
    /**
     * Ajoute les Mouselistener des pièces qui reste à placer
     * @param liste 
     */
    private void addPieceListener(ArrayList<JPanel> liste){
        for(int i=0 ; i < liste.size() ; i++)
                    liste.get(i).addMouseListener(eventSouris);
    }
    /**
     * Attribue une valeur au choix du joueur (choix 1 : PLACER - choix 2 : DEPLACER - choix 3 : SUPPRIMER - choix 4 : retour menu principal 
     *                                         choix 5 : ANNULER - choix 6 : charger partie - choix 9 : quitter le jeu)
     * @param i 
     */
    private void actionBouton(int i){
        if(!this.vue.actionEnCours()){                                  //On verifie qu'il n'y a aucune autre action  en cours
                this.vue.getListeBouton().get(i).setText("ANNULER");
                choix=i;
                this.play.clickNotify();
        }
        else if(this.vue.getListeBouton().get(i).getText()=="ANNULER"){ //sauf si il click sur le bouton ANNULER
                choix=5;
        }
        else{                                                           //sinon un message d'erreur apparait
            JOptionPane.showMessageDialog(this.vue,"Vous avez une action en cours");
        }
    }
    
    /**
     * Retourne le choix du joueur
     * @return choix du joueur
     */
    public int getChoix(){
        return this.choix;
    }
    
    @Override
    public synchronized void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if(source == this.vue.getListeBouton().get(0)){
            if(this.vue.getLigne().getSelectedIndex()!=0 && this.vue.getColonne().getSelectedIndex()!=0){
                this.play.setLargeur(this.vue.getLigne().getSelectedIndex()+4);         //On enregistre les dimension de la grille
                this.play.setLongueur(this.vue.getColonne().getSelectedIndex()+4);
                choix=1;
                this.play.clickNotify();
            }
            else {
                JOptionPane.showMessageDialog(this.vue,"Veuillez sélectionné un nombre de ligne et de colonne pour continuer !");
            }
        }
        if(source == this.vue.getListeBouton().get(1)){
            actionBouton(1);
        }
        if(source == this.vue.getListeBouton().get(2)){          //On apelle actionBouton pour envoyer le choix au controleur
            actionBouton(2);
        }
        if(source == this.vue.getListeBouton().get(3)){
            actionBouton(3);
        }
        if(source == this.vue.getListeBouton().get(4)){
            if(this.vue.actionEnCours()==false){
                if(this.vue.ouiNon("Voulez-vous sauvegarder avant de quitter ?", "Au revoir")==0){
                    this.play.setPseudo(this.vue.pseudo());
                    if(this.play.getPseudo()!=null)
                        this.play.sauvegarderLaPartie("src/file/partie/partie.txt");    //Gère si le joueur veut sauvegarder / quitter le jeu 
                }
                if(this.vue.ouiNon("Voulez-vous retourner au menu principal", "Une derniere ?")==0){
                    choix=4;
                }
                else{
                    System.exit(0);
                }
                this.play.clickNotify();
            }
        }
        if(source == this.vue.getListeBouton().get(5)){
            choix=6;
            this.play.clickNotify();
        }
        if(source == this.vue.getListeBouton().get(6)){
            this.vue.Explication();                     //Boouton explication dans le  menu principal
        }
        if(source == this.vue.getListeBouton().get(7)){ 
            this.play.clickNotify();                    //Boouton menu principale dans le tableau des scores
        }
        if(source == this.vue.getListeBouton().get(8)){
            this.play.clickNotify();                    //Boouton quitter dans le tableau des scores
            choix=9;
        }
    }     
}
