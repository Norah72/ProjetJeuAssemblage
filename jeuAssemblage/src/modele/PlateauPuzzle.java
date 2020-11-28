package modele;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import piecesPuzzle.pieces.*;
import util.*;

public class PlateauPuzzle implements Listenable{
    
        private HashMap<ArrayList<Integer>, PiecesPuzzle > plateau;
        private int x,y;
        private ArrayList<Listener> listeners ;

		private ArrayList<PiecesPuzzle> pieceAJouer = new ArrayList<PiecesPuzzle>();
		private ArrayList<PiecesPuzzle> piecePlacer = new ArrayList<PiecesPuzzle>();
		private ArrayList<String> pieceString = new ArrayList<String>(Arrays.asList("PieceH", "PieceL", "PieceRectangle", "PieceT"));
		
        
        private int minX,maxX,minY,maxY;
        private int surfacePieces ;    
	
        public PlateauPuzzle(int x, int y){
            this.plateau = new HashMap<ArrayList<Integer>, PiecesPuzzle >();
            this.x=x;
            this.y=y;
            construcPlateau(x,y);
            
            this.listeners = new ArrayList<Listener>();
            
            this.surfacePieces = 0;
            this.minX = x;
            this.maxX = 0;
            this.minY = y;
            this.maxY = 0;
        }
		
	private void construcPlateau(int x, int y){
        for(int i = 0; i <= x-1 ; i++){
			for(int j = 0; j <= y-1 ; j++){
				this.plateau.put(new ArrayList<Integer>(Arrays.asList((Integer)i, (Integer)j)), null);
			}
        }
	}
        
        private void update(){
            this.minX = x;
            this.maxX = 0;
            this.minY = y;
            this.maxY = 0;
            for (HashMap.Entry< ArrayList<Integer> , PiecesPuzzle > entry : this.plateau.entrySet()) {
                
                if(entry.getValue() != null) {
                    if(entry.getKey().get(0) < minX){
                        this.minX = entry.getKey().get(0);
                    }
                    if(entry.getKey().get(0) > maxX){
                        this.maxX = entry.getKey().get(0);
                    }
                    if(entry.getKey().get(1) < minY){
                        this.minY = entry.getKey().get(1);
                    }
                    if(entry.getKey().get(1) > maxY){
                        this.maxY = entry.getKey().get(1);
                    }
                }
            }
            
        }

        public int getScore(){
            int surfaceTotal = (this.maxX - this.minX + 1)*(this.maxY - this.minY +1);
            double score =  this.surfacePieces/ (double)surfaceTotal * 100;
            score *= this.piecePlacer.size();
            return (int) score;
        }
		
        public void newPiece(String piece, int largeur, int longueur){
                this.newPiece(piece, largeur, longueur, 0);	
        }

        public void newPiece(String piece, int largeur, int longueur, int rotation){
                this.pieceAJouer.add(createNewPiece(piece, largeur, longueur, rotation));	
        }

        public PiecesPuzzle createNewPiece(String piece, int largeur, int longueur, int rotation){
                if(piece.equals(pieceString.get(0))){
                        return new PieceH(largeur,longueur, rotation);
                }else if(piece.equals(pieceString.get(1))){
                        return new PieceL(largeur,longueur, rotation);
                }else if(piece.equals(pieceString.get(2))){
                        return new PieceRectangle(largeur,longueur, rotation);
                }else if(piece.equals(pieceString.get(3))){
                        return new PieceT(largeur,longueur, rotation);
                }
                return null;
        }
        
        public boolean addPiece(PiecesPuzzle p , ArrayList coo){
            if (validePlacement(p,coo)){
                add(p,coo);
				return true;
            }
			return false;
        }
        
        private void add(PiecesPuzzle p,ArrayList coo){
            for(int i=0;i<p.getLargeurX();i++){
                for(int j=0; j<p.getLongueurY();j++){
                    if(p.getGrid()[i][j]){
                        this.plateau.put(new ArrayList<Integer>(Arrays.asList(((Integer)coo.get(0))+i, ((Integer)coo.get(1))+j)),p);
                        this.surfacePieces ++;
                    }
                }
            }
            p.updateCoordonnees(coo);
            update();        
			this.pieceAJouer.remove(p);
			this.piecePlacer.add(p);
        }

