import java.util.Random ;
import java.util.Arrays ;
import java.lang.Math ;

import Jama.Matrix;

import java.util.ArrayList;
import java.util.List ;
import java.util.LinkedList ;
import java.util.ListIterator ;


public class Generation  {	//On va voir si ca marche.. c'est pas l'inverse?? Individu extends generation?

    int taillepop ;
    int taillegeno  ;
    int nbmut ;
    double pourcevia ;
    private ArrayList<Individu> generation ;
    double moyspdvia ;

    public static void main(String[] args) {
    }

/////////////////////////CONSTRUCTEURS////////////////////////////////////	


    public Generation(Generation generaAcopier) {	//Constructeur de copie
        for (Individu indi : generaAcopier.generation) {
            Individu Nindi = new Individu(indi, taillegeno);
            this.generation.add(Nindi);
        }
    }


    public Generation(int taillepop , int taillegeno) {
        this.taillepop = taillepop ;
        this.taillegeno = taillegeno ;
        this.generation = new ArrayList<Individu>(taillepop) ;
    }

///////////////////////////GENERATION ALEATOIRE VIABLE////////////////////	

    public void randomGeneration() {
        int i = 0 ;
        int tourvide = 0 ; //on s'en fout un peu pour les aléatoires, mais on ne sait jamais.
        while ( i < taillepop) {
            Individu idAtester = new Individu(taillegeno);
            idAtester.matriceRandom();
            if (idAtester.testVia() == true) {
                this.generation.add(idAtester) ;
                i++;
            }
            tourvide++;
        }
    }

    ///////////////////////////GENERATION DE FILS VIABLES/////////////////////
    public void naissances(Generation genera1, double seuilmut, int nbmutsurgeno) {
        Individu fils = new Individu(taillegeno) ; //instanciation fils qui ne marche pas... pk suis obligé de faire comme ca???
        Individu mere = new Individu(taillegeno) ;
        Individu pere = new Individu(taillegeno) ;

        this.nbmut = 0 ;
        double tourvide = 0 ;
        int i = 0 ;
        while ( i < taillepop) {

            //Choisit deux parents différents aléatoirement
            Random ran = new Random();
            int a = 0 ;
            int b = 0 ;

            while ( a == b) {
                a = ran.nextInt(taillepop) ;
                b = ran.nextInt(taillepop) ;
                mere = genera1.getGenerationList().get(a) ;
                pere = genera1.getGenerationList().get(b) ;
            }

            fils.accouplement(pere, mere);

            Individu fils2 = new Individu(fils, taillegeno) ;
            int cptMUT = fils2.mutation(seuilmut, nbmutsurgeno); 		//renvoyer +1 mutation si viables

            //On teste la viabilité
            if (fils2.testVia() ==  true) {		//ds l'original c'était fils, va savoir si ca change qq chose...
                this.nbmut += cptMUT ;

                if ( this.generation.size() < taillepop ) {  //empeche le bug qd génération est vide au début
                    this.generation.add( fils2);
                }
                else {
                    this.generation.set(i, fils2);
                }
                i++ ;
            }
            else
                tourvide++ ;
        }
        double idouble = i ;
        this.pourcevia = idouble / (idouble + tourvide) ; //ben j'aurais bien galéré juste pour ca...
    }


////////////////////GENERATION QUI SE PREND DES KO////////

    public void generaKO(double seuilKO, int KOtype, int coordKO, int nbKO) {
        double viable = 0 ;
        double pasviable = 0 ;

        for (Individu KOKO : this.generation) {
            if ( KOtype == 0 ) KOKO.KO(seuilKO, nbKO);
            if ( KOtype == 1 ) KOKO.KO1(seuilKO, coordKO);
            if ( KOtype == 2 ) KOKO.KO2(seuilKO, nbKO) ;
            if ( KOtype == 3 ) KOKO.KO3(seuilKO, nbKO) ;
            if ( KOtype == 4 ) KOKO.KO4(seuilKO, nbKO) ;
            if ( KOtype == 5 ) KOKO.KO5(seuilKO, nbKO) ;
            if (KOKO.testVia() == true)
                viable++ ;
            else
                pasviable++ ;
        }
        this.pourcevia = viable / (viable + pasviable) ;
    }

////////////////////////GENERATION KO TOTAL////////////////////

    public void generaKOtotal(double seuilKO) {
        double viable = 0 ;
        double pasviable = 0 ;
        boolean INDIviable = true ;
        for (Individu KOKO : this.generation) { //parcourt les indi de la genera
            Individu KOKO2 = new Individu(KOKO, taillegeno) ;

            for (int i = 0 ; i < taillegeno ; i++) {
                Individu KOKO3 = new Individu(KOKO2, taillegeno) ;
                KOKO3.KO1(seuilKO, i);
                //KOKO3.printGenotype();
                if ( KOKO3.testVia() == false ) {
                    INDIviable = false ;
                }
            }
            if (INDIviable == true)
                viable++ ;
            else
                pasviable++ ;
        }
        this.pourcevia = viable / (viable + pasviable) ;
    }

/////////////////Compteur lignes//////////////


