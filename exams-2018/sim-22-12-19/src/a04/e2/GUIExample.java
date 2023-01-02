package a04.e2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUIExample extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private static final int SIZE = 4;
    
    public GUIExample() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*SIZE, 100*SIZE);
        
        JPanel panel = new JPanel(new GridLayout(SIZE,SIZE));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        
        ActionListener al = (e)->{
            final JButton bt = (JButton)e.getSource();
            bt.setEnabled(false);
        };
                
        for (int i=0; i<SIZE; i++){
            for (int j=0; j<SIZE; j++){
                final JButton jb = new JButton(i+" "+j);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    public static void main(String[] args) {
    	new GUIExample();
    }
}
