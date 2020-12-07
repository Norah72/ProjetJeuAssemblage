package file;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import modele.PlateauPuzzle;
import controleur.*;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
public class ChargerPartie {

	/**
	 * Instance du jeu
	 */
	private final Play jeu;
	/**
	 * Fichier charger
	 */
	private final File partieFichier; 
	
	/**
	 * largeur plateau/pièce
	 */
	private int largeur, 
			/**
			 * longueur plateau/pièce
			 */
			longeur, 
			/**
			 * rotation pièce
			 */
			rotation,
			/**
			 * Numéro de la pièce
			 */
			i;

    /**
     * Constructeur
     * @param jeu Constructeur
     * @param file nom du fichier
     */
    public ChargerPartie(Play jeu, String file){
		this.jeu = jeu;
		this.partieFichier = new File(file);
	}
	
    /**
     * lit la sauvegarde pour préparer le plateau
     * @throws IOException
     */
    public void chargerSauvegarde() throws IOException{
		try{
		JsonParser parseCharger = new JsonParser();
		JsonObject chargerPartie = (JsonObject) parseCharger.parse(new FileReader(partieFichier));
		
		JsonObject initialisation = chargerPartie.get("initialisation").getAsJsonObject();  //lit chaque catégorie et regarde la valeur associé pour configurez le plateau
		
		this.jeu.setPseudo(initialisation.get("pseudo").getAsString());

		largeur = initialisation.get("largeurPlateau").getAsInt();
		longeur = initialisation.get("longeurPlateau").getAsInt();
		this.jeu.setLargeur(largeur);
		this.jeu.setLongueur(longeur);
		this.jeu.setPlateau(new PlateauPuzzle(largeur,longeur));
		
		this.jeu.setExplicationRot(initialisation.get("explication").getAsBoolean());
		
		JsonObject piecesDisponible = chargerPartie.get("piecesDisponible").getAsJsonObject();
		i = 0;
		while(piecesDisponible.has(""+i)){
			JsonObject pieces = piecesDisponible.get(""+i).getAsJsonObject();
			ajoutPieceList(pieces);
			i++;
		}
		
		JsonObject piecesPlacer = chargerPartie.get("piecesPlacer").getAsJsonObject();
		i=0;
		while(piecesPlacer.has(""+i)){
			JsonObject pieces = piecesPlacer.get(""+i).getAsJsonObject();
			ajoutPieceList(pieces);
			
			JsonArray coo = pieces.get("coordonnees").getAsJsonArray();
			int cooX = coo.get(0).getAsInt();
			int cooY = coo.get(1).getAsInt();
			
			ArrayList<Integer> cooPiece = new ArrayList(Arrays.asList(cooX,cooY));
			
			int xx = 0;
			int yy = 0;
			int posY = 0;
			while(!this.jeu.getPlateau().getPieceAJouer().get(this.jeu.getPlateau().getPieceAJouer().size()-1).getGrid()[xx][yy+posY]){
				cooPiece.set(1, cooPiece.get(1)+1);
				posY += 1;
			}
			
			this.jeu.getPlateau().addPiece(this.jeu.getPlateau().getPieceAJouer().get(this.jeu.getPlateau().getPieceAJouer().size()-1), cooPiece);
			i++;
		}
		
		}catch(Exception e){
			System.out.println("Impossible de charger la partie: "+e);
		}
	}
	/**
         * Retourne la pièce en fonction des informations dans le fichier txt
         * @param piece 
         */
	private void ajoutPieceList(JsonObject piece){
		largeur = piece.get("largeur").getAsInt();
		longeur = piece.get("longeur").getAsInt();
		rotation = piece.get("rotation").getAsInt();
		for(int z = 0; z< this.jeu.getPlateau().getPieceString().size();z++){
			if((this.jeu.getPlateau().getPieceString().get(z)).equals(piece.get("type").getAsString()))
				this.jeu.getPlateau().newPiece(this.jeu.getPlateau().getPieceString().get(z), largeur, longeur, rotation);
		}
	}
}
