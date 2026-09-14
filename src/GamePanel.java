import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS

    final int originalTileSize = 16; //casillas de 16x16
    final int scale = 3;

    final int tileSize = originalTileSize * scale; //casillas de 48x48
    final int maxScreenCol = 16;
    final int maxScreenRows = 12;
    final int screenWidth = tileSize * maxScreenCol; //768 pixels (modificable)
    final int screenHeigth = tileSize * maxScreenRows;//576 pixels (modificable)

    //FPS
    int FPS = 60;
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    //Set players default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeigth));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void startGameThread(){
        gameThread=new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while(gameThread != null){


            // 1: UPDATE things as character position
                update();
            //2: DRAW the screen with the updated information
                repaint();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0){
                    remainingTime = 0;

                }

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update(){
        if (keyH.upPressed){
            playerY -= playerSpeed;
        }
        else if (keyH.downPressed){
            playerY += playerSpeed;
        }
        else if (keyH.leftPressed){
            playerX -= playerSpeed;
        }
        else if (keyH.rightPressed){
            playerX += playerSpeed;
        }

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2= (Graphics2D)g;

        g2.setColor(Color.WHITE);
        g2.fillRect(playerX,playerY,tileSize,tileSize);
        g2.dispose(); //good practice to save memorie
    }
}
