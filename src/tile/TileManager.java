package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
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
        try {
            Tile dirt1 = new Tile();
            dirt1.setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tiles/dirt1.png"))));
            tiles.add(dirt1);

            Tile dirt2 = new Tile();
            dirt2.setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tiles/dirt2.png"))));
            tiles.add(dirt2);

            Tile dirt3 = new Tile();
            dirt3.setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tiles/dirt3.png"))));
            tiles.add(dirt3);

            Tile dirt4 = new Tile();
            dirt4.setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tiles/dirt4.png"))));
            tiles.add(dirt4);

            Tile dirt5 = new Tile();
            dirt5.setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tiles/dirt5.png"))));
            tiles.add(dirt5);

            Tile dirt6 = new Tile();
            dirt6.setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tiles/dirt6.png"))));
            tiles.add(dirt6);

            Tile stone7 = new Tile();
            stone7.setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tiles/stone7.png"))));
            tiles.add(stone7);


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

                int worldX = worldCol* gp.getTileSize();
                int worldY = worldRow * gp.getTileSize();

                int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
                int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();

                if(worldX + gp.getTileSize() > gp.player.getWorldX() - gp.player.getScreenX() &&
                    worldX - gp.getTileSize() < gp.player.getWorldX() + gp.player.getScreenX() &&
                    worldY + gp.getTileSize() > gp.player.getWorldY() - gp.player.getScreenY() &&
                    worldY - gp.getTileSize() < gp.player.getWorldY() + gp.player.getWorldY()) {
                        g2.drawImage(tiles.get(map[worldRow][worldCol]-1).getImage(),screenX,screenY, gp.getTileSize(), gp.getTileSize(), null);
                }
            }
        }
    }

    public int[][] getMap() { return map; }

    public Tile getTile(int TileIndex) { return tiles.get(TileIndex-1); }
}
