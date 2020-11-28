package controleur;

import java.util.ArrayList;
import modele.PlateauPuzzle;

public interface InterfacePlay {
	
	public int choixJeu(int nbrChoix);
	public int choix(int borneInf, int borneSup);
	public int selectAjoutePiece(int nbrPieceAJouer);
	public ArrayList selectPieceValide(int largeurPlateauX, int longueurPlateauY, PlateauPuzzle plateau);
	public ArrayList coordonnees(int largeurPlateauX, int longueurPlateauY);
	public boolean score();
	public String finDePartie();
	
}
