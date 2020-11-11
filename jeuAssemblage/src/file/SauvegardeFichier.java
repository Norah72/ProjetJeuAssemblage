package file;

import com.google.gson.stream.JsonWriter;
import controleur.PlayConsole;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import piecesPuzzle.pieces.PieceH;
import piecesPuzzle.pieces.PieceL;
import piecesPuzzle.pieces.PieceRectangle;
import piecesPuzzle.pieces.PieceT;
import piecesPuzzle.pieces.PiecesPuzzle;


public class SauvegardeFichier {
	
	private PlayConsole jeu;
	
	
	private File partieFichier = new File("src/file/partie/partie.txt"); 
	private JsonWriter writer;
	
	
	public SauvegardeFichier(PlayConsole jeu){
		this.jeu = jeu;
	}
	
	public void ecrire() throws IOException{
		try{
			System.out.println("Sauvegarde en cours.. ");
			this.writer = new JsonWriter(new FileWriter(partieFichier));
			this.writer.setIndent(" ");
			this.writer.beginObject();
				this.writer.name("initialisation");
				this.writer.beginObject();
					this.writer.name("pseudo").value(this.jeu.getpseudo());
					this.writer.name("largeurPlateau").value(this.jeu.getlargeurPlateauX());
					this.writer.name("longeurPlateau").value(this.jeu.getlongueurPlateauY());
					this.writer.name("explication").value(this.jeu.getexplicationRot());
				this.writer.endObject();

				this.writer.name("pieces disponible");
				this.writer.beginObject();
					for(int i = 0; i < this.jeu.getPlateauConsole().getPieceAJouer().size(); i++){
						this.writer.name(""+i);
						this.writer.beginObject();
							PiecesPuzzle pieceDispo = (PiecesPuzzle)this.jeu.getPlateauConsole().getPieceAJouer().get(i);
							ecrirePiece(pieceDispo);
						this.writer.endObject();
					}
				this.writer.endObject();

				this.writer.name("pieces placer");
				this.writer.beginObject();
					if(!this.jeu.getPlateauConsole().getPiecePlacer().isEmpty()){
						for(int i = 0; i < this.jeu.getPlateauConsole().getPiecePlacer().size() ; i++){			
							this.writer.name(""+i);

							this.writer.beginObject();
								PiecesPuzzle pieceDispo = (PiecesPuzzle)this.jeu.getPlateauConsole().getPiecePlacer().get(i);

								ecrirePiece(pieceDispo);

								this.writer.name("coordonnees");
								this.writer.beginArray();
									this.writer.value(pieceDispo.getCoo().get(0));
									this.writer.value(pieceDispo.getCoo().get(1));
								this.writer.endArray();
							this.writer.endObject();
						}
					}
				this.writer.endObject();

			this.writer.endObject();
			this.writer.close();
			System.out.println("Sauvegarde terminer.. ");
		}
		catch(Exception e){
			System.out.println("Impossible de sauvegarder le fichier: "+e);
		}
	}
	
	private void ecrirePiece(PiecesPuzzle pieceDispo) throws IOException{
		if(pieceDispo instanceof PieceH)
			this.writer.name("type").value("PieceH");
		else if(pieceDispo instanceof PieceL)
			this.writer.name("type").value("PieceL");
		else if(pieceDispo instanceof PieceRectangle)
			this.writer.name("type").value("PieceRectangle");
		else if(pieceDispo instanceof PieceT)
			this.writer.name("type").value("PieceT");

		this.writer.name("rotation").value(pieceDispo.getRotation());
		this.writer.name("largeur").value(pieceDispo.getX());
		this.writer.name("longeur").value(pieceDispo.getY());
	}
	
	
}
