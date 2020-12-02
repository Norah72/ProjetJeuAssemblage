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
		Integer cooY = cooPiece.get(1);
		
		//System.out.println("cooPiece av "+cooPiece+plateau.getPiecePlacer().get(choix));
		
		//Permet de récupérer les coordonnees de la piece, car cooPiece représente les coordonnees en haut a gauche de la pièce, sauf que cela peut être unecase false, non détecter par le plateau
		boolean valide = false;
		int i=0;
		while(!valide){
			cooPiece.set(1, cooPiece.get(1)+i);
			valide = plateau.getPlateau().get(cooPiece) == plateau.getPiecePlacer().get(choix) ? true : false;
			i=+1;
			/*System.out.println("cooPiece pendant "+cooPiece);
			System.out.println(plateau.getPiecePlacer().get(choix).getCoo());*/
		}
		
		//System.out.println("cooPiece ap "+cooPiece);
		cooPiece.add(cooY);
		//System.out.println("cooPiece ap "+cooPiece);
        return cooPiece;
    }


    
}
