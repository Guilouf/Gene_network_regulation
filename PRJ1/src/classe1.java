import java.util.Random ;
import java.util.Arrays ;
import java.lang.Math ;
import Jama.Matrix; ;


public class classe1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub          ??? keep it??

		classe1 ranmat = new classe1() ;
		//ranmat.matriceRandom(args) ;
		
		classe1 viatest = new classe1() ;
		viatest.testVia(args) ;
	}

	
	public static double[][] matriceRandom(String[] args) {
		double[][] matran = new double[5][5];
		//Random ran = new Random();
		
	    for (int i = 0; i < matran.length; i++) {
	    	for (int u = 0; u < matran.length ; u++ ) {
	    			matran[i][u] =  Math.random() * 10 ;
	    	}
	    }

	    
	    
	    //System.out.println(Arrays.deepToString(matran)) ;
	    
		return matran;
	   	    

	}
	
	public static void testtest(String[] args) {
		
		
	}
	
	public static void testVia(String[] args ) {
		classe1 ranmat = new classe1() ;
		//ranmat.matriceRandom(args) ;
		
		
		//[][]: lignes puis colones
		// truc.length: nb des lignes/ truc[0].length: nb colones 
		double[][] vectest =  new double[5][1] ;
		
		for (int i = 0; i < 5 ; i++) {
			vectest[i][0] =  1 ;
		}
		
		System.out.println(Arrays.deepToString(vectest)) ;
		
	    			
		double[][] resultat = new double [5][1];
		
		Matrix A = new Matrix(vectest, 5, 1);
        Matrix B = new Matrix(ranmat.matriceRandom(args), 5 , 5);
		
		//System.out.println(Arrays.deepToString(ranmat.matriceRandom(args)));
        //en imprimant, connard, tu redemande d'autre nombres al?atoires...
        
		B.print(6, 5);
		
		//installer jama; vectorz, colt??
		//resultat = vectest * ranmat.matriceRandom(args) ;
		
		Matrix C = B.times(A) ;
		
		C.print(6, 5);
		
	}
}
