package a02a.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<Pair<Integer, Integer>, JButton> cells = new HashMap<>();
    private final Logic logic;
    
    public GUI(int size) {
        logic = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(75 * size, 75 * size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var move = logic.makeMove();
            System.out.println(move);
            cells.get(move.getY()).setText(move.getX().toString());
        	if (logic.isOver()) {
                System.exit(0);
            }
        };
                
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
            	var pos = new Pair<>(j, i);
                final JButton jb = new JButton(" ");
                this.cells.put(pos, jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    
}
