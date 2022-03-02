package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;

        int entityLeftCol = entityLeftWorldX/gp.getTileSize();
        int entityRightCol = entityRightWorldX/gp.getTileSize();
        int entityTopRow = entityTopWorldY/gp.getTileSize();
        int entityBottomRow = entityBottomWorldY/gp.getTileSize();

        int tileNum1, tileNum2;

        switch(entity.getDirection()) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.getSpeed())/gp.getTileSize();
                tileNum1 = gp.tileM.getMap()[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileM.getMap()[entityTopRow][entityRightCol];
                if(gp.tileM.getTile(tileNum1).collision ||
                        gp.tileM.getTile(tileNum2).collision) {
                    // player is hitting a solid tile
                    entity.setCollisionOn(true);
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.getSpeed())/gp.getTileSize();
                tileNum1 = gp.tileM.getMap()[entityBottomRow][entityLeftCol];
                tileNum2 = gp.tileM.getMap()[entityBottomRow][entityRightCol];
                if(gp.tileM.getTile(tileNum1).collision ||
                        gp.tileM.getTile(tileNum2).collision) {
                    // player is hitting a solid tile
                    entity.setCollisionOn(true);
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.getSpeed())/gp.getTileSize();
                tileNum1 = gp.tileM.getMap()[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileM.getMap()[entityBottomRow][entityLeftCol];
                if(gp.tileM.getTile(tileNum1).collision ||
                        gp.tileM.getTile(tileNum2).collision) {
                    // player is hitting a solid tile
                    entity.setCollisionOn(true);
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.getSpeed())/gp.getTileSize();
                tileNum1 = gp.tileM.getMap()[entityTopRow][entityRightCol];
                tileNum2 = gp.tileM.getMap()[entityBottomRow][entityRightCol];
                if(gp.tileM.getTile(tileNum1).collision ||
                        gp.tileM.getTile(tileNum2).collision) {
                    // player is hitting a solid tile
                    entity.setCollisionOn(true);
                }
                break;
        }
    }
}
