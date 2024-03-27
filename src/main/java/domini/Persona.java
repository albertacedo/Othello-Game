package domini;

/**
 * Aquesta classe representa objectes persona, que son els jugadors no controlats per una IA
 * @author Adrian Braojos Parras
 * @version 1.0
 */
public class Persona extends Jugador {

    /**Atribut que indica si la persona té password o no */
    private Boolean registrat = false;
    /**id del jugador */
    private String id;
    /**password del jugador (encara no l'usem)*/
    private String password;

    /**
     * Constructora per defecte, crea un jugador persona i li assigna un color
     * @param color color de les fitxes del jugador
     */
    public Persona(String color) {
        super(color);
        if (!(color.equals("negre") || color.equals("blanc"))) {
            System.out.println("Error: el color ha de ser blanc o negre");
        }
    }

    /**
     * Consultora, retorna true si la persona té usuari i password (és a dir, si està registrada)
     * @return Retorna true si la persona està registrada (si té id i password). Altrament retorna false
     */
    public Boolean estaRegistrat() {
        return this.registrat;
    }

    /**
     * Consultora, retorna l'id de la persona
     * @return Retorna l'id de la persona si està registrada
     */
    public String getNom() {
        if (!this.registrat) {
            System.out.println("Error: l'usuari no està registrat al sistema");
            return "";
        }
        return this.id;
    }

    /**
     * Consultora, retorna el password de la persona
     * @return Retorna el password de la persona si està registrada
     */
    public String getPassword() {
        if (!this.registrat) {
            System.out.println("Error: l'usuari no està registrat al sistema");
            return "";
        }
        return this.password;
    }

    /**
     * Canvia l'id de la persona
     * @param id nom que tindrà la persona
     */
    public void canviarNom(String id) {
        if (!this.registrat) {
            System.out.println("Error: l'usuari no està registrat al sistema");
            return;
        }
        this.id = id;
    }

    /**
     * Canvia el password de la persona
     * @param password contrasenya que tindrà la persona
     */
    public void canviarPassword(String password) {
        if (!this.registrat) {
            System.out.println("Error: l'usuari no està registrat al sistema");
            return;
        }
        this.password = password;
    }

    /**
     * Assigna un id i un password a la persona
     * @param id nom que tindrà la persona
     * @param password contrasenya que tindrà la persona
     */
    public void registrarPersona(String id, String password) {
        if (this.registrat) {
            System.out.println("Error: l'usuari ja està registrat");
            return;
        }
        this.registrat = true;
        this.id = id;
        this.password = password;
    }
}
