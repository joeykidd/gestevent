package com.kumojin.gestevent.service;

public class NameAlreadyUsedException extends RuntimeException {

    public NameAlreadyUsedException() {
        super("Name is already in use");
    }
}
