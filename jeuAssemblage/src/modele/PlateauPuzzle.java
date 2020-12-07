package modele;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import piecesPuzzle.pieces.*;
import util.*;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
public class PlateauPuzzle implements Listenable, Cloneable{
    
	/**
	 * Plateau
	 */
    private HashMap<ArrayList<Integer>, PiecesPuzzle > plateau;
	
	/**
	 * Largeur du plateau
	 */
    private int x,
			/**
			 * longueur du plateau
			 */
			y;
	
	/**
	 * Liste d'écoute
	 */
    private ArrayList<Listener> listeners ;

	/**
	 * Liste des pièces a jouer/disponible
	 */
    private ArrayList<PiecesPuzzle> pieceAJouer = new ArrayList<PiecesPuzzle>();
	/**
	 * Liste des pièces placer
	 */
    private ArrayList<PiecesPuzzle> piecePlacer = new ArrayList<PiecesPuzzle>();
	/**
	 * Liste des nom de pièce (enum)
	 */
    private final ArrayList<String> pieceString = new ArrayList<String>(Arrays.asList("PieceH", "PieceL", "PieceRectangle", "PieceT"));


	/**
	 * minumum surface X du plateau (calcul score)
	 */
    private int minX,
			/**
			 * Maximum surface X du plateau (calcul score)
			 */
			maxX,
			/**
			 * minumum surface y du plateau (calcul score)
			 */
			minY,
			/**
			 * maximul surface Y du plateau (calcul score)
			 */
			maxY;
	
	/**
	 * Surface total des pièces
	 */
    private int surfacePieces ;    

    /**
     * Constructeur
     * @param x largeur du plateau
     * @param y longueur du plateau
     */
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
	
    /**
     * Construction d'un plateau vide de taille X Y
     * @param x
     * @param y 
     */
    private void construcPlateau(int x, int y){
		for(int i = 0; i <= x-1 ; i++){
			for(int j = 0; j <= y-1 ; j++){
					this.plateau.put(new ArrayList<Integer>(Arrays.asList((Integer)i, (Integer)j)), null);
			}
		}
    }
	
    /**
     * Mise à jour de l'air du plateau utilisé par le joueur pour le calcul du score
     */
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

    /**
     * Calcule du score
     * @return score
     */
    public int getScore(){
        int surfaceTotal = (this.maxX - this.minX + 1)*(this.maxY - this.minY +1);
        double score =  this.surfacePieces/ (double)surfaceTotal * 100; //On multiplie par 100 l'air de jeu pour avoir score plus agréable à voir
        score *= this.piecePlacer.size();
        return (int) score;
    }

    /**
     * Création d'une nouvelle pièce de rotation 0 par défaut
     * @param piece forme de la pièce
     * @param largeur largeur de la pièce
     * @param longueur longueur de la pièce
     */
    public void newPiece(String piece, int largeur, int longueur){
            this.newPiece(piece, largeur, longueur, 0);	
    }

    /**
     * Création d'une nouvelle pièce avec rotation définit
     * @param piece forme de la pièce
     * @param largeur largeur de la pièce
     * @param longueur longueur de la pièce
     * @param rotation rotation de la pièce
     */
    public void newPiece(String piece, int largeur, int longueur, int rotation){
            this.pieceAJouer.add(createNewPiece(piece, largeur, longueur, rotation));	
    }

    /**
     * Gestionnaire de création d'une nouvelle pièce
     * @param piece forme de la pièce
     * @param largeur largeur de la pièce
     * @param longueur longueur de la pièce
     * @param rotation rotation de la pièce
     * @return une Pièce
     */
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

    /**
     * Gestionnaire de d'ajout d'une pièce
     * @param p pièce à ajouter
     * @param coo coordonées où la pièce doit se placer
     * @return placement effectuer ou non
     */
    public boolean addPiece(PiecesPuzzle p , ArrayList coo){

        int posY = 0;
        int xx = 0;
        int yy = 0;

        while(!(p.getGrid()[xx][yy+posY])){//Si une pièce a une case de référence en false
            coo.set(1, (int) coo.get(1)-1);
            posY += 1;
        }

        if (validePlacement(p,coo)){
            add(p,coo);
            return true;
        }
        return false;
    }
	
