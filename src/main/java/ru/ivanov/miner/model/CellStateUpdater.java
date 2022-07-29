package ru.ivanov.miner.model;

public interface CellStateUpdater {
    void updateCell(int x, int y, Mines status);
    void markCell(int x, int y, CellType status, int mines);
}
