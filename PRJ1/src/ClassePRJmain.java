import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import Jama.Matrix;

import java.applet.Applet;
import java.awt.Graphics;

/*
 * -compteur de gènes uniques, fait une liste de gènes? un objet gènes?
 * 
 * -faire attention a ne pas deposseder de leur reference les objets que l'on veut copier!
 * 
 * -pk spd via se stabilise parfois à 1 et d'autres à 0?
 * 
 * 

QUESTIONS PROF:
-serialisation? je pense pas.
-imprimer l'adresse mem de la liste, et non pas du contenu? 


 */

public class ClassePRJmain  {
	
	
	public static void main(String[] args) throws IOException {
			
		//On peut pas les mettre parametre car main doit être static
		int nbgenera = 500 ;
		int taillepop = 200 ;
		int taillegeno = 5 ;	
		double seuilmut = 1 ;
		double seuilKO  = 0 ;
		int nbmutsurgeno = 1 ; //définit le nombre max de mutations sur un individu en une seule fois
		int PAS = 1 ;
		
		////////SCANNER/////////////////
//		Scanner scanner = new Scanner(System.in);
//		scanner.useLocale(Locale.US);	//pr utiliser le point en guise de virgule
//		System.out.println("Entrez nombre de generation:  ");
//		
//		nbgenera = scanner.nextInt();
//		System.out.println("Entrez taille de population:  ");
//		taillepop = scanner.nextInt();
//		System.out.println("Entrez la taille du genotype:  ");
//		taillegeno = scanner.nextInt();
//		System.out.println("Entrez le seuil de mutation (double) (1.0 aucune mutation, 0.0 systématique):  ");
//		seuilmut = scanner.nextDouble();
//		System.out.println("Entrez le seuil de KO (double) (1.0 aucun KO, 0.0 systématique):  ");
//		seuilKO  =scanner.nextDouble();
//		System.out.println("Entrez le nombre max de mutation simultanees sur un meme genotype(0 aucune mutation effectuées):  ");
//		nbmutsurgeno = scanner.nextInt(); //définit le nombre max de mutations sur un individu en une seule fois
//		System.out.println("Entrez le pas de mesure(mesure effectuees tte les n generations, 0 = aucune mesure):  ");
//		PAS = scanner.nextInt(); 
//		scanner.close();
//		System.out.print("Calcul... ");
		
		//Matrix donne = new Matrix(nbgenera, 5); //obsolete
		FileWriter csv = new FileWriter("donne.csv"); //utiliser bufferd writter c'est sans doute mieux
		//Permet de nomer les colones d'un tableur, commenter pr désactiver. faire attention à ne pas cocher la case espace comme séparateur
		csv.append("n°de generation; % de viabilite individu; vitesse moy viabili ; nb individu mute ; nb gene uniques ; nb lignes uniques; KO0 prvia ; KO0 moysp ;  KO1 prvia ; KO1 moysp ; KO2 prvia ; KO2 moysp ; KO3 prvia ; KO3 moysp ;  KO4 prvia ; KO4 moysp ; KO5 prvia ; KO5 moysp ; KOTO prvia ; KOTO moysp\n ");
		
		Generation genera1 = new Generation(taillepop, taillegeno) ;
		Generation genera2 = new Generation(taillepop, taillegeno) ;
		Generation KO =  new Generation(taillepop, taillegeno) ;
		Generation KO1 = new Generation(taillepop, taillegeno) ;
		Generation KO2 = new Generation(taillepop, taillegeno) ;
		Generation KO3 = new Generation(taillepop, taillegeno) ;
		Generation KO4 = new Generation(taillepop, taillegeno) ;
		Generation KO5 = new Generation(taillepop, taillegeno) ;
		Generation KOTOTO = new Generation(taillepop, taillegeno) ;
		
		genera1.randomGeneration();		
	
		long startTime = System.currentTimeMillis();
		int iPAS = 0 ;
		int i = 0 ;
		while (i < nbgenera) {
			
			//OPERATION PRIMAIRE
			genera2.naissances(genera1, seuilmut, nbmutsurgeno ); 
			
			if (i == iPAS) {	//incrementer iPAS avec le PAS
			
			////OPERATION SECONDAIRE
			
			KO.copy(genera2); 
			KO1.copy(genera2); 
			KO2.copy(genera2); 
			KO3.copy(genera2); 
			KO4.copy(genera2); 
			KO5.copy(genera2); 
			KOTOTO.copy(genera2); 
			
			KO.generaKO(seuilKO, 0, 0, 1);	//attention au outofbound pour coordKO. =>peut etre une exeption. ko aléatoire normal
			KO1.generaKO(seuilKO, 1, 1, 0); //KO par coordonnées, un seul possible
			KO2.generaKO(seuilKO, 2, 0, 1); //KO sur les premières plus grande lignes, nbko modifiable
			KO3.generaKO(seuilKO, 3, 0, 1); //KO sur les plus faible lignes
			KO4.generaKO(seuilKO, 4, 0, 1); //KO sur les plus grandes colones
			KO5.generaKO(seuilKO, 5, 0, 1); //KO sur les plus faibles colones
			KOTOTO.generaKOtotal(seuilKO);
			
			////Stockage de données:
//			donne.set(i, 0, genera2.getPourcevia());	//obsolete
//			donne.set(i, 1, genera2.getMoySpdvia());
//			donne.set(i, 2, genera2.getNbMUT());
			
			
			String prvia = Double.toString(genera2.getPourcevia());
			String moysp = Double.toString(genera2.getMoySpdvia());
			String nbmut = Double.toString(genera2.getNbMUT());
			String KOprvia = Double.toString(KO.getPourcevia());
			String KOmoysp = Double.toString(KO.getMoySpdvia());
			String KO1prvia = Double.toString(KO1.getPourcevia());
			String KO1moysp = Double.toString(KO1.getMoySpdvia());
			String KO2prvia = Double.toString(KO2.getPourcevia());	
			String KO2moysp = Double.toString(KO2.getMoySpdvia());
			String KO3prvia = Double.toString(KO3.getPourcevia());
			String KO3moysp = Double.toString(KO3.getMoySpdvia());
			String KO4prvia = Double.toString(KO4.getPourcevia());
			String KO4moysp = Double.toString(KO4.getMoySpdvia()); //pareil
			String KO5prvia = Double.toString(KO5.getPourcevia());
			String KO5moysp = Double.toString(KO5.getMoySpdvia()); //pareil
			String KOTOprvia = Double.toString(KOTOTO.getPourcevia());
			String KOTOmoysp = Double.toString(KOTOTO.getMoySpdvia()); //on ne peut pas avoir la vitesse car j'ai du faire des copies
			
			csv.append(""+i+";");
			csv.append(""+prvia+";");
			csv.append(""+moysp+";");
			csv.append(""+nbmut+";");
			csv.append(""+genera2.comptGeneUniGenera()+";");	
			csv.append(""+genera2.comptLigneUniGenera()+";");	
			csv.append(""+KOprvia+";");
			csv.append(""+KOmoysp+";");		//normalement c'est bon
			csv.append(""+KO1prvia+";");
			csv.append(""+KO1moysp+";");
			csv.append(""+KO2prvia+";");
			csv.append(""+KO2moysp+";");
			csv.append(""+KO3prvia+";");
			csv.append(""+KO3moysp+";");
			csv.append(""+KO4prvia+";");
			csv.append(""+KO4moysp+";");
			csv.append(""+KO5prvia+";");
			csv.append(""+KO5moysp+";");
			csv.append(""+KOTOprvia+";");
			csv.append(""+KOTOmoysp+";");
			
			////IMPRESSION

//			System.out.print("NUM GENERA: " + i);
//			
//			KO.printPourcevia("KO") ;		
//			genera2.printPourcevia("genera2                         : ") ;
//			genera2.printMoyspdvia();		
//			System.out.println(genera2.getNbMUT());		
			
//			System.out.println("\t mem GENERA ci dessous: " + genera1 + "//"); //ESPECE DE CONNARD AVEC TES COPI2S COLLER!!!!!!!!!!!!!!!
//			genera1.printGenerationIndividu("genera1");
//			
//			System.out.println("\t mem GENERA ci dessous: " + genera2 + "//");
//			genera2.printGenerationIndividu("genera2");
//			
//			System.out.println("\t mem GENE: " + KO + "//");			
//			KO.printGenerationIndividu("KO");
			
//			System.out.println("\t mem GENE: " + KOTOTO + "//");			
//			KOTOTO.printGenerationIndividu("KO");
			
			
			////PREPARATION TOUR PROCHAIN
							
								//TODO essayer de faire une mesure ac inceste
			csv.write("\n");
			iPAS = iPAS+PAS ;
			}
			genera1.copy(genera2);
			i++;
			double pourcprog = (double)i / (double)nbgenera ;
			
			System.out.print("\r");
			System.out.print("Progression: " + pourcprog*100 + "% ");
			
		}
		//TODO il eut peut etre été judicieu de faire individu extends matrix.
		//TODO Se méfier d'open office qui parfoi fait des bugs
		//TODO faire une interface graphique 
		//TODO mettre les trucs en private
		//TODO faire un super main, avec les variables qui changent dans une boucle
		//TODO faire une condition if pour définir un seuil de stabilité, à partir duquel on peut faire des mutation etc..
		//TODO on ne compte que les mutés qui sont viables, pas ceux qui sont morts.. on s'en fout qqpart car on les voit dans pourvia, mm s'ils sont melangés avec les autres.
		//TODO faire un extends thread? 1/8 c'est quand même dommage...
		//donne.print(6, 5);	//obsolete
		csv.close();
		long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println("\nTemps de calcul: " + elapsedTime) ;
	    
	    System.out.println("Un fichier donne.csv vient d'etre cree dans le repertoire ou est situe le .jar." ) ;
	}
	
	
}
