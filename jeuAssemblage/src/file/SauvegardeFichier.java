package file;

import controleur.PlayConsole;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import piecesPuzzle.pieces.PieceH;
import piecesPuzzle.pieces.PieceL;
import piecesPuzzle.pieces.PieceRectangle;
import piecesPuzzle.pieces.PieceT;
import piecesPuzzle.pieces.PiecesPuzzle;


public class SauvegardeFichier {
	
	private PlayConsole jeu;
	
	private File partieFichier = new File("src/file/partie/partie.txt"); 
	private PrintWriter sauvegarde;
	
	
	public SauvegardeFichier(PlayConsole jeu){
		this.jeu = jeu;
	}
	
	public void ecrire() throws IOException{
		try{

			this.sauvegarde =  new PrintWriter(new FileWriter (partieFichier));
			
			this.sauvegarde.println(this.jeu.getpseudo());
			this.sauvegarde.println(".");
			
			this.sauvegarde.println(this.jeu.getlargeurPlateauX());
			this.sauvegarde.println(".");
			
			this.sauvegarde.println(this.jeu.getlongueurPlateauY());
			this.sauvegarde.println(".");
			
			for(int i = 0; i < this.jeu.getpieceAJouer().size(); i++){
				PiecesPuzzle pieceDispo = (PiecesPuzzle)this.jeu.getpieceAJouer().get(i);
				ecrirePiece(pieceDispo);
				this.sauvegarde.println("-");
			}
			
			this.sauvegarde.println(".");
			
			if(!this.jeu.getpiecePlacer().isEmpty()){
				for(int i = 0; i < this.jeu.getpiecePlacer().size() ; i++){
					PiecesPuzzle pieceDispo = (PiecesPuzzle)this.jeu.getpiecePlacer().get(i);
					
					ecrirePiece(pieceDispo);
					
					sauvegarde.println(pieceDispo.getCoo().get(0)+","+pieceDispo.getCoo().get(1));
					sauvegarde.println("-");
				}
			}
			
			this.sauvegarde.println(".");
			
			this.sauvegarde.println(this.jeu.getexplicationRot());
			this.sauvegarde.println("end");
			this.sauvegarde.close();
		}
		catch(Exception e){
			System.out.println("Impossible de sauvegarder le fichier: "+e);
		}
	}
	
	private void ecrirePiece(PiecesPuzzle pieceDispo){
		if(pieceDispo instanceof PieceH)
			this.sauvegarde.println("PieceH");
		else if(pieceDispo instanceof PieceL)
			this.sauvegarde.println("PieceL");
		else if(pieceDispo instanceof PieceRectangle)
			this.sauvegarde.println("PieceRectangle");
		else if(pieceDispo instanceof PieceT)
			this.sauvegarde.println("PieceT");

		this.sauvegarde.println(pieceDispo.getRotation());
		this.sauvegarde.println(pieceDispo.getX());
		this.sauvegarde.println(pieceDispo.getY());
	}
	
	
}
