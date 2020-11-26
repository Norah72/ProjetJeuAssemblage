package vue;
import controleur.*;
import java.awt.BorderLayout;
import java.awt.Color;
import modele.*;
import util.*;
import piecesPuzzle.pieces.*;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class InterfaceGraphique extends JFrame implements Listener{
    private JPanel fenetre = new JPanel();
    private JPanel BoutonDeJeu = new JPanel();
    private JPanel grille = new JPanel();
    private JPanel listePiece = new JPanel();
    private JPanel case0 = new JPanel();
    private JLabel info = new JLabel();
    private JPanel choixRotation = new JPanel(new GridLayout(0,2,8,8));
    private Border bordure = BorderFactory.createLineBorder(Color.black,1);
    private ArrayList<JButton> listeBouton = new ArrayList<JButton>();
    private HashMap<ArrayList<Integer>, JPanel > listeCase0ForClick = new HashMap<ArrayList<Integer>, JPanel>();
    private ArrayList<JPanel> listePieceForClick = new ArrayList<JPanel>();
    private ArrayList<JRadioButton> listeRotation;
    private PlateauPuzzle modele;
    private PlayConsole controleur;
    private JComboBox ligne = new JComboBox();
    private JComboBox colonne = new JComboBox();
    private JButton bouton;
    private int nblignes, nbcolonne;
    
    public InterfaceGraphique(PlateauPuzzle modele){
        this.modele = modele;
        modele.addListener(this);
        setTitle("JyArrivePas.exe");
        setSize(850,850);
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
            creationBouton("SAUVEGARDER");
            ligne.addItem("LIGNE");
            colonne.addItem("COLONNE");
            for(int i = 5 ; i < 21; ++i) {
                String num = Integer.toString(i);
                ligne.addItem(num);
                colonne.addItem(num);
            }
            fenetre.add(ligne);
            fenetre.add(colonne);
            fenetre.add(BoutonDeJeu.getComponent(0));
        }
        else{
            nblignes = this.getLigne().getSelectedIndex()+4;
            nbcolonne = this.getColonne().getSelectedIndex()+4;
            this.modele.setXY(nblignes,nbcolonne);
            
            fenetre.setLayout(new BorderLayout()); 					
            grille.setLayout(new GridLayout(nblignes,nbcolonne,2,2));
            grille.setBackground(Color.BLACK);
            
            for(int i = 0 ; i < nblignes; i++) {
			for (int j = 0; j < nbcolonne; j++) {
                            JPanel case0 = new JPanel();
                            case0.setBorder(bordure);
                            listeCase0ForClick.put(new ArrayList<Integer>(Arrays.asList((Integer)i,(Integer)j)),case0);
                            PiecesPuzzle p0= (PiecesPuzzle)this.modele.getPlateau().get(new ArrayList<Integer>(Arrays.asList((Integer)i,(Integer)j)));
                            if(p0 != null){
                                colorization(p0,case0);
                            }
                            /*this.modele.addListener(case0);*/
                            grille.add(case0);
                        }
            }
            grille.setBorder(bordure);
            fenetre.add(grille,BorderLayout.CENTER);
            affichePieceAJouer();
            fenetre.add(listePiece,BorderLayout.NORTH);
            fenetre.add(BoutonDeJeu,BorderLayout.SOUTH);
            }
        return fenetre;
    } 
    public void start(PlayConsole controleur){
        setContentPane(buildContentPane(0));
        getAction(controleur);
        setVisible(true);
    }
    private void getAction(PlayConsole controleur){
        getLigne().addActionListener(controleur);
        getColonne().addActionListener(controleur);
        setController(controleur);
    }
    private void creationBouton(String nom){
        bouton = new JButton(nom);
        listeBouton.add(bouton);
        BoutonDeJeu.add(bouton);
    }
    private void affichePieceAJouer(){
        JPanel piece = new JPanel();
        for(int i = 0 ; i < controleur.getPlateauConsole().getPieceAJouer().size(); i++){
            PiecesPuzzle p1 = (PiecesPuzzle)controleur.getPlateauConsole().getPieceAJouer().get(i);
            piece = parcourir(p1);
            listePiece.add(piece);
            listePieceForClick.add(piece);
        }
    }
    
    public void texteInformation(String texte){
        info.setText(texte);
        info.setForeground(Color.RED);
        BoutonDeJeu.add(info);
        setContentPane(fenetre);
    }
    
    private JPanel parcourir(PiecesPuzzle p){
        JPanel piece = new JPanel();
        piece.setLayout(new GridLayout(p.getLargeurX(),p.getLongueurY(),2,2));
        for(int j = 0 ; j < p.getLargeurX(); j++) {
                for (int k = 0; k < p.getLongueurY(); k++) {
                    JPanel case0 = new JPanel();
                    if (p.getGrid()[j][k]==true){
                        colorization(p,case0);
                    }
                    piece.add(case0);
                }
        }
        return piece;
    }
    
    public void visualisationRotation(PiecesPuzzle p){
        listeRotation = new ArrayList<JRadioButton>();
        choixRotation.removeAll();
        ButtonGroup group = new ButtonGroup();
        Border border = BorderFactory.createTitledBorder("Sélection rotation");
        choixRotation.setBorder(border);
        JRadioButton rota0 = new JRadioButton("");
        JRadioButton rota1 = new JRadioButton("");
        JRadioButton rota2 = new JRadioButton("",true);
        JRadioButton rota3 = new JRadioButton("");
        listeRotation.add(rota0);
        listeRotation.add(rota1);
        listeRotation.add(rota2);
        listeRotation.add(rota3);
        choixRotation.add(rota0);
        p.createPiece(0);
        choixRotation.add(parcourir(p));   
        group.add(rota0);
        choixRotation.add(rota1);
        p.createPiece(1);
        choixRotation.add(parcourir(p));
        group.add(rota1);
        choixRotation.add(rota2);
        p.createPiece(2);
        choixRotation.add(parcourir(p));
        group.add(rota2);
        choixRotation.add(rota3);
        p.createPiece(3);
        choixRotation.add(parcourir(p));
        group.add(rota3);
        fenetre.add(choixRotation,BorderLayout.EAST);
    }
    
    private void colorization(PiecesPuzzle p, JPanel case0){
        if(p instanceof PieceT)
            case0.setBackground(Color.BLUE);
        else if(p instanceof PieceL)
            case0.setBackground(Color.GREEN);
        else if(p instanceof PieceH)
            case0.setBackground(Color.CYAN);
        else if(p instanceof PieceRectangle)
            case0.setBackground(Color.ORANGE);
        else
            case0.setBackground(Color.RED);
        case0.setBorder(bordure);
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
    public ArrayList<JButton> getListeBouton(){
        return listeBouton;
    }
    public ArrayList<JRadioButton> getListeRotation(){
        return new ArrayList((ArrayList<JRadioButton>)listeRotation.clone());
    }
    public HashMap<ArrayList<Integer>,JPanel> getListeCaseForClick(){
        return listeCase0ForClick;
    }
    public ArrayList<JPanel> getListePieceForClick(){
        return listePieceForClick;
    }
    public PlayConsole setController(PlayConsole controleur){
        this.controleur = controleur;
        return this.controleur;
    }
	
	public void setModele(){
            fenetre.remove(grille);
            fenetre.remove(choixRotation);
            BoutonDeJeu.remove(info);
            grille.removeAll();
            listeCase0ForClick.clear();
            listePieceForClick.clear();	
            grille.setLayout(new GridLayout(nblignes,nbcolonne,2,2));
            grille.setBackground(Color.BLACK);

            for(int i = 0 ; i < nblignes; i++) {
		for (int j = 0; j < nbcolonne; j++) {
                    JPanel case0 = new JPanel();
                    case0.setBorder(bordure);
                    listeCase0ForClick.put(new ArrayList<Integer>(Arrays.asList((Integer)i,(Integer)j)),case0);
                    if(this.modele.getPlateau().get(new ArrayList<Integer>(Arrays.asList((Integer)i,(Integer)j))) != null){
                        colorization((PiecesPuzzle)this.modele.getPlateau().get(new ArrayList<Integer>(Arrays.asList((Integer)i,(Integer)j))),case0);
                    }
                    grille.add(case0);
                }
            }
            grille.setBorder(bordure);

            fenetre.add(grille,BorderLayout.CENTER);
            listePiece.removeAll();
            affichePieceAJouer();
            fenetre.add(listePiece,BorderLayout.NORTH);
            setContentPane(fenetre);
	}
			
}