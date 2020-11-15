import controleur.*;
import piecesPuzzle.pieces.*;
import modele.*;
import vue.*;
import java.util.ArrayList;
import java.util.Arrays;
import util.*;
public class Main {
        
        // Affiche une grille / Une pièce ( test )
        public static void affiche(boolean[][] tab){
            for(int i=0;i<tab.length;i++){
                for(int j=0; j<tab[0].length;j++){
                    if(tab[i][j]){
                        System.out.print("█");
                    }else{
                        System.out.print("-");
                    }
                }
                System.out.println();
                
            }
            System.out.println();
        }
    
        
        
	public static void main(String[] args){

            //######### POUR LANCER LA VUE - ENLENVER LES COMMANTAIRES (LIGNE 30 à 38)#####// 
            //######### + ENLEVR COMMENTAIRES DANS CONSTRUCTEURS DE PLAYCONSOLE############//
            int x=0;
            int y=0;
            PlateauPuzzle play = new PlateauPuzzle(x,y);
            InterfaceGraphique fenetre = new InterfaceGraphique(play);
            PlayConsole lol = new PlayConsole(fenetre,play);
            
            /*int x = 5;
            int y = 5;
            PlateauPuzzle plateau = new PlateauPuzzle(x,y);
     
            ArrayList<Integer> coo0 = new ArrayList<Integer>(Arrays.asList(0,0));
            ArrayList<Integer> coo1 = new ArrayList<Integer>(Arrays.asList(1,1));
            ArrayList<Integer> coo2 = new ArrayList<Integer>(Arrays.asList(1,2));
            
            PiecesPuzzle pL = new PieceL(3,2);
            pL.createPiece(0);
            
            PiecesPuzzle pH = new PieceH(3,3);
            pH.createPiece(1);
            
            
            System.out.println("Ajout de la pièce L en "+coo0);
            plateau.addPiece(pL,coo0); 

            
            //System.out.println("Ajout de la pièce H en "+coo1);
            //plateau.addPiece(pH,coo1);
            
            if(plateau.getPlateau().get(coo0) != null)
                for(int i = 0; i<=y-1; i++){
                    for(int j = 0; j<=x-1; j++){
                        if(plateau.getPlateau().get(new ArrayList<Integer>(Arrays.asList(i,j))) != null)
                            System.out.print("█");
                        else
                            System.out.print("-");
                    }
                    System.out.println();
                }
            System.out.println(plateau.getScore());
            
            /*
            System.out.println("Déplacement de la pièce H en "+coo2);
            plateau.movePiece(pH,coo2);
            // Si on enlève le déplacement, alors la rotation devient 
            // impossible et ne s'effectue pas !
            
            System.out.println("Rotation de la pièce H");
            plateau.rotationPiece(pH, 2);
            
            
            
            System.out.println("\nLe plateau :");
            System.out.println(plateau.getPlateau());
            
            if(plateau.getPlateau().get(coo0) != null)
                for(int i = 0; i<=y-1; i++){
                    for(int j = 0; j<=x-1; j++){
                        if(plateau.getPlateau().get(new ArrayList<Integer>(Arrays.asList(i,j))) != null)
                            System.out.print("█");
                        else
                            System.out.print("-");
                    }
                    System.out.println();
                }*/
        }
}