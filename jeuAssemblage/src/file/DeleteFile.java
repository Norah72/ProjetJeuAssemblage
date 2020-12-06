package file;

import java.io.File;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
public class DeleteFile {
	
	private final File partieFichier; 

    /**
     * Constructeur
     * @param file nom du fichier
     */
    public DeleteFile(String file){
		this.partieFichier = new File(file);
	}
	
    /**
     * Suppression du fichier associé au constructeur
     */
    public void supprimerFile(){
		this.partieFichier.delete();
	}
}