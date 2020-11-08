package vue;
import controleur.*;
import java.awt.BorderLayout;
import java.awt.Color;
import modele.*;
import util.*;
import piecesPuzzle.pieces.*;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class InterfaceGraphique extends JFrame implements Listener{
    private JPanel fenetre = new JPanel();
    private JPanel BoutonDeJeu = new JPanel();
    private JPanel grille = new JPanel();
    private JPanel listePiece = new JPanel();
    private Border bordure = BorderFactory.createLineBorder(Color.black,1);
    private ArrayList<JButton> listeBouton = new ArrayList<JButton>();
    private PlateauPuzzle modele = new PlateauPuzzle(0,0);
    private PlayConsole test;
    private JComboBox ligne = new JComboBox();
    private JComboBox colonne = new JComboBox();
    private JButton bouton;
    private String num;
    
    public InterfaceGraphique(PlateauPuzzle modele){
        this.modele = modele;
        modele.addListener(this);
        setTitle("JyArrivePas.exe");
        setSize(650,650);
	setVisible(false);
	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    private JPanel buildContentPane(int occ){
        fenetre.removeAll();
        if(occ==0){
            creationBouton("VALIDER");
            creationBouton("PLACER");
            creationBouton("DEPLACER");
            creationBouton("SUPPRIMER");
            creationBouton("ROTATION");
            creationBouton("SAUVEGARDER");
            ligne.addItem("LIGNE");
            colonne.addItem("COLONNE");
            for(int i = 5 ; i < 21; ++i) {
                num = Integer.toString(i);
                ligne.addItem(num);
                colonne.addItem(num);
            }
            fenetre.add(ligne);
            fenetre.add(colonne);
            fenetre.add(BoutonDeJeu);
        }
        else{
            int nblignes = this.getLigne().getSelectedIndex()+4;
            int nbcolonne = this.getColonne().getSelectedIndex()+4;
            this.modele.setXY(nblignes,nbcolonne);
            
            fenetre.setLayout(new BorderLayout()); 					
            grille.setLayout(new GridLayout(nblignes,nbcolonne,2,2));
            grille.setBackground(Color.BLACK);
            
            for(int i = 0 ; i < nblignes; i++) {
			for (int j = 0; j < nbcolonne; j++) {
                            JPanel case0 = new JPanel();
                            case0.setBorder(bordure);
                            grille.add(case0);
                        }
            }
            grille.setBorder(bordure);
            fenetre.add(grille,BorderLayout.CENTER);
            affichePieceAJouer();
            fenetre.add(listePiece,BorderLayout.NORTH);
            fenetre.add(BoutonDeJeu,BorderLayout.PAGE_END);
            }
        
        return fenetre;
    } 
    public void start(PlayConsole controleur){
        setContentPane(buildContentPane(0));
        getAction(controleur);
        setVisible(true);
    }
    public void getAction(PlayConsole controleur){
        getLigne().addActionListener(controleur);
        getColonne().addActionListener(controleur);
        setController(controleur);
    }
    public void creationBouton(String nom){
        bouton = new JButton(nom);
        listeBouton.add(bouton);
        BoutonDeJeu.add(bouton);
    }
    public void affichePieceAJouer(){
        for(int i = 0 ; i < test.getPlateauConsole().getPieceAJouer().size(); i++){
                JPanel piece = new JPanel();
                PiecesPuzzle p1 = (PiecesPuzzle)test.getPlateauConsole().getPieceAJouer().get(i);
                piece.setLayout(new GridLayout(p1.getLargeurX(),p1.getLongueurY(),2,2));
                for(int j = 0 ; j < p1.getLargeurX(); j++) {
			for (int k = 0; k < p1.getLongueurY(); k++) {
                            JPanel case0 = new JPanel();
                            if (p1.getGrid()[j][k]==true){
                                if(p1.getClass().getName() == "piecesPuzzle.pieces.PieceT"){
                                    case0.setBackground(Color.BLUE);
                                }
                                else if(p1.getClass().getName() == "piecesPuzzle.pieces.PieceL"){
                                    case0.setBackground(Color.GREEN);
                                }
                                else if(p1.getClass().getName() == "piecesPuzzle.pieces.PieceH"){
                                    case0.setBackground(Color.CYAN);
                                }
                                else if(p1.getClass().getName() == "piecesPuzzle.pieces.PieceRectangle"){
                                    case0.setBackground(Color.ORANGE);
                                }
                                else{
                                    case0.setBackground(Color.RED);
                                }
                                case0.setBorder(bordure);
                            }
                            piece.add(case0);
                        }
                }
                listePiece.add(piece);
            }
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
    public JButton getBouton() {
        return bouton;
    }
    public ArrayList<JButton> getListeBouton(){
        return listeBouton;
    }
    public PlayConsole setController(PlayConsole controleur){
        test = controleur;
        return test;
    }
}