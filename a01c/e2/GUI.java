package a01c.e2;

import javax.swing.*;

import a01c.e2.Logics.CellType;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Position> buttonToPosition = new HashMap<>();
    private final Map<Position, JButton> positionToButton = new HashMap<>();
    private final Logics logics;
    
    public GUI(final int size) {
        this.logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            final var button = (JButton)e.getSource();
            final var position = this.buttonToPosition.get(button);
            this.logics.hit(position);
            if (this.logics.isOver()) {
                System.exit(0);
            }

            this.buttonToPosition.forEach((btn, pos) -> {
                final var cellType = this.logics.getType(pos);
                btn.setText(cellType.map(this::getCellMark).orElse(""));
            });
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	final var position = new Position(j,i);
                final JButton button = new JButton();
                this.addToGrid(button, position);
                button.addActionListener(al);
                panel.add(button);
            }
        }
        this.setVisible(true);
    }

    private void addToGrid(final JButton button, final Position position) {
        this.buttonToPosition.put(button, position);
        this.positionToButton.put(position, button);
    }

    private String getCellMark(final CellType cellType) {
        return switch (cellType) {
            case VERTEX_1 -> "1";
            case VERTEX_2 -> "2";
            case EXPANDED -> "0";
        };
    }
    
}
