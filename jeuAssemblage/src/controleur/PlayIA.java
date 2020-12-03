package controleur;

import java.util.*;
import modele.*;

public class PlayIA implements InterfacePlay{
	
			
	private ArrayList<Integer> chanceChoixJeu = new ArrayList<Integer>(Arrays.asList(3, 3, 2, 1, 2));
	private ArrayList<Integer> listeChoixIa = new ArrayList<Integer>();
	private ArrayList<String> test = new ArrayList<String>(Arrays.asList("Placer", "déplacer", "supprimer","rotation sans placer", "rotation piece", "erreur choix 6 ","fin"));
	
	public void PlayIa(){
		
	}
	
	public int choixJeu(PlateauPuzzle plateau) {
        int nbrChoix = 2;
		
        if(!plateau.getPiecePlacer().isEmpty())
            nbrChoix = 5;

        if(plateau.getPieceAJouer().isEmpty())
            nbrChoix = 7;
		
		if( !(nbrChoix == 2 && listeChoixIa.size() == 4 ) || !(nbrChoix == 5 && listeChoixIa.size() == 11 ) ){ // évite de refaire la liste si elle existe déjà
			listeChoixIa.clear();
			if(nbrChoix == 2){
				for(int i = 1; i <= this.chanceChoixJeu.get(0); i++){
					listeChoixIa.add(1);
				}
				for(int i = 1; i <= this.chanceChoixJeu.get(3); i++){
					listeChoixIa.add(4);
				}
			}
			if(nbrChoix == 5){
				for(int i = 1; i <= nbrChoix; i++){
					for(int j = 1; j <= this.chanceChoixJeu.get(i-1); j++){
						listeChoixIa.add(i);
					}
				}
			}
			
		}
		
		int choix = -1;
		if(!plateau.getPieceAJouer().isEmpty()){
			choix = this.listeChoixIa.get(choix(1,this.listeChoixIa.size())-1);
			//System.out.println(this.listeChoixIa.size()+ " + "+ choix);
		}else{
			choix = 7;
		}
		
        if(plateau.getPiecePlacer().isEmpty()){
            if(choix == 2)
                choix = 4;
        }
		
		if(!plateau.getPiecePlacer().isEmpty()){
            if(choix == 3)
                choix = 2;
        }

		//System.out.println(""+this.test.get(choix-1));
		
        return choix;
	}
    
    public int choix(int borneInf, int borneSup){
        //System.out.println("Inf : "+ borneInf+ "  |  Sup : "+borneSup);
        int choix = ((new Random()).nextInt(borneSup-borneInf + 1))+borneInf;
        //System.out.println("Chhooiixx : "+choix); // chhooiix ?? D'accord =l
        return choix;
    }
    
    public ArrayList<Integer> selectCoordonnees(int largeurPlateau, int longueurPlateau){
        ArrayList coo = new ArrayList(Arrays.asList(((new Random()).nextInt(largeurPlateau))+1, ((new Random()).nextInt(longueurPlateau))+1));
		//System.out.println(largeurPlateau+" largeurPlateau "+longueurPlateau+"largeurPlateau - coo :"+coo.get(0)+","+coo.get(1));
		return coo;
    }
    
    public ArrayList<Integer> selectPiece(int largeurPlateau, int longueurPlateau, PlateauPuzzle plateau){
		int choix = choix(1,+plateau.getPiecePlacer().size())-1;
		ArrayList<Integer> cooPiece = new ArrayList<Integer>(plateau.getPiecePlacer().get(choix).getCoo());

		//Permet de récupérer les coordonnees de la piece, car cooPiece représente les coordonnees en haut a gauche de la pièce, sauf que cela peut être unecase false, non détecter par le plateau
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
        ArrayList<Integer> choixCooPiece = selectPiece(largeur,longueur,plateau);

		ArrayList<ArrayList<Integer>> listPossibilite = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> cooAvant = new ArrayList<Integer>(plateau.getPiece(choixCooPiece).getCoo());

		plateau.removePiece(plateau.getPiece(choixCooPiece)); //supprime la pièce afin de faire une liste de possibilités
		
		for (int i = 0; i < largeur; i++){
			for (int j = 0; j < longueur; j++){
				ArrayList<Integer> coo = new ArrayList<Integer>(Arrays.asList(i, j));

				//si la case est vide
				if(plateau.getPiece(coo) == null){
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
							//Si on peut placer la pièce
							if(plateau.validePlacement(plateau.getPieceAJouer().get(plateau.getPieceAJouer().size()-1), coo) && (coo != cooAvant))
								listPossibilite.add(new ArrayList<Integer>(Arrays.asList(i, j)));
					}
				}
					
			}
		}
		
