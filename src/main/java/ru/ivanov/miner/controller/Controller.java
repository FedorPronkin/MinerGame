package ru.ivanov.miner.controller;

import ru.ivanov.miner.common.GameType;
import ru.ivanov.miner.model.BestPlayerCheckInterface;
import ru.ivanov.miner.model.CellProcessor;
import ru.ivanov.miner.model.repository.RecordReaderWriterInterface;
import ru.ivanov.miner.timer.MinerTimerInterface;
import ru.ivanov.miner.view.ButtonType;
import ru.ivanov.miner.view.MainWindow;

public class Controller implements ControllerInterface {
    private final CellProcessor cellProcessor;
    private BestPlayerCheckInterface bestPlayerCheckInterface;
    private final MainWindow mainWindow;
    private MinerTimerInterface timer;
    private GameType gameType;
    private boolean timerIsOn;
    RecordReaderWriterInterface recordReaderWriter;

    public Controller(CellProcessor cellProcessor, MainWindow mainWindow, RecordReaderWriterInterface recordReaderWriter) {
        this.cellProcessor = cellProcessor;
        this.mainWindow = mainWindow;
        this.recordReaderWriter = recordReaderWriter;
    }

    @Override
    public void someMouseButtonClicked(int x, int y, ButtonType buttonType) {
        switch (buttonType) {
            case LEFT_BUTTON -> {
                cellProcessor.openCell(x, y);
                if(!timerIsOn){
                    startTimer();
                    timerIsOn = true;
                }
            }
            case RIGHT_BUTTON -> cellProcessor.markAsMine(x, y);
            case TWO_BUTTONS -> cellProcessor.openCellsAround(x, y);
        }
        cellProcessor.checkIfWin();
    }

    @Override
    public void readHighScores() {
        recordReaderWriter.setRecordsTableListener(mainWindow);
        recordReaderWriter.readRecordsTable();
    }

    @Override
    public void startGame(){
        timerIsOn = false;
        cellProcessor.startGame();
    }

    @Override
    public void setGameType(GameType gameType) {
        this.gameType = gameType;
        cellProcessor.setGameType(gameType);
    }

    @Override
    public void setWinnerName(String name){
        bestPlayerCheckInterface.setName(name);
    }

    @Override
    public void addTimerListener(MinerTimerInterface minerTimerInterface) {
        timer = minerTimerInterface;
    }

    @Override
    public void startTimer() {
        if (timer != null) {
            timer.startTimer();
        }
    }

    @Override
    public void stopTimer() {
        if (timer != null) {
            timer.stopTimer();
            timerIsOn = false;
        }
    }

    @Override
    public void CheckBestPlayer(int time) {
        if (bestPlayerCheckInterface != null) {
            bestPlayerCheckInterface.checkAndWriteBestPlayer(gameType, time);
        }
    }

    @Override
    public void addBestPlayerCheckListener(BestPlayerCheckInterface bestPlayerCheckInterface) {
        this.bestPlayerCheckInterface = bestPlayerCheckInterface;
    }
}
