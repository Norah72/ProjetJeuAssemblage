import piecesPuzzle.pieces.*;
import modele.*;
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
			int x = 5;
			int y = 10;
            PlateauPuzzle plateau = new PlateauPuzzle(x,y);
     
            ArrayList<Integer> coo0 = new ArrayList<Integer>(Arrays.asList((Integer)0,(Integer)0));
            ArrayList<Integer> coo1 = new ArrayList<Integer>(Arrays.asList(2,3));
            
            PiecesPuzzle pL = new PieceL(3,2);
            pL.createPiece(0);
            
            PiecesPuzzle pH = new PieceH(3,3);
            pH.createPiece(1);
            
            affiche(pL.getGrid());
            affiche(pH.getGrid());
            
            plateau.addPiece(pL,coo0); 
            plateau.addPiece(pH,coo1); 
			System.out.println("Tien le plateau:");
			System.out.println(plateau.getPlateau());
			if(plateau.getPlateau().get(coo0) != null)
					System.out.println("coucou");
			for(int i = 0; i<=y-1; i++){
			   for(int j = 0; j<=x-1; j++){
					if(plateau.getPlateau().get(new ArrayList<Integer>(Arrays.asList(i,j))) != null)
						System.out.print("█");
					else
                        System.out.print("-");
				}
				System.out.println();
			}
	}
}