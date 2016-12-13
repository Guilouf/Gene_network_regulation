import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Jama.Matrix;

/**
 * 
 */

public class Traitement {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Traitement projet = new Traitement();
		

		Individu parent = projet.creerParent();
		

		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Veuillez saisir le nombre d'individu que vous voulez traiter ");
		int nbr = scanner.nextInt();
		System.out.println("vous avez saisi " + nbr);
		scanner.close();
		//TODO traiter les exceptions ..
		projet.genererParentsVialbes(30);
	}
	
	
	
	/**
	 * Méthode random qui génere les parents
	 * @return 
	 */
	public Individu creerParent(){

		double[][] mat = new double[5][5];
		Random nbr = new Random();
		
	    for (int i = 0; i < mat.length; i++) {
	    	for (int j = 0; j < mat.length ; j++ ) {
	    			mat[i][j] = nbr.nextGaussian() ; 
	    	}
	    }

	    Matrix RandomMat = new Matrix(mat, 5, 5);
		Individu parent = new Individu(RandomMat);
		return parent;
		
	}
	
	

	
	/**
	 * Méthode qui genere une liste des parents viables
	 * @param args
	 * @return une liste
	 */
	public List<Individu> genererParentsVialbes(int nbr) {

		List<Individu> ListParentsViables = new LinkedList<Individu>(); // On cree une liste vide
		int i=0;
		Individu individu; //on cree l'instance individu
		
		while (i < nbr){ 
			individu = creerParent(); 
			//on utilise la meme instance (pour ne pas saturer la memoire)
			if (individu.viable()) { 
				// on test la viabilite de l'individu 
				ListParentsViables.add(individu);
				i++;
			}
		}
		
		return ListParentsViables;
	}
	
	
	/**
	 * 
	 * @param pere
	 * @param mere
	 * @return
	 */
	public Individu creerFils(Individu pere, Individu mere){
		double[][] mat = new double[5][5];
		Matrix matFils = new Matrix(mat, 5, 5);;
		
		Random ran = new Random();
		for (int j = 0; j < 5; j++) { // parcourt les lignes
			boolean C = ran.nextBoolean();
			System.out.println("creation fils, random = "+C);
			if (C == true) {
				for (int k = 0; k < 5; k++) { // parcourt les colonnes
					matFils.set(j, k, mere.getGenotype().get(j, k)); // a quoi sert le simple = true??
				}
			} else
				for (int k = 0; k < 5; k++) {
					matFils.set(j, k, pere.getGenotype().get(j, k));
				}

		}
		
		Individu fils = new Individu(matFils);
		return fils;
		
	}
	
	/**
	 * Méthode qui permet de génerer des fils
	 * 
	 * @param args
	 */
	public List<Individu> genererFils(List<Individu> ListParentsViables) {
		int i = 0;
		List<Individu> listFils = new LinkedList<Individu>();

		Individu pere;
		Individu mere;
		Individu fils;
		while (i < 300) {
			Random ran = new Random();
			int a = ran.nextInt(i); // tirer aléatoirement le parent 1 de la liste ListViables
			int b = ran.nextInt(i); // tirer aléatoirement le parent 2 de la liste ListViables
			pere = ListParentsViables.get(a);
			mere = ListParentsViables.get(b);
			
			fils = creerFils(pere, mere);
			if(fils.viable()){
				listFils.add(fils);
				System.out.println(fils);
				i++;
			}
		} // doit retourner une liste de matrice
		return listFils;

	}
	


}
