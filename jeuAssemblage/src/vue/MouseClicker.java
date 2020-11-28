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
 * @author leovi
 */
    public class MouseClicker extends MouseAdapter {
        private boolean verification = false;
        private int pieceSelectionné;
        private ArrayList<Integer> caseSelectionné = new ArrayList();
        private InterfaceGraphique vue;
        
        public MouseClicker(InterfaceGraphique vue){
            this.vue=vue;
        }
        public boolean verif(){
            return verification;
        }
        public void setVerif(boolean bool){
            verification = bool;
        }
        public int getPieceSelectionné(){
            return pieceSelectionné;
        }
        public ArrayList<Integer> getCaseSelectionné(){
            return caseSelectionné;
        }
        @Override
        public void mouseClicked(MouseEvent e){
            System.out.println("ATTENTION");
            for(int i=0; i<this.vue.getListePieceForClick().size(); i++){
                if(e.getSource() == this.vue.getListePieceForClick().get(i)){
                    System.out.println("A LA MOUSSE");
                    pieceSelectionné = i;
                    verification = true;
                }  
            }
            for(ArrayList<Integer> i : this.vue.getListeCaseForClick().keySet()){
                if(e.getSource() == this.vue.getListeCaseForClick().get(i)){
                    System.out.println(i);
                    caseSelectionné = i;
                    verification = true; 
                }
            }
        }
        
    }

