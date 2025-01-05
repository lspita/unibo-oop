package a02c.e2;

import javax.swing.*;

import a02c.e2.Logics.Position;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logics logics;

    public GUI(final int size) {
        logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            final var position = cells.get((JButton)e.getSource());
            final var activePositions = logics.hit(position);
            if (logics.isOver()) {
                System.exit(0);
            }
            updateCells(activePositions);
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	final var position = new Position(j,i);
                final JButton button = new JButton(position.toString());
                cells.put(button, position);
                button.addActionListener(al);
                panel.add(button);
            }
        }
        updateCells(Collections.emptyList());
        this.setVisible(true);
    }

    private void updateCells(final List<Position> activePositions) {
        cells.forEach((btn, pos) -> btn.setText(activePositions.contains(pos) ? "*" : ""));
    }
    
}
