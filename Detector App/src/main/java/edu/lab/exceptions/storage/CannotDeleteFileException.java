package edu.lab.exceptions.storage;

public class CannotDeleteFileException extends StorageException{
    public CannotDeleteFileException(String msg) {
        super(msg);
    }
}
