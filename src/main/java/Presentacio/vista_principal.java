package Presentacio;

import javax.swing.*;
import java.awt.*;

import static Presentacio.vista_popups.*;

/**
 * Aquesta vista inclou la pantalla pricipal del programa
 * la pantalla de login i la de creació d'un nou usuari
 * @author Marc Fernandez Palau
 */
public class vista_principal{
    /** Creem el marc per defecte */
    private static final JFrame frame = new JFrame("OTHELLO V1.0");
    /** Declarem el propi controlador */
    private final Controlador_presentacio presentacio;
    /** Panells principals abans de entrar al joc */
    private static JPanel panell_inici;
    private final JPanel panell_login;
    private final JPanel panell_registre;

    public static final Color background = new Color(114, 156, 120);

    /**
     * Inicialitzem la vista i els panells principals abans de poder jugar
     * que son panel inicial (quan obrim el joc), panell de login i panell de registre
     * per tant es pot deduir que nomes es pot jugar al joc amb un compte creat.
     * @param ctrl_presentacio controlador
     */
    public vista_principal(Controlador_presentacio ctrl_presentacio){
        presentacio = ctrl_presentacio;
        //inicialitzacions dels panells
        panell_inici = panell_inici();
        panell_login = panell_login();
        panell_registre = panell_registre();
    }

    /**
     * Definicio de parametres basics del frame
     */
    public void visible(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        Image icono =  Toolkit.getDefaultToolkit().getImage("docs/imatges/logo.png");
        frame.setIconImage(icono);
        frame.add(panell_inici);
        frame.setVisible(true);
    }

    /**
     * Inicialitzacio del JPanel del panell per defecte que surt al obrir el joc
     * es defineixen els aspectes grafics dels botons, titols, etc
     * @return Retorna el panell inici inicialitzat
     */
    private JPanel panell_inici(){
        JPanel inici = new JPanel(new GridBagLayout());
        inici.setBackground(background);
        GridBagConstraints grid = new GridBagConstraints();
        JLabel titol = new JLabel("PLAY OTHELLO!");
        titol.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
        titol.setForeground(Color.BLACK);
        titol.setVerticalAlignment(SwingConstants.TOP);
        titol.setHorizontalAlignment(SwingConstants.CENTER);

        grid.gridy = 0;
        grid.gridx = 1;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.insets = new Insets(20, 25, 70, 25);
        inici.add(titol, grid);

        JButton ini_session = new JButton("Iniciar sessió");
        ini_session.setHorizontalTextPosition(SwingConstants.CENTER);
        ini_session.setVerticalTextPosition(SwingConstants.CENTER);
        ini_session.addActionListener(e -> {
            canvi_panell(panell_inici, panell_login);
        });
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.gridy = 3;
        grid.insets = new Insets(20, 50, 25, 50);
        inici.add(ini_session, grid);

        JButton registre = new JButton("Crear nou usuari");
        registre.setHorizontalTextPosition(SwingConstants.CENTER);
        registre.setVerticalTextPosition(SwingConstants.CENTER);
        registre.addActionListener(e -> {
            canvi_panell(panell_inici, panell_registre);
        });
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.gridy = 7;
        grid.insets = new Insets(20, 50, 25, 50);
        inici.add(registre, grid);

        JButton proves = new JButton("proves");
        proves.setHorizontalTextPosition(SwingConstants.CENTER);
        proves.setVerticalTextPosition(SwingConstants.CENTER);
        proves.addActionListener(e -> {
            //canvi_panell(panell_inici, vista_joc.getPanellUsuari());
            canvi_panell(panell_inici, vista_joc.getPanellUsuari());
        });
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.gridy = 12;
        grid.insets = new Insets(20, 50, 25, 50);
        inici.add(proves, grid);


        inici.setVisible(true);
        return inici;
    }

