/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
    public class MouseClicker extends MouseAdapter {
        private boolean verification = false;
        private int pieceSelectionne;
        private ArrayList<Integer> caseSelectionne = new ArrayList();
        private final InterfaceGraphique vue;
        
    /**
     * Constructeur
     * @param vue vue Graphique
     */
    public MouseClicker(InterfaceGraphique vue){
            this.vue=vue;
        }

    /**
     * Verifisi le joueur à bien cliquer sur une cae ou un pièce
     * @return verification faite ou non
     */
    public boolean verif(){
            boolean tmp = verification;
            verification = false;           //On remet à false pour éviter une méthode setVerif(false)
            return tmp;
        }
        
    /**
     * @return piece selectionné dans la liste de pièce
     */
    public int getPieceSelectionne(){
            return pieceSelectionne;
        }
        
    /**
     * @return case selectionné dans la grille
     */
    public ArrayList<Integer> getCaseSelectionne(){
            return caseSelectionne;
        }
        
        @Override
        public void mouseClicked(MouseEvent e){
            for(int i=0; i<this.vue.getListePieceForClick().size(); i++){
                if(e.getSource() == this.vue.getListePieceForClick().get(i)){
                    pieceSelectionne = i;           //Recuperation du numéro de la pièce selectionné 
                    verification = true;            // verif = true car le joueur à fait un clique valide
                }  
            }
            for(ArrayList<Integer> i : this.vue.getListeCaseForClick().keySet()){
                if(e.getSource() == this.vue.getListeCaseForClick().get(i)){
                    caseSelectionne = i;            //Recuperation les coordonnées de la case selectionné 
                    verification = true;            
                }
            }
        }

    /**
     * @return click est valide ou non
     */
    public boolean getValide(){
                return this.verification;
        }
		
    }

