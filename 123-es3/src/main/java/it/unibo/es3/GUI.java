package it.unibo.es3;

import javax.swing.*;

import it.unibo.es3.Logics.CellState;

import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

public class GUI extends JFrame {
    
    private static final String EXPAND_BUTTON_TEXT = ">";
    private static final String EMPTY_CELL_TEXT = " ";
    private static final String FULL_CELL_TEXT = "*";

    private final List<JButton> cellButtons;
    private final Logics logics = Logics.getDefault();

    public GUI(int width) {
        this.logics.initGrid(width);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*width, 70*width);
        final var mainPanel = this.getContentPane();

        mainPanel.setLayout(new BorderLayout());
        final JPanel gridPanel = new JPanel(new GridLayout(width,width));
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        final var expandButton = new JButton(EXPAND_BUTTON_TEXT);
        mainPanel.add(expandButton, BorderLayout.SOUTH);      
        expandButton.addActionListener(e -> {
            this.logics.expand();
            if (this.logics.isGridFull()) {
                System.exit(0);
            }
            else {
                setButtonsContent();
            }
        });
        
        cellButtons = IntStream.range(0, width * width)
            .mapToObj(i -> {
                final var btn = new JButton();
                gridPanel.add(btn);
                return btn;
            })
            .toList();

        setButtonsContent();
        this.setVisible(true);
    }
    
    private String getCellText(final CellState cell) {
        return switch (cell) {
            case EMPTY -> EMPTY_CELL_TEXT;
            case FULL -> FULL_CELL_TEXT;
        };
    }

    private void setButtonsContent() {
        final var cellButtonsContent = this.logics.getCellStates().stream()
            .map(this::getCellText)
            .toList();
        
        IntStream.range(0, this.cellButtons.size())
            .forEach(i -> this.cellButtons.get(i).setText(cellButtonsContent.get(i)));
    }
    
}