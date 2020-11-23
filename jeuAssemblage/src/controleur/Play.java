package controleur;

import java.util.Scanner;
import modele.PlateauPuzzle;
import vue.*;

public class Play {
    
    AbstractPlay controleur;
    
    public void menu(){
        
        PlateauPuzzle plateau = new PlateauPuzzle(0,0);
        
        
        System.out.println("--------------------------------------------");
        System.out.println("| ## Bienvenue dans le jeu Assemblage ! ## |");
        System.out.println("--------------------------------------------");

        System.out.println();
        System.out.println("----- Menu -----");
        System.out.println("1- Vue Console");
        System.out.println("2- Vue Graphique");

        int choix = -1;
        while(choix == -1){
            Scanner choixScan = new Scanner(System.in);
            try{
                choix = choixScan.nextInt();
                while(choix < 1 || choix > 2){
                        System.out.println("Choix invalide, vous devez donner '1', ou '2'");
                        choix = choixScan.nextInt();
                }
            }
            catch(Exception e){
                    System.out.println("Choix invalide");
            }
        }
        
        if (choix == 1){
            System.out.println("Vous avez choisi la vue textuel en terminal");
            controleur = new PlayConsole(plateau);
        }else{
            System.out.println("Vous avez choisi la vue en interface graphique");
        InterfaceGraphique vue = new InterfaceGraphique(plateau);    
        //controleur = new PlayInterface(InterfaceGraphique vue, PlateauPuzzle plateauConsole);
        }

    }
}
