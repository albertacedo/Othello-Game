package domini;

import Persistencia.Gestor_partida;
import Persistencia.Gestor_ranquing;
import Persistencia.Gestor_tauler;
import Persistencia.Gestor_usuari;
import Presentacio.Controlador_presentacio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * Gestió del domini que realitzales operacions necessaries per que el
 * programa funcioni. Es tracta d'una classe Singleton que garanteix que
 * tan sols hi hagi un objecte del seu tipus i proporciona un únic punt
 * d'accés a ell per a qualsevol altre codi
 * @author Marc Fernandez Palau - Albert Acedo Casellas
 * @version 1.0
 */
public class Controlador_domini {

    /** Atributs **/

    /**Instancia de la classe     */
    private static Controlador_domini Controlador;
    /**Instancia de la classe     */
    private static Controlador_presentacio Controlador_pres;
    /**Gestor d'usuaris (persistencia)     */
    private static final Gestor_usuari gestor_usuaris = Gestor_usuari.instancia();
    /**Gestor de taulers (persistencia)     */
    private static final Gestor_tauler gestor_taulers = Gestor_tauler.instancia();
    /**Gestor de partides (persistencia)     */
    private static final Gestor_partida gestor_partidas = Gestor_partida.instancia();
    /**Gestor de ranking (persistencia)     */
    private static final Gestor_ranquing gestor_ranquings = Gestor_ranquing.instancia();
    /**Usuari loguejat     */
    private Persona usuari_actual;
    /**Partida actual     */
    private static Partida partida_en_curs;


    /**
     *Constructora per defecte, instancia el controlador
     */
    public static Controlador_domini Instancia_controlador(){
        if (Controlador == null){
            Controlador = new Controlador_domini();
        }
        return Controlador;
    }

    /**
     *Inicia una partida nova entre 2 ias
     * @param id_partida Identifica la partida amb un id unic
     * @param dificultatIAnegre Dificultat de la IA negre
     * @param dificultatIAblanca Dificultat de la IA blanca
     * @return Retorna <code>True</code> si la partida s'ha pogut jugar sense
     * problemes, <code>False</code> si ja existia una partida en curs
     */
    public boolean partida_ias(String id_partida, String dificultatIAnegre, String dificultatIAblanca){
       if(partida_en_curs == null) {
            partida_en_curs = new Partida();
            partida_en_curs.jugar_ias(id_partida, dificultatIAnegre, dificultatIAblanca);

            //Quan acaba la partida esborrem la partida de partida_en_curs
            partida_en_curs = null;
            return true;
        }
        return false;
    }
    public boolean partida_nova(int tipus_partida, String dificultatIAnegre, String dificultatIAblanca){
        if(partida_en_curs == null) {
            partida_en_curs = new Partida(tipus_partida, dificultatIAnegre, dificultatIAblanca);
            /*int [] punts;
            while(!partida_en_curs.partida_acabada()) {
               //partida_en_curs.aplicar_moviment(id_partida);

                punts = partida_en_curs.get_puntuacio2();
            }
            //Quan acaba la partida esborrem la partida de partida_en_curs
            partida_en_curs = null;*/
            return true;
        }
        return false;
    }

    /**
     *Inicia una partida nova entre 1 jugador (per consola) i una ia
     * @param id_partida Identifica la partida amb un id unic
     * @param dificultat Dificultat de la IA contra la que juguem
     * @return Retorna <code>True</code> si la partida s'ha pogut jugar sense
     * problemes, <code>False</code> si ja existia una partida en curs
     */
    public boolean partida_ia_huma(String id_partida, String dificultat){
        if(partida_en_curs == null) {
            partida_en_curs = new Partida();
            partida_en_curs.jugar_ia_huma(id_partida, dificultat);
            //falta asociar cada jugador amb un color
            //Quan acaba la partida esborrem la partida de partida_en_curs
            partida_en_curs = null;
            return true;
        }
        return false;
    }

    /**
     *Inicia una partida nova entre 2 jugadors que interactuen amb
     * la interfície gràfica
     * @param id_partida Identifica la partida amb un id unic
     * @param dificultat Dificultat de la IA contra la que juguem
     * @return Retorna <code>True</code> si la partida s'ha pogut jugar sense
     * problemes, <code>False</code> si ja existia una partida en curs
     */
    public boolean partida_humans(String id_partida, String dificultat){
        if(partida_en_curs == null) {
            partida_en_curs = new Partida();
            partida_en_curs.jugar_ia_huma(id_partida, dificultat);
            //falta asociar cada jugador amb un color
            //Quan acaba la partida esborrem la partida de partida_en_curs
            partida_en_curs = null;
            return true;
        }
        return false;
    }

