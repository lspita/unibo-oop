package it.unibo.mvc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A very simple program using a graphical interface.
 *
 */
public final class SimpleGUIWithFileChooser {

    private static final String TITLE = "Simple GUI";
    private static final double SCALE_FACTOR = 5;
    private static final String SAVE_BUTTON_TEXT = "Save";
    private static final String FILE_CHOOSER_BUTTON_TEXT = "Choose save file";
    private static final String EXCEPTION_DIALOG_TITLE = "ERROR";
    private static final String UNSUPPORTED_FILE_SELECTION_EXCEPTION_MESSAGE = "Unsupported operation on file selection";

    private final Controller appController = new Controller();
    private final JFrame frame = new JFrame(TITLE);
    private final JTextArea textArea = new JTextArea();
    private final JTextField saveFileLabel = new JTextField();
    private final JFileChooser fileChooser = new JFileChooser();

    public SimpleGUIWithFileChooser() {
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

        final JPanel filePickerPanel = new JPanel();
        mainPanel.add(filePickerPanel, BorderLayout.NORTH);
        filePickerPanel.setLayout(new BorderLayout());

        saveFileLabel.setEditable(false);
        syncSaveFileLabel();
        filePickerPanel.add(saveFileLabel, BorderLayout.CENTER);

        final JButton fileChooserButton = new JButton(FILE_CHOOSER_BUTTON_TEXT);
        filePickerPanel.add(fileChooserButton, BorderLayout.LINE_END);

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

        fileChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (fileChooser.showSaveDialog(frame)) {
                    case JFileChooser.APPROVE_OPTION -> {
                        appController.setFile(fileChooser.getSelectedFile());
                        syncSaveFileLabel();
                    }
                    case JFileChooser.CANCEL_OPTION -> {
                    }
                    default ->
                        handleException(new Exception(UNSUPPORTED_FILE_SELECTION_EXCEPTION_MESSAGE));
                }
            }
        });
    }

    private void syncSaveFileLabel() {
        final String currentSaveFilePath = appController.getFilePath();
        saveFileLabel.setText(currentSaveFilePath);
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
        new SimpleGUIWithFileChooser().start();
    }
}
