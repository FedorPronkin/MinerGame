package ru.ivanov.miner.model.bestPlayers;

public interface WinnerNameNotifier {
    void addWinnerNameListener(WinnerNameShower winnerNameShower);
    void notifyViewAskForName();
}
