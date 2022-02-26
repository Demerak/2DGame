package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    private final int originalTileSize = 16; // 16x16 tile
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int defaultScreenWidth = (int) screenSize.getWidth();
    private final int defaultScreenHeight = (int) screenSize.getHeight();

    private final int scale = 2; // to change later to adjust with panel size

    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = (defaultScreenWidth* 3/4)/(originalTileSize*scale);
    final int maxScreenRow = (defaultScreenHeight* 3/4)/(originalTileSize*scale);

    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);

    private static final int TARGET_FPS = 60;

    // Set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;


    public GamePanel() {

        //System.out.println("width " + maxScreenCol);
        //System.out.println("height " + maxScreenRow);

        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // improve game's rendering performance

        this.addKeyListener(keyH);
        this.setFocusable(true); // the panel can be "focused" to received key input


    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // call run
    }


    @Override
    public void run() {

        double drawInterval = 1000000000.0 / TARGET_FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime =  System.nanoTime();

            delta += (currentTime - lastTime)/drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                // UPDATE: update information like character position
                update();
                // DRAW: draw the screen with the updated information
                repaint(); // call paintComponent method
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }


        /*
        double drawInterval = 1000000000.0 / FPS; // 0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null) {

            long currentTime = System.nanoTime();

            // UPDATE: update information like character position
            update();

            // DRAW: draw the screen with the updated information
            repaint(); // call paintComponent method

            try {
                double remainingTime = (nextDrawTime - System.nanoTime())/1000000; // in milliseconds
                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
         */

    }

    public void update() {

        player.update();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        player.draw(g2);

        g2.dispose();
    }

    public int getTileSize() {
        return tileSize;
    }
}
