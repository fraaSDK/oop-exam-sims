package a01b.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Summary:
 * Started: 75 minutes.
 * Current: 90 minutes. Last deadline, end of exam.
 */
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

            if (logic.isValid(move)) {
                logic.registerMove(move);
                btn.setText(String.valueOf(logic.getMoveNumber()));
            }

            if (logic.isOver()) {
                var list = logic.drawOnBoard();
                list.forEach(c -> cells.get(c).setText("*"));
                cells.values().forEach(c -> c.setEnabled(false));
            }
        };
                
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(new Pair<>(j, i), jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    
}
