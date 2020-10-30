package vue;
import java.awt.BorderLayout;
import java.awt.Color;
import modele.*;
import util.*;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class InterfaceGraphique extends JFrame implements Listener{
    private JPanel fenetre = new JPanel();
    private JPanel grille = new JPanel();
    private PlateauPuzzle modele;
    private JComboBox ligne = new JComboBox();
    private JComboBox colonne = new JComboBox();
    private JButton valide = new JButton("VALIDER");
    private String num;

    
    public InterfaceGraphique(PlateauPuzzle modele){
        this.modele = modele;
        modele.addListener(this);
        setTitle("Jeu-facile.exe");
        setSize(650,650);
        setContentPane(buildContentPane(0));
	setVisible(true);
	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    private JPanel buildContentPane(int occ){
        fenetre.removeAll();
        if(occ==0){
            ligne.addItem("LIGNE");
            colonne.addItem("COLONNE");
            for(int i = 1 ; i < 10; ++i) {
                num = Integer.toString(i);
                ligne.addItem(num);
                colonne.addItem(num);
            }
            fenetre.add(ligne);
            fenetre.add(colonne);
            fenetre.add(valide);
        }
        else{
            int nblignes = this.getLigne().getSelectedIndex();
            int nbcolonne = this.getColonne().getSelectedIndex();
            Border blackline = BorderFactory.createLineBorder(Color.black,1);
            fenetre.setLayout(new BorderLayout()); 					
            grille.setLayout(new GridLayout(this.getLigne().getSelectedIndex(),this.getColonne().getSelectedIndex(),4,4));
            grille.setBackground(Color.BLACK);
            
            for(int i = 0 ; i < nblignes; i++) {
			for (int j = 0; j < nbcolonne; j++) {
                            JPanel case0 = new JPanel();
                            case0.setBorder(blackline);
                            grille.add(case0);
                        }
            }
            grille.setBorder(blackline);
            fenetre.add(grille);
            }
        return fenetre;
    }
    @Override
	public void update(Object obs) {
            fenetre.repaint();
        }
    public void afficheGrille(){
        setContentPane(buildContentPane(1));
    }
    public JComboBox getLigne() {
	return ligne;
    }
    public JComboBox getColonne() {
	return colonne;
    }
    public JButton getValide() {
	return valide;
    }
}