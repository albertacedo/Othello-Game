package Persistencia;

import domini.Casella;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Classe singleton per gestionar els taulers creats per els jugadors
 * @author Marc Fernandez Palau
 * @version 1.0
 */
public class Gestor_tauler {

    /** Atributs **/

    /**Instancia de la clase     */
    private static Gestor_tauler gestor_taulers;
    /**Map per guardar la base de dades de taulers quan fem canvis     */
    private static Map<String, Casella[][]> map = new HashMap<String, Casella[][]>();


    /**
     * Instancia la classe singleton
     */
    public static Gestor_tauler instancia(){
        if (gestor_taulers == null){
            gestor_taulers = new Gestor_tauler();
        }
        return gestor_taulers;
    }

    /**
     * Funció que afegeig el tauler nou a un map i parseja tots els elements del
     * map en objectes json i s'actualitza la base de dades
     * @param username id del tauler
     * @param caselles array de caselles que formen el tauler a guardar
     */
    public void guardar_mapa(String username, Casella[][] caselles){
        map.put(username, caselles);
        esciure_bd_tauler();
    }

    /**
     * Funció que esborra un tauler de la base de dades
     * @param id_tauler identificador del tauler
     */
    public void esborrar_tauler(String id_tauler) {
        map.remove(id_tauler);
        esciure_bd_tauler();
    }

    /**
     *LLegeix el fitxer taulers.json i els parseja a string
     * @return Retorna un map amb les parelles (key, value) id_tauler, caselles (array amb totes
     * les caselles del tauler)
     */
    public Map<String, Casella[][]> llegir_bd_tauler(){
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("data/taulers.json")) {
            //Llegim el taulers.json
            Object obj = jsonParser.parse(reader);

            JSONArray tauler_list = (JSONArray) obj;
            //Itera sobre el array de taulers
            tauler_list.forEach(usr -> parse_Object((JSONObject) usr));
            return map;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *Funció que agafa cada objecte json (cada tauler) i inserta les seves
     * dades en un map en format (string id_tauler, array caselles)
     * @param usr Objecte json que conte 1 tauler complet
     */
    private static void parse_Object(JSONObject usr){
        //Agafem cada tauler de la llista
        //JSONObject usuari_Object = (JSONObject) usr.get("user_id");

        //Agafem el username del usuari
        //String nom_usuari = (String) usuari_Object.get("username");
        //System.out.println(nom_usuari);
        //i el seu password
        //String password = (String) usuari_Object.get("password");
        //System.out.println(password);

        //inserim la info en un map
        //map.put(nom_usuari, password);
    }

    /**
     * Aquesta funcio s'encarrega de escriure la base de dades de taulers tant quan afegim
     * o esborrem un, recorrent el map amb un iterador i per cada element parseja el seu valor
     * i crea un objecte per cada tauler, que s'afegeix a una llista en format JSON.
     */
    private void esciure_bd_tauler(){
        // using iterator
       /*
        Iterator<Map.Entry<String, String>> itr = map.entrySet().iterator();
        JSONArray usuaris_list = new JSONArray();
        while(itr.hasNext()){
            Map.Entry<String, String> entrada = itr.next();
            //JSONParser parser = new JSONParser(); //mirar si pot anar fora del loop
            JSONObject tauler_guardat = new JSONObject();
            tauler_guardat.put("username", entrada.getKey());
            tauler_guardat.put("password", entrada.getValue());
            JSONObject objecte_usuari = new JSONObject();
            objecte_usuari.put("user_id", detalls_usuari);
            //Afegir usuaris a la llista
            usuaris_list.add(objecte_usuari);
        }

        //Write JSON file
        try (FileWriter file = new FileWriter("data/usuaris.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(usuaris_list.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }
}
