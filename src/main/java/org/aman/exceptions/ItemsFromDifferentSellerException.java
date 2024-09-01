package org.aman.exceptions;

public class ItemsFromDifferentSellerException extends RuntimeException{
    public ItemsFromDifferentSellerException(String message){
        super(message);
    }
}
