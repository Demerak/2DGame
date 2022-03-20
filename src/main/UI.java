package main;

import java.awt.*;

public class UI {

    private final GamePanel gp;
    private Graphics2D g2;
    private final Font arial_40;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        if (gp.gameState == gp.playState) {
            // todo
        }
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }


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
