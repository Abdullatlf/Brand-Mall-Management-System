package Exceptions;

public class CustomerHasPurchasesException extends Exception {
    public CustomerHasPurchasesException(String message) {
        super(message);
    }
}