    /**
     * Inicialitza el panell que ens mostra i gestiona el login
     * @return retorna un JPanel amb la info de login
     */
    private JPanel panell_login(){
        JPanel login = new JPanel(new GridBagLayout());
        login.setBackground(background);
        GridBagConstraints grid = new GridBagConstraints();
        JLabel usuari = new JLabel("Inserta el teu username:");
        usuari.setHorizontalAlignment(SwingConstants.CENTER);
        usuari.setVerticalAlignment(SwingConstants.CENTER);
        grid.gridy=0;
        grid.gridx=1;
        grid.insets = new Insets(20,50,10,50);
        login.add(usuari, grid);

        JTextField username = new JTextField();
        username.setHorizontalAlignment(SwingConstants.LEFT);
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridy=1;
        grid.gridx=1;

        grid.ipady = 20;
        grid.insets = new Insets(0,50,10,50);
        login.add(username, grid);

        JLabel password = new JLabel("Inserta la teva contrasenya:");
        //titol.setFont();
        password.setHorizontalAlignment(SwingConstants.CENTER);
        password.setVerticalAlignment(SwingConstants.CENTER);
        grid.gridy=2;
        grid.gridx=1;
        grid.insets = new Insets(10,50,0,50);
        login.add(password, grid);

        JPasswordField pwd = new JPasswordField();
        //grid.weightx = 0.5;
        grid.gridy=3;
        grid.gridx=1;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.insets = new Insets(0,50,10,50);
        login.add(pwd, grid);

        JButton log_but = new JButton("Iniciar sessió");
        grid.gridy=4;
        grid.gridx=1;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.insets = new Insets(30,50,10,50);
        login.add(log_but, grid);
        log_but.addActionListener(e -> {
            String user = username.getText();
            String pass1 = pwd.getText();
            if(!user.equals("") && !pass1.equals("")) {
                boolean b;
                b = presentacio.login(user, pass1);
                if (b) {
                    vista_popups.popup_exit("Les credencials són correctes, benvingut!", "Acció executada correctament");
                    presentacio.nomUsuari = user;
                    canvi_panell(panell_login, vista_joc.getPanellUsuari());
                }
                else vista_popups.popup_error("Les dades no es corresponen a cap usuari del sistema", "Atenció la seva petició no es pot resoldre");
            }
            else vista_popups.popup_error("Siusplau, omple tots els camps per resoldre la petició", "Atenció la seva petició no es pot resoldre");
        });
        ImageIcon icon = new ImageIcon(new ImageIcon("docs/imatges/return_arrow.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        JButton retorn = new JButton(icon);
        grid.gridy=6;
        grid.gridx=1;
        grid.insets = new Insets(50,350,5,20);
        login.add(retorn, grid);
        retorn.addActionListener(e -> {
            canvi_panell(panell_login,panell_inici);
        });
        login.setVisible(true);
        return login;
    }


    /**
     * Panell que ens mostra i gestiona el login
     * @return retorna un JPanel amb la info de login
     */
    private JPanel panell_registre(){
        JPanel registre = new JPanel(new GridBagLayout());
        registre.setBackground(background);
        GridBagConstraints grid = new GridBagConstraints();
        JLabel usuari = new JLabel("Inserta un username:");
        //titol.setFont();
        usuari.setHorizontalAlignment(SwingConstants.CENTER);
        usuari.setVerticalAlignment(SwingConstants.CENTER);
        grid.gridy=0;
        grid.gridx=1;
        grid.insets = new Insets(20,50,10,50);
        registre.add(usuari, grid);

        JTextField username = new JTextField();
        username.setHorizontalAlignment(SwingConstants.LEFT);
        grid.gridy=1;
        grid.gridx=1;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.insets = new Insets(0,50,10,50);
        registre.add(username, grid);

        JLabel password = new JLabel("Inserta una contrasenya:");
        //titol.setFont();
        password.setHorizontalAlignment(SwingConstants.CENTER);
        password.setVerticalAlignment(SwingConstants.CENTER);
        grid.gridy=2;
        grid.gridx=1;
        grid.insets = new Insets(10,50,0,50);
        registre.add(password, grid);

        JPasswordField pwd = new JPasswordField();
        grid.gridy=3;
        grid.gridx=1;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.insets = new Insets(0,50,10,50);
        registre.add(pwd, grid);

        JLabel password2 = new JLabel("Repeteix la contrasenya:");
        //titol.setFont();
        password2.setHorizontalAlignment(SwingConstants.CENTER);
        password2.setVerticalAlignment(SwingConstants.CENTER);
        grid.gridy=4;
        grid.gridx=1;
        grid.insets = new Insets(10,50,0,50);
        registre.add(password2, grid);

        JPasswordField pwd2 = new JPasswordField();
        grid.gridy=5;
        grid.gridx=1;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.insets = new Insets(0,50,10,50);
        registre.add(pwd2, grid);

        JButton reg_but = new JButton("Registrar-me");
        grid.gridy=6;
        grid.gridx=1;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.insets = new Insets(30,50,10,50);
        registre.add(reg_but, grid);
        reg_but.addActionListener(e -> {
            String user = username.getText();
            String pass1 = pwd.getText();
            String pass2 = pwd2.getText();
            if(!user.equals("") && !pass1.equals("")) {
                if(pass1.equals(pass2)){
                    boolean b = presentacio.new_user(user, pass1);
                    if (b) {
                        canvi_panell(panell_registre, vista_joc.getPanellUsuari());
                        vista_popups.popup_exit("El seu usuari s'ha creat i guardat a la base de dades amb exit", "Acció executada correctament");
                    }
                    else vista_popups.popup_error("El usuari ja existeix, esculli un username diferent", "Atenció la seva petició no es pot resoldre");
                }
                else vista_popups.popup_error("Siusplau revisi que les contrasenyes coincideixen", "Atenció la seva petició no es pot resoldre");
            }
            else vista_popups.popup_error("Format de les credencials incorrecte", "Atenció la seva petició no es pot resoldre");
        });
        ImageIcon icon = new ImageIcon(new ImageIcon("docs/imatges/return_arrow.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        JButton retorn = new JButton(icon);
        grid.gridy=7;
        grid.gridx=1;
        grid.insets = new Insets(50,350,5,20);
        registre.add(retorn, grid);
        retorn.addActionListener(e -> {
            canvi_panell(panell_registre, panell_inici);
        });
        registre.setVisible(true);
        return registre;
    }

    /**
     *
     * @return
     */
    public static JPanel getPanellinici() {
        return panell_inici;
    }

    public static Color background_color(){
        return background;
    }

    /**
     * Gestiona el canvi de pantalles entre els diferents panells defnits en el joc
     * @param antic Jpanel que volem canviar
     * @param nou Jpanel que volem que surti en pantalla
     */
    public static void canvi_panell(JPanel antic, JPanel nou){
        if(antic != nou){
            antic.setVisible(false);
            nou.setVisible(true);
            frame.remove(antic);
            frame.add(nou);
        }
    }
}
