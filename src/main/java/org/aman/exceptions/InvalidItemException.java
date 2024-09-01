package org.aman.exceptions;

import org.aman.service.InventoryService;

public class InvalidItemException extends RuntimeException{
    public InvalidItemException(String message){
        super(message);
    }
}
