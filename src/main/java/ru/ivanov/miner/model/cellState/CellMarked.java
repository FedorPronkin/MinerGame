package ru.ivanov.miner.model.cellState;

import ru.ivanov.miner.model.CellType;

public class CellMarked implements State {

    private final CellType status = CellType.MARKED;

    public CellType getStatus() {
        return status;
    }

    @Override
    public void touch(StateContext context) {
        context.setState(new CellUnmarked());
    }

    @Override
    public void open(StateContext stateContext) {
        /*Do nothing*/
    }
}