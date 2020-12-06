package file;

import controleur.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
public class ScoreFile implements Comparator<String>{
	private HashMap<String, String> listeScore = new HashMap<String, String>();
	private final File scoreFile = new File("src/file/partie/score.txt"); 
        private TreeMap<String,String> listeScoreTri = new TreeMap<String,String>(this);
        private String verifPseudo;
        private int verifScore;

    /**
     *
     * @param jeu
     * @throws IOException
     */
    public void write(Play jeu) throws IOException {
		try{
			if(!this.scoreFile.exists()){
				this.scoreFile.createNewFile();
			}
			FileWriter score = new FileWriter (this.scoreFile, true);
                        verifPseudo = jeu.getPseudo();
                        verifScore = jeu.getPlateau().getScore();
			score.write(jeu.getPseudo()+" "+jeu.getPlateau().getScore()+"\n");
			score.close();
		}
		catch(Exception e){
			System.out.println("Impossible d'enregistrer le score : "+e);
		}
	}
	
    /**
     *
     */
    public void affiche(){
		boolean affichePseudo = true;
                boolean present = false;
                String p = null;
                String s = null;
		try{
			if(!this.scoreFile.exists()){
				System.out.println("Il n'y a pas encore de score !");
			}else{
				for (String ligne : Files.readAllLines(this.scoreFile.toPath(),Charset.forName("UTF-8"))) {
                                    for(String caractere : ligne.split(" ")){
                                            if(affichePseudo){
                                                    System.out.print("- Pseudo : ");
                                                    p=caractere;
                                            }
                                            else{
                                                    System.out.print(" | Score : ");
                                                    s=caractere;
                                            }
                                            System.out.print(caractere);
                                            affichePseudo = !affichePseudo;
                                    }
                                    System.out.println(" point(s)");
                                    if(p.equals(verifPseudo)){
                                        if(Integer.parseInt(s)>=verifScore){
                                            listeScore.put(p,s);
                                            verifScore=Integer.parseInt(s);
                                            present=true;
                                        }
                                        else if(Integer.parseInt(s)<verifScore){
                                            if(!present){
                                                listeScore.remove(p);
                                                listeScore.put(p,Integer.toString(verifScore));
                                                present=true;
                                            }
                                        }
                                    }
                                    else{
                                       listeScore.put(p,s);
                                    }
                                }
                                if(listeScore.size()>12){
                                    while(listeScore.size()>12){
                                        int min = 9999;
                                        String key = "";
                                        for(String i : listeScore.keySet()){
                                            int tmp = Integer.parseInt(listeScore.get(i));
                                            if(tmp<min){
                                                min=tmp;
                                                key = i;
                                            }
                                        }
                                        listeScore.remove(key);
                                    }
                                    File tmp = new File("temp.txt");
                                    BufferedWriter newScore = new BufferedWriter (new FileWriter(tmp));
                                    for(String i : listeScoreTri.keySet()){
                                        newScore.write(i+" "+listeScoreTri.get(i)+"\n");
                                    }
                                    newScore.close();
                                    scoreFile.delete();
                                    tmp.renameTo(new File("src/file/partie/score.txt"));
                                }
                                listeScoreTri.putAll(listeScore);
                        }
		}
		catch(Exception e){
			System.out.println("Impossible d'afficher le score : "+e);
		}
		
	}

    /**
     *
     * @return
     */
    public TreeMap<String, String> getListeScore(){
            return listeScoreTri;
        }
   
    public int compare(String a, String b) {
        if (Integer.parseInt(listeScore.get(a)) <= Integer.parseInt(listeScore.get(b))) {
            return 1;
        }
        else {
            return -1;
        }
    }
}
