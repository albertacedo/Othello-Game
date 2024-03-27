package Presentacio;

import domini.Casella;
import domini.Controlador_domini;
import domini.Tauler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Boto_tauler extends JButton implements ActionListener {
    int x, y, idfitxa;

    Boto_tauler(int x, int y, Icon fitxa, int idfitxa) {
        super(fitxa);
        this.x = x;
        this.y = y;
        this.idfitxa = idfitxa;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean b = false;
        List<Casella> l = Controlador_domini.on_puc_tirar(Controlador_domini.getPartida_en_curs().getTorn());
        if (!l.isEmpty()) {
            for (Casella c : l) {
                if (c.getPos_x() == this.x && c.getPos_y() == this.y) {
                    b = Controlador_presentacio.moure_fitxa(this.x, this.y, Controlador_domini.getPartida_en_curs().getTorn());
                    //vista_joc.actualitzar_partida();
                    Controlador_presentacio.actualitzar_tauler();
                    break;
                }
            }
            if (!b)
                vista_popups.popup_error("Casella invalida, aquí no pots tirar", "Atenció la seva petició no es pot resoldre");
        }
        else Controlador_presentacio.ficolorp(true, Controlador_domini.getPartida_en_curs().getTorn());
    }


}
