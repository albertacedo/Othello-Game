package Persistencia;

import java.io.*;
import java.util.*;
import domini.Tauler;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Petita classe creada únicament per a poder guardar els atributs del JSON en un map
 * @author Adrian Braojos Parras
 * @version 1.0
 */
class AtributsPartida {

    /** Atributs **/
    String color1;              // guarda el color del jugador que bguarda la partida
    boolean esTornJugador;      // guarda si el següent torn és el del jugador que guarda la partida
    boolean esjugador2unaIA;    // guarda si el jugador adversari és una IA o no
    String username2;           // guarda l'username de l'adversari (o la seva dificultat, si és una IA)
    String tauler;              // guarda el tauler de la partida en format String

    /**
     * Creadora per defecte
     */
    public AtributsPartida() {
    }

    /**
     * Creadora que inicialitza els atributs amb els valors indicats
     * @param color1 color de les fitxes que utilitza el jugador1
     * @param esjugador2unaIA indica si el segon jugador és una IA o una persona
     * @param username2 nom del jugador 2 de la partida si es una perosna, dificultat de la IA si és una IA
     * @param tauler Tauler de la partida que es vol guardar
     */
    public AtributsPartida(String color1, boolean esTornJugador, boolean esjugador2unaIA,String username2, String tauler) {
        this.color1 = color1;
        this.esTornJugador = esTornJugador;
        this.esjugador2unaIA = esjugador2unaIA;
        this.username2 = username2;
        this.tauler = tauler;
    }
}





/**
 * Classe singleton per gestionar les partides jugades
 * @author Adrian Braojos Parras
 * @version 1.0
 */
public class Gestor_partida {
    /* Atributs **/

    /**Instancia de la clase     */
    private static Gestor_partida gestor_partidas;
    /**Map per guardar la base de dades de partidas quan fem canvis     */
    private static Map<String, List<AtributsPartida>> map = new HashMap<>();

    private static List<AtributsPartida> atributs = new ArrayList<AtributsPartida>();

    /**
     * Instancia la classe singleton
     */
    public static Gestor_partida instancia() {
        if (gestor_partidas == null) gestor_partidas = new Gestor_partida();
        return gestor_partidas;
    }

