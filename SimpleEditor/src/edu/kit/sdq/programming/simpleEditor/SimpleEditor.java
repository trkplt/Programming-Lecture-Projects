/*
 * Copyright (c) 2020, IPD Koziolek.
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package edu.kit.sdq.programming.simpleEditor;

import static javax.swing.SwingUtilities.invokeLater;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class SimpleEditor extends JFrame {

    private static final String APPLICATION_PATH = ".";

    private static final String CLEAR = "Clear";

    private static final String DECREASE_SIZE = "decreaseSize";

    private static final String EMPTY = "";

    private static final String INCREASE_SIZE = "increaseSize";

    private static final String JAVA = "java";

    private static final String LINE_SEPARATOR = System.lineSeparator();

    private static final String MINUS = "-";

    private static final String PLUS = "+";

    private static final String SELECT = "Select";

    private static final long serialVersionUID = 4611197640800958406L;

    private static final String TEXT_SIZE = "Text size";

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final Exception e) {
            e.printStackTrace();
        }

        invokeLater(() -> {
            new SimpleEditor().setVisible(true);
        });
    }

    private final JFileChooser chooser;

    private File currentDirectory;

    private Map<String, String> javaFiles;

    private final JList<String> list;

    private final JSplitPane splitPane;

    private final JTextArea textArea;

    private final JScrollPane textScrollPane;

    private SimpleEditor() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        currentDirectory = Paths.get(APPLICATION_PATH).toAbsolutePath().normalize().toFile();
        setTitle(currentDirectory.toString());

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        textArea = new JTextArea();
        textScrollPane = new JScrollPane(textArea);
        chooser = new JFileChooser(currentDirectory);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        addShortcuts();

        list = new JList<>();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(e -> {
            textArea.setText(javaFiles.get(list.getSelectedValue()));
            invokeLater(() -> textScrollPane.getVerticalScrollBar().setValue(0));
        });
        updateSelectedFiles();

        final JPanel leftScrollPane = new JPanel(new BorderLayout());
        leftScrollPane.add(createFileChooserPanel(), BorderLayout.PAGE_START);
        leftScrollPane.add(new JScrollPane(list), BorderLayout.CENTER);
        leftScrollPane.add(createFontSizePanel(), BorderLayout.PAGE_END);

        splitPane.add(leftScrollPane);
        splitPane.add(textScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);

        final Dimension minimumSize = new Dimension(100, 50);
        leftScrollPane.setMinimumSize(minimumSize);
        textScrollPane.setMinimumSize(minimumSize);

        splitPane.setPreferredSize(new Dimension(600, 400));

        setContentPane(splitPane);
        pack();
        setLocationRelativeTo(null);
    }

    private void addShortcuts() {
        final KeyStroke strokeIncrease = KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, InputEvent.CTRL_DOWN_MASK);
        textArea.getInputMap().put(strokeIncrease, INCREASE_SIZE);
        textArea.getActionMap().put(INCREASE_SIZE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleEditor.this.increaseSize();
            }
        });
        final KeyStroke strokeDecrease = KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.CTRL_DOWN_MASK);
        textArea.getInputMap().put(strokeDecrease, DECREASE_SIZE);
        textArea.getActionMap().put(DECREASE_SIZE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleEditor.this.decreaseSize();
            }
        });
    }

    private JPanel createFileChooserPanel() {
        final JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        final JButton fileChooser = new JButton(SELECT);
        fileChooser.addActionListener(e -> {
            final int state = chooser.showOpenDialog(fileChooser);
            if (state == JFileChooser.APPROVE_OPTION) {
                currentDirectory = chooser.getCurrentDirectory();
                setTitle(currentDirectory.toString());
                updateSelectedFiles();
            } else {
                chooser.setCurrentDirectory(currentDirectory);
            }
        });
        panel.add(fileChooser);

        final JButton clearButton = new JButton(CLEAR);
        clearButton.addActionListener(e -> {
            list.clearSelection();
            textArea.setText(EMPTY);
        });
        panel.add(clearButton);

        return panel;
    }

    private JPanel createFontSizePanel() {
        final JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        final JButton buttonIncrease = new JButton(PLUS);
        buttonIncrease.addActionListener(e -> increaseSize());
        panel.add(buttonIncrease);

        panel.add(new JLabel(TEXT_SIZE));

        final JButton buttonDecrease = new JButton(MINUS);
        buttonDecrease.addActionListener(e -> decreaseSize());
        panel.add(buttonDecrease);

        return panel;
    }

    private void decreaseSize() {
        final Font font = textArea.getFont();
        textArea.setFont(font.deriveFont((float) (font.getSize() - 1.0)));
    }

    private Map<String, String> findJavaFiles(final Path path) {
        try {
            return Files.find(path, 10, this::isJavaFile).parallel()
                    .collect(Collectors.toMap(p -> path.relativize(p).toString(), this::readFile));
        } catch (final Exception e) {
            return Map.of(path.toString(), e.getMessage());
        }
    }

    private void increaseSize() {
        final Font font = textArea.getFont();
        textArea.setFont(font.deriveFont((float) (font.getSize() + 1.0)));
    }

    private boolean isJavaFile(Path filePath, BasicFileAttributes fileAttributes) {
        return fileAttributes.isRegularFile() && filePath.getFileName().toString().toLowerCase().endsWith(JAVA);
    }

    private String readFile(final Path path) {
        try (final BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            return reader.lines().reduce(EMPTY, (s1, s2) -> s1 + LINE_SEPARATOR + s2).strip();
        } catch (final Exception e) {
            return e.getMessage();
        }
    }

    private void updateSelectedFiles() {
        javaFiles = findJavaFiles(currentDirectory.toPath().toAbsolutePath().normalize());
        list.clearSelection();
        final DefaultListModel<String> model = new DefaultListModel<>();
        model.addAll(javaFiles.keySet().stream().sorted().collect(Collectors.toList()));
        list.setModel(model);
    }

}
