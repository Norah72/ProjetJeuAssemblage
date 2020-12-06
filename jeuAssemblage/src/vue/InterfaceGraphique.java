package vue;

import modele.*;
import util.*;
import file.*;
import piecesPuzzle.pieces.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
public class InterfaceGraphique extends JFrame implements Listener{
    private JPanel fenetre = new JPanel();                          
    private JPanel boutonDeJeu = new JPanel();                      //sous - fenetre contenant les boutons PLACER - DEPLACER... 
    private JPanel grille = new JPanel();                           //sous - fenetre contenant les case de la grille
    private JPanel listePiece = new JPanel();
    private JLabel info = new JLabel();
    private JLabel score = new JLabel("Score : 0");
    private JPanel choixRotation = new JPanel(new GridLayout(0,1));
    private final Border bordure = BorderFactory.createLineBorder(Color.black,1);
    private ArrayList<JButton> listeBouton = new ArrayList<JButton>();
    private HashMap<ArrayList<Integer>, JPanel > listeCase0ForClick = new HashMap<ArrayList<Integer>, JPanel>();
    private ArrayList<JPanel> listePieceForClick = new ArrayList<JPanel>();
    private ArrayList<JRadioButton> listeRotation;
    private PlateauPuzzle modele;
    private final JComboBox ligne = new JComboBox();
    private final JComboBox colonne = new JComboBox();
    private JButton bouton;
    private int nblignes, nbcolonne;
    private JProgressBar barre_progression;
    
