/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GameEngine.java
 *
 * Created on Sep 19, 2009, 4:21:46 AM
 */

package maze.ui;

import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Satyajeet
 */
public class GameEngine extends javax.swing.JFrame {


    private static class GameRunner implements Runnable
    {

        public void run() {
            try {
                Boolean play = true;
                int score;
                while(play == true)
                {
                    score = gamecontroller.GamePlay(gameconfig);
                if(score !=-1)
                {
                    // Game must have ended, since you have a score now
                   play = false;
                   String gameover = "Game Over. Score: " + score;
                   JOptionPane.showMessageDialog(game_scrollpane, gameover);
                }
                uicontroller.gc_local = gameconfig;
//                uicontroller.repaint();
                buff_im = uicontroller.getImage();
                 game_jlabel.repaint();
                game_scrollpane.repaint();

                txt_round_copy.setText(Integer.toString(gameconfig.current_round));
                txt_score_copy.setText(Integer.toString(gameconfig.current_score));
                Thread.sleep(gameDelay);

                }
            } catch (InterruptedException ex) {
                System.out.println("Game runner interrupted");
            }
        }


    }

    // STATIC STATUS VARIABLES FOR THE GAME
    static int gameDelay;
    Thread grunner;
    static Boolean play;
    static GameController gamecontroller;
    static GameConfig gameconfig;
    static UIController uicontroller;
    static IOController iocontroller;
    static BufferedImage buff_im;
    static JLabel game_jlabel;
    static JScrollPane game_scrollpane;
    static JTextField txt_round_copy;
    static JTextField txt_score_copy;
    /** Creates new form GameEngine */
    public GameEngine() {
        initComponents();
        myInit();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        PlayerList3 = new javax.swing.JList();
        jScrollPane8 = new javax.swing.JScrollPane();
        MazeList3 = new javax.swing.JList();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        DelaySlider = new javax.swing.JSlider();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtbox_number_of_objects3 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_round = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txt_score = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        zoomSlider = new javax.swing.JSlider();
        NewGameButton = new javax.swing.JButton();
        StepButton = new javax.swing.JButton();
        PlayButton = new javax.swing.JButton();
        PauseButton = new javax.swing.JButton();
        ResignButton = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        SecondDisplay = new javax.swing.JButton();
        gamePanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Game Simulator (Maze of Twisty Little Passages)");

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        PlayerList3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PlayerList3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane7.setViewportView(PlayerList3);

        MazeList3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        MazeList3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane8.setViewportView(MazeList3);

        jLabel17.setText("Player List");

        jLabel18.setText("Maze List");

        jLabel19.setText("Delay (0-1000 ms):");

        DelaySlider.setMaximum(1000);
        DelaySlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                DelaySliderStateChanged(evt);
            }
        });

        jLabel20.setText("Configuration");

        jLabel21.setText("Number of Objects");

        txtbox_number_of_objects3.setFont(new java.awt.Font("Tahoma", 2, 11));
        txtbox_number_of_objects3.setText("12");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel2.setText("Round");

        txt_round.setEditable(false);
        txt_round.setText("Rounds");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel1.setText("Score");

        txt_score.setEditable(false);
        txt_score.setText("Score");

        jLabel3.setText("Image zoom:");

        zoomSlider.setMajorTickSpacing(1);
        zoomSlider.setMaximum(8);
        zoomSlider.setMinimum(1);
        zoomSlider.setMinorTickSpacing(1);
        zoomSlider.setValue(3);
        zoomSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                zoomSliderStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel20)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(18, 18, 18)
                        .addComponent(txtbox_number_of_objects3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txt_score))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_round, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(12, 12, 12))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel18)
                .addContainerGap(156, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addContainerGap(118, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DelaySlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(148, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(zoomSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtbox_number_of_objects3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_round, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_score, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DelaySlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(zoomSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );

        NewGameButton.setText("Begin New Game");
        NewGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewGameButtonActionPerformed(evt);
            }
        });

        StepButton.setText("Step");
        StepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StepButtonActionPerformed(evt);
            }
        });

        PlayButton.setText("Play");
        PlayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayButtonActionPerformed(evt);
            }
        });

        PauseButton.setText("Pause");
        PauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PauseButtonActionPerformed(evt);
            }
        });

        ResignButton.setText("Resign");
        ResignButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResignButtonActionPerformed(evt);
            }
        });

        jButton6.setText("Run Tournament");

        SecondDisplay.setText("2nd Display");
        SecondDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SecondDisplayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(gamePanelLayout);
        gamePanelLayout.setHorizontalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 873, Short.MAX_VALUE)
        );
        gamePanelLayout.setVerticalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(NewGameButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(StepButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PlayButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PauseButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ResignButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SecondDisplay)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(669, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NewGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(StepButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PlayButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ResignButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SecondDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gamePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(32, 32, 32))
        );

      
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PlayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayButtonActionPerformed
        // TODO add your handling code here:
        grunner = new Thread(new GameRunner());
        grunner.start();

    }//GEN-LAST:event_PlayButtonActionPerformed

    private void PauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PauseButtonActionPerformed
           // TODO add your handling code here:
           grunner.interrupt();
           
    }//GEN-LAST:event_PauseButtonActionPerformed

    private void StepButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StepButtonActionPerformed
        // TODO add your handling code here:



       //gamecontroller.GamePlay(gameconfig);
       int score = gamecontroller.GamePlay(gameconfig);
       if(score !=-1)
        {
            // Game must have ended, since you have a score now

           String gameover = "Game Over. Score: " + score;
           JOptionPane.showMessageDialog(game_scrollpane, gameover);
        }
       else{


       uicontroller.gc_local = gameconfig;
//       uicontroller.repaint();
//       uicontroller.validate();
//       this.validate();
    // GET IMAGE FROM UIC and load give reference
        buff_im = uicontroller.getImage();
        game_jlabel.repaint();
        game_scrollpane.repaint();

        txt_round.setText(Integer.toString(gameconfig.current_round));
         txt_score.setText(Integer.toString(gameconfig.current_score));

       }
       
    }//GEN-LAST:event_StepButtonActionPerformed

    private void SecondDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SecondDisplayActionPerformed
        // TODO add your handling code here:
//        uicontroller.repaint();
        
//
//        if(uicontroller.number == 5)
//        {
//            uicontroller.number = 10;
//        }
//        else
//        {
//            uicontroller.number = 5;
//        }
//        uicontroller.repaint();
    }//GEN-LAST:event_SecondDisplayActionPerformed

    private void ResignButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResignButtonActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_ResignButtonActionPerformed

    private void NewGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewGameButtonActionPerformed
        // TODO add your handling code here:
       

       
        String mazename = (String)MazeList3.getSelectedValue();
        if(mazename == null)
        {
            return;
        }
        String playername = (String)PlayerList3.getSelectedValue();
       
        if(playername == null)
        {
            return;
        }
        if (txtbox_number_of_objects3.getText().equalsIgnoreCase("")|| txtbox_number_of_objects3.getText().equalsIgnoreCase(""))
        {
            return;
        }
     
        int number_of_objects = Integer.parseInt(txtbox_number_of_objects3.getText());
        // Lets instantiate the gameconfig object for starters
        gameconfig = null;

        gameconfig = iocontroller.makeGameConfig(mazename,playername,number_of_objects);
        uicontroller = null;
        uicontroller = new UIController();
        uicontroller.gc_local = gameconfig;
//        uicontroller.repaint();

        buff_im = uicontroller.getImage();

        if(game_scrollpane != null)
        {
            this.remove(game_scrollpane);
            
        }
        game_jlabel = null;
        game_scrollpane = null;
        game_jlabel = new JLabel(new ImageIcon(buff_im));
        game_scrollpane = new JScrollPane(game_jlabel);
        game_scrollpane.setLocation(20, 120);
        game_scrollpane.setSize(650, 600);
        this.add(game_scrollpane);
        game_scrollpane.repaint();
        this.validate();
        txt_round.setText(Integer.toString(gameconfig.current_round));
         txt_score.setText(Integer.toString(gameconfig.current_score));
         zoomSlider.setValue(2);
        //System.out.println("New Created");
        // Now we wait for GamePlay to be launched for this game configuration

       
    }//GEN-LAST:event_NewGameButtonActionPerformed

    private void DelaySliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_DelaySliderStateChanged
        // TODO add your handling code here:

        gameDelay = DelaySlider.getValue();
        //System.out.println("Value = " + gameDelay);
    }//GEN-LAST:event_DelaySliderStateChanged

    private void zoomSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_zoomSliderStateChanged
        // TODO add your handling code here:
        //System.out.println("Zoom Called");
        if(gameconfig == null)
        {
            return;
        }
        gameconfig.zoomValue = zoomSlider.getValue();
         uicontroller = null;
        uicontroller = new UIController();
        uicontroller.gc_local = gameconfig;