    /**
     * Ajoute la pièce dans le plateau
     * @param p pièce à ajouter
     * @param coo coordonées où la pièce doit se placer
     */
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

    /**
	 * Gestionnaire de déplacement de pièce
     * @param p pièce à déplacer
     * @param coo coordonées où la pièce doit se placer
     * @return déplacement effectuer ou non
     */
    public boolean movePiece( PiecesPuzzle p ,ArrayList coo){

        int posY = 0;
        int xx = 0;
        int yy = 0;

        while(!(p.getGrid()[xx][yy+posY])){//Si une pièce a une case de référence en false
            coo.set(1, (int) coo.get(1)-1);
            posY += 1;
        }

        removePiece(p); // Supprime la pièce
        if(validePlacement(p,coo)){ // valide le placement
            add(p,coo); //ajoute la pièce
            update();
            return true;
        }

        add(p,p.getCoo()); // replace la pièce si le placement n'est pas valide
        update();

        return false;
    }

    /**
	 * Gestionnaire de suppression de pièce
     * @param p pièce à supprimer
     * @return supression effectuer ou non
     */
    public boolean removePiece( PiecesPuzzle p){
        boolean valide = false;
        for (HashMap.Entry< ArrayList<Integer> , PiecesPuzzle > entry : this.plateau.entrySet()) {
            if(entry.getValue() == p){
                this.plateau.replace(entry.getKey(),null);
                this.surfacePieces --;
                valide = true;
            }
        }
        if (valide){
            update();
            this.pieceAJouer.add(p);
            this.piecePlacer.remove(p);
            return true;
        }
        return false;

    }

    /**
	 * Gestionnaire de rotation de pièce
     * @param p pièce à tourner
     * @param rotation nouvelle rotation de la pièce
     * @return rotation effectuer ou non
     */
    public boolean rotationPiece(PiecesPuzzle p , Integer rotation){
        removePiece(p);
        boolean out;
        int rotationOrigine = p.getRotation();

        p.createPiece(rotation);
        if(!validePlacement(p,p.getCoo())){     //Pour la tourner: on la supprime, on la tourne, et on la replace
            p.createPiece(rotationOrigine);
            out = false;
        }else{
            out = true;
        }
        add(p,p.getCoo());
        update();
        return out;
    }
	
	/**
     * Gestionnaire de rotation d'une pièce non placé
     * @param numPieceRotation numéro de la pièce parmi la liste des pièce à jouer
     * @param rotation rotation de la pièce souhaité
     */
    public void rotationPieceDisponible(int numPieceRotation , Integer rotation){
        this.pieceAJouer.get(numPieceRotation).createPiece(rotation);
    }

    /**
     * Valide le placement d'une pièce
     * @param p pièce
     * @param coo futur coordonées de la pièce
     * @return si le placement est possible ou non
     */
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
	
	/**
     * Valide si une case est null
     * @param coo coordonées de la case
     * @return la case est libre ou non
     */
    public boolean libre(ArrayList coo){
        return this.plateau.get(coo) == null && this.plateau.containsKey(coo);
    }

   
    /**
     * Selectionne la pièce
     * @param coo coordonées d'une case sur le plateau
     * @return si il y a une pièce ou non
     */
    public boolean selectPiece(ArrayList coo){

        PiecesPuzzle pieceSelect = (PiecesPuzzle)this.plateau.get(coo);
        return pieceSelect != null;
    }

    /**
     *
     * @return le plateau
     */
    public HashMap getPlateau(){
        return this.plateau;
    }

    /**
     *
     * @return la liste des pièces à jouer
     */
    public ArrayList<PiecesPuzzle> getPieceAJouer() {
            return pieceAJouer;
    }

