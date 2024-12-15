package a01a.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Cell> buttonToCell = new HashMap<>();
    private final Map<Cell, JButton> cellToButton = new HashMap<>();
    private final Logics logics;

    public GUI(int size) {
        this.logics = Logics.getDefault(size);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        final JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            final var jb = (JButton)e.getSource();
            final var cell = this.buttonToCell.get(jb);
            final var activeCells = this.logics.hitCell(cell);
            if (this.logics.toQuit()) {
                System.exit(0);
                return;
            }
            setCellsContent(activeCells);
        };
                
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                final var jb = new JButton();
            	final var pos = new Cell(j,i);
                jb.setText(pos.getX() + ", " + pos.getY());
                setCell(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private void setCell(final JButton jb, final Cell pos) {
        this.buttonToCell.put(jb, pos);
        this.cellToButton.put(pos, jb);
    }

    private void setCellsContent(final List<Cell> activeCells) {
        this.buttonToCell.keySet().forEach(jb -> jb.setText(""));

        IntStream.range(0, activeCells.size())
            .forEach(i -> this.cellToButton.get(activeCells.get(i)).setText(Integer.toString(i + 1)));
    }
    
}
