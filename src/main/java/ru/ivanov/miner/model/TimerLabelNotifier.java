package ru.ivanov.miner.model;

public interface TimerLabelNotifier {
    void addTimerUpdateListener(TimerLabelUpdater timerLabelUpdater);
    void notifyViewTimerUpdated();
}
