package domini;

import java.util.*;
import static java.lang.Integer.parseInt;

/**
 * Aquesta classe representa objectes partida i controla la execució d'aquestes
 * @author Marc Fernandez Palau
 * @version 1.0
 */
public class Partida {

    /** Atributos */

    /**marcador     */
    private int[] puntuacio;
    /**Taulell de joc     */
    private Tauler t;
    /** Inici de la partida     */
    private final Date temps_inici;
    /** Final de la partida     */
    private Date temps_fi;
    /** Torn de joc     */
    private String torn;
    /** Identificador de la partida     */
    private String nom;
    private Jugador jugador1, jugador2;
    private int tipus;
    private IA Ia_blanc;
    private IA IA_negre;
    private boolean finegre = false;
    private boolean fiblanc = false;
    private int counter_moviments;

    /**
     * Constructora per defecte, crea el taulell de joc inicial
     * amb la permutació de fitxes correctament marca el temps d'inici
     * i inicialitza els marcadors, sempre comença el color negre
     */
    public Partida () {
        this.t = new Tauler();
        this.temps_inici = (Calendar.getInstance()).getTime();
        this.torn = "negre";
    }
    /**
     * Constructora per defecte, crea el taulell de joc inicial
     * amb la permutació de fitxes correctament marca el temps d'inici
     * i inicialitza els marcadors, sempre comença el color negre
     */
    public Partida (int tipus_partida, String dificultat_negre, String dificultat_blanc) {
        this.tipus = tipus_partida;
        switch(tipus_partida) {
            case 1:
                this.jugador1 = new Jugador("negre");
                this.jugador2 = new Jugador("blanc");
                break;
            case 2:
                this.jugador1 = new Jugador("negre");
                Ia_blanc = new IA("blanc", dificultat_blanc);
                break;
            case 3:
                IA_negre = new IA("negre", dificultat_negre);
                Ia_blanc = new IA("blanc", dificultat_blanc);
                break;

        }
        this.t = new Tauler();
        this.temps_inici = (Calendar.getInstance()).getTime();
        this.counter_moviments = 0;
        this.puntuacio = new int[2];
        this.torn = "negre";
    }

    /**
     * Executa una partida ia vs ia
     * @param id_taulell identificador del taulell
     * @param dificultat_negre Dificultat de la IA negre
     * @param dificultat_banc Dificultat de la IA blanca
     */
    public void jugar_ias (String id_taulell, String dificultat_negre, String dificultat_banc) {
        IA IA_negre = new IA("negre", dificultat_negre);
        IA Ia_blanc = new IA("blanc", dificultat_banc);
        boolean fi_negre = false;
        boolean fi_blanc = false;
        imprimir_tauler(t);
        while(!partida_acabada()){
            puntuacio = new int[]{0,0};
            if(this.torn.equals("negre")){
                System.out.printf("torn negre" + dificultat_negre);
                Casella cn = IA_negre.millor_moviment(t);
                if(cn.getPos_x() == -1 && cn.getPos_y() == -1) fi_negre = true;
                else {
                    IA_negre.colocar_fitxa(t, cn.getPos_x(), cn.getPos_y());
                    fi_negre = false;
                }
                this.torn = "blanc";
            }
            else if(this.torn.equals("blanc")) {
                System.out.printf("torn blanc" + dificultat_banc);
                Casella cb = Ia_blanc.millor_moviment(t);
                if (cb.getPos_x() == -1 && cb.getPos_y() == -1) fi_blanc = true;
                else {
                    System.out.println("beakpoint");
                    Ia_blanc.colocar_fitxa(t, cb.getPos_x(), cb.getPos_y());
                    fi_blanc = false;
                }
                this.torn = "negre";
            }
            imprimir_tauler(t);
            get_puntuacio(t);
            System.out.println("||Punts negre: " + puntuacio[0] + " || Punts blanc: " +
                                puntuacio[1] + " ||");
            System.out.println("escriu algo + enter per avançar 1 torn");
            Scanner myObj = new Scanner(System.in);
            String comanda = myObj.nextLine();
            if(fi_negre && fi_blanc) acabar_partida();
        }
    }

    /**
     * Executa una partida ia vs humà
     * @param id_taulell identificador del taulell
     * @param dificultat Dificultat IA contra la que juguem
     */
    public void jugar_ia_huma (String id_taulell, String dificultat) {
        IA Ia_blanc = new IA("blanc",dificultat);
        Persona huma = new Persona("negre");
        boolean fi_negre = false;
        boolean fi_blanc = false;
        imprimir_tauler(t);
        while(!partida_acabada()) {
            puntuacio = new int[]{0,0};
            if(this.torn.equals("negre")){
                List<Casella> l = t.caselles_disponibles("negre");
                System.out.println("Caselles on pots tirar (x y):");
                if(l.isEmpty()){
                    fi_negre = true;
                    System.out.println("No pots moure :(");
                }
                else {
                    for (Casella c : l) {
                        System.out.println(c.getPos_x() + " " + c.getPos_y());
                    }
                    System.out.println("escriu coordenada x + enter");
                    Scanner myObj = new Scanner(System.in);
                    String posx = myObj.nextLine();
                    System.out.println("escriu coordenada y + enter");
                    String posy = myObj.nextLine();
                    huma.colocar_fitxa(t, parseInt(posx), parseInt(posy));
                    System.out.println("Moviment aplicat correctament");
                    fi_negre = false;
                }
                this.torn = "blanc";
            }
            else if(this.torn.equals("blanc")) {
                System.out.printf("torn blanc%n");
                Casella cb = Ia_blanc.millor_moviment(t);
                if (cb.getPos_x() == -1 && cb.getPos_y() == -1) fi_blanc = true;
                else {
                    Ia_blanc.colocar_fitxa(t, cb.getPos_x(), cb.getPos_y());
                    fi_blanc = false;
                }
                this.torn = "negre";
            }
            imprimir_tauler(t);
            get_puntuacio(t);
            System.out.println("||Punts negre: " + puntuacio[0] + " || Punts blanc: " +
                                puntuacio[1] + " ||");
            System.out.println("escriu algo + enter per avançar 1 torn");
            Scanner myObj = new Scanner(System.in);
            String comanda = myObj.nextLine();
            if(fi_negre && fi_blanc) acabar_partida();
        }
        //if(puntuacio[0]>puntuacio[1]) afegir_ranquing(huma, puntuacio[0], dificultat);

    }

