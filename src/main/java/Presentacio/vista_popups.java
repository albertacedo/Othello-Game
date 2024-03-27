package Presentacio;

import javax.swing.*;

/**
 * Popups que apareixen en certs parts del programa (errors, info extra)
 */
public class vista_popups extends JPanel {

    /**Frame donde se mostraran los distintos paneles de error, advertencia, éxito, ...     */
    private static final JFrame popups_frame = new JFrame("ERROR");
    /** Instància de la clase*/
    private static vista_popups popups;

    /**
     * Creadora per defecte
     */
    public vista_popups() {

    }

    /**
     * Retorna la instancia de la classe
     * @return Retorna la instancia de la classe
     */
    public static vista_popups getInstance() {
        if (popups == null) {
            popups = new vista_popups();
        }
        return popups;
    }
    /**
     * Mostra una finestra amb el error corresponent
     * @param text Texto de la ventana emergente
     * @param title Titutlo de la ventana emergente
     */
    public static void popup_error(String text, String title) {
        JOptionPane.showMessageDialog(popups_frame,text,title,JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Popup solicitant si l'usuari esta segur de l'accio a realitzar
     * @param texto Indica quina acció volem confirmar
     * @return true si s'ha marcat si, false si no
     */
    public static boolean popup_confirmacio(String texto) {
        Object[] options = {"SI","NO"};
        int n = JOptionPane.showOptionDialog(popups_frame,texto,"Confirmar acció",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
        return n == JOptionPane.YES_OPTION;
    }

    public static void popup_exit(String text, String title) {
        JOptionPane.showMessageDialog(popups_frame,text,title,JOptionPane.INFORMATION_MESSAGE);
    }
/*
    protected void popup_warning(String text, String title) {
        JOptionPane.showMessageDialog(frame,text,title,JOptionPane.WARNING_MESSAGE);
    }

    protected String preguntaNombrePartida() {
        Object nombre = JOptionPane.showInputDialog(frame,"Introduzca el nombre de la partida");
        if(nombre != null) {
            return (String) nombre;
        } else {
            return "";
        }
    }


    protected int showDropdownMessage(final String[] opts, final String title, final String text) {
        Object selected = JOptionPane.showInputDialog(frame,text,title,JOptionPane.QUESTION_MESSAGE,null,opts,opts[0]);
        int ret = -1;
        if(selected instanceof String) {
            String sel = selected.toString();
            for(int i = 0; i < opts.length; i++) {
                if(opts[i].equals(sel)) ret = i;
            }
            return ret;
        }
        return ret;
    }
    */
}