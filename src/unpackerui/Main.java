/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unpackerui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author alde
 */
public class Main {

        /**
         * @param args the command line arguments
         */
        public static void main(String[] args) throws IOException, InterruptedException {
                UnPackerGUI upUI;
                try {
                        upUI = new UnPackerGUI();
                        upUI.setVisible(true);
                } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
}
