package file;

import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import piecesPuzzle.pieces.*;
import controleur.*;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
public class SauvegardeFichier {
	
	private final Play jeu;
	private final File partieFichier;
	private JsonWriter sauvegarde;
	private final boolean affiche;
	
    /**
     *
     * @param jeu
     * @param File
     */
    public SauvegardeFichier(Play jeu,String File){
		this.jeu = jeu;

		this.affiche = (!jeu.getIa() && !jeu.getAfficheGraph()) == true;
		
		this.partieFichier = new File(File);
	}
	
    /**
     *
     * @throws IOException
     */
    public void ecrire() throws IOException{
		try{
			affiche("Sauvegarde en cours.. ");
			this.sauvegarde = new JsonWriter(new FileWriter(partieFichier));
			
			this.sauvegarde.setIndent(" ");
			this.sauvegarde.beginObject();
				this.sauvegarde.name("initialisation");
				this.sauvegarde.beginObject();
					this.sauvegarde.name("pseudo").value(this.jeu.getPseudo());
					this.sauvegarde.name("largeurPlateau").value(this.jeu.getLargeur());
					this.sauvegarde.name("longeurPlateau").value(this.jeu.getLongueur());
					this.sauvegarde.name("explication").value(this.jeu.getExplicationRot());
				this.sauvegarde.endObject();

				this.sauvegarde.name("piecesDisponible");
				this.sauvegarde.beginObject();
					for(int i = 0; i < this.jeu.getPlateau().getPieceAJouer().size(); i++){
						this.sauvegarde.name(""+i);
						this.sauvegarde.beginObject();
							PiecesPuzzle pieceDispo = (PiecesPuzzle)this.jeu.getPlateau().getPieceAJouer().get(i);
							ecrirePiece(pieceDispo);
						this.sauvegarde.endObject();
					}
				this.sauvegarde.endObject();

				this.sauvegarde.name("piecesPlacer");
				this.sauvegarde.beginObject();
					if(!this.jeu.getPlateau().getPiecePlacer().isEmpty()){
						for(int i = 0; i < this.jeu.getPlateau().getPiecePlacer().size() ; i++){			
							this.sauvegarde.name(""+i);

							this.sauvegarde.beginObject();
								PiecesPuzzle pieceDispo = (PiecesPuzzle)this.jeu.getPlateau().getPiecePlacer().get(i);

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
			affiche("Sauvegarde terminer.. ");
		}
		catch(IOException e){
			affiche("Impossible de sauvegarder le fichier: "+e);
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
	
	private void affiche(String texte){
		if(this.affiche)
			System.out.println(texte);
	}
	
}
