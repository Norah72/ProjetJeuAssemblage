import piecesPuzzle.pieces.*;
import modele.*;
import java.util.ArrayList;
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
            PlateauPuzzle plateau = new PlateauPuzzle();
            
            Coordonnees coo0 = new Coordonnees(0,0);
            Coordonnees coo1 = new Coordonnees(1,1);
            
            PiecesPuzzle pL = new PieceL(3,2);
            pL.createPiece(0);
            
            PiecesPuzzle pH = new PieceH(3,3);
            pH.createPiece(1);
            
            affiche(pL.getGrid());
            affiche(pH.getGrid());
            
            plateau.addPiece(pL,coo0); 
            plateau.addPiece(pH,coo1); 

            System.out.println(plateau.getPlateau());
        }
}