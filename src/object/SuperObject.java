package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class SuperObject {

    private BufferedImage image;
    private String name;
    private boolean collision = false;
    private int imageWidth;
    private int imageHeight;
    private int worldX, worldY;
    int x = 0;
    int y = 0;
    private Rectangle solidArea;
    private final int solidAreaDefaultX = x;
    private final int solidAreaDefaultY = y;

    public void setImage(String imageFileName) {
        String imagePath = "/res/objects/" + imageFileName;
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean getCollision() { return collision; }

    public Rectangle getSolidArea() { return solidArea; }

    public int getWorldX() { return worldX; }

    public int getWorldY() { return worldY; }

    public int getSolidAreaDefaultX() { return solidAreaDefaultX; }

    public int getSolidAreaDefaultY() { return solidAreaDefaultY; }


    public void setCollision(boolean collision) {this.collision = collision; }

    public void setWorldX(int worldX) { this.worldX = worldX; }

    public void setWorldY(int worldY) { this.worldY = worldY; }

    public void setImageWidth(int width) { this.imageWidth = width; }

    public void setImageHeight(int height) { this.imageHeight = height; }

    public void setSolidArea() { this.solidArea = new Rectangle(x, y, imageWidth, imageHeight); }



    public void draw(Graphics2D g2, GamePanel gp) {

        int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
        int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();

        if(worldX + gp.getTileSize() > gp.player.getWorldX() - gp.player.getScreenX() &&
                worldX - gp.getTileSize() < gp.player.getWorldX() + gp.player.getScreenX() &&
                worldY + gp.getTileSize() > gp.player.getWorldY() - gp.player.getScreenY() &&
                worldY - gp.getTileSize() < gp.player.getWorldY() + gp.player.getWorldY()) {
            g2.drawImage(image,screenX,screenY, imageWidth,imageHeight, null);
        }

    }

}
