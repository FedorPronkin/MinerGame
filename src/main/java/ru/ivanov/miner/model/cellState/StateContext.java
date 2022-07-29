package ru.ivanov.miner.model.cellState;

import ru.ivanov.miner.model.CellType;
import ru.ivanov.miner.model.Mines;

public class StateContext {

    private Mines mines;
    private State state;

    public CellType getStatus(){
        return state.getStatus();
    }

    public Mines getMines(){
        return mines;
    }

    public void setMines(Mines mines){
        this.mines = mines;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public StateContext(Mines mines) {
        this.mines = mines;
        state = new CellUnmarked();
    }

    public void touch() { state.touch(this); }

    public void open() { state.open(this);}
}
