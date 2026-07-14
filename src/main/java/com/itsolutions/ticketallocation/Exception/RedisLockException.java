package com.itsolutions.ticketallocation.Exception;

public class RedisLockException extends RuntimeException {
    public RedisLockException(String message) {
        super(message);
    }
}