    /**
     * Funció que afegeig la partida nova a un map i parseja tots els elements del
     * map en objectes json i s'actualitza la base de dades
     * @param username1 nom del jugador que guarda la partida (ha de ser un dels jugadors de la partida)
     * @param color1 color de les fitxes que utilitza el jugador1
     * @param esTornJugador indica si el següent torn de la partida és del jugador que guarda la partida
     * @param esjugador2unaIA indica si el segon jugador és una IA o una persona
     * @param username2 nom del jugador 2 de la partida si es una perosna, dificultat de la IA si és una IA
     * @param t Tauler de la partida que es vol guardar
     */
    public void guardar_partida(String username1, String color1, boolean esTornJugador, boolean esjugador2unaIA, String username2, Tauler t) {

        if (!(color1.equals("blanc") || color1.equals("negre"))) {
            System.out.println("Error: el color ha de ser blanc o negre");
            return;
        }
        //System.out.println(esjugador2unaIA + username2);
        if ((esjugador2unaIA && !(username2.equals("easy") || username2.equals("normal") || username2.equals("hard") || username2.equals("impossible")))) {
            System.out.println("Error: La dificultat de la IA ha de ser easy, normal, hard o impossible");
            return;
        }

        ArrayList<String> tau = new ArrayList<>();
        //String tauler = "................................................................"; // 64 posicions

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                String estat = t.getEstat(i, j);
                switch (estat) {
                    case "blanc":
                        tau.add("B");
                        break;
                    case "negre":
                        tau.add("n");
                        break;
                    default:
                        tau.add(".");
                        break;
                }
            }
        }
        String tauler = String.join("", tau);
        AtributsPartida atr = new AtributsPartida(color1, esTornJugador, esjugador2unaIA, username2, tauler);

        if (map.containsKey(username1)) {
            List<AtributsPartida> atributs = map.get(username1);
            atributs.add(atr);
            map.replace(username1, atributs);
        }
        else {
            List<AtributsPartida> atributs = new ArrayList<>();
            atributs.add(atr);
            map.put(username1, atributs);
        }
        actualitzar_JSON();
    }

    /**
     * Funció que esborra la partida de la base de dades
     * @param username1 nom del jugador que va guardar la partida (ha de ser un dels jugadors de la partida)
     * @param atributs resta d'atributs de la partida que es vol esborrar
     */
    public void esborrar_partida(String username1, AtributsPartida atributs) {
        if (!(map.containsKey(username1))) {
            System.out.println("Error: aquesta partida no existeix");
            return;
        }
        List<AtributsPartida> llistatributs = map.get(username1);
        boolean trobada = false;
        for (int i = 0; i < llistatributs.size(); ++i) {
            if (llistatributs.get(i).color1.equals(atributs.color1) && llistatributs.get(i).esTornJugador == atributs.esTornJugador &&
                    llistatributs.get(i).esjugador2unaIA == atributs.esjugador2unaIA && llistatributs.get(i).username2.equals(atributs.username2)
                    && llistatributs.get(i).tauler.equals(atributs.tauler)) {
                trobada = true;
                llistatributs.remove(i);
                break;
            }
        }
        if (!trobada) {
            System.out.println("Error: aquesta partida no existeix");
            return;
        }
        actualitzar_JSON();
    }

    /**
     *LLegeix el fitxer partidas.json i els parseja a string
     * @return Retorna un map amb les partides guardades ordenades pel jugador que les ha guardat (clau)
     */
    public void llegir_bd_partides() {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("data/partidas.json")) {
            //Llegim el partidas.json
            Object obj = jsonParser.parse(reader);

            JSONArray partidesperjugador = (JSONArray) obj;
            //Itera sobre el array de usuaris
            partidesperjugador.forEach(parju -> parse_partidas_Object((JSONObject) parju));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return;
    }

    /**
     * Funció que agafa cada objecte json (cada jugador que ha guardat partides i les seves partides guardades) en un
     * map en format <String(jugador que guarda partides), List<AtributsPartida> (Llista dels atributs de les partides
     * guardades pel jugador)
     * @param parju Objecte json que contè les partides guardades per un usuari
     */
    private static void parse_partidas_Object(JSONObject parju) {
        // Agafem cada jugador que ha guardat partides a la llista
        String usuari = (String) parju.get("jugador");

        // Agafem l'array de partides creades per cada jugador
        JSONArray partides = (JSONArray) parju.get("partides guardades pel jugador");

        // Itera sobre el array de usuaris
        partides.forEach(par -> parse_partida_Object((JSONObject) par));

        map.put(usuari, atributs);
        atributs.clear();
        return;
    }

    /**
     * Funció que agafa cada objecte json (cada partida guardada pel mateix usuari) i guarda les seves dades en una
     * List en format <AtributsPartida> com a paràmetre
     * guardades pel jugador)
     * @param par Objecte json que contè les dades de la partida
     */
    private static void parse_partida_Object(JSONObject par) {
        AtributsPartida atr = new AtributsPartida();
        atr.color1 = (String) par.get("color jugat");
        atr.esTornJugador = par.get("torn seguent").equals("jugador");
        atr.esjugador2unaIA = par.get("tipus adversari").equals("IA");
        atr.username2 = (String) par.get("adversari");
        atr.tauler = (String) par.get("tauler");
        System.out.println(atr.color1 + ", " + atr.esTornJugador + ", " + atr.esjugador2unaIA + ", " + atr.username2 + ", " + atr.tauler);
        atributs.add(atr);
    }

    /**
     *Funció privada que actualitza l'arxiu partidas.json cada vegada que es guarda o s'esborra una partida
     */
    private static void actualitzar_JSON() {
        // using iterator
        Iterator<Map.Entry<String, List<AtributsPartida>>> itr = map.entrySet().iterator();
        JSONArray partides = new JSONArray();
        while (itr.hasNext()) {
            Map.Entry<String, List<AtributsPartida>> entrada = itr.next();
            List<AtributsPartida> atributs = entrada.getValue();
            JSONArray partides_guardades = new JSONArray();
            for (AtributsPartida element : atributs) {
                JSONObject detalls_partida = new JSONObject();
                detalls_partida.put("color jugat", element.color1);
                if (element.esTornJugador) detalls_partida.put("torn seguent", "jugador");
                else detalls_partida.put("torn seguent", "adversari");
                if (element.esjugador2unaIA) detalls_partida.put("tipus adversari", "IA");
                else detalls_partida.put("tipus adversari", "persona");
                detalls_partida.put("adversari", element.username2);
                detalls_partida.put("tauler", element.tauler);
                partides_guardades.add(detalls_partida);
            }
            JSONObject partides_per_jugador = new JSONObject();
            partides_per_jugador.put("jugador", entrada.getKey());
            partides_per_jugador.put("partides guardades pel jugador", partides_guardades);
            partides.add(partides_per_jugador);
        }
        //Write JSON file
        try (FileWriter file = new FileWriter("data/partidas.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(partides.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
