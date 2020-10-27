package modele;
import java.util.HashMap;
import java.util.ArrayList;
import piecesPuzzle.pieces.*;
import util.*;

public class PlateauPuzzle {
    
        private HashMap<Coordonnees, PiecesPuzzle > plateau;
        
        public PlateauPuzzle(){
            this.plateau = new HashMap<Coordonnees, PiecesPuzzle >();
        }
        
        public HashMap getPlateau(){
            return this.plateau;
        }
        
        public void addPiece(PiecesPuzzle p , Coordonnees coo){
            
            if (validePlacement(p,coo)){
                for(int i=0;i<p.getLargeurX();i++){
                    for(int j=0; j<p.getLongueurY();j++){
                        if(p.getGrid()[i][j]){
                            this.plateau.put(new Coordonnees(coo.getX()+j,coo.getY()+i),p);
                        }
                    }
                }
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
            for(int i=0;i<p.getLargeurX();i++){
                for(int j=0; j<p.getLongueurY();j++){
                    if(p.getGrid()[i][j]){
                        if (  !this.libre( new Coordonnees(coo.getX()+j,coo.getY()+i )  ) ){
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        
}
