package recup1eva;

import java.awt.FlowLayout;
import java.awt.TextField;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * @author David Carrera Otero
 */
public class Configuracion extends JDialog{
    TextField txtName;
    JComboBox<String>cmBox;
    
    public Configuracion(Principal p){
            //booleana de super indica si es modal(true) o no molda(false)
        super(p,false);
        setLayout(new FlowLayout());
        setTitle("Configuración");
        
    }
}
