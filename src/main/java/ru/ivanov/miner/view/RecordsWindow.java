package ru.ivanov.miner.view;

import javax.swing.*;
import java.awt.*;

public class RecordsWindow {
    private RecordNameListener nameListener;
    private final JDialog jDialog;

    public RecordsWindow(JDialog jDialog) {
        this.jDialog = jDialog;

        JTextField nameField = new JTextField();

        GridLayout layout = new GridLayout(3, 1);
        Container contentPane = jDialog.getContentPane();
        contentPane.setLayout(layout);

        contentPane.add(new JLabel("Enter your name:"));
        contentPane.add(nameField);
        contentPane.add(createOkButton(nameField));

        jDialog.setDefaultCloseOperation(jDialog.DISPOSE_ON_CLOSE);
        jDialog.setPreferredSize(new Dimension(210, 120));
        jDialog.setResizable(false);
        jDialog.pack();
        jDialog.setLocationRelativeTo(null);
    }

    public void setNameListener(RecordNameListener nameListener) {
        this.nameListener = nameListener;
    }

    private JButton createOkButton(JTextField nameField) {
        JButton button = new JButton("OK");
        button.addActionListener(e -> {
            jDialog.dispose();
            if (nameListener != null) {
                nameListener.onRecordNameEntered(nameField.getText());
            }
        });
        return button;
    }


}
