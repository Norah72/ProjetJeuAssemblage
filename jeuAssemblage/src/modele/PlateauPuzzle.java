package modele;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import piecesPuzzle.pieces.*;
import util.*;

public class PlateauPuzzle {
    
        private HashMap<ArrayList<Integer>, PiecesPuzzle > plateau;
        
        public PlateauPuzzle(int x, int y){
            this.plateau = new HashMap<ArrayList<Integer>, PiecesPuzzle >();
			construcPlateau(x,y);
        }
		
		private void construcPlateau(int x, int y){
			for(int i = 0; i <= x-1 ; i++){
				for(int j = 0; j <= y-1 ; j++){
					this.plateau.put(new ArrayList<Integer>(Arrays.asList((Integer)i, (Integer)j)), null);
				}
			}
			
		}

        
        public void addPiece(PiecesPuzzle p , ArrayList coo){
            if (validePlacement(p,coo)){
                for(int i=0;i<p.getLargeurX();i++){
                    for(int j=0; j<p.getLongueurY();j++){
                        if(p.getGrid()[i][j]){
				this.plateau.put(new ArrayList<Integer>(Arrays.asList(((Integer)coo.get(0))+i, ((Integer)coo.get(1))+j)),p);
                        }
                    }
                }
            }
        }

        
		public boolean libre(ArrayList coo){
            if(this.plateau.get(coo) == null){
                return true;
			}
            return false;
        }
        
        public boolean validePlacement(PiecesPuzzle p, ArrayList coo){
            for(int i=0;i<p.getLargeurX();i++){
                for(int j=0; j<p.getLongueurY();j++){
                    if(p.getGrid()[i][j]){
                        if (  !this.libre(coo) ){
                            return false;
                        }
                    }
                }
            }
            return true;
        }
		        
        public HashMap getPlateau(){
            return this.plateau;
        }
        
}
