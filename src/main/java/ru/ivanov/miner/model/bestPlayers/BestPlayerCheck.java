package ru.ivanov.miner.model.bestPlayers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ivanov.miner.model.BestPlayerCheckInterface;
import ru.ivanov.miner.model.repository.HighScoreTableUpdater;
import ru.ivanov.miner.model.repository.Player;
import ru.ivanov.miner.model.repository.RecordReaderWriterInterface;
import ru.ivanov.miner.common.GameType;

import java.util.List;

public class BestPlayerCheck implements WinnerNameNotifier, BestPlayerCheckInterface, HighScoreTableUpdater {

    private WinnerNameShower winnerNameShower;
    private String name;
    private List<Player> bestPlayers;
    RecordReaderWriterInterface recordReaderWriter;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void setName(String name) {
        this.name = name;
    }

    public BestPlayerCheck(RecordReaderWriterInterface recordReaderWriter){
        this.recordReaderWriter = recordReaderWriter;
    }

    public void checkAndWriteBestPlayer(GameType gameType, int time) {
        recordReaderWriter.setRecordsTableListener(this);
        recordReaderWriter.readRecordsTable();
        bestPlayers.stream()
                .filter(p -> p.gameType() == gameType && time < p.time())
                .findFirst()
                .ifPresent(p -> {
                    bestPlayers.remove(p);
                    logger.info("Opening window for recoding best player name...");
                    writeHighScore(bestPlayers, gameType, time);
                });
        if(bestPlayers.size() == 0){
            writeHighScore(bestPlayers, gameType, time);
        }
    }

    @Override
    public void gotHighScores(List<Player> bestPlayers) {
        this.bestPlayers = bestPlayers;
    }

    private void writeHighScore(List<Player> bestPlayers, GameType gameType, int time) {
        logger.info("We`ve got a winner! Asking for name to record him/her!");
        notifyViewAskForName();
        bestPlayers.add(new Player(gameType, name, time));
        recordReaderWriter.writeRecordsTable(bestPlayers);
    }

    @Override
    public void notifyViewAskForName() {
        if (winnerNameShower != null) {
            winnerNameShower.showWinnerNameDialog();
        }
    }

    @Override
    public void addWinnerNameListener(WinnerNameShower winnerNameShower) {
        this.winnerNameShower = winnerNameShower;
    }
}
