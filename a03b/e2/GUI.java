package a03b.e2;

import javax.swing.*;

import a03b.e2.Logics.Cell;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logics logics;
    
    public GUI(final int width, final int height) {
        logics = new LogicsImpl(width, height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*width, 70*height);
        
        JPanel panel = new JPanel(new GridLayout(height,width));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            final var pos = cells.get((JButton)e.getSource());
            if (logics.hit(pos)) {
                System.exit(0);
            }
            updateCells();
        };
                
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++){
            	final var pos = new Position(j,i);
                final JButton jb = new JButton();
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
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
            case ACTIVE -> "*";
            case EMPTY -> "";
            case TARGET -> "o";
        };
    }
    
}
