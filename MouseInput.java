package com.company;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter
{
    public static int mouse_x = 0, mouse_y = 0;

    public void mouseReleased(MouseEvent e)
    {
        mouse_x = e.getX();
        mouse_y = e.getY();

        if(e.getButton() == MouseEvent.BUTTON1)
        {
            for(Button b : Scene.buttons)
            {
                if(mouseOver(mouse_x, mouse_y, b.x, b.y, b.width, b.height))
                {
                    if(Scene.buttons.indexOf(b) == 0)
                    {
                        Settings.bg1 = new Color(255 - Settings.bg1.getRed(), 255 - Settings.bg1.getGreen(), 255 - Settings.bg1.getBlue());
                        Settings.bg2 = new Color(255 - Settings.bg2.getRed(), 255 - Settings.bg2.getGreen(), 255 - Settings.bg2.getBlue());
                        Settings.bg3 = new Color(255 - Settings.bg3.getRed(), 255 - Settings.bg3.getGreen(), 255 - Settings.bg3.getBlue());
                        Settings.bg4 = new Color(255 - Settings.bg4.getRed(), 255 - Settings.bg4.getGreen(), 255 - Settings.bg4.getBlue());
                        Settings.col2 = new Color(255 - Settings.col2.getRed(), 255 - Settings.col2.getGreen(), 255 - Settings.col2.getBlue());
                        Settings.fg = new Color(255 - Settings.fg.getRed(), 255 - Settings.fg.getGreen(), 255 - Settings.fg.getBlue());
                        b.counter = b.counter % 2 == 0 ? 1 : 0;
                    }
                }
            }
        }
    }

    public void mouseMoved(MouseEvent e)
    {
        mouse_x = e.getX();
        mouse_y = e.getY();

        for(Button b : Scene.buttons)
        {
            b.hover = mouseOver(mouse_x, mouse_y, b.x, b.y, b.width, b.height);
        }
    }

    public boolean mouseOver(int mx, int my, int x, int y, int width, int height)
    {
        if(mx > x && mx < x + width)
        {
            return my > y && my < y + height;
        }
        else
        {
            return false;
        }
    }
}