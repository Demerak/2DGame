package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import javax.swing.plaf.multi.MultiListUI;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class TileManager {

    private GamePanel gp;
    private ArrayList<Tile> tiles = new ArrayList<>();
    private int[][] map;


    public TileManager(GamePanel gp) {
        this.gp = gp;
        map = new int[gp.getMaxWorldRow()][gp.getMaxWorldCol()];
        getTileImage();

    }

    public void getTileImage() {
        setup(0, "dirt1", false);
        setup(0, "dirt2", false);
        setup(0, "dirt3", false);
        setup(0, "dirt4", false);
        setup(0, "dirt5", false);
        setup(0, "dirt6", false);
        setup(0, "stone7", false);
    }

    public void setup(int index, String imageName, boolean collision) {

        UtilityTool uTool = new UtilityTool();

        try {
            Tile newTile = new Tile();
            newTile.setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tiles/" + imageName + ".png"))));
            newTile.setImage(uTool.scaleImage(newTile.getImage(), gp.getTileSize(), gp.getTileSize()));
            newTile.setCollision(collision);
            tiles.add(newTile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void createMap() {

        Random r = new Random();
        int tileNum = 0;

        try {
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            System.out.println("Current absolute path is: " + s+"\\src\\res\\maps\\map1.txt");
            FileWriter writer = new FileWriter(s+"\\src\\res\\maps\\map1.txt");
            for (int row = 0; row < gp.getMaxWorldRow(); row++) {
                if (row != 0) { writer.write("\r\n"); }
                for (int col = 0; col < gp.getMaxWorldCol(); col++) {
                    int randomTileNum = r.nextInt(99) + 1;
                    if (randomTileNum <= 90) {
                        tileNum = 1;
                    } else if (randomTileNum <= 92) {
                        tileNum = 2;
                    } else if (randomTileNum <= 94) {
                        tileNum = 3;
                    } else if (randomTileNum <= 96) {
                        tileNum = 4;
                    } else if (randomTileNum <= 98) {
                        tileNum = 5;
                    } else {
                        tileNum = 6;
                    }
                    writer.write(tileNum + " ");
                    }
                }
            writer.close();
            System.out.println("done");
            } catch(IOException e){
                e.printStackTrace();
        }
    }


    public void readMap(String fileName) {

        try {
            String path = "/res/maps/" + fileName;
            InputStream is = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int row = 0; row < gp.getMaxWorldRow(); row++) {
                String line = br.readLine();

                for (int col = 0; col < gp.getMaxWorldRow(); col++) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    map[row][col] = num;
                }
            }
            br.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void draw(Graphics2D g2) {

        readMap("map1.txt");

        for (int worldRow = 0; worldRow < gp.getMaxWorldRow(); worldRow++) {
            for (int worldCol = 0; worldCol < gp.getMaxWorldCol(); worldCol++) {

                int worldX = worldCol * gp.getTileSize();
                int worldY = worldRow * gp.getTileSize();

                int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
                int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();

                // Stop moving the camera at the edge
                if (gp.player.getScreenX() > gp.player.getWorldX()) {
                    screenX = worldX;
                }
                if (gp.player.getScreenY() > gp.player.getWorldY()) {
                    screenY = worldY;
                }
                int rightOffset = gp.getScreenWidth() - gp.player.getScreenX();
                if (rightOffset > gp.getWorldWidth() - gp.player.getWorldX()) {
                    screenX = gp.getScreenWidth() - (gp.getWorldWidth() - worldX);
                }
                int bottomOffset = gp.getScreenHeight() - gp.player.getScreenY();
                if (bottomOffset > gp.getWorldHeight() - gp.player.getWorldY()) {
                    screenY = gp.getScreenHeight() - (gp.getWorldHeight() - worldY);
                }

                if(worldX + gp.getTileSize() > gp.player.getWorldX() - gp.player.getScreenX() &&
                    worldX - gp.getTileSize() < gp.player.getWorldX() + gp.player.getScreenX() &&
                    worldY + gp.getTileSize() > gp.player.getWorldY() - gp.player.getScreenY() &&
                    worldY - gp.getTileSize() < gp.player.getWorldY() + gp.player.getWorldY()) {
                        g2.drawImage(tiles.get(map[worldRow][worldCol]-1).getImage(),screenX,screenY,null);
                } else {
                    g2.drawImage(tiles.get(map[worldRow][worldCol]-1).getImage(),screenX,screenY,null);
                }
            }
        }
    }

    public int[][] getMap() { return map; }

    public Tile getTile(int TileIndex) { return tiles.get(TileIndex-1); }
}
