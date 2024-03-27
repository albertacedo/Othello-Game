package Persistencia;

import domini.*;
import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Classe singleton per gestionar les dades del ranquing
 * @author Albert Acedo Casellas
 * @version 1.0
 */

public class Gestor_ranquing {
    /** Atributs **/

    /**Instancia de la clase */
    private static Gestor_ranquing gestor_ranquings;
    /**Vector auxiliar per guardar la base de dades del ranquing quan fem canvis (15 posicions) */
    private Ranquing arrel = new Ranquing("@", -1);
    private Ranquing[] top15 = new Ranquing[]
    {arrel, arrel, arrel, arrel, arrel, arrel, arrel, arrel, arrel, arrel, arrel, arrel, arrel, arrel, arrel};


    /**
     * Instancia la classe singleton
     */
    public static Gestor_ranquing instancia(){
        if (gestor_ranquings == null){
            gestor_ranquings = new Gestor_ranquing();
        }
        return gestor_ranquings;
    }


    /**
     * Consulta si el que rep es una dificultat correcte i quina es:
     * Les dificultats dels rivals:
     *      -Easy = 1
     *      -Normal = 3
     *      -Hard = 5
     *      -Impossible = 7
     * @param dif String que rebem amb el nom de la dificultat
     * @return Retornem un boleà indicant si la dificultat és correcte o no
     */
    private static boolean dificultat_correcte(String dif) {
        switch (dif) {
            case "easy":
            case "normal":
            case "hard":
            case "impossible":
                return true;
            default:
                System.out.println("Error: dificultat incorrecte");
                return false;
        }
    }


    /**
     * Rebem una dificultat correcte (no es comprova) i retornem el nom del fitxer JSON corresponent
     * @param dif String que rebem amb el nom de la dificultat
     * @return Retornem el nom del fitxer JSON corresponent, "Invalid_file_name" altrament
     */
    private static String getFitxer(String dif) {
        switch (dif) {
            case "easy": return "data/ranquing_easy.json";
            case "normal": return "data/ranquing_normal.json";
            case "hard": return "data/ranquing_hard.json";
            case "impossible": return "data/ranquing_impossible.json";
            default:
                System.out.println("Error en l'acces al fitxer");
                return "Invalid_file_name";
        }
    }


    /**
     * Aquesta funcio s'encarrega de escriure la base de dades de usuaris tant quan afegim
     * o esborrem un usuari, recorrent el map de usuaris amb un iterador i per cada element
     * parseja el seu valor i crea un objecte per cada usuari, que s'afegeix a una llista de usuaris
     * en format JSON.
     */
    private void esciure_bd_ranquing(String dif){
        JSONArray ranquing_vector = new JSONArray();
        for(int i=0; i< top15.length; ++i){
            JSONObject dades_ranquing = new JSONObject();
            dades_ranquing.put("Nom", top15[i].getNom());
            dades_ranquing.put("Puntuació", String.valueOf(top15[i].getPunts()) );
            JSONObject top = new JSONObject();
            top.put("JugadorTop", dades_ranquing);
            ranquing_vector.add(top);
        }

        try (FileWriter file = new FileWriter(getFitxer(dif))) {
            file.write(ranquing_vector.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Funció que afageix al fitxer ranquing desitjat (segons la dificultat) el jugador i la seva puntuació
     * @param p Objecte Persona que volem afegir al ranquing (no comprovem si esta registrat)
     * @param puntuacio Correspon al total de la puntuacio que volem guardar
     * @param dif Dificultat del ranquing al que afegim al jugador
     * @return Retornem un boleà que ens indica si s'ha modificat el ranquing o no
     */
    public boolean afegir_ranquing (Persona p, int puntuacio, String dif){
        String nom = p.getNom();
        Ranquing nou = new Ranquing(nom,puntuacio);
        if(dificultat_correcte(dif)){
            return afegir_ranquing_intern(nou,dif);
        }
        return false;
    }


    /**
     * Funcio privada que afegeix un jugador i la seva puntuacio en un ranquing JSON
     * @param nou Objecte ranquing que volem afegir al JSON
     * @param dif Dificultat del ranquing al que afegim al jugador (no es comprova si es correcte)
     * @return retornem un boleà que indica si hem modificat el ranquing o no
     */
    private boolean afegir_ranquing_intern(Ranquing nou,String dif) {
        boolean trobat = false;
        int punts = nou.getPunts();

        if (punts > top15[top15.length - 1].getPunts()) {
            Ranquing anterior = null;
            for (int i = 0; i < top15.length; ++i) {
                if (trobat) {
                    Ranquing aux = top15[i];
                    top15[i] = anterior;
                    anterior = aux;
                } else {
                    if (punts > top15[i].getPunts()) {
                        trobat = true;
                        anterior = top15[i];
                        top15[i] = nou;
                    }
                }
            }
        }
        if(trobat) esciure_bd_ranquing(dif);
        return trobat;
    }



    /**
     *LLegeix el fitxer ranquing_(dificultat).json i els parseja a un vector d'objectes Ranquing
     * (es comprova que la dificultat sigui correcte)
     * A més, actualitza el top15 auxiliar per si hem de guardar dades més tard.
     * @return Retorna un vector de Ranquing(string, int) que conten el nom del jugador i la seva puntuació
     */
    public Ranquing[] llegir_bd_ranquing (String dif){
        if(dificultat_correcte(dif)){
            JSONParser Parser = new JSONParser();

            try (FileReader reader = new FileReader(getFitxer(dif))) {
                Object obj = Parser.parse(reader);
                JSONArray vector_ranquing = (JSONArray) obj;

                for(int i=0; i< top15.length; ++i) {
                    JSONObject dades_ranquing = (JSONObject) vector_ranquing.get(i);
                    JSONObject ranquing_objecte = (JSONObject) dades_ranquing.get("JugadorTop");
                    String nom = (String) ranquing_objecte.get("Nom");
                    int punts = Integer.parseInt((String) ranquing_objecte.get("Puntuació"));

                    Ranquing jugador = new Ranquing(nom, punts);
                    top15[i] = jugador;
                }
                return top15;
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * Aquesta funcio s'encarrega de tornar TOTS ELS FITXERS JSON a l'estat inicial
     * A més, també reseteja el vector auxiliar top15
     */
    private void reset_bd_ranquing(){
        JSONArray ranquing_vector = new JSONArray();
        for(int i=0; i< top15.length; ++i){
            JSONObject dades_ranquing = new JSONObject();
            dades_ranquing.put("Nom", "@");
            dades_ranquing.put("Puntuació", "-1");
            JSONObject top = new JSONObject();
            top.put("JugadorTop", dades_ranquing);
            ranquing_vector.add(top);

            top15[i] = arrel;
        }

        String[] noms_dif = {"easy","normal","hard","impossible"};
        for(String nom: noms_dif){
            try (FileWriter file = new FileWriter(getFitxer(nom))) {
                file.write(ranquing_vector.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



/*
//DEBUG
    public static void main(String[] args) {
        Gestor_ranquing gestor_ranquings = Gestor_ranquing.instancia();
        Ranquing[] R = new Ranquing[15];
        R = gestor_ranquings.llegir_bd_ranquing("easy");

        Persona P = new Persona("negre");
        P.registrarPersona("Albert", "password");

        gestor_ranquings.afegir_ranquing (P, 44, "easy");
        R = gestor_ranquings.llegir_bd_ranquing("easy");

        gestor_ranquings.reset_bd_ranquing();

        for(Ranquing element:R) System.out.println(element.getNom() + " " + element.getPunts());

    }
*/

}
