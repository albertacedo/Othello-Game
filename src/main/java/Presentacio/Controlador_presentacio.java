package Presentacio;

import domini.Controlador_domini;
import domini.Tauler;
import javax.swing.*;
import java.awt.*;

import static domini.Controlador_domini.Instancia_controlador;
import static domini.Controlador_domini.canviar_contrasenya;

public class Controlador_presentacio {

    /** Atributs per guardar partides**/
    String nomUsuari;
    String colorUsuari = "negre";
    boolean esTornUsuari = true;
    boolean esContrincantUnaIA = false;
    String nomContrincant;
    Tauler tauler;

    /** Instancia del controlador de domini */
    private static Controlador_domini ctrl_domini;
    /** Instancia de la classe     */
    private static Controlador_presentacio ctrl_pres = new Controlador_presentacio();
    /** Instancia del controlador de domini */
    private static vista_principal vista_principal;
    private static vista_joc vista_joc;
    private static vista_popups vista_popup;
    //falta inicialitzar vistes

    /**
     * Constructora per defecte, instancia el controlador
     */
    public Controlador_presentacio() {
        //Inicialitzem el controlador de domini
        //i la vista principal
        ctrl_domini = Instancia_controlador();
        vista_principal = new vista_principal(this);
        vista_joc = new vista_joc(this);
        vista_popup = new vista_popups();

    }

    /**
     * Funcio per fer visible la vista
     */
    public void inicialitzar() {
        vista_principal.visible();
    }


    protected boolean login(String username, String password){
        return ctrl_domini.log_usuari(username, password);
    }

    /**
     * Crea un nou usuari del joc
     * @param username nom d'usuari
     * @param password contrasenya de l'usuari
     * @return Retorna <code>True</code> si s'ha pogut afegir sense problemes
     * al jugador, <code>False</code> si ja existia un jugador
     */
    public boolean new_user(String username, String password){
        return ctrl_domini.crear_usuari(username, password);
    }

    /**
     * Funcio per fer visible la vista
     */
    public static boolean iniciar_partida(int tipus_partida, String dificultatnegre, String difcultatblanc) {
        return ctrl_domini.partida_nova(tipus_partida, dificultatnegre, difcultatblanc);
    }

    public static boolean moure_fitxa(int x, int y, String color){
        return ctrl_domini.moure_fitxa(x, y, color);
    }

    public void guardar_partida() {
        ctrl_domini.guardar_partida(nomUsuari, colorUsuari, esTornUsuari, esContrincantUnaIA, nomContrincant, tauler);
    }

    public static void actualitzar_tauler(){
        vista_joc.actualitzar_partida();
    }

    public static void ficolorp(boolean b, String color){
        ctrl_domini.ficolor(b, color);
    }

    public static boolean partida_acabada(){
        return ctrl_domini.getestatpartida();
    }

    public static int[] getpuntspartida(){
        return ctrl_domini.getpuntuacio();
    }

    public static String gettornpartida(){
        return ctrl_domini.getTorn();
    }

    private boolean logout(){
        return  ctrl_domini.unlog_usuari();
    }


}