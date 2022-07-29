package ru.ivanov.miner.view;

import ru.ivanov.miner.common.GameType;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SettingsWindow {
    private final JDialog settingsDialog;
    private final Map<GameType, JRadioButton> radioButtonsMap = new HashMap<>(3);
    private final ButtonGroup radioGroup = new ButtonGroup();

    private GameTypeListener gameTypeListener;
    private GameType gameType;

    public JDialog getSettingsDialog() {
        return settingsDialog;
    }

    public SettingsWindow(JDialog settingsDialog) {
        this.settingsDialog = settingsDialog;

        GridBagLayout layout = new GridBagLayout();
        Container contentPane = settingsDialog.getContentPane();
        contentPane.setLayout(layout);

        int gridY = 0;
        contentPane.add(createRadioButton("Novice (10 mines, 9x9)", GameType.NOVICE, layout, gridY++));
        contentPane.add(createRadioButton("Medium (40 mines, 16x16)", GameType.MEDIUM, layout, gridY++));
        contentPane.add(createRadioButton("Expert (99 mines, 16x30)", GameType.EXPERT, layout, gridY++));

        contentPane.add(createOkButton(layout));
        contentPane.add(createCloseButton(layout));

        settingsDialog.setDefaultCloseOperation(settingsDialog.DISPOSE_ON_CLOSE);
        settingsDialog.setPreferredSize(new Dimension(300, 180));
        settingsDialog.setResizable(false);
        settingsDialog.pack();
        settingsDialog.setLocationRelativeTo(null);

        setGameType(GameType.NOVICE);
    }

    public void setGameType(GameType gameType) {
        JRadioButton radioButton = radioButtonsMap.get(gameType);

        if (radioButton == null) {
            throw new UnsupportedOperationException("Unknown game type: " + gameType);
        }

        this.gameType = gameType;
        radioGroup.setSelected(radioButton.getModel(), true);
    }

    public void setGameTypeListener(GameTypeListener gameTypeListener) {
        this.gameTypeListener = gameTypeListener;
    }

    private JRadioButton createRadioButton(String radioButtonText, GameType gameType, GridBagLayout layout, int gridY) {
        JRadioButton radioButton = new JRadioButton(radioButtonText);
        radioButton.addActionListener(e -> this.gameType = gameType);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = gridY;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 20, 0, 0);
        layout.setConstraints(radioButton, gbc);

        radioButtonsMap.put(gameType, radioButton);
        radioGroup.add(radioButton);

        return radioButton;
    }

    private JButton createOkButton(GridBagLayout layout) {
        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(80, 25));
        okButton.addActionListener(e -> {
            settingsDialog.dispose();
            if (gameTypeListener != null) {
                gameTypeListener.onGameTypeChanged(gameType);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(15, 0, 0, 0);
        layout.setConstraints(okButton, gbc);

        return okButton;
    }

    private JButton createCloseButton(GridBagLayout layout) {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(80, 25));
        cancelButton.addActionListener(e -> settingsDialog.dispose());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(15, 5, 0, 0);
        layout.setConstraints(cancelButton, gbc);

        return cancelButton;
    }
}
