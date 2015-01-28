import java.util.Random ;
import java.util.Arrays ;
import java.lang.Math ;
import Jama.Matrix; ;


public class classe1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
	    			matran[i][u] =  Math.random() * 10 -5;
	    	}
	    }

	    //mettre des nombres négatifs!
	    
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
        //en imprimant, connard, tu redemande d'autre nombres aléatoires...
        
		B.print(6, 5);
		
		//installer jama; vectorz, colt??
		//resultat = vectest * ranmat.matriceRandom(args) ;
		
		boolean o = false ;
		
		Matrix seu = new Matrix (vectest, 5, 1);
		
		for ( int i = 0 ; i<1050 ; i++ ) {
			
			
			Matrix C = B.times(seu) ;
		
			C.print(6, 5);
			
			//seuillage
			for (int u = 0; u < 5 ; u++) {
				if ( C.get(u, 0) < 0 )
					C.set(u, 0, -1.0);
				else C.set(u, 0, 1.0);
		    	   
		    }
			
			
			System.out.print("C seuillée");
			C.print(6, 5);
			seu.print(6, 5);
			A.print(6,5);
			
			if ( seu.equals(A) ) {
				System.out.print("cassé!!!!!!!!!!!!!!!!!!!!!!!");
				break;
				//o = true ;	
				//pige pas, marche qd les matrices sont différentes mais pas qd egales...
			}
			
			seu = C ;
			
			
		}
		
	}
}
