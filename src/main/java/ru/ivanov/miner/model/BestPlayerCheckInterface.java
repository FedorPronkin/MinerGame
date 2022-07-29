package ru.ivanov.miner.model;

import ru.ivanov.miner.common.GameType;

public interface BestPlayerCheckInterface {
    void checkAndWriteBestPlayer(GameType gameType, int time);
    void setName(String name);
}
