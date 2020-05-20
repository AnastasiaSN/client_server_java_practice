package ua.edu.ukma.fido.entity;

public class Counter {
    private Integer value = 0;

    public synchronized void increment() {
        value += 1;
    }

    public void incrementRaceCondition() {
        value += 1;
    }

    public Integer getValue() {
        return value;
    }
}