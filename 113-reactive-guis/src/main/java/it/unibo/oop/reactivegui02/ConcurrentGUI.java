package it.unibo.oop.reactivegui02;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Second example of reactive GUI.
 */
@SuppressWarnings("PMD.AvoidPrintStackTrace")
public final class ConcurrentGUI extends JFrame {
    
    private static final long serialVersionUID = 1L;

    private static final double WIDTH_PERC = 0.2;
    private static final double HEIGHT_PERC = 0.1;
    private final JLabel display = new JLabel("0");
    private final JButton upButton = new JButton("up");
    private final JButton downButton = new JButton("down");
    private final JButton stopButton = new JButton("stop");

    private final Agent counterAgent = new Agent();
    
    public ConcurrentGUI() {
        super();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) (screenSize.getWidth() * WIDTH_PERC), (int) (screenSize.getHeight() * HEIGHT_PERC));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        final JPanel panel = new JPanel();
        panel.add(display);
        panel.add(upButton);
        panel.add(downButton);
        panel.add(stopButton);
        this.getContentPane().add(panel);

        // Handlers
        upButton.addActionListener(ae -> counterAgent.startIncrementing());
        downButton.addActionListener(ae -> counterAgent.startDecrementing());
        stopButton.addActionListener(ae -> counterAgent.stopCounting());

        // Start
        Thread.ofPlatform().daemon(true).start(counterAgent);
        this.setVisible(true);
    }

    /*
     * The counter agent is implemented as a nested class. This makes it
     * invisible outside and encapsulated.
     */
    private class Agent implements Runnable {
        
        private enum Operation {
            INCREMENT,
            DECREMENT,
            STOP
        }

        private static final long LOOP_TIMEOUT_MS = 100;

        private volatile Operation operation = Operation.STOP;
        private int counter = 0;

        @Override
        public void run() {
            while (true) {
                try {
                    this.applyOperation();
                    final var nextText = Integer.toString(this.counter);
                    SwingUtilities.invokeAndWait(() -> ConcurrentGUI.this.display.setText(nextText));
                    Thread.sleep(LOOP_TIMEOUT_MS);
                } catch (InvocationTargetException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        private void applyOperation() {
            switch (this.operation) {
                case INCREMENT -> this.counter++;
                case DECREMENT -> this.counter--;
                default -> {}
            }
        }

        /**
         * External command to start decrementing.
         */
        public void startIncrementing() {
            this.operation = Operation.INCREMENT;
        }

        /**
         * External command to start incrementing.
         */
        public void startDecrementing() {
            this.operation = Operation.DECREMENT;
        }

        /**
         * External command to stop counting.
         */
        public void stopCounting() {
            this.operation = Operation.STOP;
        }
    }
}
