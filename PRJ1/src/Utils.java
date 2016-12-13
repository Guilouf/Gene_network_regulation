import Jama.Matrix;

/**
 *
 */

public class Utils {

	
	
	/**
	 * s
	 * @param mat1
	 * @param mat2
	 * @return
	 */
	static public boolean comparerVecteur(Matrix mat1, Matrix mat2) {
		Matrix resultat = mat1.minusEquals(mat2);
		boolean equal = true;
		for (int i = 0; i < 5; i++) {
			if(resultat.get(i, 0)!=0){
				equal=false;
				break;
			}
		}
		return equal;
	}
	
	
	
	/**
	 * 
	 * @param vecteur
	 * @return
	 */
	static public Matrix seuillerVecteur(Matrix vecteur) {
		for (int j = 0; j < 5; j++) {
			if (vecteur.get(j, 0) < 0) {
				vecteur.set(j, 0, -1.0);
			} else if (vecteur.get(j, 0) > 0) {
				vecteur.set(j, 0, 1.0);
			} else {
				vecteur.set(j, 0, 0.0);
			}
		}
		return vecteur;
	}
}
