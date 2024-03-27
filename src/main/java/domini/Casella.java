package domini;

/**
 * Aquesta classe representa objectes casella que en conjunt formen part d'un taulell
 * @author Marc Fernandez Palau
 * @version 1.0
 */
public class Casella {

    /** Atributs **/

    /**Enumeració possibles estas de la casella    */
    enum estat {
        buit,
        negre,
        blanc
    }
    /**Posicio de la casella (fila)     */
    private final int pos_x;
    /**Posicio de la casella (columna)     */
    private final int pos_y;
    /**Estat de la casella     */
    private estat estat;


    /**
     * Constructora per defecte
     * @param posx en quina fila es troba la casella
     * @param posy en quina columna es troba la casell
     */
    public Casella(int posx, int posy) {
        this.pos_x = posx;
        this.pos_y = posy;
        this.estat = estat.buit;
    }

    /**
     * Retorna la coordenada X de la casella
     * @return Retorna la coordena en format string
     */
    public int getPos_x(){
        return this.pos_x;
    }

    /**
     * Retorna la coordenada Y de la casella
     * @return Retorna la coordena en format string
     */
    public int getPos_y(){
        return this.pos_y;
    }

    /**
     * Retorna el estat de la casella
     * @return Retorna el estat de la casella en format string
     */
    public String getEstat() {
        return this.estat.name();
    }

    /**
     * Canvia l'estat de la casella
     * @param estat_nou Nou olor de la casella
     */
    public void canvi_estat(String estat_nou){
        this.estat = estat.valueOf(estat_nou);
    }

}
