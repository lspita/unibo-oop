package a01a.sol2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logic logic;

    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * size, 70 * size);
        this.logic = new LogicImpl();

        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            var jb = (JButton) e.getSource();
            if (this.logic.isOver()) {
                this.logic.reset();
                this.reset();
                return;
            }
            if (this.logic.hit(this.cells.get(jb))) {
                jb.setText("*");
                return;
            } 
            if (this.logic.isOver()) {
                int ct = 0;
                for (var entry : this.cells.entrySet()) {
                    if (this.logic.isVertex(entry.getValue())) {
                        entry.getKey().setText(String.valueOf(++ct));
                    } else if (this.logic.isSelected(entry.getValue())) {
                        entry.getKey().setText("o");
                    }
                }
            }
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton();
                this.cells.put(jb, new Position(j, i));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private void reset() {
        this.cells.forEach((k, v) -> k.setText(""));
    }

}
