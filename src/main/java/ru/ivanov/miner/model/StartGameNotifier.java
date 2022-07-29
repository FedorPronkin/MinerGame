package ru.ivanov.miner.model;

public interface StartGameNotifier {
    void addStartListener(GameStarter gameStarter);
    void notifyViewStartGame();
}
