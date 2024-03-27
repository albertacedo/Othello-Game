package domini;
import java.util.*;


/**
 * Classe que formara l'estructura de dades per ser introduides en els ranquings JSON
 * @author Albert Acedo Casellas
 * @version 1.0
 */

public class Ranquing {
    /**
     * El nom del jugador
     **/
    private final String nom;
    /**
     * El resultat de la partida
     **/
    private final int puntuacio;


    /**
     * Constructora per defecte dels elements de Ranquing
     *
     * @param n     El nom del jugador
     * @param punts La puntuació de la partida
     */
    public Ranquing(String n, int punts) {
        nom = n;
        puntuacio = punts;
    }

    /**
     * Funció per obtenir el nom de l'objecte ranquing
     *
     * @return Retorna el nom de l'objecte Ranquing
     */
    public String getNom() {
        return this.nom;
    }


    /**
     * Funció per obtenir els punts de l'objecte ranquing
     *
     * @return Retorna la puntuació de l'objecte Ranquing
     */
    public int getPunts() {
        return this.puntuacio;
    }




    public static void main(String[] args) {

    }

}
