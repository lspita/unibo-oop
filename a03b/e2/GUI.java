package a03b.e2;

import javax.swing.*;

import a03b.e2.Logics.Cell;
import a03b.e2.Logics.Position;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logics logics;
    
    public GUI(final int size) {
        logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    final var pos = cells.get((JButton)e.getSource());
                logics.hit(pos);
                updateCells();
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final var pos = new Position(j, i);
                final JButton jb = new JButton();
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        updateCells();
        this.setVisible(true);
    }

    private String getMark(final Cell cell) {
        return switch (cell) {
            case CPU -> "o";
            case EMPTY -> "";
            case PLAYER -> "*";
        };
    }

    private void updateCells() {
        cells.forEach((btn, pos) -> btn.setText(getMark(logics.getCell(pos))));
    }
}
