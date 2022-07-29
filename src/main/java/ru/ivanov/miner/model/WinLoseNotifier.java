package ru.ivanov.miner.model;

public interface WinLoseNotifier {
    void addWinLoseListener(WinLoseWindowShower winLoseWindowShower);
    void notifyViewLose();
    void notifyViewWin();
}
