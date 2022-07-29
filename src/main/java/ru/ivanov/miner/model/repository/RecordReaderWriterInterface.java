package ru.ivanov.miner.model.repository;

import java.util.List;

public interface RecordReaderWriterInterface {
    void readRecordsTable();
    void writeRecordsTable(List<Player> bestPlayers);
    void setRecordsTableListener(HighScoreTableUpdater highScoreTableUpdater);
}
