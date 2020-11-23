package controleur;

import modele.PlateauPuzzle;
import vue.*;

public class PlayConsole extends AbstractPlay {

    // Attributs 
    
    public PlayConsole(PlateauPuzzle plateau){
        super(plateau);
    }
    
    protected void play(){

        while(!this.end){
            System.out.println();
            System.out.println("----- Menu -----");
            System.out.println("1- Nouvelle partie");
            System.out.println("2- Charger la dernière partie");
            System.out.println("3- Règle de jeu");
            System.out.println("4- Score de jeu\n");
            
            int choix = choixValide(1, 4, "Que voulez vous faire ?");
            /*if(choix == 1){
                while(reinitialiser){
                        initialisationPlateau();
                        creationPieceRandom();
                        etatPlateau();
                        if(choixYesNo("Cette configuraion vous convient-elle ?"))
                                reinitialiser = false;
                }

                System.out.println();
                play();
            }

            else if(choix == 2){
                chargerPartie();
                System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                System.out.println("[---- Content de vous revoir "+this.pseudo+" ! ----]");
                System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
                etatPlateau();
                play();
            }
            else if(choix == 3){
                regle();
            }
            else if(choix == 4){
                afficheScore();
            }*/
            System.out.println(choix);
        }
    }
    
    protected abstract void initialisationPlateau(){
        return null;
    }
    
    protected abstract void ajoutPiece(){
        return null;
    }
    
    protected abstract void supprimerPiece(){
        return null;
    }
    
    protected abstract void rotationPiece(){
        return null;
    }
}
