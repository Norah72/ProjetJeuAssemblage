package modele;
import java.util.HashMap;
import java.util.ArrayList;
import piecesPuzzle.pieces.*;

public class PlateauPuzzle {
    
        private HashMap<Integer, ArrayList<PiecesPuzzle> > plateau;
        
        public PlateauPuzzle(){
            this.plateau = new HashMap<Integer, ArrayList<PiecesPuzzle> >();
        }
        
        public HashMap getPlateau(){
            return this.plateau;
        }
        
        public void addPiece(PiecesPuzzle p , Integer x){
            
            if (this.plateau.get(x)==null){
                this.plateau.put(x,new ArrayList<PiecesPuzzle>());
            }
            if (validePlacement(p,x)){
                this.plateau.get(x).add(p);
            }
            
        }
        
        public boolean validePlacement(PiecesPuzzle p, Integer x){
            /*
            boolean[][] grid = p.getGrid();
            for(int i=0;i<p.getLargeurActuel();i++){
                for(int j=0; j<p.getLongueurActuel();j++){
                    if(grid[i][j]){
                        if (  this.plateau.get(  new Coordonnee(coo.getX+i, coo.getY+j)  ) !=null ){
                            return false;
                        }
                    }
                }
            }*/
            return true;
        }
        
}