    public boolean fer_moviment (int i, int j, String color){
        if(this.counter_moviments < 61) {
            if (this.torn.equals("negre") && color.equals("negre")) {
                this.jugador1.colocar_fitxa(t, i, j);
                this.torn = "blanc";
                this.counter_moviments++;
                this.puntuacio[0] = 0;
                this.puntuacio[1] = 0;
                return true;
            } else if (this.torn.equals("blanc") && color.equals("blanc")) {
                this.jugador2.colocar_fitxa(t, i, j);
                this.torn = "negre";
                this.counter_moviments++;
                this.puntuacio[0] = 0;
                this.puntuacio[1] = 0;
                return true;
            }
        }
        return false;
    }

    public boolean fer_movimentIA (){
        if(this.torn.equals("negre") && this.IA_negre != null){
            Casella cn = IA_negre.millor_moviment(t);
            if(cn.getPos_x() == -1 && cn.getPos_y() == -1) ;//fi_negre = true;
            else {
                IA_negre.colocar_fitxa(t, cn.getPos_x(), cn.getPos_y());
                //fi_negre = false;
                this.puntuacio[0] = 0;
                this.puntuacio[1] = 0;
            }
            this.torn = "blanc";
            return true;
        }
        else if(this.torn.equals("blanc") && this.Ia_blanc != null) {
            Casella cn = Ia_blanc.millor_moviment(t);
            if(cn.getPos_x() == -1 && cn.getPos_y() == -1) ;//i_negre = true;
            else {
                Ia_blanc.colocar_fitxa(t, cn.getPos_x(), cn.getPos_y());
                //fi_negre = false;
                this.puntuacio[0] = 0;
                this.puntuacio[1] = 0;
            }
            this.torn = "negre";
            return true;
        }
        return false;
    }

    public void fi_negre(boolean b){
        this.finegre = b;
    }
    public void fi_blanc(boolean b){
        this.fiblanc = b;
    }

    public boolean getfinegre(){
        return finegre;
    }

    public boolean getfiblanc(){
        return fiblanc;
    }

    /**
     * Executa una partida ia vs ia
     * @return Retorna <code>True</code> si la partida esta acabada
     * <code>False</code> si no està acabada
     */
    public boolean partida_acabada(){
        return temps_fi != null;
    }

    /**
     * Marca el temps en que acaba la partida
     */
    public void acabar_partida(){
        temps_fi = (Calendar.getInstance()).getTime();
    }

    /**
     * Funció per imprimir graficament el taulell per consola
     * (for testing purposes)
     * @param t identificador del taulell
     */
    public static void imprimir_tauler(Tauler t) {
        System.out.printf("  0 1 2 3 4 5 6 7%n");
        for (int i = 0; i < 8; i++) {
            System.out.printf(Integer.toString(i) + " ");
            for (int j = 0; j < 8; j++) {
                char c = '·';
                if (t.getCaselles(i, j).getEstat().equals("blanc")) c = 'B';
                else if (t.getCaselles(i, j).getEstat().equals("negre")) c = 'n';
                System.out.print(c + " ");
            }
            System.out.printf(Integer.toString(i) + "%n");
        }
        System.out.printf("  0 1 2 3 4 5 6 7%n");
    }

    /**
     * Funció que recorre el taulell i retorna la puntuacio del color demanat
     * @param t identificador del taulell
     * @return Retorna la puntuacio del color demanat
     */
    public int[] get_puntuacio(Tauler t) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (t.getCaselles(i, j).getEstat().equals("negre")) this.puntuacio[0]++;
                else if (t.getCaselles(i, j).getEstat().equals("blanc")) this.puntuacio[1]++;
            }
        }
        return this.puntuacio;
    }
    /**
     * Funció que recorre el taulell i retorna la puntuacio del color demanat
     * @return Retorna la puntuacio del color demanat
     */
    public int[] get_puntuacio2() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.t.getCaselles(i, j).getEstat().equals("negre")) puntuacio[0]++;
                else if (this.t.getCaselles(i, j).getEstat().equals("blanc")) puntuacio[1]++;
            }
        }
        return new int[0];
    }

    /**
     * canvia el nom de la partida
     * @param nom_p nou nom de la partida
     */
    public void canviar_nom(String nom_p){
        this.nom = nom_p;
    }

    /* Encara no implementat o amb errors


    private boolean fer_moviment(int pos_x, int pos_y, String color){
        if (color.equals(this.torn)) {
            if(this.torn.equals("negre")) this.torn = "blanc";
            else this.torn = "negre";
            t.moures_fitxes(pos_x, pos_y, color);
            return true;
        }
        return false;
    }

*/
    public String getTorn(){
        return torn;
    }

    public Tauler get_taulell(){
        return t;
    }

    public int getTipus() {
        return tipus;
    }
}
