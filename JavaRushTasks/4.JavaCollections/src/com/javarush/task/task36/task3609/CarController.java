package com.javarush.task.task36.task3609;

public class CarController {

    private CarModel model;
    private SpeedometerView view;

    public CarController(CarModel model, SpeedometerView view) {
        this.model = model;
        this.view = view;
    }

    public void updateView() {
        view.printCarDetails(model.getCarBrand(), model.getCarModel(), model.getCarSpeed());
    }


    public void increaseSpeed(int seconds) {

        int speed = model.getSpeed();
        int maxSpeed = model.getMaxSpeed();

        if (speed < maxSpeed) {
            speed += (3.5 * seconds);
        }

        if (speed > maxSpeed) {
            speed = maxSpeed;
        }

        model.setSpeed(speed);

    }

    public void decreaseSpeed(int seconds) {

        int speed = model.getSpeed();

        if (speed > 0) {
            speed -= (12 * seconds);
        }
        if (speed < 0) {
            speed = 0;
        }
        model.setSpeed(speed);

    }

}