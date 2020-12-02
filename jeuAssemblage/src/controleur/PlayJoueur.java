package controleur;

import java.util.*;
import modele.*;

public class PlayJoueur implements InterfacePlay{
	
	public int choixJeu(PlateauPuzzle plateau) {

        int nbrChoix = 3;
		System.out.println("1- Placer une pièce");
		System.out.println("2- Rotation d'une pièce");
        System.out.println("3- Sauvegarder la partie");
		
        if(!plateau.getPiecePlacer().isEmpty()){
            nbrChoix = 6;
			
		}
        
		 if(plateau.getPieceAJouer().isEmpty())
		
        System.out.println("1- Placer une pièce");
		
        if(nbrChoix >= 6){
            System.out.println("2- Déplacer une pièce");
            System.out.println("3- Supprimer une pièce");
			if(plateau.getPieceAJouer().isEmpty()){
				System.out.println("4- Rotation d'une pièce sur la plateau");
				System.out.println("5- Sauvegarder la partie");
			}
			if(!plateau.getPieceAJouer().isEmpty())
				System.out.println("4- Rotation d'une pièce");
				System.out.println("5- Rotation d'une pièce sur la plateau");
				System.out.println("6- Sauvegarder la partie");
        }
        if(nbrChoix == 7){
            System.out.println("6- Score/Fin");
        }
        else if(nbrChoix == 3){
            System.out.println("2- Rotation d'une pièce");
            System.out.println("3- Sauvegarder la partie");
        }
        
        System.out.println("\n0- Quitter sans sauvegarder");

		int choix = choix(0,nbrChoix);
		
        if(plateau.getPiecePlacer().isEmpty()){
            if(choix == 2)
                choix = 4;
            if(choix == 3)
                choix = 6;
        }
		
		return choix;
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
    
}