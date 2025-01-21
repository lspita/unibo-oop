package a04.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logics logics;
    
    public GUI(final int width) {
        logics = new LogicsImpl(width);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*width, 70*width);
        
        JPanel panel = new JPanel(new GridLayout(width,width));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            final var pos = cells.get((JButton)e.getSource());
            if (logics.hit(pos)) {
                cells.keySet().forEach(btn -> btn.setEnabled(false));
            }
            updateCells();
        };
                
        for (int i=0; i<width; i++){
            for (int j=0; j<width; j++){
            	final var pos = new Position(j,i);
                final JButton jb = new JButton();
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        updateCells();
        this.setVisible(true);
    }

    private void updateCells() {
        cells.forEach((btn, pos) -> btn.setText(logics.isActive(pos) ? "*" : ""));
    }
    
}
