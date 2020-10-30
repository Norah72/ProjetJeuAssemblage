package file;

import controleur.PlayConsole;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

public class SauvegardeScore {
	
	private File scoreFile;

	public SauvegardeScore(){

	}

	public void write(PlayConsole jeu) throws IOException {
		try{
			this.scoreFile = new File("src/file/partie/score.txt");
			if(!this.scoreFile.exists()){
				this.scoreFile.createNewFile();
			}
			
			FileWriter score = new FileWriter (this.scoreFile, true);
			score.write(jeu.getpseudo()+" "+jeu.getPlateauConsole().getScore()+"\n");
			score.close();
		}
		catch(Exception e){
			System.out.println("Impossible d'enregistrer le score : "+e);
		}
	}
}
