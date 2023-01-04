package a01b.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final List<Pair<Pair<Integer, Integer>, JButton>> cells = new ArrayList<>();
    private final Logic logic = new LogicImpl();
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	var btn = (JButton)e.getSource();
            
            logic.newMove(btn.getX() / btn.getWidth(), btn.getY() / btn.getHeight());

            if (logic.isOver()) {
                System.exit(0);
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.add(new Pair<>(new Pair<>(i, j), jb));
                jb.addActionListener(al);
                panel.add(jb);

                logic.addToBoard(i, j, false);
            }
        }
        this.setVisible(true);
    }
    
}
