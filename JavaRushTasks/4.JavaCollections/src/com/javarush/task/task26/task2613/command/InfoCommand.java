package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;

import java.util.Collection;
import java.util.ResourceBundle;

class InfoCommand implements Command {

    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.class.getPackage().getName() + ".resources.info");

    @Override
    public void execute() {
        boolean hasMoney = false;
        Collection<CurrencyManipulator> manipulators = CurrencyManipulatorFactory.getAllCurrencyManipulators();
        for (CurrencyManipulator manipulator : manipulators) {
            if (manipulator.hasMoney()) {
                hasMoney = true;
                System.out.println(String.format("%s - %d", manipulator.getCurrencyCode(), manipulator.getTotalAmount()));
            }
        }
        if (!hasMoney) {
            System.out.println(res.getString("no.money"));
        }
    }
}