        public boolean movePiece( PiecesPuzzle p ,ArrayList coo){
            removePiece(p);
            if(validePlacement(p,coo)){
                add(p,coo);
                update();
                return true;
            }
            add(p,p.getCoo());
            update();
            return false;
        }
        
        public void removePiece( PiecesPuzzle p){
            for (HashMap.Entry< ArrayList<Integer> , PiecesPuzzle > entry : this.plateau.entrySet()) {
                if(entry.getValue() == p){
                    this.plateau.replace(entry.getKey(),null);
                    this.surfacePieces --;
                }
            }
            update();
			this.pieceAJouer.add(p);
			this.piecePlacer.remove(p);
        }

        public boolean rotationPiece(PiecesPuzzle p , Integer rotation){
            removePiece(p);
            boolean out;
            int rotationOrigine = p.getRotation();
            p.createPiece(rotation);
            if(!validePlacement(p,p.getCoo())){
                p.createPiece(rotationOrigine);
				out = false;
            }else{
                out = true;
            }
            add(p,p.getCoo());
            update();
            return out;
        }
        
        public boolean libre(ArrayList coo){
            if(this.plateau.get(coo) == null && this.plateau.containsKey(coo)){
                return true;
            }
            return false;
        }
		
		public boolean selectPiece(ArrayList coo){
			PiecesPuzzle pieceSelect = (PiecesPuzzle)this.plateau.get(coo);
			if(pieceSelect == null)
				return false;
			else
				return true;
		}
        
        public boolean validePlacement(PiecesPuzzle p, ArrayList coo){
            for(int i=0;i<p.getLargeurX();i++){
                for(int j=0; j<p.getLongueurY();j++){
                    if(p.getGrid()[i][j]){
                        if (  !this.libre(    new ArrayList<Integer>(Arrays.asList(((Integer)coo.get(0))+i, ((Integer)coo.get(1))+j))    ) ){
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

		public ArrayList<PiecesPuzzle> getPieceAJouer() {
			return pieceAJouer;
		}

		public ArrayList<PiecesPuzzle> getPiecePlacer() {
			return piecePlacer;
		}		
		
		public PiecesPuzzle getPiece(ArrayList coo){
			return this.plateau.get(coo);
		}

		public ArrayList<String> getPieceString() {
			return pieceString;
		}

		public void setPieceAJouer(ArrayList<PiecesPuzzle> pieceAJouer) {
			this.pieceAJouer = pieceAJouer;
		}

		public void setPiecePlacer(ArrayList<PiecesPuzzle> piecePlacer) {
			this.piecePlacer = piecePlacer;
		}
		
		
        @Override
        public void fireChangement(){
            for ( Listener listener : this.listeners){
                listener.update(this);
            } 
        }
        
        @Override
        public void addListener(Listener listener){
            this.listeners.add(listener);
        }
    
        @Override
        public void removeListener(Listener listener){
            this.listeners.remove(listener);
        }
        
	@Override
	public String toString(){
            System.out.print("   ");
            for(int z = 0; z<=this.y-1; z++){
                System.out.print(" "+z+" ");
            }
            System.out.println();

            for(int i = 0; i<=this.x-1; i++){
                if(i<10)
                    System.out.print(i+"  ");
		else
                    System.out.print(i+" ");
		for(int j = 0; j<=this.y-1; j++){
                    if(this.plateau.get(new ArrayList<Integer>(Arrays.asList(i,j))) != null){
                    	if(j<10)
                            System.out.print(" ■ ");
			else
                            System.out.print("  ■ ");
                    }else{
			if(j<10)
                            System.out.print(" + ");
			else
                            System.out.print("  + ");
                    }
                }
		System.out.println();
            }
            return "";
	}
        public void setXY(int newx,int newy){
            this.x = newx;
            this.y = newy;
            construcPlateau(this.x, this.y);
        }
}
