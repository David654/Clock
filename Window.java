package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Window extends Canvas implements ComponentListener
{
    private final JFrame frame;

    public Window(int width, int height, String title, Scene scene)
    {
        this.frame = new JFrame(title);

        frame.setSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width / 8, height / 8));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(scene);
        frame.setVisible(true);
        frame.addComponentListener(this);

        scene.start();
    }

    public void setTitle(String title)
    {
        frame.setTitle(title);
    }

    public void componentResized(ComponentEvent e)
    {
        for(Button b : Scene.buttons)
        {
            int w = Settings.width;
            int h = Settings.height;
            int dx = w - b.x;
            int dy = h - b.y;
            Settings.width = frame.getWidth();
            Settings.height = frame.getHeight();
            b.x = b.x > w / 2 ? Settings.width - dx : b.x;
            b.y = b.y > h / 2 ? Settings.height - dy : b.y;
        }
        Settings.radius = Settings.width / 4;
        Settings.line_thickness = Settings.width / 160;
        Settings.center_x = Settings.width / 2;
        Settings.center_y = Settings.height / 2;
        Settings.font = Settings.font.deriveFont(Settings.width / 20f);
    }

    public void componentMoved(ComponentEvent e) {}
    public void componentShown(ComponentEvent e) {}
    public void componentHidden(ComponentEvent e) {}
}