package a01a.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton, Position> buttonPositions = new HashMap<>();
    private final Map<Position, JButton> positionButtons = new HashMap<>();
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
        	    final var button = (JButton)e.getSource();
                final var position = buttonPositions.get(button);
                final var newPositions = logics.hit(position);
                if (logics.isOver()) {
                    System.exit(0);
                }

                activeCells.stream().forEach(jb -> jb.setText("")); 
                activeCells = newPositions.stream()
                    .map(positionButtons::get)
                    .peek(jb -> jb.setText("*"))
                    .toList();
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final Position pos = new Position(j, i);
                final JButton jb = new JButton();
                buttonPositions.put(jb, pos);
                positionButtons.put(pos, jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }    
}
