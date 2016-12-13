import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import Jama.Matrix;


public class Individu   { 
	
	int taillegeno ; 
	private Matrix genotype ;
	int spdvia ;
	
	public static void main(String[] args) {
	}
	
//////////////////////CONSTRUCTEURS//////////////////////////////////
	
	public Individu(int taillegeno) {
		this.taillegeno = taillegeno ;
		this.genotype = new Matrix(taillegeno, taillegeno); 
	}
	
	//Constructeur de copie:					//gestion memmoire!
	public Individu(Individu indivi2, int taillegeno) {
		Individu indivi3 = new Individu(taillegeno);
		indivi3.setGenotype(indivi2) ;
		this.genotype = indivi3.getGenotype() ; //apparement copy est mieux que clone.
		this.taillegeno = taillegeno  ;
	}

	
	
//////////////////////////////////////RANDOM//////////////////////CMPLX: taillegeno?
	
	public void matriceRandom() {
		Random ran = new Random();
	    for (int i = 0; i < taillegeno; i++) {
	    	for (int j = 0; j < taillegeno ; j++ ) {
	    			this.genotype.set(i,j,ran.nextGaussian())  ; //loi normale, on peut changer les coef et faire des graffs
	    	}
	    }
	}
	
	
	
////////////////////////TEST VIABLE////////////////////////////////	
	public boolean testVia() {
	
		boolean viable = false ;
		List<Matrix> listmat  = new LinkedList<Matrix>(); //On peut laisser ca, linkedlist est bien adapt? pour la situation.
						
		////Construction du premier vecteur
		Matrix seu = Outils.vecteurDeDEpart(1, taillegeno);
        Matrix genotypeAtester = this.genotype; 
					
		////////////////grande boucle///////////////////////
		boolean cond = false; //condition de sortie de boucle, d?s que l'on sait si le vecteur est viable ou non.
		int tour = 0;
		Gboucle:	//label si on veut faire un break 2
		while ( cond == false ) {	//Ya un compteur
			
			Matrix vecteur =  genotypeAtester.times(seu) ; // multiplication de B(5*5) et seu(5*1)
			listmat.add(seu) ; 		
			
			/////seuillage du vecteur
			Outils.seuillerVecteur(vecteur, taillegeno);
			
			////test viable			
			boolean condI =  Outils.comparerVecteur(taillegeno, listmat.get(tour), vecteur) ; 			
							
			if (condI == true) {	//le vecteur est viable
				cond = true ; 	
				viable = true ; 
				this.spdvia = tour ;
				return viable ; //c'est ici le trick.. la boucle s'arrete la, et le reste de la fct aussi.
			}
						
			//// test non viable
			int k = 0 ;
			while ( k < tour ) {		// k < tour  pour parcourir la liste (tour représente la taille de listmat en gros) => C'est mauvais
				k++;
				boolean condK = Outils.comparerVecteur(taillegeno, listmat.get(k), vecteur) ; 
					
				if (condK == true) { //le vecteur est non viable
					cond = true ; 		
				}				
			}	 			
			seu = vecteur ;	
			tour++ ; 
		}	
		//this.spdvia = tour ;			//TODO le cocher ou non?? change rien on dirait
		return viable ;		
	}
	
	
	
////////////////////////////ACCOUPLEMENT////////////////////////////////// 
	public void accouplement(Individu pere, Individu mere) {
		
		Random ran = new Random() ;
		
		for (int j = 0; j < taillegeno; j++) {		//parcourt les lignes
			boolean Cond = ran.nextBoolean() ;
			if ( Cond == true ) {
				for (int k = 0 ;  k < taillegeno ; k++) {		//parcourt les colones
					this.genotype.set(j, k, mere.getGenotype().get(j, k));		
				}
			}
			else 
				for (int k = 0 ;  k < taillegeno ; k++) {
					this.genotype.set(j, k, pere.getGenotype().get(j, k));
				}	
		}			
	}
	
///////////////////////////MUTATION////////////////////////////////////////
	