		//Remet la pièce supprimer
		plateau.addPiece(plateau.getPieceAJouer().get(plateau.getPieceAJouer().size()-1), cooAvant);
		
		
		ArrayList<Integer> choixCoo = null;
		int bestScore = -1;
	
		//choix de la meilleur possibilité
		for (int i = 0; i < listPossibilite.size(); i++){
			PlateauPuzzle copiePlateau = null; //création d'une copie du plateau a chaque boucle
			try{
				copiePlateau = null;
				copiePlateau = (PlateauPuzzle) ((PlateauPuzzle) plateau).clone();
				
			}catch(Exception e){
				System.out.println("Impossible de créer une copie du plateau : "+e);
			}
			
			copiePlateau.movePiece(copiePlateau.getPiece(choixCooPiece), listPossibilite.get(i));
				
			if(copiePlateau.getScore() > bestScore){
				bestScore = copiePlateau.getScore();
				choixCoo = new ArrayList<Integer>(listPossibilite.get(i));
			}
		}
		if(choixCoo == null)
			choixCoo = listPossibilite.size() >= 1 ? (listPossibilite.get(choix(1,listPossibilite.size())-1)) : null;
		
		return new ArrayList<ArrayList<Integer>>(Arrays.asList(choixCooPiece,choixCoo));
	}

	public ArrayList<ArrayList<Integer>> choixAjout(int largeur, int longueur, PlateauPuzzle plateau) {
	
        int choixPiece = choix(1, plateau.getPieceAJouer().size());

		ArrayList<ArrayList<Integer>> listPossibilite = new ArrayList<ArrayList<Integer>>();
		
		//Si une pièce et déjà posé, pas besoin de dresser un tableau de possibilités
		if(!plateau.getPiecePlacer().isEmpty()){
			for (int i = 0; i < largeur; i++){
				for (int j = 0; j < longueur; j++){
					ArrayList<Integer> coo = new ArrayList<Integer>(Arrays.asList(i, j));

					//Si la case est vide
					if(plateau.getPiece(coo) == null){
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
								if(plateau.validePlacement(plateau.getPieceAJouer().get(choixPiece-1), coo))
									listPossibilite.add(new ArrayList<Integer>(Arrays.asList(i, j)));
							}
					}
					
				}
			}	
		}
		
		ArrayList<Integer> choixCoo = null;
		int bestScore = -1;
	
		//choix de la meilleur possibilité
		for (int i = 0; i < listPossibilite.size(); i++){
			PlateauPuzzle copiePlateau = null;
			
			try{
				copiePlateau = null;
				copiePlateau = (PlateauPuzzle) ((PlateauPuzzle) plateau).clone();

			}catch(Exception e){
				System.out.println("Impossible de créer une copie du plateau : "+e);
			}
			
			copiePlateau.addPiece(copiePlateau.getPieceAJouer().get(choixPiece-1), listPossibilite.get(i));
				
			if(copiePlateau.getScore() > bestScore){
				bestScore = copiePlateau.getScore();
				choixCoo = new ArrayList<Integer>(listPossibilite.get(i));
			}
		}
		
		
		if(choixCoo == null){
			if(!plateau.getPiecePlacer().isEmpty())
				choixCoo = listPossibilite.size() != 0 ? listPossibilite.get(choix(1,listPossibilite.size())-1) : selectCoordonnees(largeur,longueur);
			else
				choixCoo = selectCoordonnees(largeur,longueur);
		}
		ArrayList<Integer> pieceChoix = new ArrayList<Integer>();
		pieceChoix.add(choixPiece);

		return new ArrayList<ArrayList<Integer>>(Arrays.asList(pieceChoix,choixCoo));
	}


    
}
