package a06b.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private List<JButton> list = new ArrayList<>();

    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 500);
        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(BorderLayout.CENTER, panel);

        ActionListener al = (e) -> {
            System.out.println("Pressed.");
        };

        final JButton btn = new JButton(">");
        this.getContentPane().add(BorderLayout.SOUTH, btn);
        btn.addActionListener(al);
        

        for (int i = 0; i < size * size; i++){
            final JButton jb = new JButton();
            list.add(jb);
            panel.add(jb);
        } 
        this.setVisible(true);
    }

}
