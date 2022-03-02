package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    private final GamePanel gp;
    private final KeyHandler keyH;
    private int stamina;
    private int level;

    private final int screenX;
    private final int screenY;



    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp.getScreenWidth()/2, gp.getScreenHeight()/2, 2);
        this.stamina = 100;
        this.level = 0;

        setSolidArea(8, 16, 32, 32);
        setCollisionOn(true);

        this.gp = gp;
        this.keyH = keyH;

        // center of the screen
        screenX = gp.getScreenWidth()/2 - (gp.getTileSize()/2);
        screenY = gp.getScreenHeight()/2 - (gp.getTileSize()/2);


        getPlayerImage();
        direction ="up";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/up1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/up2.png")));
            up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/up3.png")));
            up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/up4.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/down1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/down2.png")));
            down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/down3.png")));
            down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/down4.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/left1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/left2.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/left3.png")));
            left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/left4.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/right1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/right2.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/right3.png")));
            right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/right4.png")));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (keyH.upPressed || keyH.downPressed ||
                keyH.leftPressed || keyH.rightPressed) {

            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // CHECK TILE COLLISION
            setCollisionOn(false);
            gp.cChecker.checkTile(this);

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn) {
                switch (direction) {
                    case "up" -> setY(getWorldY() - getSpeed()); // goes up
                    case "down" -> setY(getWorldY() + getSpeed()); // goes down
                    case "left" -> setX(getWorldX() - getSpeed()); // goes left
                    case "right" -> setX(getWorldX() + getSpeed()); // goes right
                }
            }

            incrementSpriteCounter();
            if (getSpriteCounter() > getSpriteAnimationSpeed()) {
                incrementSpriteNum();
            }

        } else {
            setSpriteNumToOne();
        }

    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        switch (direction) {
            case "up":
                switch (getSpriteNum()) {
                    case 1 -> image = up1;
                    case 2 -> image = up2;
                    case 3 -> image = up3;
                    case 4 -> image = up4;
                }
                break;
            case "down":
                switch (getSpriteNum()) {
                    case 1 -> image = down1;
                    case 2 -> image = down2;
                    case 3 -> image = down3;
                    case 4 -> image = down4;
                }
                break;
            case "left":
                switch (getSpriteNum()) {
                    case 1 -> image = left1;
                    case 2 -> image = left2;
                    case 3 -> image = left3;
                    case 4 -> image = left4;
                }
                break;
            case "right":
                switch (getSpriteNum()) {
                    case 1 -> image = right1;
                    case 2 -> image = right2;
                    case 3 -> image = right3;
                    case 4 -> image = right4;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.getTileSize(), gp.getTileSize()*2, null);

    }

    public int getScreenX() { return screenX; }

    public int getScreenY() {
        return screenY;
    }
}
