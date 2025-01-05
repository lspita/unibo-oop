package a03a.e2;

import javax.swing.*;

import a03a.e2.Logics.Cell;
import a03a.e2.Logics.Position;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static String EMPTY_MARK = "";
    private static String TRAIL_MARK = "*";
    private static String GOAL_MARK = "o";

    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logics logics;
    
    public GUI(final int width, final int height) {
        logics = new LogicsImpl(width, height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*width, 70*height);
        
        JPanel panel = new JPanel(new GridLayout(height, width));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            final var button = (JButton)e.getSource();
            final var position = cells.get(button);
            if (logics.shoot(position)) {
                System.exit(0);
            }
            updateCells();
        };
                
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++){
            	final var position = new Position(j,i);
                final JButton button = new JButton();
                cells.put(button, position);
                button.addActionListener(al);
                panel.add(button);
            }
        }
        updateCells();
        this.setVisible(true);
    }
    
    private String getMark(final Cell cellType) {
        return switch (cellType) {
            case EMPTY -> EMPTY_MARK;
            case GOAL -> GOAL_MARK;
            case TRAIL -> TRAIL_MARK;
        };
    }

    private void updateCells() {
        cells.forEach((btn, pos) -> btn.setText(getMark(logics.getType(pos))));
    }

}
