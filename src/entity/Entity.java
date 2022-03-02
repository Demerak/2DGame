package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    private int worldX, worldY;
    private int speed;
    private int health;
    private int spriteNum;
    private int spriteCounter;
    private final int spriteAnimationSpeed = 10;
    public BufferedImage up1, up2, up3, up4,
            down1, down2, down3, down4,
            left1, left2, left3, left4,
            right1, right2, right3, right4;
    public String direction;
    public Rectangle solidArea;
    public boolean collisionOn = false;

    public Entity(int worldX, int worldY, int speed) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.speed = speed;
        this.health = 100;
        this.spriteNum = 1;
        this.spriteCounter = 0;
    }

    public int getWorldX() { return worldX; }

    public int getWorldY() {
        return worldY;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public int getSpriteNum() {return spriteNum; }

    public int getSpriteCounter() {return spriteCounter; }

    public int getSpriteAnimationSpeed() { return spriteAnimationSpeed; }

    public String getDirection() { return direction; }

    public Rectangle getSolidArea() { return solidArea; }

    public boolean getCollisionOn() { return collisionOn; }




    public void setX(int x) {
        this.worldX = x;
    }

    public void setY(int y) {
        this.worldY = y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setSolidArea(int x, int y, int width, int height) {
        this.solidArea = new Rectangle(x, y, width, height);
    }

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

    public void setSpriteNumToOne() {
        spriteNum = 1;
    }


    public void incrementSpriteNum() {
        if (spriteNum < 4) {
            spriteNum++;
        } else {
            spriteNum = 1;
        }
    }

    public void incrementSpriteCounter() {
        if (spriteCounter <= spriteAnimationSpeed) {
            spriteCounter++;
        } else {
            spriteCounter = 0;
        }
    }
}


