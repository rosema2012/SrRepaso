
package recup1eva;

import java.awt.FlowLayout;
import javax.swing.JDialog;

/**
 * @author David Carrera Otero
 */
public class VerRecords extends JDialog{
    public VerRecords(Principal p){
        super(p,true);
        setLayout(new FlowLayout());
        setTitle("Records");
    }
}
