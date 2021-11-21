package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class Scene extends Canvas implements Runnable
{
    private final Window window;
    private Thread thread;
    private boolean running = false;
    private int[] time = getTime();

    public static final ArrayList<Button> buttons = new ArrayList<>();

    public Scene()
    {
        buttons.add(new Button(Settings.width - Settings.radius / 3, Settings.radius / 10, Settings.radius / 5, Settings.radius / 5,
                getImage(Settings.directory + "light.png"), getImage(Settings.directory + "dark.png")));
        MouseInput mouse_input = new MouseInput();
        this.addMouseListener(mouse_input);
        this.addMouseMotionListener(mouse_input);
        window = new Window(Settings.width, Settings.height, Settings.title, this);
    }

    private BufferedImage getImage(String path)
    {
        try
        {
            return ImageIO.read(new File(path));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized void start()
    {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop()
    {
        try
        {
            thread.join();
            running = false;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while(running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1)
            {
                update();
                delta--;
            }
            if(running)
            {
                render();
            }
            frames++;

            if(System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
                window.setTitle(frames + " fps | " + Settings.title);
                frames = 0;
            }
        }
        stop();
    }

    private void update()
    {
        time = getTime();
    }

    private void render()
    {
        BufferStrategy bs = this.getBufferStrategy();

        if(bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Settings.bg1);
        g2d.fillRect(0, 0, Settings.width, Settings.height);

        g2d.setColor(Settings.bg1);
        g2d.fillOval((int) (Settings.center_x - Settings.radius * 1.25), (int) (Settings.center_y - Settings.radius * 1.25), (int) (2.5 * Settings.radius), (int) (2.5 * Settings.radius));

        g2d.setStroke(new BasicStroke(Settings.line_thickness));
        drawLines(g2d);

        g2d.setStroke(new BasicStroke(Settings.line_thickness * 2));
        g2d.setColor(Settings.bg2);
        g2d.drawOval((int) (Settings.center_x - Settings.radius * 1.1), (int) (Settings.center_y - Settings.radius * 1.1), (int) (2.2 * Settings.radius), (int) (2.2 * Settings.radius));

        g2d.setStroke(new BasicStroke(Settings.line_thickness));
        g2d.setColor(Settings.bg1);
        g2d.fillOval((int) (Settings.center_x - Settings.radius * 1.6 / 2), (int) (Settings.center_y - Settings.radius * 1.6 / 2), (int) (Settings.radius * 1.6), (int) (Settings.radius * 1.6));

        drawHands(g2d);

        g2d.setColor(Settings.col5);
        g2d.fillOval(Settings.center_x - Settings.radius / 16, Settings.center_y - Settings.radius / 16, Settings.radius / 8, Settings.radius / 8);

        g2d.setFont(Settings.font);
        g2d.setColor(Settings.fg);
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        String s = (time[0] < 10 ? "0" + time[0] : time[0]) + ":" + (time[1] < 10 ? "0" + time[1] : time[1]) + ":" + (time[2] < 10 ? "0" + time[2] : time[2]);
        g2d.drawString(s, Settings.center_x - metrics.stringWidth(s) / 2, Settings.radius / 2);

        for(Button b : buttons)
        {
            b.render(g2d);
        }

        g2d.dispose();
        bs.show();
    }

    private void drawLines(Graphics2D g2d)
    {
        g2d.setColor(Settings.col2);
        g2d.setStroke(new BasicStroke(Settings.line_thickness / 2f));
        for(int i = 0; i < 360; i += 6)
        {
            if(i % 30 != 0) g2d.drawLine(Settings.center_x, Settings.center_y, (int) (Settings.center_x + Settings.radius * Math.cos(Math.toRadians(i))), (int) (Settings.center_y + Settings.radius * Math.sin(Math.toRadians(i))));
        }

        g2d.setColor(Settings.bg1);
        g2d.fillOval((int) (Settings.center_x - Settings.radius * 1.8 / 2), (int) (Settings.center_y - Settings.radius * 1.8 / 2), (int) (Settings.radius * 1.8), (int) (Settings.radius * 1.8));

        g2d.setStroke(new BasicStroke(Settings.line_thickness));
        g2d.setColor(Settings.col2);
        for(int i = 0; i < 360; i += 30)
        {
            if(i % 90 != 0) g2d.drawLine(Settings.center_x, Settings.center_y, (int) (Settings.center_x + Settings.radius * Math.cos(Math.toRadians(i))), (int) (Settings.center_y + Settings.radius * Math.sin(Math.toRadians(i))));
        }

        g2d.setColor(Settings.col1);
        for(int i = 0; i < 360; i += 30)
        {
            if(i % 90 == 0) g2d.drawLine(Settings.center_x, Settings.center_y, (int) (Settings.center_x + Settings.radius * Math.cos(Math.toRadians(i))), (int) (Settings.center_y + Settings.radius * Math.sin(Math.toRadians(i))));
        }
    }

    private void drawHands(Graphics2D g2d)
    {
        int start = -90;

        g2d.setColor(Settings.col3);

        g2d.setStroke(new BasicStroke((float) Settings.width / 100));
        g2d.drawLine(Settings.center_x, Settings.center_y, (int) (Settings.center_x + Settings.radius / 2 * Math.cos(Math.toRadians(start + time[0] * 30))), (int) (Settings.center_y + Settings.radius / 2 * Math.sin(Math.toRadians(start + time[0] * 30))));

        g2d.setStroke(new BasicStroke(Settings.line_thickness));
        g2d.drawLine(Settings.center_x, Settings.center_y, (int) (Settings.center_x + Settings.radius / 1.6 * Math.cos(Math.toRadians(start + time[1] * 6))), (int) (Settings.center_y + Settings.radius / 1.6 * Math.sin(Math.toRadians(start + time[1] * 6))));

        g2d.setColor(Settings.col4);
        g2d.setStroke(new BasicStroke((float) Settings.width / 200));
        g2d.drawLine(Settings.center_x, Settings.center_y, (int) (Settings.center_x + Settings.radius / 1.4 * Math.cos(Math.toRadians(start + time[2] * 6))), (int) (Settings.center_y + Settings.radius / 1.4 * Math.sin(Math.toRadians(start + time[2] * 6))));
    }

    private int[] getTime()
    {
        String[] curr_time = LocalTime.now().toString().split(":");
        int[] time = new int[curr_time.length];
        for(int i = 0; i < curr_time.length; i++) time[i] = (int) Double.parseDouble(curr_time[i]);
        return time;
    }

    public static void main(String[] args)
    {
        new Scene();
    }
}