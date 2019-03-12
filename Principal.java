package principal;

import java.awt.Color;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javax.sound.midi.SysexMessage;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

/**
 * @author David Carrera Otero - Imagenes de los labels - utilizar bien el
 * evento y adaptadores ( necesita heredar) cuando llegue a 0 tiene que
 * cerrarse.
 *
 */
public class Principal extends JFrame implements ActionListener {

    int numVida = 5;
    JLabel lbletiqueta, lblVidas;
    JMenuBar mnuPrincipal;
    JMenu mnuOpcion;
    JMenuItem mnuItemConfiguracion, mnuItemVerRecords, mnuItemNuevoJuego;
    Configuracion frmConfiguracion;
    VerRecords frmVerRecords;

    public Principal() {
        //Título del formulario
        super("Wack-AA-ABob");
        //Layout
        this.setLayout(null);
        //5 labels de forma dinamica por bucle
        crearLabelsDinamicamente(5);
        //Mensaje que saldra al salir
        mensajeAlSalir();
        //Menu del formulario
        Menu();
        //Etiqueta Vida
        lblVidas = new JLabel("Vidas: " + numVida);
        lblVidas.setBounds(700, 320, 60, 10);
        this.add(lblVidas);
        frmConfiguracion = new Configuracion(this);
        frmVerRecords = new VerRecords(this);
    }

    public void ActualizacionDeVidas() {
        if (numVida > 0) {
            numVida--;
        }
        if (numVida == 0) {
            this.dispose();
        }
    }

    public void crearLabelsDinamicamente(int numLabels) {
        int posX = 50;
        int posY = 0;
        for (int i = 0; i < numLabels; i++) {
            lbletiqueta = new JLabel("Etiqueta" + i);
            //--Ojo al hacer el tamaño por defecto--//
            //etiqueta.setSize(etiqueta.getPreferredSize());
            lbletiqueta.setSize(170, 250);
            lbletiqueta.setLocation(posX, posY);
            posX = posX + 200;
            this.add(lbletiqueta);
        }
    }

