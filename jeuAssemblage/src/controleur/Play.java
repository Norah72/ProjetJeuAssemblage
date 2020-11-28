package controleur;

import file.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import modele.*;
import vue.*;

public class Play {
    //Attibuts
    
    public InterfacePlay joueurActuel;
    public boolean affichageGraph;
    public PlateauPuzzle plateau;
    
    private boolean montreMessage;
    private boolean end;
    
    private int largeurPlateau =5;
    private int longueurPlateau =5;
    private boolean ia = false;
    
     
    //Constructeur
    
    public Play(boolean affichageGraph){
        this.affichageGraph = affichageGraph;
        this.joueurActuel = new PlayJoueur();
        if(this.affichageGraph){
            affiche("Méthode d'affichage graphique à implémenter");
        }else{
            this.menu();
        }
    }
    
    private void menu(){
        this.montreMessage = true;
	affiche("\n----- Menu -----");
	affiche("1- Nouvelle partie");
	affiche("2- Charger la dernière partie");
	affiche("3- Règle de jeu");
	affiche("4- Score de jeu\n");
        
        affiche("Que voulez vous faire ?");
	int choix = this.joueurActuel.choix(1, 4);
        
        if (choix == 1){
            boolean reinitialiser = true;
            
            boolean ia = false;
            
            while(reinitialiser){
                
                affiche("Voulez vous que l'ordinateur joue cette partie ? 0-Oui / 1-Non");
                this.ia = joueurActuel.choix(0,1)==0;
                
                affiche("Le plateau peut faire entre 5 et 20 cases de côté");
                affiche("Veuillez entrer la largeur du plateau : ");
                this.largeurPlateau = this.joueurActuel.choix(5,20);
                affiche("Veuillez entrer la longueur du plateau : ");
                this.longueurPlateau = this.joueurActuel.choix(5,20);
                
                affiche("\nCette configuraion vous convient-elle ? 0-Oui / 1-Non : ");
                String str = "==> ";
                if (ia){
                    str += "L'ordinateur va jouer";
                }else{
                    str += "Vous allez jouer";
                }
                affiche(str+" sur un plateau "+this.largeurPlateau+" x "+this.longueurPlateau+"\n");
                if(this.joueurActuel.choix(0,1)==0)
                    reinitialiser = false;
            }
            nouvellePartie();
            this.end=false;
            jeu();
            
        }else if (choix == 2){
            System.out.println("Charger partie à implémenté");
        }else if (choix == 3){
            System.out.println("Règle à implémenté");
        }else if (choix == 4){
            System.out.println("Scores à implémenté");
        }
        
    }
    
    //Méthodes de jeu
    
    public void nouvellePartie(){
        if(this.ia){
            this.plateau = new PlateauPuzzle(this.largeurPlateau,this.longueurPlateau);
            creationPieceRandom();
        }else{
            boolean reinitialiser = true;
            while(reinitialiser){
                this.plateau = new PlateauPuzzle(this.largeurPlateau,this.longueurPlateau);
                creationPieceRandom();
                etatPlateau();
                printPiece();
                
                affiche("\nCette configuraion vous convient-elle ? 0-Oui / 1-Non : ");
                if(this.joueurActuel.choix(0,1)==0)
                        reinitialiser = false;
            }
        }
    }

    private void creationPieceRandom(){
        int largeur = 0;
        int longueur = 0;
        
        int max = (this.largeurPlateau + this.longueurPlateau) /4 ;
        if(max  < 3 )
            max = 3;
        else if (max > 5)
            max = 5;
        
        int minPiece = (this.largeurPlateau + this.longueurPlateau)/3;
        int maxPiece = (this.largeurPlateau + this.longueurPlateau)/2;
        int randPiece = new Random().nextInt(maxPiece-minPiece) + minPiece;
        
        ArrayList<String> pieceString = this.plateau.getPieceString();
        
        for(int i = 0; i <= randPiece; i++){

            String piece = pieceString.get(new Random().nextInt(pieceString.size()));
            
            if(piece.equals("PieceH")){
                largeur = rdmMin(3,max);
                longueur = rdmMin(3,max);
            }else if(piece.equals("PieceL")){
                largeur = rdmMin(2,max);
                longueur = rdmMin(2,max);
            }else if(piece.equals("PieceRectangle")){
                largeur = rdmMin(1,max-1);
                longueur = rdmMin(1,max-1);
            }else if(piece.equals("PieceT")){
                largeur = rdmMin(2,max);
                longueur = rdmMin(3,max);
            }
            this.plateau.newPiece(piece, largeur, longueur);
        }
    }
    
    private void jeu(){
        while(!this.end){
            //
        }
    }
    
    //Méthode d'affichage
    
    private void etatPlateau(){
        System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("Voici le plateau:");
        System.out.println(this.plateau);
        
    }
    
    private void printPiece(){
        System.out.println("Voici vos pièce: ");
        for(int i = 0 ; i <= this.plateau.getPieceAJouer().size()-1; i++){
                System.out.println("Piece "+(i+1)+":");
                System.out.println(this.plateau.getPieceAJouer().get(i));
        }
    }
    
    private void affiche(String texte){
        if(montreMessage){
            System.out.println(texte);
        }
    }
    
    //Autres Méthodes
    
    private int rdmMin(int min, int max){
        int rand = 0;
        while(rand < min)
            rand = new Random().nextInt(max+1);
        return rand;
    }
    
}
