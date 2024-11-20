package it.unibo.mvc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A very simple program using a graphical interface.
 *
 */
public final class SimpleGUI {

    private static final String TITLE = "Simple App";
    private static final double SCALE_FACTOR = 5;
    private static final String PRINT_BTN_TEXT = "Print";
    private static final String HISTORY_BTN_TEXT = "History";

    private final JFrame frame = new JFrame(TITLE);

    /**
     * Create new {@link SimpleGUI} using the given controller.
     *
     * @param app controller to use
     *
     * @throws NullPointerException if the controller is null
     */
    public SimpleGUI(final Controller app) {
        Objects.requireNonNull(app);

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(new Dimension(
                (int) (screenSize.getWidth() / SCALE_FACTOR),
                (int) (screenSize.getHeight() / SCALE_FACTOR)
        ));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);

        final JPanel mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);

        final JTextField messageTextField = new JTextField();
        mainPanel.add(messageTextField, BorderLayout.NORTH);

        final JTextArea historyTextArea = new JTextArea();
        mainPanel.add(historyTextArea, BorderLayout.CENTER);
        historyTextArea.setEditable(false);

        final JPanel southPanel = new JPanel(new BorderLayout());
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        final JButton printButton = new JButton(PRINT_BTN_TEXT);
        southPanel.add(printButton, BorderLayout.WEST);

        final JButton historyButton = new JButton(HISTORY_BTN_TEXT);
        southPanel.add(historyButton, BorderLayout.EAST);

        // handlers
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent ae) {
                final String message = messageTextField.getText();
                app.setMessage(message);
                app.printMessage();
            }
        });

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent ae) {
                final String history = String.join("\n", app.getHistory());
                historyTextArea.setText(history);
            }
        });
    }

    /**
     * Show the GUI.
     */
    public void start() {
        this.frame.setVisible(true);
    }

    /**
     * Entry point of the program.
     *
     * @param args program arguments
     */
    public static void main(final String... args) {
        final Controller app = new SimpleController();
        final SimpleGUI gui = new SimpleGUI(app);
        gui.start();
    }

}
