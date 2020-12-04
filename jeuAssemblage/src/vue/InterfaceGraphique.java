package vue;
import controleur.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import modele.*;
import util.*;
import file.*;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class InterfaceGraphique extends JFrame implements Listener{
    private JPanel fenetre = new JPanel();
    private JPanel boutonDeJeu = new JPanel();
    private JPanel grille = new JPanel();
    private JPanel listePiece = new JPanel();
    private JLabel info = new JLabel();
    private JLabel score = new JLabel("Score : 0");
    private JPanel choixRotation = new JPanel(new GridLayout(0,1));
    private Border bordure = BorderFactory.createLineBorder(Color.black,1);
    private ArrayList<JButton> listeBouton = new ArrayList<JButton>();
    private HashMap<ArrayList<Integer>, JPanel > listeCase0ForClick = new HashMap<ArrayList<Integer>, JPanel>();
    private ArrayList<JPanel> listePieceForClick = new ArrayList<JPanel>();
    private ArrayList<JRadioButton> listeRotation;
    private PlateauPuzzle modele;
    private JComboBox ligne = new JComboBox();
    private JComboBox colonne = new JComboBox();
    private JButton bouton;
    private int nblignes, nbcolonne;
    
    public InterfaceGraphique(PlateauPuzzle modele){
        this.modele = modele;
        //modele.addListener(this);
        setTitle("Tetris : Puzzle Edition.exe");
        setSize(850,850);
        setLocationRelativeTo(null);
	setVisible(false);
	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    private JPanel buildContentPane(){
        fenetre.removeAll();
        if(modele==null){
            listeCreationBouton();
            boutonDeJeu.add(score);
            ligne.addItem("LIGNE");
            colonne.addItem("COLONNE");
            for(int i = 5 ; i < 21; ++i) {
                String num = Integer.toString(i);
                ligne.addItem(num);
                colonne.addItem(num);
            }
            fenetre.add(ligne);
            fenetre.add(colonne);
            fenetre.add(listeBouton.get(0));
            fenetre.add(listeBouton.get(5));
            fenetre.add(listeBouton.get(6));            
        }
        else{
            score.setText("Score : " + Integer.toString(this.modele.getScore()));
            boutonDeJeu.remove(info);
            grille.removeAll();
            listeCase0ForClick.clear();
            listePieceForClick.clear();	
            nblignes = this.modele.getX();
            nbcolonne = this.modele.getY();
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
            listePiece.removeAll();
            affichePieceAJouer();
            fenetre.add(listePiece,BorderLayout.NORTH);
            fenetre.add(boutonDeJeu,BorderLayout.SOUTH);
            }
        return fenetre;
    } 
    public void start(ActionGraphique action){
        setContentPane(buildContentPane());
        getAction(action);
        setVisible(true);
    }
    private void getAction(ActionGraphique action ){
        getLigne().addActionListener(action);
        getColonne().addActionListener(action);
    }
    private void listeCreationBouton(){
        creationBouton("VALIDER");
        creationBouton("PLACER");
        creationBouton("DEPLACER");
        creationBouton("SUPPRIMER");
        creationBouton("SAVE/EXIT");
        creationBouton("CHARGER");
        creationBouton("EXPLICATION");
        creationBouton("RETOUR AU MENU");
        creationBouton("QUITTER");
    }
    private void creationBouton(String nom){
        bouton = new JButton(nom);
        listeBouton.add(bouton);
        if(nom == "PLACER" || nom == "DEPLACER" || nom =="SUPPRIMER" || nom =="SAVE/EXIT" ){
            boutonDeJeu.add(bouton);
        }
    }
    private void affichePieceAJouer(){
        JPanel piece = new JPanel();
        for(int i = 0 ; i < modele.getPieceAJouer().size(); i++){
            PiecesPuzzle p1 = (PiecesPuzzle)modele.getPieceAJouer().get(i);
            piece = parcourir(p1);
            listePiece.add(piece);
            listePieceForClick.add(piece);
        }
    }
    
    public void Explication(){
        JFrame explication = new JFrame();
        JPanel pieceDemo = new JPanel();
        explication.setSize(1000,1000);
        explication.setLocationRelativeTo(null);
        JLabel text = new JLabel("<html>Dans ce jeu l'objectif est simple ! Vous devez placer ses pièces en prenant le moins de place possible sur le plateau.<br><br>"
                + "Pour cela, plusieurs action sont possibles :<ul>"
                + "<li><U>Placer une pièce</U></li>Vous pouvez placer des pieces parmi celle présente dans votre liste (exemple ci-dessous) en cliquant dessus. Vous aurez alors plusieurs "
                + "rotations disponible.<br>Il vous suffira alors de cliquer sur la grille de jeu pour placer votre pièces.<br><br>"
                + "<U>!!!ATTENTION!!!<U> : Quand vous séléctionnez une case sur la grille, ce sont les coordonées 0,0 de la pièces qui s'y placeront "
                + "(exemple: case rouge de la derniere pièce ci-dessous). <U>CECI EST VALABLE POUR TOUTES LES INTERACTIONS!</U><br><br>"
                + "<li><U>Deplacer</U></li>Il est possible de déplacer une pièce sur la grille. Pour cela rien de plus simple, il suffit d'appuyer sur le bouton DEPLACER, "
                + "de séléctionner la pièce, de modifier la rotation si nécessaire, et de sélectionner son nouvel emplacement.<br><br>"
                + "<li><U>Supprimer</U></li>Il est aussi possible de supprimer une pièce du plateau pour la remettre dans votre liste de pièce. Comme pour le déplacement,"
                + "il vous suffit de cliquer sur le bouton SUPPRIMER et cliquer la pièces à enlever<br><br>"
                + "<li><U>Sauvegarder</U></li>Et enfin vous pouvez sauvegader votre partie pour la continuer plus tard.</ul><br>"
                + "Notre jeu est également muni d'un tableau des scores, accessible une fois la partie terminé. Comme annoncer au début, pour avoir le meilleur score "
                + "vous devez placer vos pièces en prennant le moins de place possible sur la grille. Ce score est consultable à gauche des bouton de jeu  </html>");
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setFont(new java.awt.Font("Arial",Font.BOLD,18));
        pieceDemo.add(new JPanel().add(parcourir(new PieceT(3,3))));
        pieceDemo.add(new JPanel().add(parcourir(new PieceH(3,3))));
        pieceDemo.add(new JPanel().add(parcourir(new PieceRectangle(3,3))));
        PieceL p = new PieceL(3,3);
        p.createPiece(3);
        pieceDemo.add(parcourirDemo(p));
        JPanel affichage = new JPanel();
        affichage.add(pieceDemo);
        explication.add(text);
        explication.add(affichage,BorderLayout.SOUTH);
        explication.setVisible(true);
    }

    private JPanel parcourirDemo(PiecesPuzzle p){
        JPanel piece = new JPanel();
        piece.setLayout(new GridLayout(p.getLargeurX(),p.getLongueurY(),2,2));
        for(int j = 0 ; j < p.getLargeurX(); j++) {
                for (int k = 0; k < p.getLongueurY(); k++) {
                    JPanel case0 = new JPanel();
                    if (j==0 && k==0){
                        case0.setBackground(Color.RED);
                    }
                    if (p.getGrid()[j][k]==true){
                        colorization(p,case0);
                    }
                    piece.add(case0);
                }
        }
        return piece;
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
        
    public void texteInformation(String texte){
        info.setText(texte);
        info.setForeground(Color.RED);
        boutonDeJeu.add(info);
        setContentPane(fenetre);
    }
    
    public void visualisationRotation(PiecesPuzzle p){
        listeRotation = new ArrayList<JRadioButton>();
        choixRotation.removeAll();
        ButtonGroup group = new ButtonGroup();
        Border border = BorderFactory.createTitledBorder("Rotation");
        choixRotation.setBorder(border);
        JRadioButton rota0 = new JRadioButton("");
        JRadioButton rota1 = new JRadioButton("");
        JRadioButton rota2 = new JRadioButton("");
        JRadioButton rota3 = new JRadioButton("");
        listeRotation.add(rota0);
        listeRotation.add(rota1);
        listeRotation.add(rota2);
        listeRotation.add(rota3);
        for(int i=0; i<4; i++){
            if(i==p.getRotation()){
                getListeRotation().get(i).setSelected(true);
            }    
        }
        for(int i=0;i<listeRotation.size();i++){
            JPanel affichage = new JPanel();
            choixRotation.add(listeRotation.get(i));
            group.add(listeRotation.get(i));
            p.createPiece(i);
            affichage.add(parcourir(p));
            choixRotation.add(affichage);
        }
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
        case0.setBorder(bordure);
    }
    
    @Override
	public void update(Object obs) {
            fenetre.repaint();
        }
     
    public void afficheGrille(){
        this.modele.addListener(this);
        setContentPane(buildContentPane());
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

    
    public int ouiNon(String texte, String titre){
        JOptionPane p = new JOptionPane();
        int reponse = p.showConfirmDialog(this, texte,titre, JOptionPane.YES_NO_OPTION);
        return reponse;
    }
    public String pseudo(){
        JOptionPane p = new JOptionPane();
        String reponse = p.showInputDialog(this, "Rentrez un pseudo","Sauvegarder", JOptionPane.OK_OPTION);
        return reponse;
    }
    
    public void tableauScore(ScoreFile tab){
        fenetre.removeAll();
        JLabel titre = new JLabel("Tableau des scores");
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        titre.setFont(new java.awt.Font("Arial",Font.BOLD,20));
        fenetre.add(titre,BorderLayout.NORTH);
        JLabel score = new JLabel("<html><ul>");
        ArrayList<String> t = new ArrayList();
        for(String i : tab.getListeScore().values()){
            t.add(i);
        }
        int i = 0;
        for(String j : tab.getListeScore().keySet()){
            score.setText(score.getText()+"<li>"+ j +" - "+ t.get(i)+"</li><br>");
            i++;
        }
        score.setText(score.getText()+"</ul><html>");
        score.setHorizontalAlignment(SwingConstants.CENTER);
        score.setFont(new java.awt.Font("Arial",Font.BOLD,20));
        fenetre.add(score,BorderLayout.CENTER);
        boutonDeJeu.removeAll();
        boutonDeJeu.add(listeBouton.get(7));
        boutonDeJeu.add(listeBouton.get(8));
        fenetre.add(boutonDeJeu,BorderLayout.SOUTH);
        setContentPane(fenetre);
    }
    
    public void chargerModele(PlateauPuzzle modele){
        this.modele = modele;
    }

    public boolean actionEnCours(){
        for(int i =0; i<getListeBouton().size();i++){
            if(getListeBouton().get(i).getText()=="ANNULER")
                return true;
        }
        return false;
    }		
}