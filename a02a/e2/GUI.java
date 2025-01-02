package a02a.e2;

import javax.swing.*;

import a02a.e2.Logics.CellType;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final String BLOCKED_MARK = "*";
    private static final String ACTIVE_MARK = "o";
    private static final String FREE_MARK = "";
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Position> buttonsPositions = new HashMap<>();
    private final Map<Position, JButton> positionsButtons = new HashMap<>();
    private final Logics logics;

    public GUI(final int size) {
        logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            final var button = (JButton) e.getSource();
            final var position = buttonsPositions.get(button);
            logics.hit(position);
            if (logics.isOver()) {
                System.exit(0);
            }
            fillGrid();
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	final var position = new Position(j,i);
                final JButton button = new JButton();
                addCell(button, position);
                button.addActionListener(al);
                panel.add(button);
            }
        }
        fillGrid();
        this.setVisible(true);
    }

    private void addCell(final JButton button, final Position position) {
        buttonsPositions.put(button, position);
        positionsButtons.put(position, button);
    }

    private String getTypeMark(final CellType type) {
        return switch (type) {
            case ACTIVE -> ACTIVE_MARK;
            case BLOCKED -> BLOCKED_MARK;
        };
    }

    private String getTypeMark(final Optional<CellType> type) {
        return type.map(this::getTypeMark).orElse(FREE_MARK);
    }

    private void fillGrid() {
        buttonsPositions.forEach((btn, pos) -> btn.setText(getTypeMark(logics.getType(pos))));
    }
    
}
