package Presentacio;

import com.sun.management.UnixOperatingSystemMXBean;
import domini.Controlador_domini;
import domini.Tauler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Aquesta classe configura els JPanels encarregats de la vista
 */
public class vista_joc extends JPanel {
    boolean myTurn = true;
    /** Creem el marc per defecte */
    private final Controlador_presentacio presentacio;
    /** Panells de joc */
    private static JPanel panell_usuari, panell_setup_joc, panell_partida, panell_setup_1ia, panell_setup_ias, panell_guardapartida;
    static Icon fitxa_blanca = new ImageIcon("docs/imatges/fitxablanca.png");
    static Icon fitxa_negre = new ImageIcon("docs/imatges/fitxanegre.png");

    /**
     * Inicialitzem la vista i els panells corresponents a "quan ja estem
     * dins el joc" (es a dir loguejats), aquesta vista s'encarregarà de mostrar
     * la resta de panells del programa
     * @param ctrl_presentacio
     */
    public vista_joc(Controlador_presentacio ctrl_presentacio){
        presentacio = ctrl_presentacio;
        //inicialitzacions dels panells
        panell_usuari = panell_usuari();
        panell_setup_joc = panell_setup_joc();
        panell_partida = panell_partida();
        panell_setup_1ia = panell_setup1ia();
        panell_setup_ias = panell_setup_ia_vs_ia();
        panell_guardapartida = panell_guardapartida();
    }


    /**
     * Primer panell que ens mostra quan ens hem loguejat
     */
    private static JPanel panell_usuari(){
        JPanel usuari = new JPanel(new GridBagLayout());
        usuari.setBackground(vista_principal.background_color());
        GridBagConstraints grid = new GridBagConstraints();

        JButton jugar = new JButton("Jugar");
        jugar.setHorizontalTextPosition(SwingConstants.CENTER);
        jugar.setVerticalTextPosition(SwingConstants.CENTER);
        jugar.addActionListener(e -> {
            vista_principal.canvi_panell(panell_usuari, panell_setup_joc);
        });
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridy = 0;
        grid.gridx = 1;
        grid.ipady = 20;
        grid.insets = new Insets(20, 50, 25, 50);
        usuari.add(jugar, grid);

        JButton registre = new JButton("Crear taulell");
        registre.setHorizontalTextPosition(SwingConstants.CENTER);
        registre.setVerticalTextPosition(SwingConstants.CENTER);
        registre.addActionListener(e -> {
            //vista_principal.canvi_panell(panell_usuari, panell_setup_joc);
        });
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.gridy = 2;
        grid.gridx = 1;
        grid.insets = new Insets(0, 50, 25, 50);
        usuari.add(registre, grid);

        JButton ranq_i = new JButton("Ranquing Imposible");
        ranq_i.setHorizontalTextPosition(SwingConstants.CENTER);
        ranq_i.setVerticalTextPosition(SwingConstants.CENTER);
        ranq_i.addActionListener(e -> {
            //vista_principal.canvi_panell(panell_usuari, panell_setup_joc);
        });
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.gridy = 3;
        grid.gridx = 1;
        grid.insets = new Insets(0, 50, 25, 50);
        usuari.add(ranq_i, grid);

        JButton ranq_h = new JButton("Ranquing Hard");
        ranq_h.setHorizontalTextPosition(SwingConstants.CENTER);
        ranq_h.setVerticalTextPosition(SwingConstants.CENTER);
        ranq_h.addActionListener(e -> {
            //vista_principal.canvi_panell(panell_usuari, panell_setup_joc);
        });
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.gridy = 4;
        grid.gridx = 1;
        grid.insets = new Insets(0, 50, 25, 50);
        usuari.add(ranq_h, grid);

        JButton ranq_n = new JButton("Ranquing Normal");
        ranq_n.setHorizontalTextPosition(SwingConstants.CENTER);
        ranq_n.setVerticalTextPosition(SwingConstants.CENTER);
        ranq_n.addActionListener(e -> {
            //vista_principal.canvi_panell(panell_usuari, panell_setup_joc);
        });
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.gridy = 5;
        grid.gridx = 1;
        grid.insets = new Insets(0, 50, 25, 50);
        usuari.add(ranq_n, grid);

        JButton ranq_e = new JButton("Ranquing Easy");
        ranq_e.setHorizontalTextPosition(SwingConstants.CENTER);
        ranq_e.setVerticalTextPosition(SwingConstants.CENTER);
        ranq_e.addActionListener(e -> {
            //vista_principal.canvi_panell(panell_usuari, panell_setup_joc);
        });

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.gridy = 6;
        grid.gridx = 1;
        grid.insets = new Insets(0, 50, 25, 50);
        usuari.add(ranq_e, grid);
/*
        JButton registre = new JButton("Records");
        registre.setHorizontalTextPosition(SwingConstants.CENTER);
        registre.setVerticalTextPosition(SwingConstants.CENTER);
        registre.addActionListener(e -> {
            //vista_principal.canvi_panell(panell_usuari, panell_setup_joc);
        });
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.gridy = 7;
        grid.insets = new Insets(20, 50, 25, 50);
        usuari.add(registre, grid);

 */

        JButton unlog = new JButton("Exit");
        unlog.setHorizontalTextPosition(SwingConstants.CENTER);
        unlog.setVerticalTextPosition(SwingConstants.CENTER);
        unlog.addActionListener(e -> {
            if(vista_popups.popup_confirmacio("Estàs segur que vol sortir del teu compte?")){
                vista_principal.canvi_panell(panell_usuari, vista_principal.getPanellinici());
            }
        });

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.gridy = 8;
        grid.gridx = 1;
        grid.insets = new Insets(50, 350, 5, 20);
        usuari.add(unlog, grid);
        usuari.setVisible(true);
        return usuari;
    }