    /**
     * Constructeur
     * @param modele plateau de jeu
     */
    public InterfaceGraphique(PlateauPuzzle modele){
        this.modele = modele;
        setTitle("Tetris : Puzzle Edition.exe");
        setSize(850,850);
        setLocationRelativeTo(null);
	setVisible(false);
	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    /**
     * Création fenetre
     * @return fenetre
     */
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
            listeCase0ForClick.clear();         //on nettoie toute les sous-fenetre principal
            listePieceForClick.clear();	
            nblignes = this.modele.getX();
            nbcolonne = this.modele.getY();
            fenetre.setLayout(new BorderLayout()); 					
            grille.setLayout(new GridLayout(nblignes,nbcolonne,2,2));
            grille.setBackground(Color.BLACK);
            for(int i = 0 ; i < nblignes; i++) {
                for (int j = 0; j < nbcolonne; j++) {
                    JPanel newCase = new JPanel();
                    newCase.setBorder(bordure);
                    listeCase0ForClick.put(new ArrayList<Integer>(Arrays.asList((Integer)i,(Integer)j)),newCase);
                    PiecesPuzzle p0= (PiecesPuzzle)this.modele.getPlateau().get(new ArrayList<Integer>(Arrays.asList((Integer)i,(Integer)j)));
                    if(p0 != null){
                        colorization(p0,newCase);                   //on parcourt le tableau et colorie la pièce en fonction de sa forme
                    }
                    grille.add(newCase);
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

    /**
     *  Affichage de la vue
     * @param action manager clique boutons
     */
    public void start(ActionGraphique action){
        setContentPane(buildContentPane());
        getAction(action);
        setVisible(true);
    }
    /**
     * Attribue un listener au bouton ligne et colonne
     * @param action 
     */
    private void getAction(ActionGraphique action ){
        getLigne().addActionListener(action);
        getColonne().addActionListener(action);
    }
    /**
     * crétaion des Bouton du jeu
     */
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
    /**
     * Creation d'un bouton
     * @param nom 
     */
    private void creationBouton(String nom){
        bouton = new JButton(nom);
        listeBouton.add(bouton);                                                                //Onl'ajoute dans l'ArrayListe de bouton
        if(nom == "PLACER" || nom == "DEPLACER" || nom =="SUPPRIMER" || nom =="SAVE/EXIT" ){    //Seul ces boutons sont visible pendant une partie
            boutonDeJeu.add(bouton);
        }
    }
    /**
     * affiche la liste des pieces à jouer
     */
    private void affichePieceAJouer(){
        for(int i = 0 ; i < modele.getPieceAJouer().size(); i++){
            PiecesPuzzle p1 = (PiecesPuzzle)modele.getPieceAJouer().get(i);
            JPanel piece = parcourir(p1);
            listePiece.add(piece);
            listePieceForClick.add(piece);
        }
    }
    
    /**
     * fenetre expliquant le fonctionnement du jeu
     */
    public void Explication(){
        JFrame explication = new JFrame();
        JPanel pieceDemo = new JPanel();
        explication.setSize(1000,1000);
        explication.setLocationRelativeTo(null);
        JLabel text = new JLabel("<html>Dans ce jeu l'objectif est simple ! Vous devez placer vos pièces en prenant le moins de place possible sur le plateau.<br><br>"
                + "Pour cela, plusieurs action sont possibles :<ul>"
                + "<li><U>Placer une pièce</U></li>Vous pouvez placer des pieces parmi celle présente dans votre liste (exemple ci-dessous) en cliquant dessus. Vous aurez alors plusieurs "
                + "rotations disponible.<br>Il vous suffira alors de cliquer sur la grille de jeu pour placer votre pièces.<br><br>"
                + "<U>!!!ATTENTION!!!<U> : Quand vous séléctionnez une case sur la grille, ce sont les coordonées de la premiere case en couleur de la pièces qui s'y placeront "
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
    
    /**
     * piece special pour illsutrer le fonctionement du jeux (visible dans Explication)
     * @param p
     * @return 
     */
    private JPanel parcourirDemo(PiecesPuzzle p){
        JPanel piece = new JPanel();
        piece.setLayout(new GridLayout(p.getLargeurX(),p.getLongueurY(),2,2));
        boolean cible = true;
        for(int j = 0 ; j < p.getLargeurX(); j++) {
                for (int k = 0; k < p.getLongueurY(); k++) {
                    JPanel newCase = new JPanel();
                    if (p.getGrid()[j][k]==true && cible){
                        newCase.setBackground(Color.RED);
                        cible=false;
                    }
                    else if (p.getGrid()[j][k]==true){
                        colorization(p,newCase);
                    }
                    piece.add(newCase);
                }
        }
        return piece;
    }
    /**
     * Parcours une piece pour afficher sa forme
     * @param p
     * @return 
     */
    private JPanel parcourir(PiecesPuzzle p){
        JPanel piece = new JPanel();
        piece.setLayout(new GridLayout(p.getLargeurX(),p.getLongueurY(),2,2));
        for(int j = 0 ; j < p.getLargeurX(); j++) {
                for (int k = 0; k < p.getLongueurY(); k++) {
                    JPanel newCase = new JPanel();
                    if (p.getGrid()[j][k]==true){
                        colorization(p,newCase);
                    }
                    piece.add(newCase);
                }
        }
        return piece;
    }
        
    /**
     * test explicative lorsqu'on clique sur un bouton
     * @param texte informations explicative
     */
    public void texteInformation(String texte){
        info.setText(texte);
        info.setForeground(Color.RED);
        boutonDeJeu.add(info);
        setContentPane(fenetre);
    }
    
    /**
     * Permet de sélectionner la rotation que l'on souhaite
     * @param p pièce sélectionné
     */
    public void visualisationRotation(PiecesPuzzle p){
        listeRotation = new ArrayList<JRadioButton>();
        choixRotation.removeAll();
        ButtonGroup group = new ButtonGroup(); //permet de sélectionner qu"une seule rotation
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
                getListeRotation().get(i).setSelected(true); //On verifie dans quel rotation la pièce se trouve pour la mettre en True par défaut
            }    
        }
        for(int i=0;i<listeRotation.size();i++){
            JPanel affichage = new JPanel();                //Une fenetre suplémentaire pour resserrer les cases de la pièces et avoir un affichage propre
            choixRotation.add(listeRotation.get(i));
            group.add(listeRotation.get(i));
            p.createPiece(i);
            affichage.add(parcourir(p));
            choixRotation.add(affichage);
        }
        fenetre.add(choixRotation,BorderLayout.EAST);
    }
    /**
     * colorie la pièce en fonction de sa forme
     * @param p
     * @param newCase 
     */
    private void colorization(PiecesPuzzle p, JPanel newCase){
        if(p instanceof PieceT)
            newCase.setBackground(Color.BLUE);
        else if(p instanceof PieceL)
            newCase.setBackground(Color.GREEN);
        else if(p instanceof PieceH)
            newCase.setBackground(Color.CYAN);
        else if(p instanceof PieceRectangle)
            newCase.setBackground(Color.ORANGE);
        newCase.setBorder(bordure);
    }
    
    /**
     *
     * @param obs
     */
    @Override
	public void update(Object obs) {    //Observer non implementé
            fenetre.repaint();
        }
     
    /**
     * Met à jour de la grille
     */
    public void afficheGrille(){
        this.modele.addListener(this);
        setContentPane(buildContentPane());
    }

    /**
     * @return bouton Ligne
     */
    public JComboBox getLigne() {
	return ligne;
    }

    /**
     * @return bouton Colonne
     */
    public JComboBox getColonne() {
	return colonne;
    }

    /**
     * @return liste des bouton de jeu
     */
    public ArrayList<JButton> getListeBouton(){
        return listeBouton;
    }

    /**
     * @return liste des rotations de la pièce
     */
    public ArrayList<JRadioButton> getListeRotation(){
        return new ArrayList((ArrayList<JRadioButton>)listeRotation.clone()); //le clone permet d'avoir la derniere versions de la liste
    }

    /**
     * @return liste des cases de la grille
     */
    public HashMap<ArrayList<Integer>,JPanel> getListeCaseForClick(){
        return listeCase0ForClick;
    }

    /**
     * @return liste des cases des pièce à placer
     */
    public ArrayList<JPanel> getListePieceForClick(){
        return listePieceForClick;
    }

    /**
     * fenetre de choix ouiNon (0 = oui ; 1 = non)
     * @param texte informartion de la fenetre
     * @param titre titre de la fenetre
     * @return reponse (oui / non)
     */
    public int ouiNon(String texte, String titre){
        int reponse = JOptionPane.showConfirmDialog(this, texte,titre, JOptionPane.YES_NO_OPTION);
        return reponse;
    }

    /**
     * Permet au joueur de rentrer son pseudo
     * @return pseudo du joueur
     */
    public String pseudo(){
        String reponse = JOptionPane.showInputDialog(this, "Rentrez un pseudo","Sauvegarder", JOptionPane.OK_OPTION);
        return reponse;
    }
    
    /**
     * Construit le tableau des scores
     * @param tab tableau des scores
     */
    public void tableauScore(ScoreFile tab){
        fenetre.removeAll();
        JLabel titre = new JLabel("Tableau des scores");
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        titre.setFont(new java.awt.Font("Arial",Font.BOLD,20));
        fenetre.add(titre,BorderLayout.NORTH);
        JLabel tableauScore = new JLabel("<html><ol>");
        ArrayList<String> t = new ArrayList();
        for(String i : tab.getListeScore().values()){
            t.add(i);
        }                                                   //On fait une liste html dans laquelle on rajaoute chaque pseudo / score
        int i = 0;
        for(String j : tab.getListeScore().keySet()){
            tableauScore.setText(tableauScore.getText()+"<li>"+ j +" - "+ t.get(i)+"</li><br>");
            i++;
        }
        tableauScore.setText(tableauScore.getText()+"</ol><html>");
        tableauScore.setHorizontalAlignment(SwingConstants.CENTER);
        tableauScore.setFont(new java.awt.Font("Arial",Font.BOLD,20));
        fenetre.add(tableauScore,BorderLayout.CENTER);
        boutonDeJeu.removeAll();
        boutonDeJeu.add(listeBouton.get(7));
        boutonDeJeu.add(listeBouton.get(8));
        fenetre.add(boutonDeJeu,BorderLayout.SOUTH);
        setContentPane(fenetre);
    }
    
    /**
     * Mise à jour du modèle
     * @param modele nouveau modèle
     */
    public void chargerModele(PlateauPuzzle modele){
        this.modele = modele;
    }

    /**
     * Verification si un une autre action est en cours
     * @return action en cours ou non
     */
    public boolean actionEnCours(){
        for(int i =0; i<getListeBouton().size();i++){
            if(getListeBouton().get(i).getText()=="ANNULER")
                return true;
        }
        return false;
    }
	
    /**
     *  Barre de Chargement quand l'ia joue
     * @param minimum début de la barre (0)
     * @param maximum fin de la barre (n partie jouer par l'ia)
     */
    public void barreChargement(int minimum, int maximum){
		JLabel label = new JLabel();
		label.setText("Chargement en cours...");
		barre_progression = new JProgressBar( );

		JPanel barre = new JPanel();

		barre_progression.setMinimum(minimum);
		barre_progression.setMaximum(maximum);
		barre.setLayout(new BoxLayout(barre, BoxLayout.X_AXIS));
		barre.add(label, 0, 0);
		barre.add(barre_progression, 1, 1);
		setContentPane(barre);
		setVisible(true);

	}

    /**
     * mise à jour de la barre de chargement
     * @param newValue nombre de partie joué par l'ia
     */
    public void updateBar(final int newValue)
	{
	  SwingUtilities.invokeLater(new Runnable( ) {
             public void run( ) {
               barre_progression.setValue(newValue);
             }
         });
	}
}