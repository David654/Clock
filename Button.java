package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class Button
{
    public int x, y, width, height;
    public boolean hover = false;
    private final BufferedImage img1, img2;
    public int counter = 0;

    public Button(int x, int y, int width, int height, BufferedImage img1, BufferedImage img2)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img1 = resize(img1, width, height);
        this.img2 = resize(img2, width, height);
    }

    private BufferedImage resize(BufferedImage img, int newW, int newH)
    {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    private BufferedImage changeColor(BufferedImage img, Color color)
    {
        int width = img.getWidth();
        int height = img.getHeight();
        WritableRaster raster = img.getRaster();

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                int[] pixels = raster.getPixel(x, y, (int[]) null);
                pixels[0] = color.getRed();
                pixels[1] = color.getGreen();
                pixels[2] = color.getBlue();
                raster.setPixel(x, y, pixels);
            }
        }
        return img;
    }

    public void render(Graphics2D g2d)
    {
        g2d.setColor(hover ? Settings.bg4 : Settings.bg3);
        g2d.fillRoundRect(x, y, width, height, width / 2, width / 2);
        g2d.drawImage(counter == 0 ? changeColor(img1, new Color(255 - Settings.bg1.getRed(), 255 - Settings.bg1.getGreen(), 255 - Settings.bg1.getBlue())) :
                changeColor(img2, new Color(255 - Settings.bg1.getRed(), 255 - Settings.bg1.getGreen(), 255 - Settings.bg1.getBlue())), x, y, null);
    }
}