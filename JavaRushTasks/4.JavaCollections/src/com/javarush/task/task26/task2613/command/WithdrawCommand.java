package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.Map;

class WithdrawCommand implements Command {
    @Override
    public void execute() throws InterruptOperationException {

        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);

        while (true) {
            try {
                ConsoleHelper.writeMessage("Please, specify amount for withdrawing");
                String s = ConsoleHelper.readString();
                if (s == null) {
                    ConsoleHelper.writeMessage("Please, specify valid amount");
                } else {
                    try {
                        int amount = Integer.parseInt(s);
                        boolean isAmountAvailable = manipulator.isAmountAvailable(amount);
                        if (isAmountAvailable) {
                            Map<Integer, Integer> denominations = manipulator.withdrawAmount(amount);
                            for (Integer item : denominations.keySet()) {
                                ConsoleHelper.writeMessage("\t" + item + " - " + denominations.get(item));
                            }

                            ConsoleHelper.writeMessage(String.format("%d %s was withdrawn successfully", amount, currencyCode));
                            break;
                        } else {
                            ConsoleHelper.writeMessage("Not enough money on your account, please try again");
                        }
                    } catch (NumberFormatException e) {
                        ConsoleHelper.writeMessage("Please, specify valid amount");
                        continue;
                    }

                }
            } catch (NotEnoughMoneyException e) {
                ConsoleHelper.writeMessage("Not enough money");
            }
        }
    }
}
