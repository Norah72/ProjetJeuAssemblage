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
        private int pieceSelectionné;
        private ArrayList<Integer> caseSelectionné = new ArrayList();
        private final InterfaceGraphique vue;
        
    /**
     * Constructeur
     * @param vue
     */
    public MouseClicker(InterfaceGraphique vue){
            this.vue=vue;
        }

    /**
     * Verifisi le joueur à bien cliquer sur une cae ou un pièce
     * @return
     */
    public boolean verif(){
            boolean tmp = verification;
            verification = false;           //On remet à false pour éviter une méthode setVerif(false)
            return tmp;
        }
        
    /**
     * Retourne la piece selectionné dans la liste de pièce
     * @return
     */
    public int getPieceSelectionné(){
            return pieceSelectionné;
        }
        
    /**
     * Retourne la case selectionné dans la grille
     * @return
     */
    public ArrayList<Integer> getCaseSelectionné(){
            return caseSelectionné;
        }
        
        @Override
        public void mouseClicked(MouseEvent e){
            for(int i=0; i<this.vue.getListePieceForClick().size(); i++){
                if(e.getSource() == this.vue.getListePieceForClick().get(i)){
                    pieceSelectionné = i;           //Recuperation du numéro de la pièce selectionné 
                    verification = true;            // verif = true car le joueur à fait un clique valide
                }  
            }
            for(ArrayList<Integer> i : this.vue.getListeCaseForClick().keySet()){
                if(e.getSource() == this.vue.getListeCaseForClick().get(i)){
                    caseSelectionné = i;            //Recuperation les coordonnées de la case selectionné 
                    verification = true;            
                }
            }
        }

    /**
     * Retourne si le click est valide ou non
     * @return
     */
    public boolean getValide(){
                return this.verification;
        }
		
    }

