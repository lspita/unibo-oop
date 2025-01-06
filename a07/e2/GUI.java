package a07.e2;

import javax.swing.*;

import a07.e2.Logics.Position;

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
        	    final var position = cells.get((JButton)e.getSource());
                logics.hit(position);
                if (logics.isOver()) {
                    System.exit(0);
                }
                updateCells();
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final var position = new Position(j, i);
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
        cells.forEach((btn, pos) -> btn.setText(logics.isActive(pos) ? "*" : ""));
    }
}
