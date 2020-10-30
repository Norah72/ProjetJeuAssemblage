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

	File partieFichier = new File("src/file/partie/partie.txt"); 
	ArrayList<PiecesPuzzle> piecesAJouer = new ArrayList<PiecesPuzzle>();
	ArrayList<PiecesPuzzle> piecesPlacer = new ArrayList<PiecesPuzzle>();
	ArrayList<ArrayList<Integer>> piecesPlacerCoordonnees = new ArrayList<ArrayList<Integer>>();
	String piece, rotation, coordonnees, x, y, coo;
	int j;
	boolean pieceAjouter = false;
	
	public ChargerPartie(){
		

	} 
	
	public void write(PlayConsole jeu) throws IOException{
		int i = 0;
		int j = 0;
		
		for (String ligne : Files.readAllLines(partieFichier.toPath())) {
			if(ligne.equals("."))
				i++;
			
			else if(ligne.equals("end")){
				jeu.setPlateauConsole(new PlateauPuzzle(jeu.getlargeurPlateauX(),jeu.getlongueurPlateauY()));
				
				for(int z = 0;z < this.piecesAJouer.size(); z++)
					this.piecesAJouer.get(z).createPiece();
				
				jeu.setPieceAJouer(this.piecesAJouer);
				
				if(!this.piecesPlacer.isEmpty()){
					for(int o = 0;o < this.piecesPlacer.size(); o++)
						this.piecesPlacer.get(o).createPiece();
					
					jeu.setPiecePlacer(this.piecesPlacer);
					
					for (int y = 0;y < this.piecesPlacer.size(); y++)
						jeu.getPlateauConsole().addPiece((PiecesPuzzle)this.piecesPlacer.get(y), this.piecesPlacerCoordonnees.get(y));
					
				}
				
			}else if(i<3){
				
				if(i==0){
					jeu.setPseudo(ligne);
				}else if(i==1){
					jeu.setLargeurPlateauX(Integer.parseInt(ligne));
				}else if(i==2){
					jeu.setLongueurPlateauY(Integer.parseInt(ligne));
				}
				
			}else{
				
				if(i==3){
					ajoutPieceList(this.piecesAJouer, ligne);
					this.j+=1;
				}else if(i==4){
					ajoutPieceList(this.piecesPlacer, ligne);
					this.j+=1;
				}else if(i==5){
					jeu.setExplicationRot(Boolean.valueOf(ligne));
				}
				
			}
		}
	}
	
	private void ajoutPieceList(ArrayList list, String ligne){
		if(this.pieceAjouter){
			this.pieceAjouter = false;
			this.j = 0;
		}
		
		if(this.j == 0)
			piece = ligne;
		else if(this.j == 1)
			rotation = ligne;
		else if(this.j == 2)
			x = ligne;
		else if(this.j == 3)
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
			
		}else if(this.j == 4){
			ArrayList<Integer> coo = new ArrayList<Integer>();
			
			for(String caractere : ligne.split(","))
				coo.add(Integer.parseInt(caractere));
			
			this.piecesPlacerCoordonnees.add(coo);			
		}
	}
}
