package com.mycompany.mainframe;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MainFrame extends JFrame {

    private JTextArea textArea;
    private JFileChooser fileChooser;
    private File currentFile = null;

    public MainFrame() {
        initComponents();
    }

    private void initComponents() {

        // Main Window
        setTitle("Notepad - Aditya Mishra");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Text Area
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 18));

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        fileChooser = new JFileChooser();

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();

        // ================= FILE MENU =================
        JMenu fileMenu = new JMenu("File");

        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem saveAsItem = new JMenuItem("Save As");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // ================= EDIT MENU =================
        JMenu editMenu = new JMenu("Edit");

        JMenuItem cutItem = new JMenuItem("Cut");
        JMenuItem copyItem = new JMenuItem("Copy");
        JMenuItem pasteItem = new JMenuItem("Paste");
        JMenuItem selectAllItem = new JMenuItem("Select All");
        JMenuItem findItem = new JMenuItem("Find");
        JMenuItem replaceItem = new JMenuItem("Replace");

        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.addSeparator();
        editMenu.add(selectAllItem);
        editMenu.addSeparator();
        editMenu.add(findItem);
        editMenu.add(replaceItem);

        // ================= FORMAT MENU =================
        JMenu formatMenu = new JMenu("Format");

        JCheckBoxMenuItem wordWrapItem =
                new JCheckBoxMenuItem("Word Wrap");

        JMenuItem fontItem = new JMenuItem("Change Font");

        formatMenu.add(wordWrapItem);
        formatMenu.add(fontItem);

        // Add menus
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);

        setJMenuBar(menuBar);

        // ================= ACTIONS =================

        // NEW
        newItem.addActionListener(e -> {
            textArea.setText("");
            currentFile = null;
            setTitle("Untitled - Notepad");
        });

        // OPEN
        openItem.addActionListener(e -> openFile());

        // SAVE
        saveItem.addActionListener(e -> saveFile());

        // SAVE AS
        saveAsItem.addActionListener(e -> saveAsFile());

        // EXIT
        exitItem.addActionListener(e -> System.exit(0));

        // CUT
        cutItem.addActionListener(e -> textArea.cut());

        // COPY
        copyItem.addActionListener(e -> textArea.copy());

        // PASTE
        pasteItem.addActionListener(e -> textArea.paste());

        // SELECT ALL
        selectAllItem.addActionListener(e -> textArea.selectAll());

        // WORD WRAP
        wordWrapItem.addActionListener(e -> {
            boolean selected = wordWrapItem.isSelected();
            textArea.setLineWrap(selected);
            textArea.setWrapStyleWord(selected);
        });

        // FIND
        findItem.addActionListener(e -> findText());

        // REPLACE
        replaceItem.addActionListener(e -> replaceText());

        // FONT
        fontItem.addActionListener(e -> changeFont());
    }

    // ================= OPEN FILE =================
    private void openFile() {

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {

            currentFile = fileChooser.getSelectedFile();

            try (BufferedReader reader =
                         new BufferedReader(new FileReader(currentFile))) {

                textArea.read(reader, null);

                setTitle(currentFile.getName() + " - Notepad");

            } catch (IOException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to open file."
                );
            }
        }
    }

    // ================= SAVE FILE =================
    private void saveFile() {

        if (currentFile == null) {
            saveAsFile();
        } else {

            try (BufferedWriter writer =
                         new BufferedWriter(new FileWriter(currentFile))) {

                textArea.write(writer);

                JOptionPane.showMessageDialog(
                        this,
                        "File Saved Successfully"
                );

            } catch (IOException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to save file."
                );
            }
        }
    }

    // ================= SAVE AS =================
    private void saveAsFile() {

        int result = fileChooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {

            currentFile = fileChooser.getSelectedFile();

            try (BufferedWriter writer =
                         new BufferedWriter(new FileWriter(currentFile))) {

                textArea.write(writer);

                setTitle(currentFile.getName() + " - Notepad");

                JOptionPane.showMessageDialog(
                        this,
                        "File Saved Successfully"
                );

            } catch (IOException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to save file."
                );
            }
        }
    }

    // ================= FIND =================
    private void findText() {

        String find =
                JOptionPane.showInputDialog(this, "Enter text to find:");

        if (find != null && !find.isEmpty()) {

            String content = textArea.getText();

            int index = content.indexOf(find);

            if (index >= 0) {

                textArea.select(index, index + find.length());

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Text Not Found"
                );
            }
        }
    }

    // ================= REPLACE =================
    private void replaceText() {

        String find =
                JOptionPane.showInputDialog(this, "Enter text to replace:");

        if (find == null) {
            return;
        }

        String replace =
                JOptionPane.showInputDialog(this, "Replace with:");

        if (replace == null) {
            return;
        }

        textArea.setText(
                textArea.getText().replace(find, replace)
        );
    }

    // ================= CHANGE FONT =================
    private void changeFont() {

        String fontName =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Font Name:",
                        "Arial"
                );

        if (fontName != null) {

            String sizeInput =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Font Size:",
                            "18"
                    );

            try {

                int size = Integer.parseInt(sizeInput);

                textArea.setFont(
                        new Font(fontName, Font.PLAIN, size)
                );

            } catch (Exception e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Font Size"
                );
            }
        }
    }

    // ================= MAIN METHOD =================
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new MainFrame().setVisible(true);

        });
    }
}