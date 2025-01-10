package a01c.sol2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logic logic;
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*size, 70*size);
        this.logic = new LogicImpl();
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
            if (this.logic.isOver()){
                this.logic.reset();
                this.reset();
                return;
            }
            Optional<Integer> val = this.logic.hit(this.cells.get(jb));
            if (val.isEmpty()){
                this.reset();
            } else {
                jb.setText(String.valueOf(val.get()));
            }            
            for (var entry: this.cells.entrySet()){
                if (this.logic.isSelected(entry.getValue())){
                    entry.getKey().setText("*");
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
        this.setVisible(true);
    }

    private void reset(){
        this.cells.forEach((k, v) -> k.setText(""));
    }
    
}
