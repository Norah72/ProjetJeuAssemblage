package file;

import java.io.File;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
public class DeleteFile {
	
	private final File partieFichier; 

    /**
     *
     * @param file
     */
    public DeleteFile(String file){
		this.partieFichier = new File(file);
	}
	
    /**
     *
     */
    public void supprimerFile(){
		this.partieFichier.delete();
	}
}