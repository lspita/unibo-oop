package it.unibo.mvc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * A very simple program using a graphical interface.
 *
 */
public final class SimpleGUI {

    private static final String TITLE = "Simple GUI";
    private static final double SCALE_FACTOR = 5;
    private static final String SAVE_BUTTON_TEXT = "Save";
    private static final String EXCEPTION_DIALOG_TITLE = "ERROR";

    private final Controller appController = new Controller();
    private final JFrame frame = new JFrame(TITLE);
    private final JTextArea textArea = new JTextArea();

    public SimpleGUI() {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(
                (int) (screenSize.getWidth() / SCALE_FACTOR),
                (int) (screenSize.getHeight() / SCALE_FACTOR)
        );
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JPanel mainPanel = new JPanel();
        frame.add(mainPanel);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(textArea, BorderLayout.CENTER);

        final JButton saveButton = new JButton(SAVE_BUTTON_TEXT);
        mainPanel.add(saveButton, BorderLayout.SOUTH);

        // Handlers
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                final String content = textArea.getText();
                try {
                    appController.saveToFile(content);
                } catch (IOException e) {
                    handleException(e);
                }
            }
        });
    }

    private void handleException(final Exception e) {
        JOptionPane.showMessageDialog(
                frame,
                e.getMessage(),
                EXCEPTION_DIALOG_TITLE,
                JOptionPane.ERROR_MESSAGE
        );
    }

    public void start() {
        this.frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SimpleGUI().start();
    }
}
