package principal;

import java.awt.FlowLayout;
import java.awt.TextField;
import javafx.scene.control.RadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

/**
 * @author David Carrera Otero
 */
public class Configuracion extends JDialog{
    TextField txtName;
    JComboBox<String>cmBox;
    JRadioButton rdColor1,rdColor2;
    ButtonGroup gbText,gbColor;
    
    public Configuracion(Principal p){
            //booleana de super indica si es modal(true) o no molda(false)
        super(p,false);
        setLayout(new FlowLayout());
        setTitle("Configuración");
        
    }
}