package ru.ivanov.miner.controller;

import ru.ivanov.miner.model.BestPlayerCheckInterface;
import ru.ivanov.miner.timer.MinerTimerInterface;
import ru.ivanov.miner.view.ButtonType;
import ru.ivanov.miner.common.GameType;

public interface ControllerInterface {

    void someMouseButtonClicked(int x, int y, ButtonType buttonType);

    void readHighScores();

    void setGameType(GameType gameType);

    void startGame();

    void setWinnerName(String name);

    void stopTimer();

    void addTimerListener(MinerTimerInterface minerTimerInterface);

    void startTimer();

    void CheckBestPlayer(int time);

    void addBestPlayerCheckListener(BestPlayerCheckInterface bestPlayerCheckInterface);
}
