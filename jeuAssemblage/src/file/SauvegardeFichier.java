package file;

import controleur.PlayConsole;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import piecesPuzzle.pieces.PieceH;
import piecesPuzzle.pieces.PieceL;
import piecesPuzzle.pieces.PieceRectangle;
import piecesPuzzle.pieces.PieceT;
import piecesPuzzle.pieces.PiecesPuzzle;


public class SauvegardeFichier {
	
	Writer sauvegarde;
	File partieFichier = new File("src/file/partie/partie.txt"); 
	
	public SauvegardeFichier(){
		

	} 
	
	public void write(PlayConsole jeu) throws IOException{
		try{
			//System.out.println(partieFichier.getAbsolutePath());

			PrintWriter sauvegarde =  new PrintWriter(new FileWriter (partieFichier));
			/*
			sauvegarde.println(jeu.getpieceAJouer().get(0));
			sauvegarde.println(jeu.getpiecePlacer().get(0));
			sauvegarde.println(jeu.getexplicationRot());*/
			sauvegarde.println(jeu.getpseudo());
			sauvegarde.println(".");
			sauvegarde.println(jeu.getlargeurPlateauX());
			sauvegarde.println(".");
			sauvegarde.println(jeu.getlongueurPlateauY());
			sauvegarde.println(".");
			for(int i = 0; i < jeu.getpieceAJouer().size(); i++){
				PiecesPuzzle pieceDispo = (PiecesPuzzle)jeu.getpieceAJouer().get(i);
				if(pieceDispo instanceof PieceH)
					sauvegarde.println("PieceH");
				else if(pieceDispo instanceof PieceL)
					sauvegarde.println("PieceL");
				else if(pieceDispo instanceof PieceRectangle)
					sauvegarde.println("PieceRectangle");
				else if(pieceDispo instanceof PieceT)
					sauvegarde.println("PieceT");
				sauvegarde.println(pieceDispo.getRotation());
				sauvegarde.println(pieceDispo.getX());
				sauvegarde.println(pieceDispo.getY());
				sauvegarde.println("-");
			}
			sauvegarde.println(".");
			if(!jeu.getpiecePlacer().isEmpty()){
				for(int i = 0; i < jeu.getpiecePlacer().size() ; i++){
					PiecesPuzzle pieceDispo = (PiecesPuzzle)jeu.getpiecePlacer().get(i);
					if(pieceDispo instanceof PieceH)
						sauvegarde.println("PieceH");
					else if(pieceDispo instanceof PieceL)
						sauvegarde.println("PieceL");
					else if(pieceDispo instanceof PieceRectangle)
						sauvegarde.println("PieceRectangle");
					else if(pieceDispo instanceof PieceT)
						sauvegarde.println("PieceT");
					
					sauvegarde.println(pieceDispo.getRotation());
					sauvegarde.println(pieceDispo.getX());
					sauvegarde.println(pieceDispo.getY());
					sauvegarde.println(pieceDispo.getCoo().get(0)+","+pieceDispo.getCoo().get(1));
					sauvegarde.println("-");
				}
			}
			sauvegarde.println(".");
			sauvegarde.println(jeu.getexplicationRot());
			sauvegarde.println("end");
			sauvegarde.close();
		}
		catch(Exception e){
			System.out.println("Impossible de sauvegarder le fichier: "+e);
		}
	}
}
