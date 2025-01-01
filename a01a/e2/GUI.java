package a01a.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Position> buttonToPosition = new HashMap<>();
    private final Map<Position, JButton> positionToButton = new HashMap<>();
    private List<Position> activePositions = Collections.emptyList();
    private final Logics logics;

    public GUI(final int size) {
        this.logics = Logics.newDefault(size);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            final var button = (JButton) e.getSource();
            final var position = this.buttonToPosition.get(button);
            final var newPositions = this.logics.hit(position);
            this.drawNewPositions(newPositions);
            if (this.logics.isOver()) {
                System.exit(0);
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	final var position = new Position(j, i);
                final JButton jb = new JButton();
                this.addCell(jb, position);;
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private void addCell(final JButton button, final Position position) {
        this.buttonToPosition.put(button, position);
        this.positionToButton.put(position, button);
    }

    private void drawNewPositions(final List<Position> positions) {
        this.activePositions.stream()
        .map(this.positionToButton::get)
        .forEach(button -> button.setText(""));
        
        record ButtonIndexPair(JButton button, int index) { }
        positions.stream()
            .map(p -> new ButtonIndexPair(this.positionToButton.get(p), positions.indexOf(p) + 1))
            .forEach(pair -> pair.button().setText(String.valueOf(pair.index())));

        this.activePositions = positions;
    }
    
}
