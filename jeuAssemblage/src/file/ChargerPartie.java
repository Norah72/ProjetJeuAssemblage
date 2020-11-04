package file;

import controleur.PlayConsole;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import modele.PlateauPuzzle;
import piecesPuzzle.pieces.PieceH;
import piecesPuzzle.pieces.PieceL;
import piecesPuzzle.pieces.PieceRectangle;
import piecesPuzzle.pieces.PieceT;
import piecesPuzzle.pieces.PiecesPuzzle;

public class ChargerPartie {

	private File partieFichier = new File("src/file/partie/partie.txt"); 
	
	private ArrayList<ArrayList<Integer>> piecesPlacerCoordonnees = new ArrayList<ArrayList<Integer>>();
	
	private String piece, rotation, x, y;
	private int changementDeMemoirePiece = 0;
	private boolean pieceAjouter = false;
	
	private PlayConsole jeu;
	
	public ChargerPartie(PlayConsole jeu){
		this.jeu = jeu;
	}
	
	
	public void chargerSauvegarde() throws IOException{
		int changementDeMemoire = 0;
		
		for (String ligne : Files.readAllLines(partieFichier.toPath())) {
			if(ligne.equals("."))
				changementDeMemoire++;
			
			else if(ligne.equals("end")){
				this.jeu.setPlateauConsole(new PlateauPuzzle(this.jeu.getlargeurPlateauX(),this.jeu.getlongueurPlateauY()));
				
			}else if(changementDeMemoire<3){
				
				if(changementDeMemoire==0){
					this.jeu.setPseudo(ligne);
				}else if(changementDeMemoire==1){
					this.jeu.setLargeurPlateauX(Integer.parseInt(ligne));
					System.out.println(ligne);
				}else if(changementDeMemoire==2){
					this.jeu.setLongueurPlateauY(Integer.parseInt(ligne));
				}
				
			}else{
				
				if(changementDeMemoire!=5){
					ajoutPieceList(ligne);
					this.changementDeMemoirePiece +=1;
				}else if(changementDeMemoire==5){
					this.jeu.setExplicationRot(Boolean.valueOf(ligne));
				}
				
			}
		}
	}
	
	private void ajoutPieceList(String ligne){
		if(this.pieceAjouter){
			this.pieceAjouter = false;
			this.changementDeMemoirePiece = 0;
		}
		
		if(this.changementDeMemoirePiece == 0)
			piece = ligne;
		else if(this.changementDeMemoirePiece == 1)
			rotation = ligne;
		else if(this.changementDeMemoirePiece == 2)
			x = ligne;
		else if(this.changementDeMemoirePiece == 3)
			y = ligne;
		else if(ligne.equals("-")){
			this.jeu.getPlateauConsole().newPiece(piece, Integer.parseInt(y),Integer.parseInt(rotation));
			
			this.pieceAjouter = true;
			
		}else if(this.changementDeMemoirePiece == 4){
			ArrayList<Integer> coo = new ArrayList<Integer>();
			
			for(String caractere : ligne.split(","))
				coo.add(Integer.parseInt(caractere));
			this.jeu.getPlateauConsole().newPiece(piece, Integer.parseInt(y),Integer.parseInt(rotation));
			this.jeu.getPlateauConsole().addPiece(this.jeu.getPlateauConsole().getPieceAJouer().get(-1), coo);
			this.piecesPlacerCoordonnees.add(coo);
		}
	}
}
