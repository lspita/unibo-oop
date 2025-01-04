package a02b.e2;

import javax.swing.*;

import a02b.e2.Logics.CellState;
import a02b.e2.Logics.Position;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private static final String CHECK_RESTART_BUTTON_TEXT = "Check > Restart";
    private static final String ACTIVE_CELL_MARK = "*";
    private static final String EMPTY_CELL_MARK = "";

    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logics logics;
    
    public GUI(final int size) {
        logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        final var main = new JPanel(new BorderLayout());
        final var panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(main);
        main.add(BorderLayout.CENTER, panel);
        final var checkRestartButton = new JButton(CHECK_RESTART_BUTTON_TEXT);
        main.add(BorderLayout.SOUTH, checkRestartButton);

        checkRestartButton.addActionListener(e -> {
            logics.checkReset();
            updateCells();
        });
        
        ActionListener al = e -> {
            final var button = (JButton)e.getSource();
            final var newStatus = logics.hit(cells.get(button));
            updateCell(button, newStatus);
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final var position = new Position(j, i);
                final var button = new JButton();
                this.cells.put(button, position);
                button.addActionListener(al);
                panel.add(button);
            }
        }
        updateCells();
        this.setVisible(true);
    }

    private void updateCell(final JButton button, final CellState status) {
        button.setEnabled(true);
        switch (status) {
            case ACTIVE -> button.setText(ACTIVE_CELL_MARK);
            case EMPTY -> button.setText(EMPTY_CELL_MARK);
            case BLOCKED -> button.setEnabled(false);
        }
    }

    private void updateCells() {
        cells.forEach((button, position) -> updateCell(button, logics.getCell(position)));
    }
}
