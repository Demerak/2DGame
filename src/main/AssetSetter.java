package main;

import object.OBJ_Chest;
import object.SuperObject;

public class AssetSetter {

    private GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        OBJ_Chest chest = new OBJ_Chest(gp);
        chest.setWorldX(23*gp.getTileSize());
        chest.setWorldY(23*gp.getTileSize());
        gp.objList.add(chest);
    }

}
