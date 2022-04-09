package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class KeyHandler implements KeyListener {

    private final GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean escapePressed;
    public boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //TITLE STATE
        int code = e.getKeyCode();
        if(gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            } else if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                } else if (gp.ui.commandNum == 1) {
                    // add later
                } else if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }

            }
        }

        // PLAYING STATE
        //int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_D -> rightPressed = true;
            case KeyEvent.VK_ESCAPE -> escapePressed = true;

            // DEBUG
            case KeyEvent.VK_T -> checkDrawTime = true;
        }

        if (escapePressed && gp.gameState == gp.playState) {
            gp.gameState = gp.pauseState;
        } else if (escapePressed && gp.gameState == gp.pauseState) {
            gp.gameState = gp.playState;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_S -> downPressed = false;
            case KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_D -> rightPressed = false;
            case KeyEvent.VK_ESCAPE -> escapePressed = false;

            // DEBUG
            case KeyEvent.VK_T -> checkDrawTime = false;
        }
    }
}
