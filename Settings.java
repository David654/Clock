package com.company;

import java.awt.*;

public class Settings
{
    public static Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    public static int width = (int) (d.width / 3.2);
    public static int height = (int) (d.height / 1.8);
    public static int radius = width / 4;
    public static int line_thickness = width / 160;
    public static int center_x = width / 2;
    public static int center_y = height / 2;

    public static final String title = "Mechanical Clock";
    public static Color bg1 = new Color(40, 40, 40);
    public static Color bg2 = new Color(29, 29, 29);
    public static Color bg3 = new Color(60, 60, 60);
    public static Color bg4 = new Color(80, 80, 80);
    public static Color fg = new Color(255, 255, 255);
    public static final Color col1 = new Color(29, 245, 47);
    public static Color col2 = new Color(189, 189, 203);
    public static final Color col3 = new Color(97, 175, 255);
    public static final Color col4 = new Color(190, 101, 30);
    public static final Color col5 = new Color(77, 75, 99);

    public static Font font = new Font("Segoe UI", Font.PLAIN, width / 20);
    public static final String directory = "data\\";
}