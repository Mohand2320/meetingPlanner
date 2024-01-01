package com.meetingPlanner.exception;

public class EntityAleadyExisteException extends RuntimeException {

    public EntityAleadyExisteException() {
    }

    public EntityAleadyExisteException(String message) {
        super(message);
    }
}
