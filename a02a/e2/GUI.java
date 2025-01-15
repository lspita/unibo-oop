package a02a.e2;

import javax.swing.*;

import a02a.e2.Logics.Cell;

import java.util.*;
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
            final var position = cells.get((JButton) e.getSource());
            if (logics.hit(position)) {
                System.exit(0);
            }
            updateCells();
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	final var position = new Position(j,i);
                final JButton button = new JButton();
                this.cells.put(button, position);
                button.addActionListener(al);
                panel.add(button);
            }
        }
        updateCells();
        this.setVisible(true);
    }

    private void updateCells() {
        cells.forEach((btn, pos) -> btn.setText(getMark(logics.getCell(pos))));
    }

    private String getMark(final Cell cell) {
        return switch (cell) {
            case EMPTY -> "";
            case BLOCKED -> "*";
            case ACTIVE -> "o";
        };
    }
    
}