    /**
     * Primer panell que ens mostra quan ens hem loguejat
     */
    private JPanel panell_setup_joc(){
        JPanel setup_joc = new JPanel(new GridBagLayout());
        setup_joc.setBackground(vista_principal.background_color());
        GridBagConstraints grid = new GridBagConstraints();

        JButton h_vs_m = new JButton("Humà VS Màquina");
        h_vs_m.setHorizontalTextPosition(SwingConstants.CENTER);
        h_vs_m.setVerticalTextPosition(SwingConstants.CENTER);
        h_vs_m.addActionListener(e -> {
            vista_principal.canvi_panell(panell_setup_joc, panell_setup_1ia);
        });
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.gridx = 1;
        grid.gridy = 0;
        grid.insets = new Insets(20, 50, 25, 50);
        setup_joc.add(h_vs_m, grid);

        JButton h_vs_h = new JButton("Humà VS Humà");
        h_vs_h.setHorizontalTextPosition(SwingConstants.CENTER);
        h_vs_h.setVerticalTextPosition(SwingConstants.CENTER);
        h_vs_h.addActionListener(e -> {
            if(Controlador_presentacio.iniciar_partida(1, null, null)) {
                vista_principal.canvi_panell(panell_setup_joc, panell_partida);
            }
            else vista_popups.popup_error("Actualment hi ha una partida ja començada", "Atenció la seva petició no es pot resoldre");
        });
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.gridy = 1;
        grid.gridx = 1;
        grid.insets = new Insets(20, 50, 25, 50);
        setup_joc.add(h_vs_h, grid);

        JButton m_vs_m = new JButton("Màquina VS Màquina");
        m_vs_m.setHorizontalTextPosition(SwingConstants.CENTER);
        m_vs_m.setVerticalTextPosition(SwingConstants.CENTER);
        m_vs_m.addActionListener(e -> {
            vista_principal.canvi_panell(panell_setup_joc, panell_setup_ias);
        });
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.gridy = 2;
        grid.gridx = 1;
        grid.insets = new Insets(20, 50, 25, 50);
        setup_joc.add(m_vs_m, grid);

        JButton cargar = new JButton("Carregar Partida");
        cargar.setHorizontalTextPosition(SwingConstants.CENTER);
        cargar.setVerticalTextPosition(SwingConstants.CENTER);
        cargar.addActionListener(e -> {
            //vista_principal.canvi_panell(panell_setup_joc, );
        });
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.gridy = 3;
        grid.gridx = 1;
        grid.insets = new Insets(20, 50, 25, 50);
        setup_joc.add(cargar, grid);

        setup_joc.setVisible(true);
        return setup_joc;
    }

