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
            return (int) score;
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
            
        }

        public boolean movePiece( PiecesPuzzle p ,ArrayList coo){
            removePiece(p);
            if(validePlacement(p,coo)){
                add(p,coo);
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
        }

        public boolean rotationPiece(PiecesPuzzle p , Integer rotation){
            removePiece(p);
            int rotationOrigine = p.getRotation();
            p.createPiece(rotation);
            if(!validePlacement(p,p.getCoo())){
                p.createPiece(rotationOrigine);
		return false;
            }
            add(p,p.getCoo());
            update();
            return true;
        }
        
        public boolean libre(ArrayList coo){
            if(this.plateau.get(coo) == null && this.plateau.containsKey(coo)){
                return true;
            }
            return false;
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
        
        
}
