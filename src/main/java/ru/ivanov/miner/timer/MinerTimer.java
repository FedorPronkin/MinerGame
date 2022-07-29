package ru.ivanov.miner.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MinerTimer implements MinerTimerInterface {
    private Timer timer;
    private int time;

    private final List<TimerUpdateTimeNotifyer> timerUpdateTimeNotifyer = new ArrayList<>();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void addListener(TimerUpdateTimeNotifyer timerUpdateTimeNotifyer) {
        this.timerUpdateTimeNotifyer.add(timerUpdateTimeNotifyer);
    }

    @Override
    public void stopTimer() {
        logger.info("Timer stopped!");
        timer.cancel();
    }

    @Override
    public void startTimer(){
        logger.info("Timer started!");
        timer = new Timer();
        timer.schedule(new TimerTask() {
            final Date dateBegin = new Date();
            @Override
            public void run() {
                Date dateEnd = new Date();
                time = (int) (dateEnd.getTime() - dateBegin.getTime());
                for (TimerUpdateTimeNotifyer notifyer : timerUpdateTimeNotifyer
                ) {
                    notifyer.updateTime(time / 1000);
                }
            }
        }, 1000,200);
    }
}
