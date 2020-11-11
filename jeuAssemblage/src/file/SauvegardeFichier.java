package file;

import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import piecesPuzzle.pieces.PieceH;
import piecesPuzzle.pieces.PieceL;
import piecesPuzzle.pieces.PieceRectangle;
import piecesPuzzle.pieces.PieceT;
import piecesPuzzle.pieces.PiecesPuzzle;
import controleur.PlayConsole;


public class SauvegardeFichier {
	
	private PlayConsole jeu;
	private File partieFichier = new File("src/file/partie/partie.txt"); 
	private JsonWriter sauvegarde;
	
	
	public SauvegardeFichier(PlayConsole jeu){
		this.jeu = jeu;
	}
	
	public void ecrire() throws IOException{
		try{
			System.out.println("Sauvegarde en cours.. ");
			this.sauvegarde = new JsonWriter(new FileWriter(partieFichier));
			
			this.sauvegarde.setIndent(" ");
			this.sauvegarde.beginObject();
				this.sauvegarde.name("initialisation");
				this.sauvegarde.beginObject();
					this.sauvegarde.name("pseudo").value(this.jeu.getpseudo());
					this.sauvegarde.name("largeurPlateau").value(this.jeu.getlargeurPlateauX());
					this.sauvegarde.name("longeurPlateau").value(this.jeu.getlongueurPlateauY());
					this.sauvegarde.name("explication").value(this.jeu.getexplicationRot());
				this.sauvegarde.endObject();

				this.sauvegarde.name("piecesDisponible");
				this.sauvegarde.beginObject();
					for(int i = 0; i < this.jeu.getPlateauConsole().getPieceAJouer().size(); i++){
						this.sauvegarde.name(""+i);
						this.sauvegarde.beginObject();
							PiecesPuzzle pieceDispo = (PiecesPuzzle)this.jeu.getPlateauConsole().getPieceAJouer().get(i);
							ecrirePiece(pieceDispo);
						this.sauvegarde.endObject();
					}
				this.sauvegarde.endObject();

				this.sauvegarde.name("piecesPlacer");
				this.sauvegarde.beginObject();
					if(!this.jeu.getPlateauConsole().getPiecePlacer().isEmpty()){
						for(int i = 0; i < this.jeu.getPlateauConsole().getPiecePlacer().size() ; i++){			
							this.sauvegarde.name(""+i);

							this.sauvegarde.beginObject();
								PiecesPuzzle pieceDispo = (PiecesPuzzle)this.jeu.getPlateauConsole().getPiecePlacer().get(i);

								ecrirePiece(pieceDispo);

								this.sauvegarde.name("coordonnees");
								this.sauvegarde.beginArray();
									this.sauvegarde.value(pieceDispo.getCoo().get(0));
									this.sauvegarde.value(pieceDispo.getCoo().get(1));
								this.sauvegarde.endArray();
							this.sauvegarde.endObject();
						}
					}
				this.sauvegarde.endObject();

			this.sauvegarde.endObject();
			this.sauvegarde.close();
			System.out.println("Sauvegarde terminer.. ");
		}
		catch(Exception e){
			System.out.println("Impossible de sauvegarder le fichier: "+e);
		}
	}
	
	private void ecrirePiece(PiecesPuzzle pieceDispo) throws IOException{
		if(pieceDispo instanceof PieceH)
			this.sauvegarde.name("type").value("PieceH");
		else if(pieceDispo instanceof PieceL)
			this.sauvegarde.name("type").value("PieceL");
		else if(pieceDispo instanceof PieceRectangle)
			this.sauvegarde.name("type").value("PieceRectangle");
		else if(pieceDispo instanceof PieceT)
			this.sauvegarde.name("type").value("PieceT");

		this.sauvegarde.name("largeur").value(pieceDispo.getX());
		this.sauvegarde.name("longeur").value(pieceDispo.getY());
		this.sauvegarde.name("rotation").value(pieceDispo.getRotation());
	}
	
	
}
