package domini;

/**
 * Aquesta classe representa objectes jugador, que son els que juguen una partida
 * @author Adrian Braojos Parras
 * @version 1.0
 */
public class Jugador {

    /**Color de les fitxes del jugador. Només pot tenir com a valors "blanc* o "negre" */
    private String color;

    /**
     * Constructora per defecte, crea un jugador i li assigna un color
     * @param color color de les fitxes del jugador
     */
    public Jugador (String color) {
        if (!(color.equals("negre") || color.equals("blanc"))) {
            System.out.println("Error: el color ha de ser blanc o negre");
            return;
        }
        this.color = color;
    }

    /**
     * Consultora, retorna el color del jugador
     * @return Retorna el color de les fitxes del jugador
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Col·loca una fitxa amb el color del jugador al taulell, i canvia de color les fitxes guanyades a l'altre jugador.
     * Els paràmetres "x" i "y" han de ser sí o sí la direcció d'una casella disponible al taulell per al jugador
     * @param t taulell a on es colocarà la fitxa
     * @param x fila del taulell on es col·locarà la peça (0 a 7, de dalt a baix)
     * @param y columna del taulell on es col·locarà la peça (0 a 7, d'esquerra a dreta)
     */
    void colocar_fitxa(Tauler t, int x, int y) {

        String color = this.color;
        String colorRival;

        if (color.equals("negre")) colorRival = "blanc";
        else colorRival = "negre";
        System.out.printf("%nmoviment final: " + x + "," + y + "%n%n");

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
                    for (int i = x - x2 - 1; i > 0; --i) t.moures_fitxes(x - i, y + i, color);
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
                    for (int i = y2 - y - 1; i > 0; --i) t.moures_fitxes(x, y + i, color);
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
                    for (int i = x2 - x - 1; i > 0; --i) t.moures_fitxes(x + i, y + i, color);
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
                    for (int i = x2 - x - 1; i > 0; --i) t.moures_fitxes(x + i, y, color);
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
                    for (int i = x2 - x - 1; i > 0; --i) t.moures_fitxes(x + i, y - i, color);
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
                    for (int i = y - y2 - 1; i > 0; --i) t.moures_fitxes(x, y - i, color);
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
                    for (int i = x - x2 - 1; i > 0; --i) t.moures_fitxes(x - i, y - i, color);
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
                    for (int i = x - x2 - 1; i > 0; --i) t.moures_fitxes(x - i, y, color);
                    break;
                }
            }
        }

        t.moures_fitxes(x, y, color);

    }

}
