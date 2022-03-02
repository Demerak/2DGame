package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    private final int originalTileSize = 16; // 16x16 tile
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    // if screen is 2560x1440 but the screen scaling setting is 125% for example,
    // the width will be 2048 and the height, 1152 (because 2048 * 125% = 2560, etc).
    private final int defaultScreenWidth = (int) screenSize.getWidth();
    private final int defaultScreenHeight = (int) screenSize.getHeight();

    private final int scale = 2; // to change later to adjust with panel size

    private final int tileSize = originalTileSize * scale;

    // these two variable are the max number of tiles display on the screen (width and height)
    private final int maxScreenCol = (defaultScreenWidth* 3/4)/(originalTileSize*scale);
    private final int maxScreenRow = (defaultScreenHeight* 3/4)/(originalTileSize*scale);

    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;

    // WORLD SETTINGS
    private final int maxWorldCol = 80;
    private final int maxWorldRow = 80;
    private final int worldWidth = tileSize * maxWorldCol;
    private final int worldHeight = tileSize * maxWorldRow;

    // public final int worldWidth = tileSize *

    TileManager tileM = new TileManager(this);
    public CollisionChecker cChecker = new CollisionChecker(this);

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public Player player = new Player(this, keyH);

    private static final int TARGET_FPS = 60;

    public GamePanel() {

        System.out.println(defaultScreenWidth);
        System.out.println(defaultScreenHeight);

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

        tileM.createMap();

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
    }

    public void update() {

        player.update();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2);
        player.draw(g2);

        g2.dispose();
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getMaxScreenCol() { return maxScreenCol; }

    public int getMaxScreenRow() { return maxScreenRow;}

    public int getScreenWidth() { return screenWidth; }

    public int getScreenHeight() { return screenHeight; }

    public int getOriginalTileSize() { return originalTileSize; }

    public int getMaxWorldCol() { return maxWorldCol; }

    public int getMaxWorldRow() { return maxWorldRow; }
}
