package ru.ivanov.miner.timer;

public interface MinerTimerInterface {
    void addListener(TimerUpdateTimeNotifyer timerUpdateTimeNotifyer);
    void stopTimer();
    void startTimer();
}
