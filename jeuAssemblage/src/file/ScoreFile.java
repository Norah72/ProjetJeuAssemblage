package file;

import controleur.PlayConsole;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.HashMap;


public class ScoreFile{
	private HashMap<String, String> listeScore = new HashMap<String, String>();
	private File scoreFile = new File("src/file/partie/score.txt");

	public void write(PlayConsole jeu) throws IOException {
		try{
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
	
	public void affiche(){
		boolean affichePseudo = true;
                String p = null;
                String s = null;
		try{
			if(!this.scoreFile.exists()){
				System.out.println("Il n'y a pas encore de score !");
			}else{
				for (String ligne : Files.readAllLines(this.scoreFile.toPath())) {
                                    for(String caractere : ligne.split(" ")){
                                        if(affichePseudo){
                                            System.out.print("- Pseudo : ");
                                            p = caractere;
                                            System.out.println(p);
                                        }
                                        else{
                                            System.out.print(" | Score : ");
                                            s = caractere;
                                            System.out.println(s);
                                        }
                                        affichePseudo = !affichePseudo;
                                    }
                                    listeScore.put(p,s);
                                    System.out.println(" point(s)");
				}
			}
                    //System.out.println(listeScore);
		}
		catch(Exception e){
			System.out.println("Impossible d'afficher le score : "+e);
		}
		
	}
        public HashMap<String, String> getListeScore(){
            return listeScore;
        }
}
