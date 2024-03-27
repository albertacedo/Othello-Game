package domini;

import java.util.*;

/**
 * Aquesta classe representa objectes taulell
 * @author Marc Fernandez Palau - Albert Acedo Casellas
 * @version 1.0
 */
public class Tauler {

    /* Atributss **/

    /**
     * Vector de caselles que componen el taulell
     * Com que sabem el tamany de la llista de caselles que formen un
     * taulell utilitzem un array, ja que el access (read/write) a qualsevol
     * posició ràpid.
     * */
    private Casella[][] caselles;


    /**
     * Constructora per defecte, crea el taulell de joc inicial
     * amb la permutació de fitxes correctament
     */
    public Tauler() {
        caselles = new Casella [8][8];
        for(int x=0;x<8;x++){
            for(int y=0;y<8;y++) {
                caselles[x][y] = new Casella(x, y);
            }
        }
        caselles[3][4].canvi_estat("negre");
        caselles[4][3].canvi_estat("negre");
        caselles[3][3].canvi_estat("blanc");
        caselles[4][4].canvi_estat("blanc");
    }

    /**
     * Canvia el estat de una casella per el color corresponent
     * @param pos_x coordenada x de la casella
     * @param pos_y coordenada y de la casella
     * @param color nou color que tindra la casella
     */
    public void moures_fitxes(int pos_x, int pos_y, String color ){
         caselles[pos_x][pos_y].canvi_estat(color);
    }

    /**
     * Retorna la casella de la posició x,y
     * @param pos_x coordenada x de la casella
     * @param pos_y coordenada y de la casella
     * @return Retorna un objecte casella
     */
    public Casella getCaselles(int pos_x, int pos_y){
        return caselles[pos_x][pos_y];
    }

    /**
     * Retorna l'estat de la casella a la posició x,y
     * @param pos_x coordenada x de la casella
     * @param pos_y coordenada y de la casella
     * @return Retorna un objecte casella
     */
    public String getEstat(int pos_x, int pos_y){
        return caselles[pos_x][pos_y].getEstat();
    }



    /**
     * Algorisme que calcula les caselles disponibles en la propera jugada.
     * -Primer hem de recorrer el tauler per cercar les caselles del rival
     * -Després, comprovarem si les caselles adjacents a les caselles del rival son buides, i si ho son, les posarem en una pila
     * -Per últim, aquestes caselles buides han de ser vàlides per a jugar, per lo que haurem de comprovar una serie de requisits tals com
     * que existeixi una cadena de caselles Rival-Rival-Rival-Primari.
     *
     * @param colorPrimari El color del jugador que esta jugant, del qual volem saber les caselles disponilbes
     * @return Una llista amb les caselles on podrem moure la proxima tirada (o una llista buida si el color no existeix)
     */
    public List<Casella> caselles_disponibles(String colorPrimari){

        //mirem quin es el color del rival i comprovem errors
        String colorRival;
        List<Casella> L = new ArrayList<>();

        if(colorPrimari.equals("blanc")){
            colorRival = "negre";
        } else if(colorPrimari.equals("negre")){
            colorRival = "blanc";
        } else {
            System.out.println("Error: color de jugador erroni");
            return L;
        }

        //Dreta,Diagonal Amunt Dreta,Amunt,Diagonal Amunt Esquerra,Esquerra,Diagonal Abaix Esquerra,Abaix,Diagonal Abaix Dreta.
        int X[] = {1,1,0,-1,-1,-1,0,1};
        int Y[] = {0,-1,-1,-1,0,1,1,1};


        //omplim la llista de totes les caselles del jugador rival
        for(int i=0; i<8; ++i){
            for(int j=0; j<8; ++j) {
                Casella c = this.caselles[i][j];
                if(c.getEstat().equals(colorRival)) L.add(c);
            }
        }

        Stack<Casella> S = new Stack<>();

        for(Casella actual:L){
            //comprovem que les 8 caselles adjacents estiguin dins del taulell i que estiguin buides

            for(int i=0; i<8; ++i){
                Casella seguent = new Casella(actual.getPos_x()+X[i], actual.getPos_y()+Y[i]);
                if(casella_buida(seguent)) S.push(seguent);
            }
        }


        Set<Casella> Visitat = new HashSet<>();
        List<Casella> Disponible = new ArrayList<>();

        while(!S.empty()){
            //comprovar que no estem en Set(Visitat)
            Casella c = S.peek();
            boolean valid = false;
            if(!Visitat.contains(c)){
                //funció per determinar si la casella es vàlida:

                int i = 0;
                while(!valid && i<8){
                    valid = casella_valida(c,colorPrimari,colorRival,i,X,Y);
                    ++i;
                }

                //afegim la casella a Visitat i si es vàlida a Disponible
                Visitat.add(c);
                if(valid) Disponible.add(c);
            }
            S.pop();
        }

        /*
        System.out.println("Caselles disponibles:");
        for (int i = 0; i < Disponible.size(); ++i) {
            System.out.println(Disponible.get(i).getPos_x() + "," + Disponible.get(i).getPos_y());
        }*/

        return Disponible;
    }


