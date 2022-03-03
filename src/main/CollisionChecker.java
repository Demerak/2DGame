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

        int entityLeftCol = entityLeftWorldX / gp.getTileSize();
        int entityRightCol = entityRightWorldX / gp.getTileSize();
        int entityTopRow = entityTopWorldY / gp.getTileSize();
        int entityBottomRow = entityBottomWorldY / gp.getTileSize();

        int tileNum1, tileNum2;

        switch (entity.getDirection()) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.tileM.getMap()[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileM.getMap()[entityTopRow][entityRightCol];
                if (gp.tileM.getTile(tileNum1).collision ||
                        gp.tileM.getTile(tileNum2).collision) {
                    // player is hitting a solid tile
                    entity.setCollisionOn(true);
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.tileM.getMap()[entityBottomRow][entityLeftCol];
                tileNum2 = gp.tileM.getMap()[entityBottomRow][entityRightCol];
                if (gp.tileM.getTile(tileNum1).collision ||
                        gp.tileM.getTile(tileNum2).collision) {
                    // player is hitting a solid tile
                    entity.setCollisionOn(true);
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.tileM.getMap()[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileM.getMap()[entityBottomRow][entityLeftCol];
                if (gp.tileM.getTile(tileNum1).collision ||
                        gp.tileM.getTile(tileNum2).collision) {
                    // player is hitting a solid tile
                    entity.setCollisionOn(true);
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.tileM.getMap()[entityTopRow][entityRightCol];
                tileNum2 = gp.tileM.getMap()[entityBottomRow][entityRightCol];
                if (gp.tileM.getTile(tileNum1).collision ||
                        gp.tileM.getTile(tileNum2).collision) {
                    // player is hitting a solid tile
                    entity.setCollisionOn(true);
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for(int i = 0; i < gp.objList.size(); i++) {
            if(gp.objList.get(i) != null) {

                // Get entity's solid area position
                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

                // Get the object's solid area position
                gp.objList.get(i).getSolidArea().x = gp.objList.get(i).getWorldX() +
                        gp.objList.get(i).getSolidArea().x;
                gp.objList.get(i).getSolidArea().y = gp.objList.get(i).getWorldY() +
                        gp.objList.get(i).getSolidArea().y;

                // Check if solid area rectangle of entity intersect with rectangle solid
                // area of the object
                switch (entity.getDirection()) {
                    case "up" -> {
                        entity.getSolidArea().y -= entity.getSpeed();
                        if (entity.getSolidArea().intersects(gp.objList.get(i).getSolidArea())) {
                            if(gp.objList.get(i).getCollision()) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                    }
                    case "down" -> {
                        entity.getSolidArea().y += entity.getSpeed();
                        if (entity.getSolidArea().intersects(gp.objList.get(i).getSolidArea())) {
                            if(gp.objList.get(i).getCollision()) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                    }
                    case "left" -> {
                        entity.getSolidArea().x -= entity.getSpeed();
                        if (entity.getSolidArea().intersects(gp.objList.get(i).getSolidArea())) {
                            if(gp.objList.get(i).getCollision()) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                    }
                    case "right" -> {
                        entity.getSolidArea().x += entity.getSpeed();
                        if (entity.getSolidArea().intersects(gp.objList.get(i).getSolidArea())) {
                            if(gp.objList.get(i).getCollision()) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                    }
                }

                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();
                gp.objList.get(i).getSolidArea().x = gp.objList.get(i).getSolidAreaDefaultX();
                gp.objList.get(i).getSolidArea().y = gp.objList.get(i).getSolidAreaDefaultY();

            }
        }

        return index;

    }
}