    public int comptLigneUniGenera() {
        /**
         * Count, for the whole generation, how many unique matrix line there is, e.g if the same line is present 10
         * times among the individuals of the whole generation, it will be counted as one.
         **/
        ArrayList<Double> ligneuniques = new ArrayList<Double>() ;
        //genesuniques.add(0.0) ;	//initialiser ac un gene d'individu
        for (Individu KOKO : this.generation) {	//parcourt individu
            for (int i = 0 ; i < taillegeno ; i++) {	//parcourt les lignes
                double gene = KOKO.comptLigne(i) ; //value that identifies a line

                int j = 0 ;

                // TODO refaire, c'est moche, faut juste une collectio, et hop?
                boolean bool = true ;
                while ( j < ligneuniques.size() && bool == true) {	//parcourt liste de gènes
                    double geneuni = ligneuniques.get(j);
                    if  (Math.abs(gene - geneuni ) < 0.00000001 ) {	// if gene equals geneuni
                        bool = false ;	// le gene n'est pas unique, on arrete d'itérer, et on ne le rentre pas
                        //System.out.print(" faux ");
                    }
                    j++ ;
                }

                if ( bool == true) { //putain j'ai galéré tt ce temps avec un = au lieu de ==....
                    ligneuniques.add(gene) ;
                }


            }
        }
        return ligneuniques.size() ; //renvoi taillepop * geno +1..
    }

    //////////////////Compteur genes
    public int comptGeneUniGenera() {
        /**
         * Count, for the whole generation, how many unique matrix line and columns(gene), from the diagonal, are there.
         **/

        ArrayList<Double> genesuniques = new ArrayList<Double>() ;
        //genesuniques.add(0.0) ;	//initialiser ac un gene d'individu
        for (Individu KOKO : this.generation) {	//parcourt individu
            for (int i = 0 ; i < taillegeno ; i++) {	//parcourt les genes
                double gene = KOKO.comptGeneUni(i) ;

                int j = 0 ;
                boolean bool = true ;
                while ( j < genesuniques.size() && bool == true) {	//parcourt liste de gènes
                    double geneuni = genesuniques.get(j);
                    if  (Math.abs(gene - geneuni ) < 0.00000001 ) {	// ca marchait qd mm l'égalité des doubles
                        bool = false ;
                        //System.out.print(" faux ");
                    }
                    j++ ;
                }

                if ( bool == true) { //putain j'ai galéré tt ce temps avec un = au lieu de ==....
                    genesuniques.add(gene) ;
                }


            }
        }
        return genesuniques.size() ; //renvoi taillepop * geno +1..
    }


    ////////////////////ACCESSEURS/////////////////////////////////
    ////LES GETS////
    public double getPourcevia() {
        return this.pourcevia ;
    }
    /////////////////////
    public int getNbMUT() {
        return this.nbmut ;
    }
    /////////////////////
    public double getMoySpdvia() {
        double somspdvia = 0 ;
        for (Individu indi : this.generation) {
            somspdvia += indi.getSpdvia() ;
        }
        this.moyspdvia = somspdvia / taillepop ;
        return this.moyspdvia ;
    }
    /////////////////////
    public List<Individu> getGenerationList() {
        return this.generation ;
    }
    ////LES PRINTS///////
    public String printMoyspdvia() {
        System.out.println("moyspdvia: " + getMoySpdvia());
        return "";
    }
    /////////////////////
    public String printPourcevia(String nomgenera) {
        System.out.println("\n pourcevia de: " + nomgenera + " est de: " + this.pourcevia );
        return "" ;
    }
    /////////////////////
    public String printGenerationIndividu(String nomdelageneration) {
        System.out.println("\n ////////////////////////////////////////////////////////////////////////"+ nomdelageneration + "/////////////");
        //System.out.print(Generation);  //imprime l'adresse mem de l'objet generation, ou de la liste generation?
        for (Individu id : this.generation) {
            System.out.println("\n /////////////////UN INDIVIDU DE: " + nomdelageneration +  "/////////////");
            System.out.print(id); //addre mem individu
            id.printGenotype() ;
            System.out.println("\n");
            System.out.print("SPDVIA: " + id.spdvia);
        }
        return "" ;
    }
    ////////////////COPY/////////////////
    public void copy(Generation generaAcopier) {	//copieur, va t'il marcher??
        //this.generation = generaAcopier.generation ;
        int i = 0 ;
        for (Individu indi : generaAcopier.generation) {
            Individu Nindi = new Individu(indi, taillegeno);
            if ( this.generation.size() < taillepop ) {
                this.generation.add(Nindi);
            }
            else {
                this.generation.set(i, Nindi);
            }
            i++ ;
        }
    }
}
