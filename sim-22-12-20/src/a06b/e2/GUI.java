package a06b.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private Logic logic = new LogicImpl();
    private List<JButton> buttonList = new ArrayList<>();

    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 500);
        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(BorderLayout.CENTER, panel);

        ActionListener al = (e) -> {
            var index = logic.getNextMove().get();
            var currentBtn = buttonList.get(index);
            currentBtn.setText("*");
        };

        final JButton btn = new JButton(">");
        this.getContentPane().add(BorderLayout.SOUTH, btn);
        btn.addActionListener(al);
        
        var index = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton();
                buttonList.add(jb);
                panel.add(jb);
                logic.addToGrid(index++, i , j);
            }
        }

        this.setVisible(true);
    }

}
