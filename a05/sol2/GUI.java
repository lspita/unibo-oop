package a05.sol2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logic logic;
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*size, 70*size);
        this.logic = new LogicImpl(size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
            if (this.logic.hit(this.cells.get(jb))){
                redraw();
                if (this.logic.isOver()){
                    this.cells.forEach((k, v) -> k.setEnabled(false));
                } else {
                    this.logic.moveEnemy();
                    redraw();
                }
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	final JButton jb = new JButton();
                this.cells.put(jb, new Position(j,i));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.redraw();
        this.setVisible(true);
    }

    private void redraw() {
        for (var entry: this.cells.entrySet()){
            entry.getKey().setText(
                this.logic.getPlayer().equals(entry.getValue()) ? "P" :
                this.logic.getEnemy().equals(entry.getValue()) ? "E" : " ");
        }
    }
    
}
