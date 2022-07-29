package ru.ivanov.miner.model;

public enum Mines {
    EMPTY(0),
    NUM_1(1),
    NUM_2(2),
    NUM_3(3),
    NUM_4(4),
    NUM_5(5),
    NUM_6(6),
    NUM_7(7),
    NUM_8(8),
    BOMB(9);

    private final int name;

    Mines(int name){
        this.name = name;
    }

    public int getName(){
        return name;
    }

    public static Mines getValue(int name){
        for (int i = 0; i < values().length; i++) {
            if(values()[i].getName() == name){
                return values()[i];
            }
        }
        return null;
    }
}
