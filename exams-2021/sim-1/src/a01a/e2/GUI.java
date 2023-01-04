package a01a.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<Pair<Integer, Integer>, JButton> cells = new HashMap<>();
    private final Logic logic;
    
    public GUI(int size) {
        this.logic = new LogicImpl(size);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	var btn = (JButton)e.getSource();
        	btn.setText("1");

            logic.registerMove(btn.getX() / btn.getWidth(), btn.getY() / btn.getHeight());

            var buttons = logic.computeRectangle();

            buttons.forEach(b -> cells.get(b).setText("*"));

            if (logic.isOver()) {
                cells.values().forEach(b -> b.setEnabled(false));
            }
        };
                
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton(" ");
                /*
                 * Swapped to correctly model the
                 * grid's coordinates.
                 */
                this.cells.put(new Pair<>(j, i), jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    
}
