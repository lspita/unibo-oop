package a06.e2;

import javax.swing.*;

import a06.e2.Logics.Position;

import java.util.*;
import java.awt.*;

public class GUI extends JFrame {
    
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logics logics;

    public GUI(final int size) {
        logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel main = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(main);
        main.add(BorderLayout.CENTER, panel);
        final var fireButton = new JButton("Fire");
        main.add(BorderLayout.SOUTH, fireButton);
        fireButton.addActionListener(e -> {
            if (logics.fire()) {
                System.exit(0);
            }
            updateCells();
        });
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final var position = new Position(j, i);
                final var button = new JButton();
                this.cells.put(button, position);
                panel.add(button);
            }
        }
        updateCells();
        this.setVisible(true);
    }

    private void updateCells() {
        cells.forEach((btn, pos) -> btn.setText(
            logics.getValue(pos)
                .map(String::valueOf)
                .orElse("")
        ));
    }
}
