package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends SuperObject {

    GamePanel gp;

    public OBJ_Chest(GamePanel gp) {
        this.gp = gp;
        setImage("chest.png");
        setImageWidth(gp.getTileSize());
        setImageHeight(gp.getTileSize()*2);
        setSolidArea();
        setCollision(true);

    }
}
