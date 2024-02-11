package a03b.sol2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logic logic;
    
    public GUI(int width, int height) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*width, 70*height);
        this.logic = new LogicImpl(width, height);
        
        JPanel panel = new JPanel(new GridLayout(height, width));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
            this.logic.hit(this.cells.get(jb));
            if (this.logic.isOver()){
                System.exit(0);
            }
            this.redraw();
        };
                
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++){
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
                this.logic.isMarked(entry.getValue()) 
                    ? "*" 
                    : this.logic.getGoal().equals(entry.getValue()) ? "o" : " ");
        }
    }
    
}
