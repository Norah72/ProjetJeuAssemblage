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
	
	private ArrayList<PiecesPuzzle> piecesAJouer = new ArrayList<PiecesPuzzle>();
	private ArrayList<PiecesPuzzle> piecesPlacer = new ArrayList<PiecesPuzzle>();
	private ArrayList<ArrayList<Integer>> piecesPlacerCoordonnees = new ArrayList<ArrayList<Integer>>();
	
	private String piece, rotation, x, y;
	private int changementDeMemoirePiece = 0;
	private boolean pieceAjouter = false;
	
	
	public void chargerSauvegarde(PlayConsole jeu) throws IOException{
		int changementDeMemoire = 0;
		
		for (String ligne : Files.readAllLines(partieFichier.toPath())) {
			if(ligne.equals("."))
				changementDeMemoire++;
			
			else if(ligne.equals("end")){
				jeu.setPlateauConsole(new PlateauPuzzle(jeu.getlargeurPlateauX(),jeu.getlongueurPlateauY()));
				
				jeu.setPieceAJouer(this.piecesAJouer);
				
				if(!this.piecesPlacer.isEmpty()){					
					jeu.setPiecePlacer(this.piecesPlacer);
					
					for (int i = 0;i < this.piecesPlacer.size(); i++)
						jeu.getPlateauConsole().addPiece((PiecesPuzzle)this.piecesPlacer.get(i), this.piecesPlacerCoordonnees.get(i));
					
				}
				
			}else if(changementDeMemoire<3){
				
				if(changementDeMemoire==0){
					jeu.setPseudo(ligne);
				}else if(changementDeMemoire==1){
					jeu.setLargeurPlateauX(Integer.parseInt(ligne));
				}else if(changementDeMemoire==2){
					jeu.setLongueurPlateauY(Integer.parseInt(ligne));
				}
				
			}else{
				
				if(changementDeMemoire==3){
					ajoutPieceList(this.piecesAJouer, ligne);
					this.changementDeMemoirePiece +=1;
				}else if(changementDeMemoire==4){
					ajoutPieceList(this.piecesPlacer, ligne);
					this.changementDeMemoirePiece+=1;
				}else if(changementDeMemoire==5){
					jeu.setExplicationRot(Boolean.valueOf(ligne));
				}
				
			}
		}
	}
	
	private void ajoutPieceList(ArrayList list, String ligne){
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
			
			if(piece.equals("PieceH"))
				list.add(new PieceH(Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(rotation)));
			
			else if(piece.equals("PieceL"))
				list.add(new PieceL(Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(rotation)));
			
			else if(piece.equals("PieceRectangle"))
				list.add(new PieceRectangle(Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(rotation)));
			
			else if(piece.equals("PieceT"))
				list.add(new PieceT(Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(rotation)));
			
			this.pieceAjouter = true;
			
		}else if(this.changementDeMemoirePiece == 4){
			ArrayList<Integer> coo = new ArrayList<Integer>();
			
			for(String caractere : ligne.split(","))
				coo.add(Integer.parseInt(caractere));
			
			this.piecesPlacerCoordonnees.add(coo);			
		}
	}
}
