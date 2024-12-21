package a06.sol2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton, Pair<Integer,Integer>> cells = new HashMap<>();
    private Logic logic;
    private boolean toInit = false;
    
    public GUI(int size) {
        this.logic = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel main = new JPanel(new BorderLayout());
        JPanel grid = new JPanel(new GridLayout(size,size));
        main.add(BorderLayout.CENTER, grid);
        JButton check = new JButton("Fire");
        main.add(BorderLayout.SOUTH, check);
        this.getContentPane().add(main);

        check.addActionListener(e -> {
            logic.fire();
            if (logic.isOver()){
                check.setEnabled(false);
            }
            this.update();
        });
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<>(j,i));
                grid.add(jb);
            }
        }
        this.update();
        this.setVisible(true);
    }   

    private void update() {
        for (var entry: cells.entrySet()){
            entry.getKey().setText(this.logic.value(entry.getValue().getX(), entry.getValue().getY()).map(String::valueOf).orElse(""));
        }
    }
}
