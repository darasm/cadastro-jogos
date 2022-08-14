package com.dontclosethedoor.game.exception;

public class ObjectNotFound extends RuntimeException {

    public ObjectNotFound() {
        super("This game was not found!");
    }

}
