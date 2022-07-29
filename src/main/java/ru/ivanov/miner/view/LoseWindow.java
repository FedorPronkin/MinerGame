package ru.ivanov.miner.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoseWindow {

    private final JDialog jDialog;
    private ActionListener newGameListener;
    private ActionListener exitListener;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public LoseWindow(JDialog looseDialog) {
        jDialog = looseDialog;

        GridBagLayout layout = new GridBagLayout();
        Container contentPane = jDialog.getContentPane();
        contentPane.setLayout(layout);

        contentPane.add(createLoseLabel(layout));
        contentPane.add(createNewGameButton(layout));
        contentPane.add(createExitButton(layout));

        jDialog.setDefaultCloseOperation(jDialog.DISPOSE_ON_CLOSE);
        jDialog.setPreferredSize(new Dimension(300, 130));
        jDialog.setResizable(false);
        jDialog.pack();
        jDialog.setLocationRelativeTo(null);
    }

    public void setNewGameListener(ActionListener newGameListener) {
        this.newGameListener = newGameListener;
    }

    public void setExitListener(ActionListener exitListener) {
        this.exitListener = exitListener;
    }

    private JLabel createLoseLabel(GridBagLayout layout) {
        JLabel label = new JLabel("You lose!");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        layout.setConstraints(label, gbc);
        return label;
    }

    private JButton createNewGameButton(GridBagLayout layout) {
        JButton newGameButton = new JButton("New game");
        newGameButton.setPreferredSize(new Dimension(100, 25));

        newGameButton.addActionListener(e -> {
            jDialog.dispose();

            if (newGameListener != null) {
                newGameListener.actionPerformed(e);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(15, 0, 0, 0);
        layout.setConstraints(newGameButton, gbc);

        return newGameButton;
    }

    private JButton createExitButton(GridBagLayout layout) {
        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(100, 25));

        exitButton.addActionListener(e -> {
            logger.info("Application stopped!");
            jDialog.dispose();
            if (exitListener != null) {
                exitListener.actionPerformed(e);

            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(15, 5, 0, 0);
        layout.setConstraints(exitButton, gbc);

        return exitButton;
    }
}
