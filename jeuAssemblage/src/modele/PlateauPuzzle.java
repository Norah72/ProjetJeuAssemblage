package modele;
import java.util.HashMap;
import java.util.ArrayList;
import piecesPuzzle.pieces.*;
import util.*;

public class PlateauPuzzle {
    
        private HashMap<Coordonnees, ArrayList<PiecesPuzzle> > plateau;
        
        public PlateauPuzzle(){
            this.plateau = new HashMap<Coordonnees, ArrayList<PiecesPuzzle> >();
        }
        
        public HashMap getPlateau(){
            return this.plateau;
        }
        
        public void addPiece(PiecesPuzzle p , Coordonnees coo){
            
            if (validePlacement(p,coo)){
                if (this.plateau.get(coo)==null){
                    this.plateau.put(coo,new ArrayList<PiecesPuzzle>());
                }
                this.plateau.get(coo).add(p);
            }
            
        }
        
        public boolean libre(Coordonnees coo){
            for (Coordonnees key : this.plateau.keySet()) {
                if( (coo.getX() == key.getX()) && (coo.getY()==key.getY()) ){
                    return false;
                }
            }
            return true;
        }
        
        public boolean validePlacement(PiecesPuzzle p, Coordonnees coo){
            boolean[][] grid = p.getGrid();
            for(int i=0;i<p.getLargeurX();i++){
                for(int j=0; j<p.getLongueurY();j++){
                    if(grid[i][j]){
                        if (  !this.libre( new Coordonnees(coo.getX()+i, coo.getY()+j)  ) ){
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        
}
