package principal;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;

/**
 * @author David Carrera Otero
 */
public class VerRecords extends JDialog{
    JList<String> lista;
    JButton Eliminar;
    
    public VerRecords(Principal p){
        super(p,true);
        setLayout(new FlowLayout());
        setTitle("Records");
    }
}