package edu.lab.exceptions.storage;

public class NoSuchFileInStorageException extends StorageException{
    public NoSuchFileInStorageException(String msg) {
        super(msg);
    }
}
