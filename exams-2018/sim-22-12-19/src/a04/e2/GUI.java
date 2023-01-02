package a04.e2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private LogicImpl logic = new LogicImpl();
    private static final int SIZE = 4;

    public GUI() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100 * SIZE, 100 * SIZE);
        this.setVisible(true);

        JPanel panel = new JPanel(new GridLayout(SIZE, SIZE));
        this.getContentPane().add(BorderLayout.CENTER, panel);
        
        ActionListener al = (e)->{
            final JButton bt = (JButton)e.getSource();
            if (logic.isValidMove(bt.getX() / bt.getWidth(), bt.getY() / bt.getHeight())) {
                bt.setEnabled(false);
            }

            // Checking game end condition.
            if (logic.isOver()) {
                System.exit(0);
            }
        };
                
        for (int i = 0; i < SIZE; i++){
            for (int j = 0; j < SIZE; j++){
                final JButton jb = new JButton();
                jb.addActionListener(al);
                panel.add(jb);
                logic.addToGrid(i, j, false);
            }
        }
        this.setVisible(true);
    }
}
