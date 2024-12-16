package a01b.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Cell> cells = new HashMap<>();
    private final Logics logics;
    
    public GUI(int size) {
        this.logics = Logics.getDefault(size);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            final var jb = (JButton)e.getSource();
            final var cell = this.cells.get(jb);

            this.logics.hitCell(cell);
            if (this.logics.isOver()) {
                System.exit(0);
                return;
            }
            
            this.cells.entrySet().forEach(entry -> {
                final var cellValue = this.logics.getCellValue(entry.getValue());
                // map lambda cannot be method reference Integer::toString because of abiguity between
                // Integer.toString(int i) and Integer.toString(int i, int radix)
                entry.getKey().setText(cellValue.map(i -> Integer.toString(i)).orElse(""));
            });
            
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	final var cell = new Cell(j,i);
                final JButton jb = new JButton();
                this.cells.put(jb, cell);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    
}
