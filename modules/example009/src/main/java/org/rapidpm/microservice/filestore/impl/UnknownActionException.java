package org.rapidpm.microservice.filestore.impl;

/**
 * Created by b.bosch on 13.10.2015.
 */
public class UnknownActionException extends Exception {
    public UnknownActionException(String message) {
        super(message);
    }
}
