package a01b.e2;

import javax.swing.*;

import a01b.e2.Logics.Position;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.awt.*;

public class GUI extends JFrame {
    
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logics logics;

    public GUI(final int size, final int mines) {
        logics = new LogicsImpl(size, mines);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(size * 200, size * 200);
        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        ActionListener al = (e)->{
            final var jb = (JButton)e.getSource();
            final var pos = cells.get(jb);
            final var result = logics.hit(pos);
            result.ifPresentOrElse(
                r -> {
                    jb.setText(String.valueOf(r));
                    jb.setEnabled(false);
                }, 
                () -> System.exit(0)
            );
        };
        for (int i=0; i< size; i++){
            for (int j = 0; j < size; j++) {
                final var pos = new Position(j, i);
                final JButton jb = new JButton();
                jb.addActionListener(al);
                panel.add(jb);
                cells.put(jb, pos);
            }
        } 
        this.setVisible(true);
    }
        
}
