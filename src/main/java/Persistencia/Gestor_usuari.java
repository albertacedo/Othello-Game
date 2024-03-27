package Persistencia;

import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Classe singleton per gestionar les dades dels usuaris
 * @author Marc Fernandez Palau
 * @version 1.0
 */
public class Gestor_usuari {

    /** Atributs **/

    /**Instancia de la clase     */
    private static Gestor_usuari gestor_usuaris;
    /**Map per guardar la base de dades de usuaris quan fem canvis     */
    private static Map<String,String> map = new HashMap<String,String>();


    /**
     * Instancia la classe singleton
     */
    public static Gestor_usuari instancia(){
        if (gestor_usuaris == null){
            gestor_usuaris = new Gestor_usuari();
        }
        return gestor_usuaris;
    }

    /**
     * Funció que afegeig al usuari nou a un map i parseja tots els elements del
     * map en objectes json i s'actualitza la base de dades
     * @param username nom del usuari
     * @param password contrasenya del usuari
     */
    public void guardar_usuari(String username, String password){
        map.put(username, password);
        esciure_bd_usuaris();
    }

    /**
     * Funció que esborra al usuari de la base de dades
     * @param username nom del usuari
     */
    public void esborrar_usuari(String username) {
        map.remove(username);
        esciure_bd_usuaris();
    }

    /**
     *LLegeix el fitxer usuaris.json i els parseja a string
     * La funcio un cop parsejades les dades per cada objecte (usuari) crida a una funcio
     * que fica les dades en format string en un map
     * @return Retorna un map amb les parelles (key, value) username, password
     */
    public Map<String, String> llegir_bd_usuaris(){
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("data/usuaris.json")) {
            //Llegim el usuaris.json
            Object obj = jsonParser.parse(reader);

            JSONArray usuaris_list = (JSONArray) obj;
            //Itera sobre el array de usuaris
            usuaris_list.forEach(usr -> parse_usuari_Object((JSONObject) usr));
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
     *Funció que agafa cada objecte json (cada usuari) i les seves dades en un
     * map en format string com username, password
     * @param usr Objecte json que conte 1 usuari
     */
    private static void parse_usuari_Object(JSONObject usr){
            //Agafem cada usuari de la llista
            JSONObject usuari_Object = (JSONObject) usr.get("user_id");

            //Agafem el username del usuari
            String nom_usuari = (String) usuari_Object.get("username");
            //System.out.println(nom_usuari);
            //i el seu password
            String password = (String) usuari_Object.get("password");
            //System.out.println(password);

            //inserim la info en un map
            map.put(nom_usuari, password);
    }

    /**
     * Aquesta funcio s'encarrega de escriure la base de dades de usuaris tant quan afegim
     * o esborrem un usuari, recorrent el map de usuaris amb un iterador i per cada element
     * parseja el seu valor i crea un objecte per cada usuari, que s'afegeix a una llista de usuaris
     * en format JSON.
     */
    private void esciure_bd_usuaris(){
        // using iterator
        Iterator<Map.Entry<String, String>> itr = map.entrySet().iterator();
        JSONArray usuaris_list = new JSONArray();
        while(itr.hasNext()){
            Map.Entry<String, String> entrada = itr.next();
            //JSONParser parser = new JSONParser(); //mirar si pot anar fora del loop
            JSONObject detalls_usuari = new JSONObject();
            detalls_usuari.put("username", entrada.getKey());
            detalls_usuari.put("password", entrada.getValue());
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
    }

}
