package org.aman.exceptions;

public class NotEnoughInventoryException extends RuntimeException{
    public NotEnoughInventoryException(String message){
        super(message);
    }
}
