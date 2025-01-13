package a01c.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private final Map<JButton, Position> buttonsPositions = new HashMap<>();
    private final Map<Position, JButton> positionsButtons = new HashMap<>();
    private final Logics logics;
    
    public GUI(final int size) {
        logics = new LogicsImpl();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * size, 70 * size);
    
        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            final var pos = buttonsPositions.get((JButton) e.getSource());
            updateCells(logics.hit(pos));
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final var pos = new Position(j, i);
                final JButton jb = new JButton();
                this.buttonsPositions.put(jb, pos);
                this.positionsButtons.put(pos, jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        updateCells(Collections.emptyList());
        this.setVisible(true);
    }

    private void updateCells(final List<Position> vertexes) {
        buttonsPositions.keySet().forEach(btn -> btn.setText(""));

        logics.getSquare().ifPresent(
            s -> s.stream().map(positionsButtons::get).forEach(btn -> btn.setText("*"))
        );
        vertexes.forEach(
            pos -> positionsButtons.get(pos).setText(String.valueOf(vertexes.indexOf(pos) + 1))
        );
    }
}