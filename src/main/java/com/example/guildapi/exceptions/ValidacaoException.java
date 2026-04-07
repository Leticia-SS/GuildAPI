package com.example.guildapi.exceptions;

public class ValidacaoException extends RuntimeException{
    public ValidacaoException(String message) {super(message);}

    public ValidacaoException(String message, Throwable cause) {super(message, cause);}
}
