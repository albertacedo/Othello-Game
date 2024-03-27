package domini;
import java.util.*;

/**
 * Aquesta classe representa objectes IA, que son jugadors amb inteligència artificial, és a dir, controlats per
 * la màquina. És una subclasse de jugador
 * @author Adrian Braojos Parras
 * @version 1.0
 */
public class IA extends Jugador{

    /** Dificultat de la IA. Només pot tenir com a valors "easy*, "normal", "hard" o "impossible"    */
    private String dificultat;

    /**
     * Constructora per defecte, crea un jugador amb inteligència artificial i li assigna un color i una dificultat
     * @param color color de les fitxes de la ia
     * @param dificultat dificultat que tindrà la IA
     */
    public IA (String color, String dificultat) {
        super(color);
        if (!(dificultat.equals("easy") || dificultat.equals("normal") || dificultat.equals("hard") || dificultat.equals("impossible"))) {
            System.out.println("Error: la dificultat ha de ser easy, normal, hard o impossible");
            return;
        }
        this.dificultat = dificultat;
    }

    /**
     * Consultora, retorna la dificultat de la IA
     */
    public String getDificultat() {
        return this.dificultat;
    }

    /**
     * Canvia la dificultat de la IA
     * @param dificultat la dificultat nova que tindrà la IA
     */
    public void canviar_dificultat(String dificultat) {
        if (!(dificultat.equals("easy") || dificultat.equals("normal") || dificultat.equals("hard") || dificultat.equals("impossible"))) {
            System.out.println("Error: la dificultat ha de ser easy, normal, hard o impossible");
            return;
        }
        this.dificultat = dificultat;
    }

    /**
     * Calcula quin és el millor moviment que pot jugar la IA al taulell seguint l'algorisme minmax amb una profunditat
     * fixada segons el nivell de dificultat de la IA. Retorna una casella amb la possició de la casella on es pot fer
     * el millor moviment. Si no hi ha cap moviment possible retorna una casella amb posició (-1, -1)
     * @param t taulell on s'ha de trobar el millor moviment
     */
    Casella millor_moviment (Tauler t) {
        //System.out.println("DEBUG: suposem que jugador te l'estat color = {blanc} o {negre}");
        //System.out.println("PRE: rebem un string amb el color del jugador");
        //System.out.println("POST: retornem la Casella on més punts tindrem segons la profunditat" +
        //        "i si no hi ha moviments possibles retornem una casella amb valors (-1, -1)");

        String color = this.getColor();
        Casella c = new Casella(-1, -1);
        if (!(color.equals("blanc") || color.equals("negre"))) {
            System.out.println("Error: color de jugador erroni");
            return c;
        }

        List<Casella> caselles;
        caselles = t.caselles_disponibles(color);

        if (caselles.isEmpty()) return c;
        if (caselles.size() == 1) return caselles.get(0);

        int puntuacio = 0;
        int bool = 0;           // per la primera casella disponible
        int profunditat;

        switch (this.dificultat) {
            case "easy":
                profunditat = 1;
                break;
            case "normal":
                profunditat = 3;
                break;
            case "hard":
                profunditat = 5;
                break;
            default:
                profunditat = 7;
                break;
        }

        for (Casella element:caselles) {
            if (bool == 0) {
                puntuacio = getPuntuacio(t, color, element, profunditat, 1);
                c = new Casella(element.getPos_x(), element.getPos_y());
                bool = 1;
            }
            else {
                int punt = getPuntuacio(t, color, element, profunditat, 1);
                if (punt > puntuacio) {
                    puntuacio = punt;
                    c = new Casella(element.getPos_x(), element.getPos_y());
                }
            }
        }
        return c;
    }

