package file;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import modele.PlateauPuzzle;
import controleur.PlayConsole;

public class ChargerPartie {

	private PlayConsole jeu;
	private File partieFichier = new File("src/file/partie/partie.txt"); 
	
	private int largeur, longeur, rotation, i;

	
	public ChargerPartie(PlayConsole jeu){
		this.jeu = jeu;
	}
	
	
	public void chargerSauvegarde() throws IOException{
		try{
		JsonParser parseCharger = new JsonParser();
		JsonObject chargerPartie = (JsonObject) parseCharger.parse(new FileReader(partieFichier));
		
		JsonObject initialisation = chargerPartie.get("initialisation").getAsJsonObject();
		
		
		this.jeu.setPseudo(initialisation.get("pseudo").getAsString());

		largeur = initialisation.get("largeurPlateau").getAsInt();
		longeur = initialisation.get("longeurPlateau").getAsInt();
		this.jeu.setLargeurPlateauX(largeur);
		this.jeu.setLongueurPlateauY(longeur);
		this.jeu.setPlateauConsole(new PlateauPuzzle(largeur,longeur));
		
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
			
			this.jeu.getPlateauConsole().addPiece(this.jeu.getPlateauConsole().getPieceAJouer().get(this.jeu.getPlateauConsole().getPieceAJouer().size()-1), new ArrayList(Arrays.asList(cooX,cooY)));
			i++;
		}
		
		}catch(Exception e){
			System.out.println("Impossible de charger la partie: "+e);
		}
	}
	
	
	private void ajoutPieceList(JsonObject piece){
		largeur = piece.get("largeur").getAsInt();
		longeur = piece.get("longeur").getAsInt();
		rotation = piece.get("rotation").getAsInt();
		for(int z = 0; z< this.jeu.getPlateauConsole().getPieceString().size();z++){
			if((this.jeu.getPlateauConsole().getPieceString().get(z)).equals(piece.get("type").getAsString()))
				this.jeu.getPlateauConsole().newPiece(this.jeu.getPlateauConsole().getPieceString().get(z), largeur, longeur, rotation);
		}
	}
}
