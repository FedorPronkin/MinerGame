package ru.ivanov.miner.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ivanov.miner.controller.ControllerInterface;
import ru.ivanov.miner.model.*;
import ru.ivanov.miner.model.bestPlayers.WinnerNameShower;
import ru.ivanov.miner.model.repository.HighScoreTableUpdater;
import ru.ivanov.miner.model.repository.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MainWindow implements WinnerNameShower, WinLoseWindowShower, CellStateUpdater, GameStarter,
        TimerLabelUpdater, HighScoreTableUpdater {
    private final JFrame mainFrame;

    private final Container contentPane;
    private final GridBagLayout mainLayout;

    private ControllerInterface controller;

    private JMenuItem newGameMenu;

    private JMenuItem highScoresMenu;
    private JMenuItem settingsMenu;
    private JMenuItem exitMenu;
    private CellEventListener listener;

    private JButton[][] cellButtons;

    private JLabel timerLabel;
    private JLabel bombsCounterLabel;
    private List<Player> bestPlayers;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public MainWindow(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);

        createMenu();

        contentPane = mainFrame.getContentPane();
        mainLayout = new GridBagLayout();
        contentPane.setLayout(mainLayout);

        contentPane.setBackground(new Color(144, 158, 184));
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        gameMenu.add(newGameMenu = new JMenuItem("New Game"));
        gameMenu.addSeparator();
        gameMenu.add(highScoresMenu = new JMenuItem("High Scores"));
        gameMenu.add(settingsMenu = new JMenuItem("Settings"));
        gameMenu.addSeparator();
        gameMenu.add(exitMenu = new JMenuItem("Exit"));

        menuBar.add(gameMenu);
        mainFrame.setJMenuBar(menuBar);
    }

    public void setController(ControllerInterface controller) {
        this.controller = controller;
    }

    public void setNewGameMenuAction(ActionListener listener) {
        newGameMenu.addActionListener(listener);
    }

    public void setHighScoresMenuAction(ActionListener listener) {
        highScoresMenu.addActionListener(listener);
    }

    public void setSettingsMenuAction(ActionListener listener) {
        settingsMenu.addActionListener(listener);
    }

    public void setExitMenuAction(ActionListener listener) {
        exitMenu.addActionListener(listener);
    }

    public void setCellListener(CellEventListener listener) {
        this.listener = listener;
    }

    public void setCellImage(int x, int y, GameImage gameImage) {
        cellButtons[x][y].setIcon(gameImage.getImageIcon());
    }

    public void setBombsCount(int bombsCount) {
        bombsCounterLabel.setText(String.valueOf(bombsCount));
    }

    public void setTimerValue(int value) {
        contentPane.repaint();
        timerLabel.setText(String.valueOf(value));
    }

    public void createGameField(int rowsCount, int colsCount) {
        contentPane.removeAll();
        mainFrame.setPreferredSize(new Dimension(20 * colsCount + 70, 20 * rowsCount + 110));

        addButtonsPanel(createButtonsPanel(rowsCount, colsCount));
        addTimerImage();
        addTimerLabel(timerLabel = new JLabel("0"));
        addBombCounter(bombsCounterLabel = new JLabel("0"));
        addBombCounterImage();
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
    }

    private JPanel createButtonsPanel(int numberOfRows, int numberOfCols) {
        cellButtons = new JButton[numberOfRows][numberOfCols];
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setPreferredSize(new Dimension(20 * numberOfCols, 20 * numberOfRows));
        buttonsPanel.setLayout(new GridLayout(numberOfRows, numberOfCols, 0, 0));

        for (int row = 0; row < numberOfRows; row++) {
            for (int col = 0; col < numberOfCols; col++) {
                final int x = row;
                final int y = col;

                cellButtons[row][col] = new JButton(GameImage.CLOSED.getImageIcon());
                cellButtons[row][col].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (listener == null) {
                            return;
                        }

                        switch (e.getButton()) {
                            case MouseEvent.BUTTON1 -> listener.onMouseClick(x, y, ButtonType.LEFT_BUTTON);
                            case MouseEvent.BUTTON3 -> listener.onMouseClick(x, y, ButtonType.RIGHT_BUTTON);
                        }
                        isRightPressed = false;
                        isLeftPressed = false;
                    }

                    boolean isLeftPressed = false;
                    boolean isRightPressed = false;

                    @Override
                    public void mousePressed(MouseEvent e) {

                        if (SwingUtilities.isLeftMouseButton(e)) {
                            isLeftPressed = true;

                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            isRightPressed = true;
                        }

                        if (isLeftPressed && isRightPressed) {
                            listener.onMouseClick(x, y, ButtonType.TWO_BUTTONS);
                            isLeftPressed = false;
                            isRightPressed = false;
                        }

                    }
                });

                buttonsPanel.add(cellButtons[x][y]);
            }
        }
        return buttonsPanel;
    }

    private void addButtonsPanel(JPanel buttonsPanel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.insets = new Insets(20, 20, 5, 20);
        mainLayout.setConstraints(buttonsPanel, gbc);
        contentPane.add(buttonsPanel);
    }

    private void addTimerImage() {
        JLabel label = new JLabel(GameImage.TIMER.getImageIcon());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 20, 0, 0);
        gbc.weightx = 0.1;
        mainLayout.setConstraints(label, gbc);
        contentPane.add(label);
    }

    private void addTimerLabel(JLabel timerLabel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 5, 0, 0);
        mainLayout.setConstraints(timerLabel, gbc);
        contentPane.add(timerLabel);
    }

    private void addBombCounter(JLabel bombsCounterLabel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weightx = 0.7;
        mainLayout.setConstraints(bombsCounterLabel, gbc);
        contentPane.add(bombsCounterLabel);
    }

    private void addBombCounterImage() {
        JLabel label = new JLabel(GameImage.BOMB_ICON.getImageIcon());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 3;
        gbc.insets = new Insets(0, 5, 0, 20);
        gbc.weightx = 0.1;
        mainLayout.setConstraints(label, gbc);
        contentPane.add(label);
    }

    public void showSettings(){
        JDialog settingsDialog = new JDialog(mainFrame, "Settings", true);
        SettingsWindow settingsWindow = new SettingsWindow(settingsDialog);
        settingsWindow.setGameTypeListener(controller::setGameType);
        settingsWindow.getSettingsDialog().setVisible(true);
        controller.startGame();
    }

    @Override
    public void updateCell(int x, int y, Mines status) {
        setCellImage(x, y, GameImage.valueOf(status.toString()));
    }

    @Override
    public void showWin() {
        logger.info("We`ve got a winner!");
        controller.stopTimer();
        controller.CheckBestPlayer(Integer.parseInt(timerLabel.getText()));
        JDialog winDialog = new JDialog(mainFrame, "Great game!", true);
        WinWindow winWindow = new WinWindow(winDialog);
        winWindow.setExitListener(e -> mainFrame.dispose());
        winWindow.setNewGameListener(e -> controller.startGame());
        winDialog.setVisible(true);
    }

    @Override
    public void showLose() {
        logger.info("We`ve got a looser!");
        controller.stopTimer();
        JDialog looseDialog = new JDialog(mainFrame, "Looser", true);
        LoseWindow loseWindow = new LoseWindow(looseDialog);
        loseWindow.setExitListener(e -> mainFrame.dispose());
        loseWindow.setNewGameListener(e -> controller.startGame());
        looseDialog.setVisible(true);
    }

    @Override
    public void markCell(int x, int y, CellType status, int mines) {
        bombsCounterLabel.setText(String.valueOf(mines));
        setCellImage(x, y, GameImage.valueOf(status.toString()));
    }

    @Override
    public void updateTimerLabel(int time) {
        setTimerValue(time);
    }

    @Override
    public void showWinnerNameDialog() {
        JDialog recordsDialog = new JDialog(mainFrame, "New Record", true);
        RecordsWindow recordsWindow = new RecordsWindow(recordsDialog);
        recordsWindow.setNameListener(name -> controller.setWinnerName(name));
        recordsDialog.setVisible(true);
    }

    @Override
    public void startGame(int height, int width, int minesAmount) {
        createGameField(height, width);
        setTimerValue(0);
        setBombsCount(minesAmount);
    }

    public void showHighScores() {
        JDialog highScoresDialog = new JDialog(mainFrame, "High Scores", true);
        HighScoresWindow highScoresWindow = new HighScoresWindow(highScoresDialog);
        controller.readHighScores();
        highScoresWindow.putHighScores(bestPlayers);
        highScoresWindow.getHighScoreDialog().setVisible(true);
    }

    @Override
    public void gotHighScores(List<Player> bestPlayers) {
        this.bestPlayers = bestPlayers;
    }
}