    public JPanel panell_partida(){
        JPanel partida = new JPanel(new GridBagLayout());
        partida.setBackground(vista_principal.background_color());
        GridBagConstraints grid = new GridBagConstraints();
        JPanel tauler = new JPanel();
        tauler.setLayout(new GridLayout(8,8));
        Boto_tauler b[][] = new Boto_tauler[8][8];
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if (i==3 && j==4 || i==4 && j==3) {
                    b[i][j] = new Boto_tauler(i, j, fitxa_negre, 1);
                } else if (i==3 && j==3 || i==4 && j==4) {
                    b[i][j] = new Boto_tauler(i, j, fitxa_blanca, 2);
                } else b[i][j] = new Boto_tauler(i, j, null, 0);
                b[i][j].setBackground(new Color(0, 143, 103));
                tauler.add(b[i][j]);
            }
        }

        partida.add(tauler);

        JLabel punts1 = new JLabel("Punts NEGRE:");
        punts1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 0;
        grid.gridy = 1;
        grid.insets = new Insets(5, 0, 5, 10);
        partida.add(punts1, grid);
        JLabel punts11 = new JLabel("2");
        punts11.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 0;
        grid.gridy = 1;
        grid.insets = new Insets(5, 120, 5, 10);
        partida.add(punts11, grid);

        JLabel punts2 = new JLabel("Punts Blanc:");
        punts2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 0;
        grid.gridy = 2;
        grid.insets = new Insets(5, 0, 5, 10);
        partida.add(punts2, grid);

        JLabel punts22 = new JLabel("2");
        punts22.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 0;
        grid.gridy = 2;
        grid.insets = new Insets(5, 120, 5, 10);
        partida.add(punts22, grid);

        JLabel torn = new JLabel("torn:");
        torn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 0;
        grid.gridy = 3;
        grid.insets = new Insets(5, 0, 5, 10);
        partida.add(torn, grid);

        JLabel torn1 = new JLabel("negre");
        torn1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 0;
        grid.gridy = 3;
        grid.insets = new Insets(5, 120, 5, 10);
        partida.add(torn1, grid);
        partida.setVisible(true);
        return partida;

    }

    /**
     * Primer panell que ens mostra quan ens hem loguejat
     */
    private JPanel panell_setup1ia(){
        JPanel setup_1ia = new JPanel(new GridBagLayout());
        setup_1ia.setBackground(vista_principal.background_color());
        GridBagConstraints grid = new GridBagConstraints();
        JLabel titol = new JLabel("Escull la dificultat:");
        titol.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        titol.setForeground(Color.BLACK);
        titol.setVerticalAlignment(SwingConstants.TOP);

        grid.gridy = 0;
        grid.gridx = 0;
        //grid.fill = GridBagConstraints.HORIZONTAL;
        grid.insets = new Insets(0, 0, 30, 0);
        setup_1ia.add(titol, grid);

        JLabel text1 = new JLabel("Escull dificultat màquina:");
        text1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        text1.setHorizontalAlignment(SwingConstants.LEADING);

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 0;
        grid.gridy = 1;
        grid.insets = new Insets(5, 0, 5, 10);
        setup_1ia.add(text1, grid);

        ButtonGroup dificultat = new ButtonGroup();
        JRadioButton facil = new JRadioButton("Fàcil");
        facil.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        facil.setSelected(true);
        grid.gridx = 0;
        grid.gridy = 2;
        grid.insets = new Insets(5, 5, 10, 5);
        setup_1ia.add(facil, grid);
        JRadioButton mitjana = new JRadioButton("Mitja");
        mitjana.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        grid.gridx = 1;
        grid.gridy = 2;
        grid.insets = new Insets(5, 0, 10, 5);
        setup_1ia.add(mitjana, grid);
        JRadioButton difícil = new JRadioButton("Difícil");
        difícil.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        grid.gridx = 2;
        grid.gridy = 2;
        grid.insets = new Insets(5, 0, 10, 5);
        setup_1ia.add(difícil, grid);
        JRadioButton impossible = new JRadioButton("Impossible");
        impossible.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        grid.gridx = 3;
        grid.gridy = 2;
        grid.insets = new Insets(5, 0, 10, 5);
        setup_1ia.add(impossible, grid);
        dificultat.add(facil);
        dificultat.add(mitjana);
        dificultat.add(difícil);
        dificultat.add(impossible);

        JButton cargar = new JButton("Iniciar partida");
        cargar.setHorizontalTextPosition(SwingConstants.CENTER);
        cargar.setVerticalTextPosition(SwingConstants.CENTER);
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.gridy = 3;
        grid.gridx = 1;
        grid.insets = new Insets(20, 50, 25, 50);
        setup_1ia.add(cargar, grid);

        cargar.addActionListener(actionEvent -> {
            if(facil.isSelected()){
                Controlador_presentacio.iniciar_partida(2, null, "easy");
                vista_principal.canvi_panell(panell_setup_1ia, panell_partida);
            }
            else if(mitjana.isSelected()){
                Controlador_presentacio.iniciar_partida(2, null, "normal");
                vista_principal.canvi_panell(panell_setup_1ia, panell_partida);
            }
            else if (difícil.isSelected()){
                Controlador_presentacio.iniciar_partida(2, null, "hard");
                vista_principal.canvi_panell(panell_setup_1ia, panell_partida);
            }
            else if (impossible.isSelected()){
                Controlador_presentacio.iniciar_partida(2, null, "impossible");
                vista_principal.canvi_panell(panell_setup_1ia, panell_partida);
            }
            else {
                vista_popups.popup_error("Selecciona una opció", "Atenció la seva petició no es pot resoldre");
            }
        });
        setup_1ia.setVisible(true);
        return setup_1ia;
    }

    /**
     * Primer panell que ens mostra quan ens hem loguejat
     */
    private JPanel panell_setup_ia_vs_ia(){
        JPanel setup_ias = new JPanel(new GridBagLayout());
        setup_ias.setBackground(vista_principal.background_color());
        GridBagConstraints grid = new GridBagConstraints();
        JLabel titol = new JLabel("Escull la dificultat:");
        titol.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        titol.setForeground(Color.BLACK);
        titol.setVerticalAlignment(SwingConstants.TOP);

        grid.gridy = 0;
        grid.gridx = 0;
        //grid.fill = GridBagConstraints.HORIZONTAL;
        grid.insets = new Insets(0, 0, 30, 0);
        setup_ias.add(titol, grid);

        JLabel text1 = new JLabel("Escull dificultat màquina 1:");
        text1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        text1.setHorizontalAlignment(SwingConstants.LEADING);

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 0;
        grid.gridy = 1;
        grid.insets = new Insets(5, 0, 5, 10);
        setup_ias.add(text1, grid);

        ButtonGroup dificultat = new ButtonGroup();
        JRadioButton facil = new JRadioButton("Fàcil");
        facil.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        facil.setSelected(true);
        grid.gridx = 0;
        grid.gridy = 2;
        grid.insets = new Insets(5, 5, 10, 5);
        setup_ias.add(facil, grid);
        JRadioButton mitjana = new JRadioButton("Mitja");
        mitjana.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        grid.gridx = 1;
        grid.gridy = 2;
        grid.insets = new Insets(5, 0, 10, 5);
        setup_ias.add(mitjana, grid);
        JRadioButton difícil = new JRadioButton("Difícil");
        difícil.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        grid.gridx = 2;
        grid.gridy = 2;
        grid.insets = new Insets(5, 0, 10, 5);
        setup_ias.add(difícil, grid);
        JRadioButton impossible = new JRadioButton("Impossible");
        impossible.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        grid.gridx = 3;
        grid.gridy = 2;
        grid.insets = new Insets(5, 0, 10, 5);
        setup_ias.add(impossible, grid);
        dificultat.add(facil);
        dificultat.add(mitjana);
        dificultat.add(difícil);
        dificultat.add(impossible);

        JLabel text2 = new JLabel("Escull dificultat màquina 2:");
        text2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        text2.setHorizontalAlignment(SwingConstants.LEADING);

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 0;
        grid.gridy = 3;
        grid.insets = new Insets(5, 0, 5, 10);
        setup_ias.add(text2, grid);

        ButtonGroup dificultat2 = new ButtonGroup();
        JRadioButton facil2 = new JRadioButton("Fàcil");
        facil2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        facil2.setSelected(true);
        grid.gridx = 0;
        grid.gridy = 4;
        grid.insets = new Insets(5, 5, 10, 5);
        setup_ias.add(facil2, grid);
        JRadioButton mitjana2 = new JRadioButton("Mitja");
        mitjana2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        grid.gridx = 1;
        grid.gridy = 4;
        grid.insets = new Insets(5, 0, 10, 5);
        setup_ias.add(mitjana2, grid);
        JRadioButton difícil2 = new JRadioButton("Difícil");
        difícil2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        grid.gridx = 2;
        grid.gridy = 4;
        grid.insets = new Insets(5, 0, 10, 5);
        setup_ias.add(difícil2, grid);
        JRadioButton impossible2 = new JRadioButton("Impossible");
        impossible2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        grid.gridx = 3;
        grid.gridy = 4;
        grid.insets = new Insets(5, 0, 10, 5);
        setup_ias.add(impossible2, grid);
        dificultat2.add(facil2);
        dificultat2.add(mitjana2);
        dificultat2.add(difícil2);
        dificultat2.add(impossible2);

        JButton cargar = new JButton("Iniciar partida");
        cargar.setHorizontalTextPosition(SwingConstants.CENTER);
        cargar.setVerticalTextPosition(SwingConstants.CENTER);
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.gridy = 5;
        grid.gridx = 1;
        grid.insets = new Insets(20, 50, 25, 50);
        setup_ias.add(cargar, grid);

        cargar.addActionListener(actionEvent -> {
            String dif1 = null, dif2 = null;
            if(facil.isSelected()){
                dif1="easy";
            }
            else if(mitjana.isSelected()){
                dif1="normal";
            }
            else if (difícil.isSelected()){
                dif1="hard";
            }
            else if (impossible.isSelected()){
                dif1="impossible";
            }
            if(facil2.isSelected()){
                dif2="easy";
            }
            else if(mitjana2.isSelected()){
                dif2="normal";
            }
            else if (difícil2.isSelected()){
                dif2="hard";
            }
            else if (impossible2.isSelected()){
                dif2="impossible";
            }
            else {
                vista_popups.popup_error("Selecciona una opció", "Atenció la seva petició no es pot resoldre");
            }
            Controlador_presentacio.iniciar_partida(2, dif1, dif2);
            vista_principal.canvi_panell(panell_setup_1ia, panell_partida);
        });

        setup_ias.setVisible(true);
        return setup_ias;
    }

    /**
     * Panell per guardar partides
     */
    private JPanel panell_guardapartida() {
        JPanel guardapartida = new JPanel(new GridBagLayout());
        guardapartida.setBackground(vista_principal.background_color());
        GridBagConstraints grid = new GridBagConstraints();
        JTextField adversari = new JTextField();

        if (!(presentacio.esContrincantUnaIA)) {
            JLabel comesdiu = new JLabel("Escriu el nom del teu adversari:");
            comesdiu.setHorizontalAlignment(SwingConstants.CENTER);
            comesdiu.setVerticalAlignment(SwingConstants.CENTER);
            grid.gridy=0;
            grid.gridx=1;
            grid.insets = new Insets(20,50,10,50);
            guardapartida.add(comesdiu, grid);

            adversari.setHorizontalAlignment(SwingConstants.LEFT);
            grid.gridy=1;
            grid.gridx=1;
            grid.fill = GridBagConstraints.HORIZONTAL;
            grid.ipady = 20;
            grid.insets = new Insets(0,50,10,50);
            guardapartida.add(adversari, grid);
        }

        JButton ini_session = new JButton("Guardar");
        ini_session.setHorizontalTextPosition(SwingConstants.CENTER);
        ini_session.setVerticalTextPosition(SwingConstants.CENTER);
        ini_session.addActionListener(e -> {
            if (!(presentacio.esContrincantUnaIA)) presentacio.nomContrincant = adversari.getText();
            if ((Controlador_domini.getPartida_en_curs().getTorn()).equals(presentacio.colorUsuari)) presentacio.esTornUsuari = true;
            else presentacio.esTornUsuari = false;
            presentacio.tauler = Controlador_domini.getPartida_en_curs().get_taulell();
            presentacio.guardar_partida();
            vista_principal.canvi_panell(panell_guardapartida, panell_partida);
        });
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.gridy = 2;
        grid.gridx=1;
        grid.insets = new Insets(20, 50, 25, 50);
        guardapartida.add(ini_session, grid);

        guardapartida.setVisible(true);
        return guardapartida;
    }


    public static void actualitzar_partida(){
        JPanel partida = new JPanel(new GridBagLayout());
        partida.setBackground(vista_principal.background_color());
        GridBagConstraints grid = new GridBagConstraints();
        JPanel tauler = new JPanel();
        tauler.setLayout(new GridLayout(8,8));
        Boto_tauler b[][] = new Boto_tauler[8][8];
        Tauler t = Controlador_domini.getPartida_en_curs().get_taulell();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (t.getCaselles(i, j).getEstat().equals("blanc"))
                    b[i][j] = new Boto_tauler(i, j, fitxa_blanca, 2);
                else if (t.getCaselles(i, j).getEstat().equals("negre"))
                    b[i][j] = new Boto_tauler(i, j, fitxa_negre, 1);
                else b[i][j] = new Boto_tauler(i, j, null, 0);

                b[i][j].setBackground(new Color(0, 143, 103));
                tauler.add(b[i][j]);
            }
        }

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.gridy = 1;
        grid.gridx = 1;
        grid.insets = new Insets(20, 50, 25, 50);
        partida.add(tauler);
        JLabel punts1 = new JLabel("Punts NEGRE:");
        punts1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 0;
        grid.gridy = 1;
        grid.insets = new Insets(5, 0, 5, 10);
        partida.add(punts1, grid);
        int intArray[] = Controlador_presentacio.getpuntspartida();
        JLabel punts11 = new JLabel(String.valueOf(intArray[0]));
        punts11.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 0;
        grid.gridy = 1;
        grid.insets = new Insets(5, 120, 5, 10);
        partida.add(punts11, grid);

        JLabel punts2 = new JLabel("Punts Blanc:");
        punts2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 0;
        grid.gridy = 2;
        grid.insets = new Insets(5, 0, 5, 10);
        partida.add(punts2, grid);

        JLabel punts22 = new JLabel(String.valueOf(intArray[1]));
        punts22.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 0;
        grid.gridy = 2;
        grid.insets = new Insets(5, 120, 5, 10);
        partida.add(punts22, grid);

        JLabel torn = new JLabel("torn:");
        torn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 0;
        grid.gridy = 3;
        grid.insets = new Insets(5, 0, 5, 10);
        partida.add(torn, grid);

        JLabel torn1 = new JLabel(Controlador_presentacio.gettornpartida());
        torn1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 0;
        grid.gridy = 3;
        grid.insets = new Insets(5, 120, 5, 10);
        partida.add(torn1, grid);

        if(Controlador_presentacio.partida_acabada()){
            vista_popups.popup_exit("Partida finalitzada", "Partida finalitzada");
            vista_principal.canvi_panell(partida, panell_usuari);
        }


        JButton guardar = new JButton("Guardar Partida");
        guardar.setHorizontalTextPosition(SwingConstants.CENTER);
        guardar.setVerticalTextPosition(SwingConstants.CENTER);
        guardar.addActionListener(e -> {
            vista_principal.canvi_panell(panell_partida, panell_guardapartida);
        });
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.ipady = 20;
        grid.gridy = 2;
        grid.gridx = 0;
        grid.insets = new Insets(20, 50, 25, 50);
        partida.add(guardar, grid);
        partida.setVisible(true);
        vista_principal.canvi_panell(panell_partida, partida);
        panell_partida = partida;
    }

    public static JPanel getPanellUsuari() {
        return panell_usuari;
    }

    public static JPanel getPanell_partida() {
        return panell_partida;
    }
/*
    public static void main(String[] args) {
        panell_usuari();
        JFrame jprova = new JFrame();
        jprova.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jprova.setSize(600, 600);
        jprova.setLocationRelativeTo(null);
        jprova.add(panell_usuari);
        jprova.setVisible(true);
    }
    */
}
