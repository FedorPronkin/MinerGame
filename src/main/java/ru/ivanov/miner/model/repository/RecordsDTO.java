package ru.ivanov.miner.model.repository;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class RecordsDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final List<Player> bestPlayers;

    public List<Player> getRecordsList() {
        return bestPlayers;
    }

    public RecordsDTO(List<Player> bestPlayers) {
        this.bestPlayers = bestPlayers;
    }

}
