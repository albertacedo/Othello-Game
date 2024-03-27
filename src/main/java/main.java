import domini.Controlador_domini;
import Presentacio.Controlador_presentacio;

import java.util.Scanner;
import static domini.Controlador_domini.Instancia_controlador;

/**
 * Classe que conte la funcio main per ininciar la execucio del programa
 * @author Marc Fernandez Palau
 * @version 1.0
 */
public class main {

    /**
     * Fil principal d'execucio, el joc s'inicia aqui on es declara
     * el controlador de persistencia que s'ecarrega d'inicialitzar la resta
     * d'elements incrementalment a mida que avança l'execució
     */
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater (
                new Runnable() {
                    public void run() {
                        Controlador_presentacio ctrlPresentacio = new Controlador_presentacio();
                        ctrlPresentacio.inicialitzar();
                    }
                }
        );
    }


    /**
     * Funcio main per provar el funcionament del joc i de les funcionalitats
     * implementades.
     * Cada cop que escrius una comanda entres en un dels casos d'ús
     * El funcionament de com provar les funcionalitats està explicat al readme.txt
     */
    /*
    public static void main(String[] args) {
        Controlador_domini Controlador = Instancia_controlador();
        boolean joc_actiu = true;
        System.out.println("WELCOME TO OTHELLO V1.0");
        while (joc_actiu) {
            System.out.println("Escriu una comanda:");
            Scanner myObj = new Scanner(System.in);
            String comanda = myObj.nextLine();
            switch (comanda) {
                case "crear_usuari": {
                    System.out.println("Inserta username:");
                    String username = myObj.nextLine();
                    System.out.println("Inserta contrasenya:");
                    String contrasenya = myObj.nextLine();
                    Controlador_domini.crear_usuari(username, contrasenya);
                    break;
                }
                case "login": {
                    System.out.println("Inserta username:");
                    String username = myObj.nextLine();
                    System.out.println("Inserta contrasenya:");
                    String contrasenya = myObj.nextLine();
                    System.out.println(Controlador.log_usuari(username, contrasenya));
                    break;
                }
                case "ia_vs_ia":{
                    System.out.println("Dificultat de la IA negre?");
                    System.out.println("Valors posibles a introduir: easy normal hard impossible");
                    String dificultatIAnegre = myObj.nextLine();
                    System.out.println("Dificultat de la IA blanca?");
                    System.out.println("Valors posibles a introduir: easy normal hard impossible");
                    String dificultatIAblanca = myObj.nextLine();
                    if (dificultatIAnegre.equals(dificultatIAblanca)){
                        System.out.println("Las IAs no poden tenir la mateixa dificultat");
                        break;
                    }
                    Controlador.partida_ias("id1", dificultatIAnegre, dificultatIAblanca);
                    break;
                }
                case "ia_vs_persona":{
                    System.out.println("Dificultat de la IA?");
                    System.out.println("Valors posibles a introduir: easy normal hard impossible");
                    String dificultat = myObj.nextLine();
                    Controlador.partida_ia_huma("id2", dificultat);
                    break;
                }
                case "sortir": {
                    System.out.println("Estas segur que vols sortir? (y/n)");
                    String sortir = myObj.nextLine();
                    if ("y".equals(sortir)){
                        //cal guardar les dades si hi ha una partida començada i
                        //dades del usuari actiu
                        joc_actiu = false;
                    }
                }
            }
        }
        System.out.println("Gracies per jugar OTHELLO, ciao!");
    }
*/
}
