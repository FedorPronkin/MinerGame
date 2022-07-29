package ru.ivanov.miner.model.repository;

import java.util.List;

public interface HighScoreTableUpdater {
    void gotHighScores(List<Player> bestPlayers);
}
