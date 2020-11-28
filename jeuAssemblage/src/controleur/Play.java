package controleur;

import file.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import modele.*;
import vue.*;

public class Play {
    //Attibuts
    
    private InterfacePlay joueurActuel;
    private boolean affichageGraph;
    private PlateauPuzzle plateau;
    
    //Constructeur
    
    public Play(boolean affichageGraph){
        this.affichageGraph = affichageGraph;
        //this.plateau = new PlateauPuzzle(0,0);    ??? 
        if(this.affichageGraph){
            System.out.println("Méthode d'affichage graphique à implémenter");
        }else{
            this.menu();
        }
    }
    
    //Méthodes
    
    public void menu(){
        System.out.println("Menu à implémenter");
    }
    
    
}
