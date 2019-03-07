package recup1eva;

import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javafx.scene.control.ComboBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * @author David Carrera Otero - Imagenes de los labels 
 * - utilizar bien el evento y adaptadores ( necesita heredar) cuando llegue a 0 tiene que
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
        lblVidas.setBounds(700, 320, 50, 10);
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
            CaracteristicasFrmVerRecords();
        } else if(e.getSource() == (mnuItemNuevoJuego)){
            
        }
    }
    
    public void componentesFrmConfiguracion(){
        String [] numeros = {"1","2","3","4",
                              "5","6","7","8",
                                "9","10"};
        //TextField
        frmConfiguracion.txtName = new TextField();
        frmConfiguracion.add(frmConfiguracion.txtName);
        //Combobox
        frmConfiguracion.cmBox = new JComboBox<String>(numeros);
        frmConfiguracion.cmBox.setMaximumRowCount(9);
        frmConfiguracion.cmBox.setSelectedIndex(2);
        frmConfiguracion.add(frmConfiguracion.cmBox);
    }

    @Override
    public void addKeyListener(KeyListener l) {
        
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

}
