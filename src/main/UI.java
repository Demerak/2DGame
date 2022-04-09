package main;

import java.awt.*;

public class UI {

    private final GamePanel gp;
    private Graphics2D g2;
    private final Font arial_40;
    private final Font titleFont;
    public int commandNum = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 30);
        titleFont = new Font("Monospaced", Font.PLAIN, 130);
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // PLAY STATE
        if (gp.gameState == gp.playState) {
            // todo
        }

        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }




    }

    public void drawTitleScreen() {

        // TITLE NAME

        g2.setFont(titleFont);
        String text = "ECO";
        int x = getXForCenteredText(text);
        int y = gp.getScreenHeight()/5;
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        g2.setFont(arial_40);

        text = "NEW GAME";
        x = getXForCenteredText(text);
        y = gp.getScreenHeight()*6/20;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">",x-gp.getTileSize(), y);
        }

        text = "LOAD  GAME";
        x = getXForCenteredText(text);
        y = gp.getScreenHeight()*7/20;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">",x-gp.getTileSize(), y);
        }

        text = "QUIT";
        x = getXForCenteredText(text);
        y = gp.getScreenHeight()*8/20;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">",x-gp.getTileSize(), y);
        }

        // https://www.youtube.com/watch?v=_SJU99LU1IQ&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq&index=20
        // todo 12:43


    }

    public void drawPauseScreen() {
        String text = "PAUSED";

        int x = getXForCenteredText(text);
        int y = gp.getScreenHeight()/2;

        g2.drawString(text, x, y);
    }

    public int getXForCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        return gp.getScreenWidth()/2 - length/2;
    }
}
