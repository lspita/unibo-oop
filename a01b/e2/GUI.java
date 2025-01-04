package a01b.e2;

import javax.swing.*;

import a01b.e2.Logics.Position;

import java.util.*;
import java.util.stream.IntStream;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private static final String COMPUTED_MARK = "*";
    
    private final Map<JButton, Position> buttonsPositions = new HashMap<>();
    private final Map<Position, JButton> positionsButtons = new HashMap<>();
    private final Logics logics;
    
    public GUI(final int size) {
        logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	final var button = (JButton)e.getSource();
            final var angles = logics.hit(buttonsPositions.get(button));
            IntStream.range(0, angles.size())
                .forEach(i -> positionsButtons.get(angles.get(i)).setText(String.valueOf(i + 1)));
            logics.getComputed().ifPresent(computed -> {
                computed.forEach(pos -> positionsButtons.get(pos).setText(COMPUTED_MARK));
                buttonsPositions.keySet().forEach(btn -> btn.setEnabled(false));
            });
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final var position = new Position(j, i);
                final JButton button = new JButton();
                addCell(button, position);
                button.addActionListener(al);
                panel.add(button);
            }
        }
        this.setVisible(true);
    }

    private void addCell(final JButton button, final Position position) {
        buttonsPositions.put(button, position);
        positionsButtons.put(position, button);
    }
    
}
