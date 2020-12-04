package controleur;

import java.util.*;
import modele.*;

public class PlayJoueur implements InterfacePlay{
	private ArrayList<EnumAction> plateauPlein = new ArrayList<EnumAction>(Arrays.asList(EnumAction.DEPLACER, EnumAction.SUPPRIMER, EnumAction.ROTATION_PIECEPLACER, EnumAction.SAUVEGARDER, EnumAction.FIN_DE_PARTIE, EnumAction.QUITTER));
	private ArrayList<EnumAction> plateauAvecPiecePlacer = new ArrayList<EnumAction>(Arrays.asList(EnumAction.PLACER, EnumAction.DEPLACER, EnumAction.SUPPRIMER, EnumAction.ROTATION_PIECEAJOUER, EnumAction.ROTATION_PIECEPLACER, EnumAction.SAUVEGARDER, EnumAction.QUITTER));
	private ArrayList<EnumAction> plateauSansPiecePlacer = new ArrayList<EnumAction>(Arrays.asList(EnumAction.PLACER, EnumAction.ROTATION_PIECEAJOUER, EnumAction.SAUVEGARDER, EnumAction.QUITTER));

	public EnumAction choixJeu(PlateauPuzzle plateau) {
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
        
		//Affichage de la liste
        for(int i = 0; i < choixListe.size(); i++){
			System.out.println(i+" - "+choixListe.get(i));
		}

		int choix = choix(0,choixListe.size()-1);
		
		return choixListe.get(choix);
	}
    
    public int choix(int borneInf, int borneSup){
        int choix = -1;
            while(choix == -1){
                Scanner choixScan = new Scanner(System.in);
                try{
                    if(choixScan.hasNext("r")){
                        choix = -2;
                    }else{
                        choix = choixScan.nextInt();
                        while(choix < borneInf || choix > borneSup){
                            System.out.println("Choix invalide, veuillez donnez un nombre entre "+borneInf+" et "+borneSup);
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
    
    public ArrayList<Integer> selectCoordonnees(int largeurPlateau, int longueurPlateau){
        int x =-1;
        int y =-1;
        boolean valide = false;

        while(!valide) {
            Scanner sc = new Scanner(System.in);

            try{
                String cooScan = sc.next();

                Scanner scanVirgule = new Scanner(cooScan).useDelimiter(",");
                x = scanVirgule.nextInt();
                y = scanVirgule.nextInt();
                scanVirgule.close();

                if(((x < 0) || (x > largeurPlateau-1)) || ((y < 0) || (y > longueurPlateau-1)))
                        System.out.println("Coordonnées non valide : vous avez entrée des coordonnées inférieur a zero ou supérieur a la grandeur du plateau");
                else
                        valide = true;
            }
            catch (Exception e) {
                System.out.println("Vous devez saisir sous le format 2,3 !");
                continue;
            }

        }
        return new ArrayList<Integer>(Arrays.asList((Integer)x, (Integer)y));
    }
    
    public ArrayList<Integer> selectPiece(int largeurPlateau, int longueurPlateau, PlateauPuzzle plateau){
        ArrayList<Integer> coo = selectCoordonnees(largeurPlateau,longueurPlateau);
        while(plateau.getPiece(coo) == null){
            System.out.println("Il n'y a pas pièce ici ! Redonnez des coordonnées :");
            coo = selectCoordonnees(largeurPlateau,longueurPlateau);
        }
        return coo;
    }

	public ArrayList<ArrayList<Integer>> choixDeplacement(int largeur, int longueur, PlateauPuzzle plateau) {
		System.out.println("Que pièce voulez vous déplacer ? (Veuillez indiqué une de ses coordonnées en format 2,3)");
		ArrayList<Integer> piece = selectPiece(largeur,longueur,plateau);
		
		System.out.println("Indiquer les coordonnées où vous voulez placer la partie haut gauche de la pièce (Exemple: 2,3)");
		ArrayList<Integer> selectCoo = selectCoordonnees(largeur,longueur);
		
		return new ArrayList<ArrayList<Integer>>(Arrays.asList(piece,selectCoo));
	}

	public ArrayList<ArrayList<Integer>> choixAjout(int largeur, int longueur, PlateauPuzzle plateau) {
		
        System.out.println("Quel pièce voulez vous ajouter ? (Veuillez indiqué le numéro de la pièce)");
        
        int choixPiece = choix(1, plateau.getPieceAJouer().size());

		System.out.println("Indiquer les coordonnées où vous voulez placer la partie haut gauche de la pièce (Exemple: 2,3)");

		ArrayList<Integer> choixCoo = selectCoordonnees(largeur, longueur);

		ArrayList<Integer> pieceChoix = new ArrayList<Integer>();
		pieceChoix.add(choixPiece-1);
		
		return new ArrayList<ArrayList<Integer>>(Arrays.asList(pieceChoix,choixCoo));
	}
    
}