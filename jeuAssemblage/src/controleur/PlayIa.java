package controleur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import modele.PlateauPuzzle;

public class PlayIa implements InterfacePlay{
	
	public PlayIa(){
		
	}

	public int choixJeu(int nbrChoix) {
		int choix = 3;
		while(choix == 3)
			choix = ((new Random()).nextInt(nbrChoix))+1;
		return choix;
	}

	public int choix(int borneInf, int borneSup) {
		int choix = ((new Random()).nextInt(borneSup-borneInf))+borneInf;
		System.out.println("Chhooiixx : "+choix);
		return choix;
	}

	public int selectAjoutePiece(int nbrPieceAJouer) {
		System.out.println("nbrPieceAJouer "+ nbrPieceAJouer);
		int choix = ((new Random()).nextInt(nbrPieceAJouer))+1;
		System.out.println("SSelect peice : "+choix);
		return choix;
	}

	public ArrayList selectPieceValide(int largeurPlateauX, int longueurPlateauY, PlateauPuzzle plateau) {
		int cooX = ((new Random()).nextInt(largeurPlateauX))+1;
		int cooY = ((new Random()).nextInt(longueurPlateauY))+1;

		while(plateau.getPlateau().get(new ArrayList(Arrays.asList(cooX , cooY))) == null){
			cooX = ((new Random()).nextInt(largeurPlateauX))+1;
			cooY = ((new Random()).nextInt(longueurPlateauY))+1;
			
		}
		
		return new ArrayList(Arrays.asList(cooX , cooY));
	}

	public ArrayList coordonnees(int largeurPlateauX, int longueurPlateauY) {
		return new ArrayList(Arrays.asList(((new Random()).nextInt(largeurPlateauX))+1, ((new Random()).nextInt(longueurPlateauY))+1));
	}

	public boolean score() {
		System.out.println("fini");
		return true;
	}

	public String finDePartie() {
		return "couuoocuul";
	}
	

}