    /**
     * Comprova si la casella amb coordenades [x,y] esta dins del tauler i si es troba buida
     * @param c Casella que volem comprovar
     * @return Retorna  <code>True</code> si la casella es troba dins del tauler i es buida, <code>False</code> altrament
     */
    private boolean casella_buida(Casella c){
        int x = c.getPos_x();
        int y = c.getPos_y();
        if(x < 8 && x >= 0 && y < 8 && y >= 0 && this.caselles[x][y].getEstat().equals("buit")) {
            return true;
        }
        return false;
    }


    /**
     * Comprova si la casella amb coordenades [x,y] es valida. Per ser valida necessitem saber si
     * existeix una direccio amb sequencia de caselles: colorRival...colorRival, colorPrimari
     * @param c Casella que volem comprovar
     * @param colorPrimari Color del jugador actiu
     * @param colorRival Color del jugador rival
     * @return Retorna  <code>True</code> si la casella es valida, <code>False</code> altrament
     */
    private boolean casella_valida(Casella c, String colorPrimari, String colorRival, int i, int X[], int Y[]){
        int x = c.getPos_x();
        int y = c.getPos_y();
        boolean llindar = false;

        switch (i) {
            case 0:  if(x < 6) llindar = true;
                break;
            case 1:  if(x < 6 && y > 1) llindar = true;
                break;
            case 2:  if(y > 1) llindar = true;
                break;
            case 3:  if(x > 1 && y > 1) llindar = true;
                break;
            case 4:  if(x > 1) llindar = true;
                break;
            case 5:  if(x > 1 && y < 6) llindar = true;
                break;
            case 6:  if(y < 6) llindar = true;
                break;
            case 7:  if(x < 6 && y < 6) llindar = true;
                break;
            default:
                System.out.println("Error: iterador fora de llinadars");
                return llindar;
        }

        if(llindar){
            int auxx = x + X[i];
            int auxy = y + Y[i];
            while( auxx >= 0 && auxx < 8 && auxy >= 0 && auxy < 8){
                if(this.caselles[auxx][auxy].getEstat().equals(colorRival)){
                    auxx = auxx + X[i];
                    auxy = auxy + Y[i];
                }
                else if(this.caselles[auxx][auxy].getEstat().equals(colorPrimari) && this.caselles[auxx-X[i]][auxy-Y[i]].getEstat().equals(colorRival)) {
                    return true;
                }
                else return false;
            }
        }
        return false;
    }




    public static void main(String[] args) {
        List<Casella> R = new ArrayList<Casella>();

        Tauler  T = new Tauler();

        R = T.caselles_disponibles("negre");

        for(Casella element:R) System.out.println(element.getPos_x() + " " + element.getPos_y());

    }

}