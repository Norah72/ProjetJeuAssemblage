package controleur;

import java.util.*;

public class PlayMenu {
    
    public static void menu(){
        //Affichage console du choix
        System.out.println("--------------------------------------------");
        System.out.println("| ## Bienvenue dans le jeu Assemblage ! ## |");
        System.out.println("--------------------------------------------");
        System.out.println();
        System.out.println("----- Menu -----");
        System.out.println("1- Vue Console");
        System.out.println("2- Vue Graphique");
        
        //Récupération du choix de l'Utilisateur
        int vue = -1;
        System.out.println();
        System.out.println("Que voulez vous faire ?");
        
        while(vue == -1){
            Scanner scan = new Scanner(System.in);
            try{
                vue = scan.nextInt();
                while( vue < 1 || vue > 2 ){
                    System.out.println("Choix invalide");
                    vue = scan.nextInt();
                }
            }
            catch(Exception e){
                System.out.println("Choix invalide");
            }
        }
        
        Play play = new Play(vue==2);
        
    }
}
