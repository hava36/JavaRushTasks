package com.javarush.task.task34.task3410.view;

import com.javarush.task.task34.task3410.controller.EventListener;
import com.javarush.task.task34.task3410.model.Direction;
import com.javarush.task.task34.task3410.model.GameObject;
import com.javarush.task.task34.task3410.model.GameObjects;
import com.javarush.task.task34.task3410.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Field extends JPanel {

    private View view;
    private EventListener eventListener;

    public Field(View view) {
        this.view = view;
        addKeyListener(new KeyHandler());
        setFocusable(true);
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (GameObject gameobject : view.getGameObjects().getAll()
             ) {
            gameobject.draw(g);
        }

    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case  KeyEvent.VK_LEFT: eventListener.move(Direction.LEFT); break;
                case  KeyEvent.VK_RIGHT: eventListener.move(Direction.RIGHT); break;
                case  KeyEvent.VK_UP: eventListener.move(Direction.UP); break;
                case  KeyEvent.VK_DOWN: eventListener.move(Direction.DOWN); break;
                case  KeyEvent.VK_R: eventListener.restart(); break;
                default: break;
            }
        }
    }

}
