package a01b.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Position> buttonToPosition = new HashMap<>();
    private final Map<Position, JButton> positionToButton = new HashMap<>();
    private final Logics logics;
    private List<JButton> activeCells = Collections.emptyList();

    public GUI(int size) {
        this.logics = Logics.newDefault(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            final var button = (JButton)e.getSource();
            final var position = this.buttonToPosition.get(button);
            final var newCells = this.logics.hit(position);
            if (this.logics.isOver()) {
                System.exit(0);
            }
            this.updateCells(newCells);
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	final var position = new Position(j,i);
                final JButton button = new JButton();
                this.addCell(button, position);
                button.addActionListener(al);
                panel.add(button);
            }
        }
        this.setVisible(true);
    }

    private void addCell(final JButton button, final Position position) {
        this.buttonToPosition.put(button, position);
        this.positionToButton.put(position, button);
    }

    private void updateCells(final List<Position> positions) {
        this.activeCells.stream().forEach(button -> button.setText(""));
        this.activeCells = positions.stream()
            .map(pos -> {
                final var button = this.positionToButton.get(pos);
                button.setText(String.valueOf(positions.indexOf(pos) + 1));
                return button;
            }).toList();

    }
    
}
