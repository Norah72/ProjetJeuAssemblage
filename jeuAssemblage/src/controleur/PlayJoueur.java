package controleur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import modele.PlateauPuzzle;

public class PlayJoueur implements InterfacePlay{
	
	public PlayJoueur(){
	}
	
	//mettre aussi le menu de la consolle ici ? comme ça il y aura charger et sauvegarde partie ce qui est pour le joueur
	
	public int choixJeu(int nbrChoix){
		
		System.out.println("1- Placer une pièce");
		if(nbrChoix == 5){
			System.out.println("2- Déplacer une pièce");
			System.out.println("3- Supprimer une pièce");
			System.out.println("4- Rotation d'une pièce");
			System.out.println("5- Sauvegarder la partie");
			if(nbrChoix == 6)
				System.out.println("6- Score/Fin");
		}else{
			System.out.println("2- Sauvegarder la partie");
		}

		int choix = choix(1,nbrChoix);
		
		//if (choix == ) si choix = sauvegarde en fonction du nbr choix alors faire tout (psued tout ca...)
		
		return choix;
	}
	
	public int selectAjoutePiece(int nbrPieceAJouer){
		System.out.println("Que pièce voulez vous ajouter ? (Veuillez indiqué le numéro de la pièce)");
		System.out.println("Taper r pour retour");
		return choixValide(1, nbrPieceAJouer);	
	}
	
	public ArrayList coordonnees(int largeurPlateauX, int longueurPlateauY){
		System.out.println("Indiquer les coordonnées où vous voulez placer la partie haut gauche de la pièce (Exemple: 2,3)");
		return valideCoordonnees(largeurPlateauX, longueurPlateauY);
	}
	
	public int choix(int borneInf, int borneSup){
		System.out.println("Que voulez vous faire ?");
		return choixValide(borneInf, borneSup);
	}
	
	public int choixValide(int borneInf, int borneSup){
		int choix = -1;
		while(choix == -1){
			Scanner choixScan = new Scanner(System.in);
			try{
				if(choixScan.hasNext("r")){
					choix = -2;
				}else{
					choix = choixScan.nextInt();
					while(choix < borneInf || choix > borneSup){
						System.out.println(" Choix non accepter, vous devez choisir une valeur entre"+borneInf+" et "+borneSup);
						choix = choixScan.nextInt();
					}
				}
			}
			catch(Exception e){
				System.out.println("Choix invalide");
			}
		}
		return choix;
	}
	
	private ArrayList valideCoordonnees(int largeurPlateauX, int longueurPlateauY) throws InputMismatchException{
		int cooPlaceX =-1;
		int cooPlaceY =-1;
		boolean valide = false;
		
		while(!valide) {
			Scanner sc = new Scanner(System.in);
			
			try{
				String cooScan = sc.next();

				Scanner scanVirgule = new Scanner(cooScan).useDelimiter(",");
				cooPlaceX = scanVirgule.nextInt();
				cooPlaceY = scanVirgule.nextInt();
				scanVirgule.close();
				
				if(((cooPlaceX < 0) || (cooPlaceX > largeurPlateauX-1)) || ((cooPlaceY < 0) || (cooPlaceY > longueurPlateauY-1)))
					System.out.println("Coordonnées non valide : vous avez entrée des coordonnées inférieur a zero ou supérieur a la grandeur du plateau");
				else
					valide = true;
			}
			catch (Exception e) {
				System.out.println("Vous devez saisir sous le format 2,3 !");
				continue;
            }

		}
		return new ArrayList<Integer>(Arrays.asList((Integer)cooPlaceX, (Integer)cooPlaceY));
	}
	
	public ArrayList selectPieceValide(int largeurPlateauX, int longueurPlateauY, PlateauPuzzle plateau){
		System.out.println("Veuillez sélectionner une pièce en indiquant une de ses coordonnées en format 2,3)");
		boolean valide = false;
		ArrayList coo = null;
		while(!valide){
			coo = valideCoordonnees(largeurPlateauX, longueurPlateauY);
			valide = plateau.selectPiece(coo);
			if(!valide)
				System.out.println("Il n'y a pas de pièce ici");
		}
		return coo;
	}
	
	public boolean score(){
		return choixYesNo("Voulez vous arretez la partie ?");
	}
	
	private boolean choixYesNo(String texte){
		System.out.println(texte+" 0-non / 1-Oui");
		
		if(choixValide(0,1) == 0)
			return false;
		return true;
	}
	
	public String finDePartie(){
		Scanner pseudoScan = new Scanner(System.in);
		return pseudoScan.next();
	}
	
	
}