//        uicontroller.repaint();
        uicontroller.setImage();

        buff_im = uicontroller.getImage();

        if(game_scrollpane != null)
        {
            this.remove(game_scrollpane);

        }
        game_jlabel = null;
        game_scrollpane = null;
        game_jlabel = new JLabel(new ImageIcon(buff_im));
        game_scrollpane = new JScrollPane(game_jlabel);
        game_scrollpane.setLocation(20, 120);
        game_scrollpane.setSize(650, 600);
        this.add(game_scrollpane);
        game_scrollpane.repaint();
        this.validate();

    }//GEN-LAST:event_zoomSliderStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameEngine().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSlider DelaySlider;
    private javax.swing.JList MazeList3;
    private javax.swing.JButton NewGameButton;
    private javax.swing.JButton PauseButton;
    private javax.swing.JButton PlayButton;
    private javax.swing.JList PlayerList3;
    private javax.swing.JButton ResignButton;
    private javax.swing.JButton SecondDisplay;
    private javax.swing.JButton StepButton;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTextField txt_round;
    private javax.swing.JTextField txt_score;
    private javax.swing.JTextField txtbox_number_of_objects3;
    private javax.swing.JSlider zoomSlider;
    // End of variables declaration//GEN-END:variables


    public void myInit()
    {

//        play = true;
//        grunner = new Thread(new GameRunner());
        gamecontroller = new GameController();
        gameconfig = new GameConfig();
        gameDelay = 0;
       
        txt_round_copy = this.txt_round;
        txt_score_copy = this.txt_score;
        uicontroller = new UIController();

       

//       uicontroller.setLocation(20, 100);
//        uicontroller.setSize(new Dimension(900, 700));

        // We shall create a JScrollPane with the returned image

        
        


       
//        this.add(uicontroller);
        this.validate();

        iocontroller = new IOController();

        populatePlayerList();
        populateMazeList();
        txtbox_number_of_objects3.selectAll();
        
        int number_of_objects = Integer.parseInt(txtbox_number_of_objects3.getText());
        // Lets instantiate the gameconfig object for starters
        gameconfig = null;

        gameconfig = iocontroller.makeGameConfig("test1.maze","maze.g3.G3Player",number_of_objects);
        uicontroller = null;
        uicontroller = new UIController();
        uicontroller.gc_local = gameconfig;
//        uicontroller.repaint();

        buff_im = uicontroller.getImage();

        if(game_scrollpane != null)
        {
            this.remove(game_scrollpane);
            
        }
        game_jlabel = null;
        game_scrollpane = null;
        game_jlabel = new JLabel(new ImageIcon(buff_im));
        game_scrollpane = new JScrollPane(game_jlabel);
        game_scrollpane.setLocation(20, 120);
        game_scrollpane.setSize(650, 600);
        this.add(game_scrollpane);
        game_scrollpane.repaint();
        this.validate();
        txt_round.setText(Integer.toString(gameconfig.current_round));
         txt_score.setText(Integer.toString(gameconfig.current_score));
         zoomSlider.setValue(2);
       


    }

    

    public void populatePlayerList()
    {
        PlayerList3.removeAll();
        ArrayList<String> playernames = iocontroller.getPlayerList();
        PlayerList3.setModel(new DefaultListModel());
        DefaultListModel dlm = (DefaultListModel) PlayerList3.getModel();
         for(int loop=0;loop<playernames.size();loop++)
         {
             dlm.addElement((Object)playernames.get(loop));
         }
    }

    public void populateMazeList()
    {

        MazeList3.removeAll();
         ArrayList<String> mazenames = iocontroller.getMazeList();
         MazeList3.setModel(new DefaultListModel());
        DefaultListModel dlm = (DefaultListModel) MazeList3.getModel();
         for(int loop=0;loop<mazenames.size();loop++)
         {
             dlm.addElement((Object)mazenames.get(loop));
         }
    }
    

    
}