    public void mensajeAlSalir() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int eleccion = JOptionPane.showConfirmDialog(null, "¿Quieres salir del programa?",
                        "Mensaje Informativo", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (eleccion == JOptionPane.OK_OPTION) {
                    e.getWindow().dispose();
                }
            }
        });
    }

    public void GuardarDatosColores() {
        if (frmConfiguracion.rdColor1.isSelected()) {
            this.getContentPane().setBackground(Color.magenta);
        } else if (frmConfiguracion.rdColor2.isSelected()) {
            this.getContentPane().setBackground(Color.orange);
        }
    }

    public void GuardarDatos() {
        frmConfiguracion.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                lblVidas.setText("Vidas: " + frmConfiguracion.cmBox.getSelectedItem());
                GuardarDatosColores();
            }
        });
    }

    public void Menu() {
        mnuPrincipal = new JMenuBar();
        //Para que el menu sea el principal y no componente del formulario
        setJMenuBar(mnuPrincipal);

        //Opcion del menu "Juego"
        mnuOpcion = new JMenu("Juego");
        //Añadir al menu no al formulario
        mnuOpcion.setMnemonic('J');
        mnuPrincipal.add(mnuOpcion);

        //Items de la opcion "Juego"
        mnuItemNuevoJuego = new JMenuItem("Nuevo Juego");
        mnuItemNuevoJuego.setMnemonic('N');
        mnuItemNuevoJuego.addActionListener(this);
        mnuOpcion.add(mnuItemNuevoJuego);
        //SeparadorDeItems
        mnuOpcion.addSeparator();

        mnuItemConfiguracion = new JMenuItem("Configuración");
        mnuItemConfiguracion.setMnemonic('C');
        mnuItemConfiguracion.addActionListener(this);
        mnuOpcion.add(mnuItemConfiguracion);

        mnuItemVerRecords = new JMenuItem("Ver Récords");
        mnuItemVerRecords.setMnemonic('V');
        mnuItemVerRecords.addActionListener(this);
        mnuOpcion.add(mnuItemVerRecords);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == (mnuItemConfiguracion)) {
            CaracteristicasFrmConfiguracion();
            componentesFrmConfiguracion();
        } else if (e.getSource() == (mnuItemVerRecords)) {
            try {
                ComponentesVerRecords();
            } catch (FileNotFoundException ex) {
                System.err.println("Error de lector de archivos");
            }
            CaracteristicasFrmVerRecords();
        } else if (e.getSource() == (mnuItemNuevoJuego)) {
        }
        //PONER UTILIDAD AL BOTON DE ELIMINAR
    }

    boolean unaVez = true;

    public void componentesFrmConfiguracion() {
        String[] numeros = {"1", "2", "3", "4",
            "5", "6", "7", "8",
            "9", "10"};
        if (unaVez) {
            //GuardarConfiguracion
            GuardarDatos();
            //TextField
            frmConfiguracion.txtName = new TextField();
            frmConfiguracion.add(frmConfiguracion.txtName);
            //Combobox
            frmConfiguracion.cmBox = new JComboBox<>(numeros);
            frmConfiguracion.cmBox.setMaximumRowCount(9);
            frmConfiguracion.cmBox.setSelectedIndex(2);
            frmConfiguracion.add(frmConfiguracion.cmBox);
            //RadioButtons Color 1

            frmConfiguracion.rdColor1 = new JRadioButton("Magenta");
            frmConfiguracion.rdColor1.setForeground(Color.MAGENTA);
            frmConfiguracion.add(frmConfiguracion.rdColor1);

            frmConfiguracion.rdColor2 = new JRadioButton("Naranja");
            frmConfiguracion.rdColor2.setForeground(Color.orange);
            frmConfiguracion.add(frmConfiguracion.rdColor2);
            //GroupButton
            frmConfiguracion.gbText = new ButtonGroup();
            frmConfiguracion.gbText.add(frmConfiguracion.rdColor1);
            frmConfiguracion.gbText.add(frmConfiguracion.rdColor2);
            unaVez = false;
        }
    }

    DefaultListModel<String> recordsMatch = new DefaultListModel<>();

    public void LecturaArchivo() throws FileNotFoundException {
        String directorio = System.getProperty("user.home");
        File f = new File(directorio + "\\Records.txt");
        if (f.exists()) {
            Scanner sc = new Scanner(f);
            while (sc.hasNext()) {
                String frase = sc.nextLine();
                recordsMatch.addElement(frase);
            }
        }
    }

    boolean unaVezRecords = true;

    public void ComponentesVerRecords() throws FileNotFoundException {

        if (unaVezRecords) {

            //JList
            frmVerRecords.lista = new JList(recordsMatch);
            frmVerRecords.lista.setVisibleRowCount(6);
            frmVerRecords.lista.setSelectionMode(2);
            frmVerRecords.lista.setBounds(20, 20, 50, 50);
            frmVerRecords.add(frmVerRecords.lista);
            LecturaArchivo();

            //JButton EliminarRecords
            frmVerRecords.Eliminar = new JButton("Eliminar");
            frmVerRecords.Eliminar.setBounds(20, 20, 40, 40);
            frmVerRecords.add(frmVerRecords.Eliminar);
        }
    }

    public void CaracteristicasFrmConfiguracion() {
        frmConfiguracion.setBounds(50, 50, 600, 170);
        frmConfiguracion.setVisible(true);
    }

    public void CaracteristicasFrmVerRecords() {
        frmVerRecords.setBounds(50, 50, 200, 200);
        frmVerRecords.setVisible(true);

    }

    public static void main(String[] args) {
        //Formulario denominado "Principal"
        Principal p = new Principal();
        //Tamaño y lugar de aparición del formulario.
        p.setBounds(20, 20, 1000, 400);
        //--Ojo cerrar de forma manual y que suelte cuadro de dialogo--//
        p.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        p.setVisible(true);
    }

    public static class ComponenteVerde extends MouseAdapter { //Duda con clases internas y como llamar a esta funcion dandole un evento

        public void mostrar() {
            System.out.println("Hola");
        }

        @Override
        public void mouseMoved(MouseEvent event) {
            //TODO
            System.out.println("Mouse movement detected! Actual mouse position is: " + event.getX() + "," + event.getY() + ".");
        }
    }
}
