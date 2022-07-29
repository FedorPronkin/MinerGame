package ru.ivanov.miner.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ivanov.miner.common.GameType;

public class FieldCounter implements Countable {
    private int height;
    private int width;
    private int minesAmount;
    private final GameType gameType;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getMinesAmount() {
        return minesAmount;
    }

    public FieldCounter(GameType gameType) {
        this.gameType = gameType;
        countParams();
    }

    private void countParams() {
        logger.info("Counting game type parameters...");
        switch (gameType) {
            case NOVICE -> {
                height = 9;
                width = 9;
                minesAmount = 10;
            }
            case MEDIUM -> {
                height = 16;
                width = 16;
                minesAmount = 40;
            }
            case EXPERT -> {
                height = 16;
                width = 30;
                minesAmount = 99;
            }
        }
        logger.info("Field height: " + height + ", field width: " + width + ", amount of mines: " + minesAmount);
    }
}