	public  int  mutation(double seuilmut, int nbmutsurgeno) { 
		Random ran = new Random();
		Matrix matmut = this.genotype ;
		int cptMUT = 0 ;
		
		int i = 0 ;
		while (i < nbmutsurgeno) {
		int lignemut = ran.nextInt(taillegeno) ;	//inclusif ou exclusif le 4?? c'est bon normalement
		int colonemut = ran.nextInt(taillegeno) ;
		double probmut = ran.nextDouble(); //normalement entre 0 et 1
		double mutat = ran.nextGaussian();
		
			if (probmut > seuilmut) {		//pb: il n'est possible de faire qu'une mutation ? chaque fois
				matmut.set(lignemut, colonemut, mutat);
				cptMUT++ ;
			}
			i++;
		}
		this.genotype = matmut ; 
		return cptMUT ;		
	}
	
	
///////////////////////////KO////////////////////////////////////////	
	public void KO(double seuilKO, int nbKO) { //KO al?atoire
		Random ran = new Random();
			
		int i = 0 ;
		while (i < nbKO) {
			int coordKO = ran.nextInt(taillegeno) ; 
			double probKOmut = ran.nextDouble();
			i++;
			if (probKOmut > seuilKO) {  
				for (int j = 0 ; j < taillegeno ; j++) {//ligne ? 0
					this.genotype.set(coordKO, j, 0);
				}
				for (int k = 0 ; k < taillegeno ; k++) {//colone ? 0
					this.genotype.set(k, coordKO, 0);
				}				
			}
		}	
	}
	
	
///////////////////////////KO1/////////////////////////////////////////////////	
	public void KO1(double seuilKO, int coordKO) { //KO par coordonn?es(peut prendre l'a?atoire)
		Random ran = new Random();
		double probKOmut = ran.nextDouble();
		
		if (probKOmut > seuilKO) {  
			for (int j = 0 ; j < taillegeno ; j++) {//ligne ? 0
				this.genotype.set(coordKO, j, 0);
			}
			for (int k = 0 ; k < taillegeno ; k++) {//colone ? 0
				this.genotype.set(k, coordKO, 0);
			}				
		}		
	}
	
/////////////////////////KO2////////////////////////////////////////////	
	public void KO2(double seuilKO, int nbKO) { //KO la ligne la plus forte
		Random ran = new Random();
		double probKOmut = ran.nextDouble();
		
		int i = 0 ;
		while ( i < nbKO ) {	
			double maxligne = Integer.MIN_VALUE ;
			int coordmax = 0 ;
			i++ ;
			if (probKOmut > seuilKO) {  //integre la probas de mut
				for	( int j = 0 ; j < taillegeno ; j++) { //ligne
					double somligne = 0 ;
					for ( int k= 0 ; k < taillegeno ; k++) { // colone
						double valligne = this.genotype.get(j, k);
						somligne += valligne ;
					}			
					if ( maxligne < somligne) {
						maxligne = somligne ;
								coordmax = j ;
					}
				}
				KO1(seuilKO, coordmax);
			}	
		}	
	}
	
	
//////////////////////////////KO3///////////////////////////////////	a v?rifier mais a priori ca marche
	public void KO3(double seuilKO, int nbKO) { //KO la ligne la plus faible
		Random ran = new Random();
		double probKOmut = ran.nextDouble();
		
		int i = 0 ;
		while ( i < nbKO ) {	
			double minligne = Integer.MAX_VALUE ;
			int coordmin = 0 ;
			i++ ;
			if (probKOmut > seuilKO) {  //integre la probas de mut
				for	( int j = 0 ; j < taillegeno ; j++) { //ligne
					double somligne = 0 ;
					for ( int k= 0 ; k < taillegeno ; k++) { // colone
						double valligne = this.genotype.get(j, k);
						somligne += valligne ;
					}			
					if ( minligne > somligne) {
						minligne = somligne ;
								coordmin = j ;
					}
				}
				KO1(seuilKO, coordmin);
			}	
		}	
	}
	
	
////////////////////////////////////////KO4////////////////////////////	
	public void KO4(double seuilKO, int nbKO) { //KO la colone la plus forte
		Random ran = new Random();
		double probKOmut = ran.nextDouble();
		
		int i = 0 ;
		while ( i < nbKO ) {	
			double maxligne = Integer.MIN_VALUE ;
			int coordmax = 0 ;
			i++ ;
			if (probKOmut > seuilKO) {  //integre la probas de mut
				for	( int j = 0 ; j < taillegeno ; j++) { //colone
					double somligne = 0 ;
					for ( int k= 0 ; k < taillegeno ; k++) { // ligne
						double valligne = this.genotype.get(k, j);	//en th?orie suffit d'inverser k et j
						somligne += valligne ;
					}			
					if ( maxligne < somligne) {
						maxligne = somligne ;
								coordmax = j ;
					}
				}
				KO1(seuilKO, coordmax);
			}	
		}	
	}
	
////////////////////////////////////////KO5/////////////////////////////	
	public void KO5(double seuilKO, int nbKO) { //KO la colone la plus faible
		Random ran = new Random();
		double probKOmut = ran.nextDouble();
		
		int i = 0 ;
		while ( i < nbKO ) {	
			double minligne = Integer.MAX_VALUE ;
			int coordmin = 0 ;
			i++ ;
			if (probKOmut > seuilKO) {  //integre la probas de mut
				for	( int j = 0 ; j < taillegeno ; j++) { 
					double somligne = 0 ;
					for ( int k= 0 ; k < taillegeno ; k++) { 
						double valligne = this.genotype.get(k, j);
						somligne += valligne ;
					}			
					if ( minligne > somligne) {
						minligne = somligne ;
								coordmin = j ;
					}
				}
				KO1(seuilKO, coordmin);
			}	
		}	
	}
	
	
///////////////////////////Compteur lignes/////////////////	
	public double comptLigne(int index) {	//retoure les taillegeno valeurs repr?sentant chacune 1 seule g?ne.
			
			double valgene = 0 ;
			for (int j = 0 ; j < taillegeno ; j++ ) {
				valgene += this.genotype.get(index, j) ;
			}
			
		
			return valgene ;
		}
	
	
////////////////////////COMPTEUR GENE	
	public double comptGeneUni(int index) {	//retoure les taillegeno valeurs repr?sentant chacune 1 seule g?ne.
		
		double valgene = 0 ;
		for (int j = 0 ; j < taillegeno ; j++ ) {
			valgene += this.genotype.get(index, j) ;
		}
		for ( int k = 0 ; k < taillegeno ; k++) {
			valgene += this.genotype.get(k, index ) ;
		}
	
		return valgene ;
	}
	
///////////////////////////////ACCESSEURS///////////////////////////////////////////////////

	public Matrix getGenotype() {
		return genotype;
	}
	
	public int getSpdvia() {
		return this.spdvia ;
	}
	
	public String printSpdvia() {
		System.out.print(" print spdviat: " + this.spdvia+ " ");
		return "" ;
	}
	
	public void setGenotype(Individu machin) {  //gestion memmoire!!! wow sans deconner ca marche. un vrai bordel ce code
		Matrix truc = machin.getGenotype() ;
		Matrix truc2 = new Matrix(taillegeno,taillegeno);
		truc2 = truc.copy();
		this.genotype = truc2 ;
	}
	
	public String printGenotype() {
		this.genotype.print(6, 5);
		System.out.print("mem matrix: ");
		System.out.print(genotype);
		return "" ;
	}
}



