/*import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;*/
import java.util.*;

import Jama.Matrix;

/**
 *
 *
 */
public class Individu {

	private Matrix genotype;
	private boolean mute;
	private boolean viable;

	/**
	 * Constructeur de la classe Individu
	 * 
	 * @param genotype
	 */
	public Individu(Matrix genotype) {
		this.genotype = genotype;
	}

	/**
	 * Methode qui teste la viabilite des individus
	 * 
	 * @return un boolean
	 */
	public boolean viable() {

		List<Matrix> listVecteurs = new LinkedList<Matrix>();

		// tableau de 5 lignes et 1 colonne
		double[][] vect = new double[5][1];

		// on remplit le tableau
		for (int i = 0; i < 5; i++) {
			vect[i][0] = 1;
		}

		Matrix vecteur = new Matrix(vect);
		listVecteurs.add(vecteur);

		boolean fin = false;
		boolean viable = false;
		
		while (fin == false) {

			Matrix produit = this.getGenotype().times(vecteur);
			// Produit matriciel (individu * vecteur)
			// System.out.println(Arrays.deepToString(produit));

			// Seuillage en (1, -1 ou 0) des vecteurs obtenus apres
			// multiplication
			produit = Utils.seuillerVecteur(produit);
			System.out.println(" vecteur seuillé");

			// Matrix avantMat = vecteur;
			// comparaison du produit seuillé avec le vecteur
			vecteur = listVecteurs.get(listVecteurs.size()-1);

			boolean vectEgaleProduit = Utils.comparerVecteur(vecteur, produit);
			// si le produit est egal au vecteur, le produit est viable, je
			// break .
			if (vectEgaleProduit) {
				System.out.println("l'individu est viable");
				viable = true;
				fin = true;
				// sinon, on compare le produit dans toute la liste listVecteurs
			} else {

				// on parcours la liste sauf le dernier element "vecteur" (deja
				// fait)
				int j = 0;
				while (j < listVecteurs.size() - 1) {
					vecteur = listVecteurs.get(j);
					vectEgaleProduit = Utils.comparerVecteur(vecteur,	produit);

					// si je le trouve je le garde pas et je break (parce que je
					// vais avoir le meme cercle des vecteurs par la suite)
					if (vectEgaleProduit) {
						System.out.println("vecteur éliminé");
						System.out.println("l'individu n'est pas viable");
						viable=false;
						fin = true;
						break;
					}
					j++;
				}

				//
				if (fin == false) {
					// on l'ajoute a la liste et je continu.
					System.out.println("vecteur ajouté à la liste");
					System.out.println("on recommence");
					vecteur = produit;
					listVecteurs.add(vecteur);
				}

			}

		}

		 return viable;

	}

	/**
	 * méthode qui cree des mutations de manière aléatoire chez les individus
	 * 
	 * @return un boolean
	 */
	public boolean creerMutationAleatoire() {
		Random random = new Random();
		// seuil pour mutation d'un seul �l�ment
		double seuilMut = random.nextDouble();
		// seuil pour mutation KO
		double seuilKO = random.nextDouble();
		Matrix individu = getGenotype();
		
		int randomLigne = random.nextInt(6);
		int randomColonne = random.nextInt(6);
		
		boolean mutation = false;
		if (seuilMut > 0.95) {
			// si seuilKO est > 0.95 on provoque un KO
			mutation = true;
			for (int j = 0; j < 5; j++){
				individu.set(randomLigne, j, seuilKO);
			}
			for (int i = 0; i < 5; i++) { // si on veut faire (j<individu.length)
				individu.set(i, randomColonne, seuilKO);
			}
			
		}else if (seuilMut > 0.90) {
			mutation = true;
			// si seuilMutation est > 0.90 on provoque une mutation
			// on modifie une valeure d'un element
			individu.set(randomLigne, randomColonne, seuilMut);
		}
		return mutation;
	}

	/**
	 * @return the genotype
	 */
	public Matrix getGenotype() {
		return genotype;
	}

	/**
	 * @param genotype
	 *            the genotype to set
	 */
	public void setGenotype(Matrix genotype) {
		this.genotype = genotype;
	}

	/**
	 * @return the mute
	 */
	public boolean isMute() {
		return mute;
	}

	/**
	 * @param mute
	 *            the mute to set
	 */
	public void setMute(boolean mute) {
		this.mute = mute;
	}

	 @Override
	 public String toString() {
		 genotype.print(5, 5);
		 return "";
	 }

	/**
	 * @return the viable
	 */
	public boolean isViable() {
		return viable;
	}

	/**
	 * @param viable
	 *            the viable to set
	 */
	public void setViable(boolean viable) {
		this.viable = viable;
	}

}
