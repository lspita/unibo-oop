package a02a.e2;

import javax.swing.*;

import a02a.e2.Logics.Position;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private static String BISHOP_MARK = "B";
    private static String EMPTY_MARK = "";

    private final Map<JButton, Position> buttonsPositions = new HashMap<>();
    private final Logics logics;
    
    public GUI(final int size) {
        logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    final var position = buttonsPositions.get((JButton)e.getSource());
                logics.hit(position);
                updateCells();
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final var position = new Position(j, i);
                final JButton button = new JButton(EMPTY_MARK);
                buttonsPositions.put(button, position);
                button.addActionListener(al);
                panel.add(button);
            }
        }
        updateCells();
        this.setVisible(true);
    }

    private void updateCells() {
        buttonsPositions.forEach((button, position) -> {
            button.setEnabled(true);
            final var type = logics.getType(position);
            switch (type) {
                case BISHOP -> button.setText(BISHOP_MARK);
                case EMPTY -> button.setText(EMPTY_MARK);
                case BLOCKED -> button.setEnabled(false);
            }
        });
    }
}