    public boolean moure_fitxa(int i, int j, String color){
        if (partida_en_curs != null && !partida_en_curs.partida_acabada()) {
            if(partida_en_curs.getfinegre() && partida_en_curs.getfiblanc()) partida_en_curs.acabar_partida();
            else if (partida_en_curs.getTipus() == 1)
                return partida_en_curs.fer_moviment(i, j, color);

            else if (partida_en_curs.getTipus() == 2) {
                boolean s = partida_en_curs.fer_moviment(i, j, color);
                actualitzar_tauler();
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                partida_en_curs.fer_movimentIA();
                return true;
            } else if (partida_en_curs.getTipus() == 3) {

                return true;
            }
        }
        return false;
    }

    public static List<Casella> on_puc_tirar(String color){
        List<Casella> llista;
        llista = partida_en_curs.get_taulell().caselles_disponibles(color);
        return llista;
    }

    /**
     * Afegeix un usuari a la base de dades
     * @param username nom d'usuari
     * @param password contrasenya de l'usuari
     * @return Retorna <code>True</code> si s'ha pogut afegir sense problemes
     * al jugador, <code>False</code> si ja existia un jugador
     */
    public static boolean crear_usuari(String username, String password){
        Map usuaris = gestor_usuaris.llegir_bd_usuaris();
        if (!usuaris.containsKey(username)){
            //usuari_actual = new Jugador();
            gestor_usuaris.guardar_usuari(username, password);
            return true;
        }
        return false;
    }

/*
    public static boolean esborrar_usuari(){
        if(usuari_actual != null){
            String nom_jugador = usuari_actual.();
            return gestor_usuaris.esborrar_jugador(nom_jugador);
        }
        return false;
    }
*/
    /**
     * logueja un usuari i el posaa com a usuari_actual
     * @param username nom d'usuari
     * @param password contrasenya de l'usuari
     * @return Retorna <code>True</code> si s'ha pogut logejar sense problemes
     * <code>False</code> si les dades no son correctes
     */
    public boolean log_usuari(String username, String password){
        Map usuaris = gestor_usuaris.llegir_bd_usuaris();
        if (Objects.equals(usuaris.get(username), password)){
            //this.usuari_actual = new Jugador();
            return true;
        }
        return false;
   }

   public void ficolor(boolean b, String color){
        if (color.equals("negre"))
            partida_en_curs.fi_negre(true);
        else if (color.equals("blanc"))
            partida_en_curs.fi_blanc(true);
   }

    /**
     * deslogueja el usuari actual
     */
   public static boolean unlog_usuari(){
        /*
        if(usuari_actual != null){
            //guardar la info de usuari
            usuari_actual = null;
            return true;
        }

         */
        return false;
   }

   private void actualitzar_tauler(){
       Controlador_presentacio.actualitzar_tauler();
   }

   public int[] getpuntuacio(){
       return partida_en_curs.get_puntuacio(partida_en_curs.get_taulell());
   }

   public String getTorn(){
       return partida_en_curs.getTorn();
   }

    /**
     * Funció per canviar la contrasenya del usuari_actual, comprova si el
     * usuari existeix i si es ell, preguntant per el password antic
     * @param antic_password password que usava fins ara
     * @param nou_password password que utilitzarà a partir d'ara
     */
    public static void canviar_contrasenya(String antic_password, String nou_password){
        /*
        Map usuaris = gestor_usuaris.llegir_bd_usuaris();
        if (usuaris.get(usuari_actual.getnom()) == antic_password ){
            usuaris.put(usuari_actual.get_nom(), nou_password)
            return true;
        }
        return false;
        */
    }


    /**
     * Consulta les 15 posicions del ranquing segons la dificultat desitjada
     * @param dif Dificultat del ranquing que volem consultar
     * @return Retornem un vector d'objectes Ranquing (string, int) que contenen les posicions amb el nom i puntuació
     */
    public static Ranquing[] consultar_ranquing(String dif){
        Ranquing[] R = gestor_ranquings.llegir_bd_ranquing(dif);

        return R;
    }


    /**
     * Funció que afageix al fitxer ranquing desitjat (segons la dificultat) el jugador i la seva puntuació
     * @param P Objecte Persona que volem afegir al ranquing
     * @param punts Correspon al total de la puntuacio que volem guardar
     * @param dif Dificultat del ranquing al que afegim al jugador
     * @return Retornem un boleà que ens indica si s'ha modificat el ranquing o no
     */
    public boolean afegir_ranquing(Persona P, int punts, String dif){
        Ranquing[] R = gestor_ranquings.llegir_bd_ranquing(dif);
        return gestor_ranquings.afegir_ranquing (P, punts, dif);

    }

    public boolean getestatpartida(){
        return partida_en_curs.partida_acabada();
    }

    public static Partida getPartida_en_curs() {
        return partida_en_curs;
    }

    public void guardar_partida(String nom, String color, boolean esTornJugador, boolean esIA, String contrincant, Tauler tauler) {
        gestor_partidas.llegir_bd_partides();
        gestor_partidas.guardar_partida(nom, color, esTornJugador, esIA, contrincant, tauler);

    }
}
