package a01b.e2;

import a01b.e2.Logics.Position;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private static final String ACTIVE_MARK = "*";

    private final Map<JButton, Position> buttonsPostions = new HashMap<>();
    private final Map<Position, JButton> positionsButtons = new HashMap<>();
    private final Logics logics;
    private List<JButton> activeCells = Collections.emptyList();
    
    public GUI(final int size) {
        logics = new LogicsImpl(size);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                final var position = buttonsPostions.get((JButton)e.getSource());
                final var activePositions = logics.hit(position);
                if (logics.isOver()) {
                    System.exit(0);
                }
                updateCells(activePositions);
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final var position = new Position(j, i);
                final var button = new JButton("");
                addCell(button, position);
                button.addActionListener(al);
                panel.add(button);
            }
        }
        this.setVisible(true);
    }

    private void addCell(final JButton button, final Position position) {
        buttonsPostions.put(button, position);
        positionsButtons.put(position, button);
    }

    private void updateCells(final List<Position> activePositions) {
        activeCells.forEach(button -> button.setText(""));
        activeCells = activePositions.stream()
            .map(positionsButtons::get)
            .peek(button -> button.setText(ACTIVE_MARK))
            .toList();
    }
}
