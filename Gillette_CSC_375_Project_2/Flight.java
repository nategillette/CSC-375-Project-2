package com.flights;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Flight {
    private int passengers;

    private String flightNum;
    private ReadWriteLock rwl = new ReentrantReadWriteLock();


    public Flight(String flightNum, int pass){
        this.flightNum = flightNum;
        this.passengers = pass;
    }

    //Writes
    public void setPassengers(int n){
        rwl.writeLock().lock();
        this.passengers = n;
        rwl.writeLock().unlock();
    }

    //Reads
    public int getPassengers(){
        rwl.readLock().lock();
        int ret = this.passengers;
        rwl.readLock().unlock();
        return ret;
    }
    public String getID(){
        rwl.readLock().lock();
        String ret = this.flightNum;
        rwl.readLock().unlock();
        return ret;
    }

}