    /**
     *
     * @return la liste des pièces placer
     */
    public ArrayList<PiecesPuzzle> getPiecePlacer() {
            return piecePlacer;
    }		

    /**
     *
     * @param coo coordonées de la case
     * @return la pièce au coorddonnées rentré
     */
    public PiecesPuzzle getPiece(ArrayList coo){
            return this.plateau.get(coo);
    }

    /**
     *
     * @return le type de la pièce
     */
    public ArrayList<String> getPieceString() {
            return pieceString;
    }

    /**
     * Modifie la liste des pièces à jouer
     * @param pieceAJouer liste des pièces à jouer
     */
    public void setPieceAJouer(ArrayList<PiecesPuzzle> pieceAJouer) {
            this.pieceAJouer = pieceAJouer;
    }

    /**
     * Modifie la liste des pièces placer
     * @param piecePlacer liste des pièces placer
     */
    public void setPiecePlacer(ArrayList<PiecesPuzzle> piecePlacer) {
            this.piecePlacer = piecePlacer;
    }

    /**
     * Modifie la taille du plateau
     * @param newx nouvelle largeur
     * @param newy nouvelle longueur
     */
    public void setXY(int newx,int newy){
        this.x = newx;
        this.y = newy;
        construcPlateau(this.x, this.y);
    }

    /**
     * 
     * @return largeur du plateau
     */
    public int getX(){
        return this.x;
    }

    /**
     *
     * @return longueur du plateau
     */
    public int getY(){
        return this.y;
    }
	
	/**
     * Met à jour la vue (non implémenté)
     */
    @Override
    public void fireChangement(){
        for ( Listener listener : this.listeners){
            listener.update(this);
        } 
    }

    /**
     *
     * @param listener
     */
    @Override
    public void addListener(Listener listener){
        this.listeners.add(listener);
    }

    /**
     *
     * @param listener
     */
    @Override
    public void removeListener(Listener listener){
        this.listeners.remove(listener);
    }
        
    /**
     * Clone un plateau pour que l'ia fasse ses simultaions 
     * @return PlateauPuzzle
     * @throws CloneNotSupportedException 
     */
	@Override
    public Object clone() throws CloneNotSupportedException {
        PlateauPuzzle plateauPuzzle = null;
        try{
                plateauPuzzle = (PlateauPuzzle) super.clone();
                plateauPuzzle.plateau = (HashMap) this.plateau.clone();
                plateauPuzzle.pieceAJouer = (ArrayList) this.pieceAJouer.clone();
                plateauPuzzle.piecePlacer = (ArrayList) this.piecePlacer.clone();

        } catch (CloneNotSupportedException c) {
                System.out.println("Erreur clonage: "+c);
        }
        return plateauPuzzle;
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
				System.out.print(i+"  "+"\u001B[0m");
			else
				System.out.print(i+" "+"\u001B[0m");
			for(int j = 0; j<=this.y-1; j++){
				if(this.plateau.get(new ArrayList<Integer>(Arrays.asList(i,j))) != null){
					if(j<10)
						System.out.print(((AbstractPiece)this.plateau.get(new ArrayList<Integer>(Arrays.asList(i,j)))).getColor()+" ■ "+((AbstractPiece)this.plateau.get(new ArrayList<Integer>(Arrays.asList(i,j)))).getColor());
					else
						System.out.print(((AbstractPiece)this.plateau.get(new ArrayList<Integer>(Arrays.asList(i,j)))).getColor()+"  ■ "+((AbstractPiece)this.plateau.get(new ArrayList<Integer>(Arrays.asList(i,j)))).getColor());
				}else{
					if(j<10)
						System.out.print("\u001B[0m"+" + "+"\u001B[0m");
					else
						System.out.print("\u001B[0m"+"  + "+"\u001B[0m");
				}
			}
        System.out.println();
        }
        return "";
    }
	
}
