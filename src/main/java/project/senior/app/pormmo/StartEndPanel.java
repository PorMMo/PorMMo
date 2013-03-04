/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.senior.app.pormmo;

import uk.co.caprica.vlcj.player.MediaPlayer;

/**
 *
 * @author TheMax
 */
public class StartEndPanel extends javax.swing.JPanel {

    private MediaPlayer mplayer;
    
    /**
     * Creates new form StartEndPanel
     */
    public StartEndPanel(MediaPlayer mediaPlayer) {
        initComponents();
        mplayer = mediaPlayer;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        startTimeTxt = new javax.swing.JTextField();
        endTimeTxt = new javax.swing.JTextField();
        frameRateTxt = new javax.swing.JTextField();

        jButton1.setText("Start Time");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("End Time");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Frames Rate ");
        jLabel1.setToolTipText("");

        startTimeTxt.setText("Default");

        endTimeTxt.setText("Defualt");

        frameRateTxt.setText("Default");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(startTimeTxt)
                    .addComponent(frameRateTxt)
                    .addComponent(endTimeTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(startTimeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(endTimeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(frameRateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        startTimeTxt.setText(""+mplayer.getTime());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        endTimeTxt.setText(""+mplayer.getTime());
    }//GEN-LAST:event_jButton2ActionPerformed

    public long getStartTime(){
        if (startTimeTxt.getText().equalsIgnoreCase("default") 
                || startTimeTxt.getText().equalsIgnoreCase("")){
            return 0;
        }
        else{
            try{
                return Long.parseLong(startTimeTxt.getText());
            }
            catch(NumberFormatException e){
                return -1;//negative numbers will act as a fail safe against invalid input
            }
        }
    }
    
    public long getEndTime(){
        if (endTimeTxt.getText().equalsIgnoreCase("default") 
                || endTimeTxt.getText().equalsIgnoreCase("")){
            return 0;
        }
        else{
            try{
                return Long.parseLong(endTimeTxt.getText());
            }
            catch(NumberFormatException e){
                return -1;//negative numbers will act as a fail safe against invalid input
            }
        }
    }
    
    public float getFrameRate(){
        if (frameRateTxt.getText().equalsIgnoreCase("default") 
                || frameRateTxt.getText().equalsIgnoreCase("")){
            return 0;
        }
        else{
            try{
                return Float.parseFloat(frameRateTxt.getText());
            }
            catch(NumberFormatException e){
                return -1;//negative numbers will act as a fail safe against invalid input
            }
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField endTimeTxt;
    private javax.swing.JTextField frameRateTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField startTimeTxt;
    // End of variables declaration//GEN-END:variables
}