/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UnPackerGUI.java
 *
 * Created on Aug 22, 2010, 7:54:25 AM
 */
package unpackerui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;

/**
 *
 * @author alde
 */
public class UnPackerGUI extends javax.swing.JFrame {

        private LoadConfig lc;
        private String sourcedir;
        private String targetdir;
        private DefaultListModel dlm;
        private List<String> finisheddownloads;
        private DefaultBoundedRangeModel brm;
        private int num = 0;

        void setConfig(LoadConfig lc) {
                this.lc = lc;
        }

        /** Creates new form UnPackerGUI */
        public UnPackerGUI() throws IOException, InterruptedException {
                initComponents();
                lc = new LoadConfig();
                lc.doLoad();
                this.sourcedir = lc.getSourceDir();
                this.targetdir = lc.getTargetDir();
                this.finisheddownloads = new ArrayList<String>();
                this.brm = new DefaultBoundedRangeModel(0, 0, 0, 100);

                jProgressBar1.setModel(brm);
                jLabel1.setText(sourcedir + " >> " + targetdir);
                dlm = new DefaultListModel();
                jList1.setModel(dlm);
                jProgressBar1.setStringPainted(true);
                dlm.clear();
                init();
        }

        private void init() throws IOException, InterruptedException {
                sourcedir = lc.getSourceDir();
                targetdir = lc.getTargetDir();
                String cmd = "ls " + sourcedir + " | grep .finished";
                BufferedReader br = startCommand(cmd);

                String line;
                while ((line = br.readLine()) != null) {
                        finisheddownloads.add(line);
                        System.out.println(line);
                }

                for (String s : finisheddownloads) {
                        dlm.addElement(s);
                }
        }

        private void unpack(List<String> finisheddownloads) throws IOException {
                for (String current : finisheddownloads) {
                        String rartype = checkRarType(current);
                        String cmd = "unrar -y -r x " + sourcedir + current + "/" + rartype + " " + targetdir;
                        BufferedReader br = startCommand(cmd);
                        String line;
                        int success = 0;
                        System.out.print(current + ": ");
                        while ((line = br.readLine()) != null) {
                                if (line.contains("%")) {
                                        String regex = "\\d+";
                                        Pattern myPattern = Pattern.compile(regex);
                                        Matcher myMatcher = myPattern.matcher(line);

                                        while (myMatcher.find()) {
                                                num = Integer.parseInt(myMatcher.group());
                                                System.out.println("" + num);
                                        }
                                        updateProgress(num);
                                } else if (line.contains("All OK")) {
                                        System.out.print("Done.");
                                        success = 1;
                                } else if (line.contains("ERROR")) {
                                        System.out.println(line);
                                }
                        }
                        if (success != 0) {
                                //deleteCurrent(current);
                        }
                }
        }

        private void updateProgress(final int number) {
                SwingUtilities.invokeLater(new Runnable() {

                        public void run() {
                                jProgressBar1.setValue(number);
                        }
                });
        }

        private BufferedReader startCommand(String cmd) throws IOException {
                ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd).redirectErrorStream(true);
                Process process = pb.start();
                InputStream is = process.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                return br;
        }

        private String checkRarType(String current) throws IOException {
                String cmd = "ls " + sourcedir + current + "/*.rar";
                BufferedReader br = startCommand(cmd);
                String line;
                while ((line = br.readLine()) != null) {
                        if (line.contains(".part01.rar")) {
                                return "*.part01.rar";
                        } else {
                                return "*.rar";
                        }
                }
                return "*.rar";
        }

        private void deleteCurrent(String current) throws IOException {
                String cmd = "rm " + sourcedir + current;
                Process process = new ProcessBuilder("bash", "-c", cmd).start();
                System.out.println("\033[37m" + current + " deleted.");
        }

        /** This method is called from within the constructor to
         * initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is
         * always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jScrollPane1 = new javax.swing.JScrollPane();
                jList1 = new javax.swing.JList();
                jLabel3 = new javax.swing.JLabel();
                jProgressBar1 = new javax.swing.JProgressBar();
                jButton1 = new javax.swing.JButton();
                jLabel1 = new javax.swing.JLabel();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                jList1.setModel(new javax.swing.AbstractListModel() {
                        String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
                        public int getSize() { return strings.length; }
                        public Object getElementAt(int i) { return strings[i]; }
                });
                jScrollPane1.setViewportView(jList1);

                jLabel3.setText("Files");

                jButton1.setText("Unpack");
                jButton1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton1ActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 513, Short.MAX_VALUE)
                                                .addComponent(jButton1))
                                        .addComponent(jLabel3)
                                        .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE))
                                .addContainerGap())
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1)
                                        .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addGap(6, 6, 6)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents

        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                try {
                        unpack(finisheddownloads);
                } catch (IOException ex) {
                        Logger.getLogger(UnPackerGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
        }//GEN-LAST:event_jButton1ActionPerformed
        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton jButton1;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JList jList1;
        private javax.swing.JProgressBar jProgressBar1;
        private javax.swing.JScrollPane jScrollPane1;
        // End of variables declaration//GEN-END:variables
}
