import piecesPuzzle.pieces.*;
import modele.*;
import java.util.ArrayList;

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
            
            PiecesPuzzle p1 = new PieceL(3,3);
            PiecesPuzzle p2 = new PieceH(5,3);
            PiecesPuzzle p3 = new PieceT(3,5);
            
            p1.createPiece(0);
            p2.createPiece(1);
            p3.createPiece(2);
            
            plateau.addPiece(p1,0);
            plateau.addPiece(p2,1);
            plateau.addPiece(p3,0);
            
            System.out.println(plateau.getPlateau());
        }
}