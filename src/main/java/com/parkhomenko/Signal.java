package com.parkhomenko;

/**
 * @author Dmytro Parkhomenko
 *         Created on 11.11.16.
 */

public class Signal {
    private boolean isWorkCompleted = false;

    public synchronized boolean isWorkCompleted() {
        return isWorkCompleted;
    }

    public synchronized void setWorkCompleted(boolean workCompleted) {
        isWorkCompleted = workCompleted;
    }
}
