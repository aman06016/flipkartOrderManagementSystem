package org.aman.exceptions;

public class InvalidInventoryException extends RuntimeException{

    public InvalidInventoryException(String message){
        super(message);
    }
}
