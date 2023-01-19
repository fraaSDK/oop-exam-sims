package a01c.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<Pair<Integer, Integer>, JButton> cells = new HashMap<>();
    private final Logic logic = new LogicImpl();
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50 * size, 50 * size);
        
        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	var btn = (JButton) e.getSource();
            var move = new Pair<>(btn.getX() / btn.getWidth(), btn.getY() / btn.getHeight());
            logic.registerMove(move.getX(), move.getY());
            if (logic.isValid()) {
                btn.setText("*");
                var btnList = logic.toDisplay();
                if (btnList.isPresent()) {
                    btnList.get().forEach(b -> cells.get(b).setText("*"));
                }   
            }
        };
                
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                final JButton jb = new JButton(" ");
                cells.put(new Pair<>(j, i), jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    
}
