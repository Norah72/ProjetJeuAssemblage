import piecesPuzzle.pieces.*;
import modele.*;
import java.util.ArrayList;
import util.*;
public class Main {
    
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
            
            Coordonnees coo1 = new Coordonnees(0,0);
            
            PiecesPuzzle p1 = new PieceL(3,3);
            
            p1.createPiece(0);
            
            plateau.addPiece(p1,coo1); 
            plateau.addPiece(p1,coo1);
            
            System.out.println(plateau.getPlateau());
        }
}