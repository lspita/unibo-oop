package a01a.e2;

import javax.swing.*;

import a01a.e2.Logics.Position;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.awt.*;

public class GUI extends JFrame {
    
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logics logics;

    public GUI(final int size, final int boat) {
        logics = new LogicsImpl(size, boat);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 500);
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        ActionListener al = (e)->{
            final var jb = (JButton)e.getSource();
            jb.setText(logics.fire(cells.get(jb)) ? "x" : "o");
            jb.setEnabled(false);

            final var state = logics.getGameState();
            state.ifPresent(s -> {
                System.out.println(s.name());
                System.exit(0);
            });
        };
        for (var i = 0; i < size; i++) {
            for (var j = 0; j < size; j++) {
                final var pos = new Position(j, i);
                final JButton jb = new JButton();
                jb.addActionListener(al);
                cells.put(jb, pos);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
        
}
