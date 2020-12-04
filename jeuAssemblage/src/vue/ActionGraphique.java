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
 * @author leovi
 */
public class ActionGraphique implements ActionListener{
    
    private Play play;
    private InterfaceGraphique vue;
    private int choix = 0;
    private MouseClicker eventSouris;
    private Object wait = null;
    
    public ActionGraphique(Play play, InterfaceGraphique vue, MouseClicker eventSouris){
        this.play = play;
        this.vue = vue;
        this.eventSouris = eventSouris;
    }
    
    public void placementPieceVue(){
        this.vue.texteInformation("Sélectionnez une pièce");
        addPieceListener(this.vue.getListePieceForClick());
        while(!eventSouris.verif()){
            if(choix==5){
                break;
            }
            System.out.print("");
                    /*voila voila voila... on attend... que MONSIEUR daigne appuyé... car sinon ca va etre long... TRES LONG!!!! DEPECHE PTN!!                                                                                                                                                              coucou tu m'as vu mi homo <3 */
        }
        if(choix!=5){
            removePieceListener(this.vue.getListePieceForClick());
            System.out.println("test");
            this.vue.visualisationRotation(this.play.getPlateau().getPieceAJouer().get(eventSouris.getPieceSelectionné()));
            System.out.println("test1");
            this.vue.texteInformation("Sélectionnez une case (partie haut gauche de la pièce)");
            System.out.println("test2");
            addCaseListener(this.vue.getListeCaseForClick());
            System.out.println("test3");
            System.out.println(eventSouris.verif());
            while(!eventSouris.verif()){
                if(choix==5){
                    break;
                }
                System.out.print("");
                        /*et encore.... ca devient relou la par contre... ECOUTE SI T'ES NUL TU POSES AU PIF ET TU FAIS PAS CHIER!!                                                                                                                                                                             je suis toujours là mi homo ;) */
            }
            if(choix!=5){
                removeCaseListener(this.vue.getListeCaseForClick());
                int test=0;
                for(int i=0; i<4; i++){
                    if(this.vue.getListeRotation().get(i).isSelected()){
                        test=i;
                    }    
                }
                this.play.getPlateau().getPieceAJouer().get(eventSouris.getPieceSelectionné()).createPiece(test);
                boolean res = this.play.ajoutPiece(eventSouris.getPieceSelectionné(), eventSouris.getCaseSelectionné());
                if(!res)
                    JOptionPane.showMessageDialog(this.vue,"PLacement mpossible par anque de place");
            }
        }
        vue.getListeBouton().get(1).setText("PLACER");
        if(choix==5)
            this.vue.afficheGrille();
    }
    
    
    public void deplacementPieceVue(){
        this.vue.texteInformation("Sélectionnez une pièce");
        addCaseListener(this.vue.getListeCaseForClick());
        ArrayList<Integer> pieceSelectionne = new ArrayList();
        ArrayList<Integer> deplacementSelectionne = new ArrayList();
        while(!this.eventSouris.verif()){
            if(choix==5){
                break;
            }
            if(this.play.getPlateau().selectPiece(this.eventSouris.getCaseSelectionné()))
                pieceSelectionne = this.eventSouris.getCaseSelectionné();
        }
        if(choix!=5){
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
                int test=0;
                for(int i=0; i<4; i++){
                    if(this.vue.getListeRotation().get(i).isSelected()){
                        test=i;
                    }    
                }
                this.play.getPlateau().getPiece(pieceSelectionne).createPiece(test);
                boolean res = this.play.deplacementPiece(pieceSelectionne, deplacementSelectionne);
                if(!res)
                    JOptionPane.showMessageDialog(this.vue,"Deplacement impossible par manque de place");
                removeCaseListener(this.vue.getListeCaseForClick());
            }
        }
        this.vue.getListeBouton().get(2).setText("DEPLACER");
        if(choix==5){
            System.out.println(this.play.getPlateau());
            this.vue.afficheGrille();
        }
    }

    public void supprimerPieceVue(){
        this.vue.texteInformation("Sélectionnez une pièce");
        addCaseListener(this.vue.getListeCaseForClick());
        ArrayList<Integer> pieceSelectionne = new ArrayList();
        while(!this.eventSouris.verif()){
            if(choix==5){
                break;
            }
            if(this.play.getPlateau().selectPiece(this.eventSouris.getCaseSelectionné()))
                pieceSelectionne = this.eventSouris.getCaseSelectionné();
        }
        if(choix!=5){
            this.play.supprimerPiece(pieceSelectionne);
            removeCaseListener(this.vue.getListeCaseForClick());
        }
        this.vue.getListeBouton().get(3).setText("SUPRIMER");
        if(choix==5)
            this.vue.afficheGrille();
    }
    
    private void removeCaseListener(HashMap<ArrayList<Integer>,JPanel> liste){
        for(ArrayList<Integer> i : liste.keySet()){
            liste.get(i).removeMouseListener(eventSouris);
        }
    }

    private void addCaseListener(HashMap<ArrayList<Integer>,JPanel> liste){
        for(ArrayList<Integer> i : liste.keySet()){
            liste.get(i).addMouseListener(eventSouris);
        }
    }

    private void removePieceListener(ArrayList<JPanel> liste){
        for(int i=0 ; i < liste.size() ; i++)
                    liste.get(i).removeMouseListener(eventSouris);
    }

    private void addPieceListener(ArrayList<JPanel> liste){
        for(int i=0 ; i < liste.size() ; i++)
                    liste.get(i).addMouseListener(eventSouris);
    }
    
    private void actionBouton(int i){
        if(!this.vue.actionEnCours()){
                this.vue.getListeBouton().get(i).setText("ANNULER");
                choix=i;
                this.play.clickNotify();
        }
        else if(this.vue.getListeBouton().get(i).getText()=="ANNULER"){
                choix=5;
        }
    }
    
    public int getChoix(){
        return choix;
    }
    
    @Override
    public synchronized void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if(source == this.vue.getListeBouton().get(0)){
            if(this.vue.getLigne().getSelectedIndex()!=0 && this.vue.getColonne().getSelectedIndex()!=0){
                this.play.setLargeur(this.vue.getLigne().getSelectedIndex()+4);
                this.play.setLongueur(this.vue.getColonne().getSelectedIndex()+4);
                choix=1;
                this.play.nouvellePartie(this.play.getLargeur(),this.play.getLongueur());
                this.play.clickNotify();
            }
            else {
                JOptionPane.showMessageDialog(this.vue,"Veuillez sélectionné un nombre de ligne et de colonne pour continuer !");
            }
        }
        if(source == this.vue.getListeBouton().get(1)){
            actionBouton(1);
        }
        if(source == this.vue.getListeBouton().get(2)){
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
                        this.play.sauvegarderLaPartie("src/file/partie/partie.txt");
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
            this.play.chargerPartie();
            this.play.clickNotify();
        }
        if(source == this.vue.getListeBouton().get(6)){
            this.vue.Explication();
        }
        if(source == this.vue.getListeBouton().get(7)){
            this.play.clickNotify();
        }
        if(source == this.vue.getListeBouton().get(8)){
            this.play.clickNotify();
            choix=1;
        }
    }     
}
