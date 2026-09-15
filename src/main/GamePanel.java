package main;

import entity.Player;
import tile.Tile;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS

    final int originalTileSize = 16; //casillas de 16x16
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //casillas de 48x48
    final int maxScreenCol = 16;
    final int maxScreenRows = 12;
    final int screenWidth = tileSize * maxScreenCol; //768 pixels (modificable)
    final int screenHeigth = tileSize * maxScreenRows;//576 pixels (modificable)

    //FPS
    int FPS = 60;


    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this,keyH);
    TileManager tileM = new TileManager(this);

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
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        long drawCount = 0;
        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta>=1){

                // 1: UPDATE things as character position
                update();
                //2: DRAW the screen with the updated information
                repaint();
                delta--;
                drawCount++;
            }

            if(timer>1000000000){
                System.out.println("FPS prueba:" + drawCount);
            }

        }
    }

    public void update(){
    player.update();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2= (Graphics2D)g;
        tileM.draw(g2);
        player.draw(g2);
        g2.dispose(); //good practice to save memorie
    }
}
