package com.javarush.task.task37.task3711;

public class Computer {

    private CPU cpu;
    private HardDrive hardDrive;
    private Memory memory;

    public Computer() {
        this.cpu = new CPU();
        this.hardDrive = new HardDrive();
        this.memory = new Memory();
    }

    void run() {

        cpu.calculate();
        memory.allocate();
        hardDrive.storeData();

    }
}
