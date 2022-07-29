package ru.ivanov.miner.model.repository;

import java.util.List;

public interface ReadTableNotifier {
    void setRecordsTableListener(HighScoreTableUpdater highScoreTableUpdater);
    void notifyListenerGotHighScores(List<Player> bestPlayers);
}