    /**
     * Mètode privat que es crida des de la funció millor_moviment. Aplica l'algorisme minmax de manera recursiva.
     * Sumant un nivell de dificultat segons la profunditat fixada per cada nivell de dificultat.
     * Primer busco la puntuació que em donaria posar casella a la posició de la casella "c", desprès si soc a
     * l'últim nivell de profunditat retorno aquest resultat. Si no, col·loco peça a aquesta posició i canvio de color
     * les peces guanyades. Llavors torno a calcular les caselles disponibles i crido aquesta funció una vegada per
     * cada nova casella disponible.
     * El paràmetre "sumant" m'indica si estic a un torn "imaginari" meu o del rival. Si "sumant" és igual a 1 estic al
     * meu torn i em quedo amb la puntuació de la casella disponible més baixa. Si és igual a 0 estic al torn del rival
     * i he de quedar-me amb la puntuació més alta.
     * Quan ja tinc la puntuació correcta deixo el taulell com era a l'inici i retorno la puntuació (Si soc al torn del
     * rival la puntuació de posar casella a la posició indicada per la casella "c" la retorno negada).
     * @param t taulell on s'ha de trobar el millor moviment
     * @param color color de les fitxes a col·locar a aquest nivell
     *             (si soc al meu torn serà el color del jugador, si no el del rival)
     * @param c casella guardant la posició de la casella de la que he de sumar puntuació i utilitzar en aquest nivell
     * @param profunditat vegades que he de baixar recursivament. Si és 1 sóc a l'ultim nivell de recursió.
     * @param sumant indica si soc al meu torn o al del rival
     */
    private int getPuntuacio(Tauler t, String color, Casella c, int profunditat, int sumant) {
        int x = c.getPos_x();
        int y = c.getPos_y();
        int puntuacio = 0;
        int punt = 0;

        int[] distancies = {0, 0, 0, 0, 0, 0, 0, 0};
        int[] direccions = {0, 0, 0, 0, 0, 0, 0, 0};

        String colorRival;
        if (color.equals("negre")) colorRival = "blanc";
        else colorRival = "negre";

        // Mirem les direccions on hauran de canviar peces de color:

        // Nord-est
        if (x > 1 && y < 6) {
            int bool = 0;
            for (int x2 = x - 1, y2 = y + 1; x2 >= 0 && y2 <= 7; --x2, ++y2) {
                Casella c2 = t.getCaselles(x2, y2);
                String s = c2.getEstat();
                if (bool == 0) {
                    if (s.equals(color) || s.equals("buit")) break;
                    bool = 1;
                } else {
                    if (s.equals(colorRival)) continue;
                    if (s.equals("buit")) break;
                    puntuacio += x - x2 - 1;
                    distancies[0] = x - x2 - 1;
                    direccions[0] = 1;
                    break;
                }
            }
        }

        // Est
        if (y < 6) {
            int bool = 0;
            for (int y2 = y + 1; y2 <= 7; ++y2) {
                Casella c2 = t.getCaselles(x, y2);
                String s = c2.getEstat();
                if (bool == 0) {
                    if (s.equals(color) || s.equals("buit")) break;
                    bool = 1;
                } else {
                    if (s.equals(colorRival)) continue;
                    if (s.equals("buit")) break;
                    puntuacio += y2 - y - 1;
                    distancies[1] = y2 - y - 1;
                    direccions[1] = 1;
                    break;
                }
            }
        }

        // Sud-est
        if (x < 6 && y < 6) {
            int bool = 0;
            for (int x2 = x + 1, y2 = y + 1; x2 <= 7 && y2 <= 7; ++x2, ++y2) {
                Casella c2 = t.getCaselles(x2, y2);
                String s = c2.getEstat();
                if (bool == 0) {
                    if (s.equals(color) || s.equals("buit")) break;
                    bool = 1;
                } else {
                    if (s.equals(colorRival)) continue;
                    if (s.equals("buit")) break;
                    puntuacio += x2 - x - 1;
                    distancies[2] = x2 - x - 1;
                    direccions[2] = 1;
                    break;
                }
            }
        }

        // Sud
        if (x < 6) {
            int bool = 0;
            for (int x2 = x + 1; x2 <= 7; ++x2) {
                Casella c2 = t.getCaselles(x2, y);
                String s = c2.getEstat();
                if (bool == 0) {
                    if (s.equals(color) || s.equals("buit")) break;
                    bool = 1;
                } else {
                    if (s.equals(colorRival)) continue;
                    if (s.equals("buit")) break;
                    puntuacio += x2 - x - 1;
                    distancies[3] = x2 - x - 1;
                    direccions[3] = 1;
                    break;
                }
            }
        }

        // Sud-oest
        if (x < 6 && y > 1) {
            int bool = 0;
            for (int x2 = x + 1, y2 = y - 1; x2 <= 7 && y2 >= 0; ++x2, --y2) {
                Casella c2 = t.getCaselles(x2, y2);
                String s = c2.getEstat();
                if (bool == 0) {
                    if (s.equals(color) || s.equals("buit")) break;
                    bool = 1;
                } else {
                    if (s.equals(colorRival)) continue;
                    if (s.equals("buit")) break;
                    puntuacio += x2 - x - 1;
                    distancies[4] = x2 - x - 1;
                    direccions[4] = 1;
                    break;
                }
            }
        }

        // Oest
        if (y > 1) {
            int bool = 0;
            for (int y2 = y - 1; y2 >= 0; --y2) {
                Casella c2 = t.getCaselles(x, y2);
                String s = c2.getEstat();
                if (bool == 0) {
                    if (s.equals(color) || s.equals("buit")) break;
                    bool = 1;
                } else {
                    if (s.equals(colorRival)) continue;
                    if (s.equals("buit")) break;
                    puntuacio += y - y2 - 1;
                    distancies[5] = y - y2 - 1;
                    direccions[5] = 1;
                    break;
                }
            }
        }

        // Nord-oest
        if (x > 1 && y > 1) {
            int bool = 0;
            for (int x2 = x - 1, y2 = y - 1; x2 >= 0 && y2 >= 0; --x2, --y2) {
                Casella c2 = t.getCaselles(x2, y2);
                String s = c2.getEstat();
                if (bool == 0) {
                    if (s.equals(color) || s.equals("buit")) break;
                    bool = 1;
                } else {
                    if (s.equals(colorRival)) continue;
                    if (s.equals("buit")) break;
                    puntuacio += x - x2 - 1;
                    distancies[6] = x - x2 - 1;
                    direccions[6] = 1;
                    break;
                }
            }
        }

        // Nord
        if (x > 1) {
            int bool = 0;
            for (int x2 = x - 1; x2 >= 0; --x2) {
                Casella c2 = t.getCaselles(x2, y);
                String s = c2.getEstat();
                if (bool == 0) {
                    if (s.equals(color) || s.equals("buit")) break;
                    bool = 1;
                } else {
                    if (s.equals(colorRival)) continue;
                    if (s.equals("buit")) break;
                    puntuacio += x - x2 - 1;
                    distancies[7] = x - x2 - 1;
                    direccions[7] = 1;
                    break;
                }
            }
        }

        // Si som a l'últim nivell de profunditat, retornem el resultat
        if (profunditat == 1) {
            if (sumant == 1) return puntuacio;
            return -puntuacio;
        }

        // Col·loquem peça i canviem de color les caselles que guanyem
        if (direccions[0] == 1) {
            for (int i = distancies[0]; i > 0; --i) t.moures_fitxes(x - i, y + i, color);
        }
        if (direccions[1] == 1) {
            for (int i = distancies[1]; i > 0; --i) t.moures_fitxes(x, y + i, color);
        }
        if (direccions[2] == 1) {
            for (int i = distancies[2]; i > 0; --i) t.moures_fitxes(x + i, y + i, color);
        }
        if (direccions[3] == 1) {
            for (int i = distancies[3]; i > 0; --i) t.moures_fitxes(x + i, y, color);
        }
        if (direccions[4] == 1) {
            for (int i = distancies[4]; i > 0; --i) t.moures_fitxes(x + i, y - i, color);
        }
        if (direccions[5] == 1) {
            for (int i = distancies[5]; i > 0; --i) t.moures_fitxes(x, y - i, color);
        }
        if (direccions[6] == 1) {
            for (int i = distancies[6]; i > 0; --i) t.moures_fitxes(x - i, y - i, color);
        }
        if (direccions[7] == 1) {
            for (int i = distancies[7]; i > 0; --i) t.moures_fitxes(x - i, y, color);
        }
        t.moures_fitxes(x, y, color);

        // Anem al seguent nivell de profunditat de l'algorisme minmax
        List<Casella> caselles;
        caselles = t.caselles_disponibles(color);
        int bool = 0;       // pel primer element
        for (Casella element : caselles) {
            if (bool == 0) {
                if (sumant == 1) punt = getPuntuacio(t, colorRival, element, profunditat - 1, 0);
                else punt = getPuntuacio(t, colorRival, element, profunditat - 1, 1);
                bool = 1;
            } else {
                if (sumant == 1) {
                    int punt2 = getPuntuacio(t, colorRival, element, profunditat - 1, 0);
                    if (punt2 < punt) punt = punt2;
                } else {
                    int punt2 = getPuntuacio(t, colorRival, element, profunditat - 1, 1);
                    if (punt2 > punt) punt = punt2;
                }
            }
        }

        // Deixem el taulell com era abans
        if (direccions[0] == 1) {
            for (int i = distancies[0]; i > 0; --i) t.moures_fitxes(x - i, y + i, colorRival);
        }
        if (direccions[1] == 1) {
            for (int i = distancies[1]; i > 0; --i) t.moures_fitxes(x, y + i, colorRival);
        }
        if (direccions[2] == 1) {
            for (int i = distancies[2]; i > 0; --i) t.moures_fitxes(x + i, y + i, colorRival);
        }
        if (direccions[3] == 1) {
            for (int i = distancies[3]; i > 0; --i) t.moures_fitxes(x + i, y, colorRival);
        }
        if (direccions[4] == 1) {
            for (int i = distancies[4]; i > 0; --i) t.moures_fitxes(x + i, y - i, colorRival);
        }
        if (direccions[5] == 1) {
            for (int i = distancies[5]; i > 0; --i) t.moures_fitxes(x, y - i, colorRival);
        }
        if (direccions[6] == 1) {
            for (int i = distancies[6]; i > 0; --i) t.moures_fitxes(x - i, y - i, colorRival);
        }
        if (direccions[7] == 1) {
            for (int i = distancies[7]; i > 0; --i) t.moures_fitxes(x - i, y, colorRival);
        }
        t.moures_fitxes(x, y, "buit");

        // retornem resultat
        if (sumant == 1) return puntuacio + punt;
        return -puntuacio + punt;
    }

}