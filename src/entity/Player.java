package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

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

        setSolidArea(8, 50, 16, 2);
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);
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

        up1 = setup("up1");
        up2 = setup("up2");
        up3 = setup("up3");
        up4 = setup("up4");

        down1 = setup("down1");
        down2 = setup("down2");
        down3 = setup("down3");
        down4 = setup("down4");

        left1 = setup("left1");
        left2 = setup("left2");
        left3 = setup("left3");
        left4 = setup("left4");

        right1 = setup("right1");
        right2 = setup("right2");
        right3 = setup("right3");
        right4 = setup("right4");

    }

    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/" + imageName + ".png")));
            image = uTool.scaleImage(image, gp.getTileSize(), gp.getTileSize()*2);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return image;
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

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);

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

        int x = screenX;
        int y = screenY;

        if (screenX > getWorldX()) {
            x = getWorldX();
        }
        if (screenY > getWorldY()) {
            y = getWorldY();
        }
        int rightOffset = gp.getScreenWidth() - screenX;
        if (rightOffset > gp.getWorldWidth() - getWorldX()) {
            x = gp.getScreenWidth() - (gp.getWorldWidth() - getWorldX());
        }
        int bottomOffset = gp.getScreenHeight() - screenY;
        if (bottomOffset > gp.getWorldHeight() - getWorldY()) {
            y = gp.getScreenHeight() - (gp.getWorldHeight() - getWorldY());
        }
        g2.drawImage(image, x, y, null);

    }

    public int getScreenX() { return screenX; }

    public int getScreenY() {
        return screenY;
    }
}
