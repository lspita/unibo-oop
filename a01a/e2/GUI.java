package a01a.e2;

import javax.swing.*;

import a01a.e2.Logics.CellState;
import a01a.e2.Logics.Position;

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
        this.setSize(50*size, 50*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	final var button = (JButton)e.getSource();
            logics.hit(cells.get(button));
            cells.forEach((btn, pos) -> {
                switch (logics.getState(pos)) {
                    case ACTIVE -> btn.setText("*");
                    case EMPTY -> btn.setText("");
                    case PIN -> btn.setText("1");
                    case BLOCKED -> btn.setEnabled(false);
                }
            });
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
        this.setVisible(true);
    }
    
}
