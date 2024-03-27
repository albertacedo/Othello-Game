/*package test;
import domini.*;
import org.junit.*;
import static org.junit.Assert.*;

public class JugadorTest {

    Jugador j1, j2;

    @Test
    public void Jugador () {
        Jugador j1 = new Jugador("negre");
        assertNotNull("El jugador creat no hauria de donar null",j1);
        
        Jugador j2 = new Jugador("negre");
        assertNotNull("El jugador creat no hauria de donar null",j2);

        assertNotSame("Els jugadors no haurien de ser el mateix objecte", j1, j2);

        j1 = new Jugador("blanc", "Adrian");
        assertNotNull("El jugador creat no hauria de donar null",j1);

        j2 = new Jugador("blanc", "Adrian");
        assertNotNull("El jugador creat no hauria de donar null",j2);

        assertNotSame("Els jugadors no haurien de ser el mateix objecte", j1, j2);
    }

    @Test
    public void getColor() {
        Jugador j1 = new Jugador("negre");
        assertNotNull("El color del jugador no hauria de donar null",j1.getColor());
        assertEquals("El color del jugador hauria de ser negre", j1.getColor(), "negre");

        j1 = new Jugador("blanc");
        assertNotNull("El color del jugador no hauria de donar null",j1.getColor());
        assertEquals("El color del jugador hauria de ser negre", j1.getColor(), "blanc");

        j1 = new Jugador("vermell");
        assertNull("El color del jugador hauria de donar null",j1.getColor());

        j1 = new Jugador("negre", "Adrian");
        assertNotNull("El color del jugador no hauria de donar null",j1.getColor());
        assertEquals("El color del jugador hauria de ser negre", j1.getColor(), "negre");

        j1 = new Jugador("blanc", "Albert");
        assertNotNull("El color del jugador no hauria de donar null",j1.getColor());
        assertEquals("El color del jugador hauria de ser negre", j1.getColor(), "blanc");

        j1 = new Jugador("verd", "Marc");
        assertNull("El color del jugador hauria de donar null",j1.getColor());
    }

    @Test
    public void getnom() {
        Jugador j1 = new Jugador("negre", "Adrian");
        assertNotNull("El nom del jugador no hauria de ser null", j1.getnom());
        assertEquals("El nom del jugador hauria de ser Adrian", j1.getnom(), "Adrian");
    }

    @Test
    public void colocar_fitxa() {
        Jugador j1 = new Jugador("negre", "Adrian");
        Jugador j2 = new Jugador("blanc", "Adriano");
        Tauler t = new Tauler("id1");

        //est
        j1.colocar_fitxa(t, 4, 5);
        assertEquals("La casella hauria de ser negra", t.getCaselles(4, 5).getEstat(), "negre");
        assertEquals("La casella hauria de ser negra", t.getCaselles(4, 4).getEstat(), "negre");

        //sud
        j2.colocar_fitxa(t, 5, 3);
        assertEquals("La casella hauria de ser blanca", t.getCaselles(5, 3).getEstat(), "blanc");
        assertEquals("La casella hauria de ser blanca", t.getCaselles(4, 3).getEstat(), "blanc");

        //oest
        j1.colocar_fitxa(t, 4, 2);
        assertEquals("La casella hauria de ser negra", t.getCaselles(4, 2).getEstat(), "negre");
        assertEquals("La casella hauria de ser negra", t.getCaselles(4, 3).getEstat(), "negre");

        //est i nord-est
        j2.colocar_fitxa(t, 3, 5);
        assertEquals("La casella hauria de ser blanca", t.getCaselles(3, 5).getEstat(), "blanc");
        assertEquals("La casella hauria de ser blanca", t.getCaselles(3, 4).getEstat(), "blanc");
        assertEquals("La casella hauria de ser blanca", t.getCaselles(4, 4).getEstat(), "blanc");

        //sud-est
        j1.colocar_fitxa(t, 6, 4);
        assertEquals("La casella hauria de ser negra", t.getCaselles(6, 4).getEstat(), "negre");
        assertEquals("La casella hauria de ser negra", t.getCaselles(5, 3).getEstat(), "negre");

        //nord-est
        j1.colocar_fitxa(t, 2, 4);
        assertEquals("La casella hauria de ser negra", t.getCaselles(2, 4).getEstat(), "negre");
        assertEquals("La casella hauria de ser negra", t.getCaselles(3, 3).getEstat(), "negre");

        //nord
        j2.colocar_fitxa(t, 1, 4);
        assertEquals("La casella hauria de ser blanca", t.getCaselles(1, 4).getEstat(), "blanc");
        assertEquals("La casella hauria de ser blanca", t.getCaselles(2, 4).getEstat(), "blanc");

        //nord-oest
        j1.colocar_fitxa(t, 2, 3);
        assertEquals("La casella hauria de ser negra", t.getCaselles(2, 3).getEstat(), "negre");
        assertEquals("La casella hauria de ser negra", t.getCaselles(3, 4).getEstat(), "negre");
    }
}
*/