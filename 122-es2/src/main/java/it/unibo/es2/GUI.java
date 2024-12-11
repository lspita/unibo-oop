package it.unibo.es2;

import javax.swing.*;

import it.unibo.es2.Logics.CellState;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private final Map<JButton, Pair<Integer, Integer>> buttons = new HashMap<>();
    private final Logics logics;
    
    public GUI(int size) {
        this.logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        
        ActionListener al = (e)->{
            final JButton buttonClicked = (JButton)e.getSource();
            final Pair<Integer,Integer> buttonPosition = buttons.get(buttonClicked);
            logics.toggle(buttonPosition);
            buttonClicked.setText(getCellString(buttonPosition));
            if (logics.gameFinished()){
                System.exit(1);
            } 
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(getCellString(new Pair<>(i, j)));
                jb.addActionListener(al);
                this.buttons.put(jb,new Pair<>(i,j));
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private String getCellString(final Pair<Integer, Integer> pos) {
        final var state = this.logics.getState(pos);
        return switch (state) {
            case EMPTY -> " ";
            case FULL -> "*";
        };
    }
}
