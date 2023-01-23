package a03a.e2;

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
        this.setSize(50*size, 50*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var btn = (JButton) e.getSource();
            var move = new Pair<>(btn.getX() / btn.getWidth(), btn.getY() / btn.getHeight());
        	if (logic.registerMove(move)) {
                cells.get(move).setText("*");
                var toDraw = logic.getRectangle();
                toDraw.forEach(c -> cells.get(c).setText("*"));
            }
            if (logic.isOver()) {
                cells.values().forEach(b -> b.setEnabled(false));
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	var pos = new Pair<>(j,i);
                final JButton jb = new JButton(" ");
                cells.put(pos, jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    
}
