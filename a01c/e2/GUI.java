package a01c.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logics logics;

    public GUI(final int size) {
        logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            final var position = cells.get((JButton) e.getSource());
            if (logics.hit(position)) {
                System.exit(0);
            }
            cells.forEach((btn, pos) -> {
                final var index = logics.getVertexIndex(pos);
                index.ifPresentOrElse(
                    i -> btn.setText(String.valueOf(i)),
                    () -> btn.setText(logics.isActive(pos) ? "O" : "") 
                );
            });
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton button = new JButton();
                this.cells.put(button, new Position(j,i));
                button.addActionListener(al);
                panel.add(button);
            }
        }
        this.setVisible(true);
    }
    
}
