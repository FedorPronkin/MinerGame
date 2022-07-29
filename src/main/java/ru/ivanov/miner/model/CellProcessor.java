package ru.ivanov.miner.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ivanov.miner.common.GameType;
import ru.ivanov.miner.model.cellState.CellMarked;
import ru.ivanov.miner.model.cellState.CellOpened;
import ru.ivanov.miner.model.cellState.CellUnmarked;
import ru.ivanov.miner.model.cellState.StateContext;
import ru.ivanov.miner.timer.TimerUpdateTimeNotifyer;

public class CellProcessor implements CellStateNotifier, WinLoseNotifier, TimerUpdateTimeNotifyer,
        StartGameNotifier, TimerLabelNotifier {

    private GameType gameType;
    private StateContext[][] cells;
    private int height;
    private int width;
    private int minesAmount;
    private int time;
    private boolean newGame;

    private CellStateUpdater cellStateUpdater;
    private WinLoseWindowShower winLoseWindowShower;
    private GameStarter gameStarter;
    private BackgroundField field;
    private TimerLabelUpdater timerLabelUpdater;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public void startGame() {
        logger.info("Starting game...");
        newGame = true;
        Countable countable = new FieldCounter(gameType);
        height = countable.getHeight();
        width = countable.getWidth();
        minesAmount = countable.getMinesAmount();
        notifyViewStartGame();
        resetFieldToEmpty();
    }

    public void resetFieldToEmpty() {
        logger.info("Resetting game field before starting...");
        field = new BackgroundField();
        cells = field.makeEmptyField(height, width);
        logger.info("The game field is null! Waiting for first cell open...");
    }

    public void openCell(int row, int column) {
        if (newGame) {
            logger.info("First click on a new field! Generating mines...");
            cells = field.putMines(minesAmount, row, column);
            logger.info("Mines coordinates is ready. Let`s play!");
            newGame = false;
        }

        if (cells[row][column].getState() instanceof CellUnmarked) {

            cells[row][column].open();

            notifyViewOpenCell(row, column, cells[row][column].getMines());

            if (cells[row][column].getMines() == Mines.EMPTY) {
                lookForMinesAround(row, column, LookAroundAction.OPEN_EMPTY_CELLS);
            }
        }
        if (cells[row][column].getMines() == Mines.BOMB) {
            notifyViewLose();
        }
    }

    private int lookForMinesAround(int row, int column, LookAroundAction whatToDo) {
        int result = 0;

        for (int i = row - 1; i <= row + 1; i++) {
            if (i < 0 || i > height - 1) {
                continue;
            }
            for (int j = column - 1; j <= column + 1; j++) {
                if (j < 0 || j > width - 1) {
                    continue;
                }
                if (i == row && j == column) {
                    continue;
                }
                switch (whatToDo) {
                    case COUNT_MARKED_MINES -> {
                        if (cells[i][j].getState() instanceof CellMarked) {
                            result++;
                        }
                    }
                    case OPEN_EMPTY_CELLS -> {
                        if (cells[i][j].getState() instanceof CellUnmarked) {
                            openCell(i, j);
                        }
                    }
                }
            }
        }
        return result;
    }

    public void openCellsAround(int row, int column) {
        if (cells[row][column].getMines() != Mines.EMPTY) {
            int openedMines = lookForMinesAround(row, column, LookAroundAction.COUNT_MARKED_MINES);
            if (openedMines == cells[row][column].getMines().getName()) {
                lookForMinesAround(row, column, LookAroundAction.OPEN_EMPTY_CELLS);
            }
        }

    }

    public void markAsMine(int row, int column) {
        if (((cells[row][column].getState() instanceof CellUnmarked) && countMarkedAndOpened(false) < minesAmount) ||
                (cells[row][column].getState() instanceof CellMarked)){
                cells[row][column].touch();
                notifyViewMarkCell(row, column, cells[row][column].getStatus());
        }
    }

    private int countMarkedAndOpened(boolean countAll) {
        int result = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (cells[i][j].getState() instanceof CellMarked){
                    result++;
                }
                if((cells[i][j].getState() instanceof CellOpened) && countAll) {
                    result++;
                }
            }
        }
        return result;
    }

    public void checkIfWin() {
        if (countMarkedAndOpened(true) == (width * height)) {
            notifyViewWin();
        }
    }

    @Override
    public void addCellListener(CellStateUpdater cellStateUpdater) {
        this.cellStateUpdater = cellStateUpdater;
    }

    @Override
    public void addWinLoseListener(WinLoseWindowShower winLoseWindowShower) {
        this.winLoseWindowShower = winLoseWindowShower;
    }

    @Override
    public void addStartListener(GameStarter gameStarter) {
        this.gameStarter = gameStarter;
    }

    @Override
    public void addTimerUpdateListener(TimerLabelUpdater timerLabelUpdater) {
        this.timerLabelUpdater = timerLabelUpdater;
    }

    @Override
    public void notifyViewOpenCell(int x, int y, Mines status) {
        if (cellStateUpdater != null) {
            cellStateUpdater.updateCell(x, y, status);
        }
    }

    @Override
    public void notifyViewMarkCell(int x, int y, CellType status) {
        if (cellStateUpdater != null) {
            cellStateUpdater.markCell(x, y, status, minesAmount - countMarkedAndOpened(false));
        }
    }

    @Override
    public void notifyViewLose() {
        if(winLoseWindowShower != null){
            winLoseWindowShower.showLose();
        }
    }

    @Override
    public void notifyViewWin() {
        if (winLoseWindowShower != null) {
            winLoseWindowShower.showWin();
        }
    }

    @Override
    public void notifyViewStartGame() {
        if(gameStarter != null) {
            gameStarter.startGame(height, width, minesAmount);
        }
    }

    @Override
    public void updateTime(int time) {
        this.time = time;
        notifyViewTimerUpdated();
    }

    @Override
    public void notifyViewTimerUpdated() {
        if (timerLabelUpdater != null) {
            timerLabelUpdater.updateTimerLabel(time);
        }
    }

}