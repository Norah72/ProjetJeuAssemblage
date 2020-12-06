package controleur;

import java.util.*;
import modele.*;

public class PlayIA implements InterfacePlay{
	
	private ArrayList<EnumAction> listeChoixIa = new ArrayList<EnumAction>();
	private ArrayList<EnumAction> plateauPlein = new ArrayList<EnumAction>(Arrays.asList(EnumAction.FIN_DE_PARTIE));
	private ArrayList<EnumAction> plateauAvecPiecePlacer = new ArrayList<EnumAction>(Arrays.asList(EnumAction.PLACER, EnumAction.DEPLACER, EnumAction.SUPPRIMER, EnumAction.ROTATION_PIECEAJOUER, EnumAction.ROTATION_PIECEPLACER));
	private ArrayList<EnumAction> plateauSansPiecePlacer = new ArrayList<EnumAction>(Arrays.asList(EnumAction.PLACER, EnumAction.ROTATION_PIECEAJOUER));

	public void PlayIa(){
		
	}
	
	public EnumAction choixJeu(PlateauPuzzle plateau) {
		listeChoixIa.clear();
		ArrayList<EnumAction> choixListe = null;
		
		//Si toutes les pièces ont été jouer
		if(plateau.getPieceAJouer().isEmpty()){
			choixListe = plateauPlein;
        }	
		//Si au moins 1 pièce a été placer
		else if(!plateau.getPiecePlacer().isEmpty()){
			choixListe = plateauAvecPiecePlacer;
		}
		//Si aucune pièce a été placer
        else{
			choixListe = plateauSansPiecePlacer;
        }
		
		//Mise en place d'une liste de choix en fonction du nombre de probabilité de choisir ce choix
		int nbrProbaChoix = 0;
		for(int i = 0; i < choixListe.size(); i++){
			nbrProbaChoix = choixListe.get(i).getProbaAction(choixListe.get(i));
			for(int j = 0; j < nbrProbaChoix; j++)
				listeChoixIa.add(choixListe.get(i));
		}
		
		int choix = choix(0, listeChoixIa.size()-1);
		
        return listeChoixIa.get(choix);
	}
    
    public int choix(int borneInf, int borneSup){
        return ((new Random()).nextInt(borneSup-borneInf + 1))+borneInf;
    }
    
    public ArrayList<Integer> selectCoordonnees(int largeurPlateau, int longueurPlateau){
        return new ArrayList(Arrays.asList(((new Random()).nextInt(largeurPlateau))+1, ((new Random()).nextInt(longueurPlateau))+1));
    }
    
    public ArrayList<Integer> selectPiece(int largeurPlateau, int longueurPlateau, PlateauPuzzle plateau){
		int choix = choix(1,+plateau.getPiecePlacer().size())-1;
		ArrayList<Integer> cooPiece = new ArrayList<Integer>(plateau.getPiecePlacer().get(choix).getCoo());
		
		//Permet de récupérer les coordonnees de la piece, car cooPiece représente les coordonnees en haut a gauche de la pièce, sauf que cela peut être une case false, non détecter par le plateau
		boolean valide = false;
		int i=0;
		while(!valide){
			cooPiece.set(1, cooPiece.get(1)+i);
			valide = plateau.getPlateau().get(cooPiece) == plateau.getPiecePlacer().get(choix) ? true : false;
			i=+1;
		}
		
        return cooPiece;
    }

