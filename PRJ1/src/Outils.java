import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import Jama.Matrix;

//On ne met ici que les methodes statiques

public class Outils {

//////////////////////////////////SEUILLER VECTEUR///////////////////////////////////	
	
	static public Matrix seuillerVecteur(Matrix vecteur, int taillegeno) {
		
		for (int j = 0; j < taillegeno; j++) {
			if (vecteur.get(j, 0) < 0) {
				vecteur.set(j, 0, -1.0);
			} 
			else if (vecteur.get(j, 0) > 0) {
				vecteur.set(j, 0, 1.0);
			} 
			else {
				vecteur.set(j, 0, 0.0);
			}
		}
		return vecteur;
	}

////////////////COMPARER VECTEUR///////////////////////////
	
	static public boolean comparerVecteur(int taillegeno, Matrix avantmat2, Matrix vecteur2) {
		Matrix avantmat = avantmat2 ;
		Matrix vecteur = vecteur2 ;
		boolean condI = true;
		for (int o = 0 ; o < taillegeno ; o++) {		//si une seule case est diff?rente, les matrices sont diff?rentes
			if ( vecteur.get(o, 0) != avantmat.get(o, 0) )
				condI = false ;	
		}
		return condI ;
	}		
	
//////////////////VECTEUR DE DEPART////////////////////////////////	
	
	static public Matrix vecteurDeDEpart(int valeurVect, int taillegeno ) {
		Matrix vectest =  new Matrix(taillegeno,1) ;
		
		for (int i = 0; i < taillegeno ; i++) { 
			vectest.set(i, 0, valeurVect); ;
		}	
		return vectest ;
	}
	
}