package a04.e2;

import javax.swing.*;

import java.util.HashMap;
import java.util.Map;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<Pair<Integer, Integer>, JButton> cells = new HashMap<>();
    private final JButton quit = new JButton("QUIT");
    private final Logic logic;
        
    public GUI(int size) {
        this.logic = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100 * size, 100 * size);
        
        JPanel grid = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(BorderLayout.CENTER, grid);
        this.getContentPane().add(BorderLayout.SOUTH, quit);
        
        quit.addActionListener(e -> {
            System.out.println("Result: " + logic.computeResult());
        	System.exit(0);
       	});
        
        ActionListener al = e -> {
        	var jb = (JButton) e.getSource();
            var currentAction = new Pair<>(jb.getX() / jb.getWidth(), jb.getY() / jb.getHeight());
            if (logic.registerAction(currentAction, jb.getText())) {
                jb.setEnabled(false);
            }
        };
        
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                final JButton jb = new JButton("");
                var pos = new Pair<>(j, i);
                cells.put(pos, jb);
                grid.add(jb);
                jb.addActionListener(al);
            }
        }

        logic.initGrid()
                .entrySet()
                .forEach(e -> cells.get(e.getKey()).setText(e.getValue()));
        this.setVisible(true);
    }
}