package controleur;

import java.util.*;
import modele.*;

public class PlayJoueur implements InterfacePlay{
    
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