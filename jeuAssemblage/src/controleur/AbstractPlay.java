package controleur;

import file.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import modele.PlateauPuzzle;
import vue.*;

public abstract class AbstractPlay extends MouseAdapter implements ActionListener{
    
    // Attributs 
    
    protected PlateauPuzzle plateau;
    protected int largeurPlateauX,longueurPlateauY;
    protected ScoreFile sauvegardeScore;
    
    protected boolean end,stop = false;
    protected String pseudo = null;
    
    // Constructeur
    
    public AbstractPlay(PlateauPuzzle plateau){
        this.plateau = plateau;
        play();
    }
    
    protected abstract void play();
    
    protected abstract void initialisationPlateau();
    
    protected void creationPieceRandom(){		
        int largeur = 0;
        int longueur = 0;

        // minimum et maximum pour la taille des pièces
        int max =  (this.largeurPlateauX + this.longueurPlateauY) /4 ;

        // On ne veux pas de pièce trop grandes, on borne donc leur taille à 5, pour permettre d'avoir 
        //une grande grille de petites pièces, et non des pièces de taille relatives à la taille du plateau.
        if (max < 3) {
                max = 3;
        }else if(max > 5){
                max = 5;
        }

        // minimum et maximum pour le nombre de pièce, puis aléatoire entre ces deux valeurs
        int minPiece = (this.largeurPlateauX + this.longueurPlateauY) /3;
        int maxPiece = (this.largeurPlateauX+this.longueurPlateauY)/2;
        int randPiece = new Random().nextInt(maxPiece - minPiece ) + minPiece;

        for(int i = 0; i <= randPiece; i++){

            String piece = this.plateau.getPieceString().get(new Random().nextInt(this.plateau.getPieceString().size()));

            if(piece.equals("PieceH")){
                    largeur = rdmMinimum(3,max);
                    longueur = rdmMinimum(3,max);
            }else if(piece.equals("PieceL")){
                    largeur = rdmMinimum(2,max);
                    longueur = rdmMinimum(2,max);
            }else if(piece.equals("PieceRectangle")){
                    largeur = rdmMinimum(1,max-1);
                    longueur = rdmMinimum(1,max-1);
            }else if(piece.equals("PieceT")){
                    largeur = rdmMinimum(2,max);
                    longueur = rdmMinimum(3,max);
            }
            
            plateau.newPiece(piece, largeur, longueur);
            
        }
    }

    protected void sauvegarderPartie(){
        SauvegardeFichier sauvegarde = new SauvegardeFichier(this);
        try{
            sauvegarde.ecrire();
        }
        catch(Exception e){
            System.out.println("Impossible de sauvegarder");
        }
    }

    protected void chargerPartie(){
        ChargerPartie charger = new ChargerPartie(this);
        try{
            charger.chargerSauvegarde();
        }catch(Exception e){
            System.out.println("Impossible de charger le fichier");
        }
    }

    protected abstract void ajoutPiece();
    
    protected abstract void supprimerPiece();
    
    protected abstract void rotationPiece();
    
    protected int difZero(int max){
            return rdmMinimum(1,max);
    }

    protected int rdmMinimum(int min, int max ){
            int rand = 0;
            while(rand < min)
                    rand = new Random().nextInt(max+1);
            return rand;
    }
    
    
}
