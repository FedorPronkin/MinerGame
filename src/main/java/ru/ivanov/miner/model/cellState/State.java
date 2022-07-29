package ru.ivanov.miner.model.cellState;

import ru.ivanov.miner.model.CellType;

interface State {
    void touch(StateContext stateContext);
    void open(StateContext stateContext);
    CellType getStatus();
}
