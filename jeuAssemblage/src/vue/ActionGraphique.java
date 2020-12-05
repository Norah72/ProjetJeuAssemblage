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
        }
        if(choix!=5){
            removePieceListener(this.vue.getListePieceForClick());
            this.vue.visualisationRotation(this.play.getPlateau().getPieceAJouer().get(eventSouris.getPieceSelectionné()));
            this.vue.texteInformation("Sélectionnez une case (partie haut gauche de la pièce)");
            addCaseListener(this.vue.getListeCaseForClick());
            while(!eventSouris.verif()){
                if(choix==5){
                    break;
                }
                System.out.print("");
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
                    JOptionPane.showMessageDialog(this.vue,"PLacement impossible par manque de place");
            }
        }
        vue.getListeBouton().get(1).setText("PLACER");
        if(choix==5)
            this.vue.afficheGrille();
		
		this.eventSouris.setSelection();
    }
    
    
    public void deplacementPieceVue(){
		boolean ok = false;
        this.vue.texteInformation("Sélectionnez une pièce");
        addCaseListener(this.vue.getListeCaseForClick());
        ArrayList<Integer> pieceSelectionne = new ArrayList();
        ArrayList<Integer> deplacementSelectionne = new ArrayList();
        while(!ok){
			
			while(!this.eventSouris.verif()){
				
				if(choix==5){
					break;
				}

				if(this.eventSouris.getValide()){
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
                int rotationSelect=0;
                for(int i=0; i<4; i++){
                    if(this.vue.getListeRotation().get(i).isSelected()){
                        rotationSelect=i;
                    }    
                }

				this.play.supprimerPiece(pieceSelectionne);
				this.play.rotationPieceDisponible((this.play.getPlateau().getPieceAJouer().size()-1), rotationSelect);


                boolean res = this.play.ajoutPiece((this.play.getPlateau().getPieceAJouer().size()-1), deplacementSelectionne);
                if(!res){
                    this.play.rotationPieceDisponible((this.play.getPlateau().getPieceAJouer().size()-1), err);
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
		this.eventSouris.setSelection();
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
		
		this.eventSouris.setSelection();
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
            choix=9;
        }
    }     
}
