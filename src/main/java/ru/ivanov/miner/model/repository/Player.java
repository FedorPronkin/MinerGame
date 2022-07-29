package ru.ivanov.miner.model.repository;

import ru.ivanov.miner.common.GameType;

import java.io.Serializable;

public record Player(GameType gameType, String name, int time) implements Serializable {

}
