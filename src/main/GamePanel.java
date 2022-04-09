package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
    private final int worldWidth = maxWorldCol * tileSize;
    private final int worldHeight= maxWorldRow * tileSize;

    // public final int worldWidth = tileSize *

    // SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound(); // Sound Effect
    public CollisionChecker cChecker = new CollisionChecker(this); // todo change to private
    public AssetSetter aSetter = new AssetSetter(this); // todo change to private
    public ArrayList<SuperObject> objList =  new ArrayList<>();
    public UI ui = new UI(this);

    Thread gameThread;
    public Player player = new Player(this, keyH);


    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;

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

    public void setupGame() {
        aSetter.setObject();
        // playMusic(0); // uncomment to play music
        gameState = titleState;
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
        if (gameState == playState) {
            player.update();
        }
        if (gameState == pauseState) {
            //nothing for now
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        long drawStart = 0;
        if (keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        // TITLE STATE
        if (gameState == titleState) {
            ui.draw(g2);
        }
        // OTHER STATE
        else {
            // TILE
            tileM.draw(g2);

            // OBJECT
            for (int i = 0; i < objList.size(); i++) {
                if(objList.get(i) != null) {
                    objList.get(i).draw(g2, this);
                }
            }

            // PLAYER
            player.draw(g2);

            // UI
            ui.draw(g2);

            if (keyH.checkDrawTime) {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;
                System.out.println("Draw Time: " + passed*Math.pow(10,-9) + " second");
            }
        }




        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) { // Sound Effect
        se.setFile(i);
        se.play();
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

    public int getWorldWidth() { return worldWidth; }

    public int getWorldHeight() { return worldHeight; }

}