	public ArrayList<ArrayList<Integer>> choixDeplacement(int largeur, int longueur, PlateauPuzzle plateau) {
		
		PlateauPuzzle copiePlateau = null; //création d'une copie du plateau a chaque boucle

		try{
			copiePlateau = null;
			copiePlateau = (PlateauPuzzle) ((PlateauPuzzle) plateau).clone();

		}catch(Exception e){
			System.out.println("Impossible de créer une copie du plateau : "+e);
		}


        ArrayList<Integer> choixCooPiece = selectPiece(largeur,longueur,plateau);
		
		ArrayList<Integer> choixCoo = null;
		int bestScore = -1;
		
		for (int i = 0; i < largeur; i++){
			for (int j = 0; j < longueur; j++){
				ArrayList<Integer> cooAPlacer = new ArrayList<Integer>(Arrays.asList(i, j));
				
				//si la case est vide
				if(plateau.getPiece(choixCooPiece) == null){
					int x = i;
					int y = j;
					
					//s'il y a au moins une pièce a deux case à la ronde
					if( (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x-1,y))) != null)
						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+1,y))) != null)
						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+2,y))) != null)

						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x,y-1))) != null)
						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x,y-2))) != null)
						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x,y+1))) != null)
						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x,y+2))) != null)

						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+1,y+1))) != null)
						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+2,y+1))) != null)
						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x-1,y+1))) != null)
						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x-2,y+1))) != null)
						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+1,y+2))) != null)
						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+2,y+2))) != null)
						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x-1,y+2))) != null)
						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+2,y+2))) != null)
							
						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+1,y-1))) != null)
						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+2,y-1))) != null)
						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x-1,y-1))) != null)
						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x-2,y-1))) != null)
						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+1,y-2))) != null)
						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+2,y-2))) != null)
						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x-1,y-2))) != null)
						|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x-2,y+2))) != null)
						){
							//Si différent de la pièce
							if(cooAPlacer != choixCooPiece){
								
								copiePlateau.removePiece(copiePlateau.getPiece(cooAPlacer));
								if(plateau.validePlacement(copiePlateau.getPiece(choixCooPiece), cooAPlacer)){
									copiePlateau.addPiece(plateau.getPiece(choixCooPiece), plateau.getPiece(choixCooPiece).getCoo());
									if(copiePlateau.movePiece(copiePlateau.getPiece(choixCooPiece), cooAPlacer)){

										if(copiePlateau.getScore() > bestScore){
											bestScore = copiePlateau.getScore();
											choixCoo = new ArrayList<Integer>(cooAPlacer);

										}
									}
								}
							}
					}
				}
						
			}
		}
		
		return new ArrayList<ArrayList<Integer>>(Arrays.asList(choixCooPiece,choixCoo));
	}

	public ArrayList<ArrayList<Integer>> choixAjout(int largeur, int longueur, PlateauPuzzle plateau) {
		ArrayList<Integer> choixCoo = null;
		int bestScore = -1;
	
        int choixPiece = choix(1, plateau.getPieceAJouer().size());
		
		//Si aucune pièce n'est déjà posé, pas besoin de dresser un tableau de possibilités
		if(!plateau.getPiecePlacer().isEmpty()){
			for (int i = 0; i < largeur; i++){
				for (int j = 0; j < longueur; j++){
					ArrayList<Integer> coo = new ArrayList<Integer>(Arrays.asList(i, j));
					
					PlateauPuzzle copiePlateau = null;
					
					try{
						copiePlateau = null;
						copiePlateau = (PlateauPuzzle) ((PlateauPuzzle) plateau).clone();

					}catch(CloneNotSupportedException e){
						System.out.println("Impossible de créer une copie du plateau : "+e);
					}

					ArrayList<Integer> coocopie = new ArrayList<Integer>(Arrays.asList(i, j));
					int posY = 0;

					int xx = 0;
					int yy = 0;


					while(!(plateau.getPieceAJouer().get(choixPiece-1).getGrid()[xx][yy+posY])){
						coocopie.set(1, (int) coocopie.get(1)-1);
						posY += 1;
					}

					//si la case est vide
					if(plateau.getPiece(coocopie) == null){
						int x = i;
						int y = j;
						//s'il y a au moins une pièce a deux case à la ronde
						if( (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x-1,y))) != null)
							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x-2,y))) != null)
							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+1,y))) != null)
							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+2,y))) != null)

							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x,y-1))) != null)
							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x,y-2))) != null)
							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x,y+1))) != null)
							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x,y+2))) != null)

							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+1,y+1))) != null)
							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+2,y+1))) != null)
							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x-1,y+1))) != null)
							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x-2,y+1))) != null)
							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+1,y+2))) != null)
							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+2,y+2))) != null)
							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x-1,y+2))) != null)
							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+2,y+2))) != null)
								
							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+1,y-1))) != null)
							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+2,y-1))) != null)
							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x-1,y-1))) != null)
							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x-2,y-1))) != null)
							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+1,y-2))) != null)
							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x+2,y-2))) != null)
							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x-1,y-2))) != null)
							|| (plateau.getPiece(new ArrayList<Integer>(Arrays.asList(x-2,y+2))) != null)
							){
								//Si on peut la placer
								if(plateau.validePlacement(copiePlateau.getPieceAJouer().get(choixPiece-1), coocopie)){
									if(copiePlateau.addPiece(copiePlateau.getPieceAJouer().get(choixPiece-1), coo)){

										if(copiePlateau.getScore() > bestScore){
											bestScore = copiePlateau.getScore();
											choixCoo = new ArrayList<Integer>(coo);
										}
									}
								}
							}
					}
					
				}
			}	
		}
		
		if(choixCoo == null){
			if(!plateau.getPiecePlacer().isEmpty())
				choixCoo = selectCoordonnees(largeur,longueur);
			else
				choixCoo = selectCoordonnees(largeur,longueur);
		}
		ArrayList<Integer> pieceChoix = new ArrayList<Integer>();
		pieceChoix.add(choixPiece-1);

		return new ArrayList<ArrayList<Integer>>(Arrays.asList(pieceChoix,choixCoo));
	}
    
}
