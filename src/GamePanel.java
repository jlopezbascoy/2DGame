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
    final int screenHeigth = tileSize * maxScreenRows; //576 pixels (modificable)
    Thread gameThread;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeigth));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }
    public void startGameThread(){
        gameThread=new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        while(gameThread != null){

            // 1: UPDATE things as character position
                update();
            //2: DRAW the screen with the updated information
                repaint();
        }
    }

    public void update(){}

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2= (Graphics2D)g;

        g2.setColor(Color.WHITE);
        g2.fillRect(100,100,tileSize,tileSize);
        g2.dispose(); //good practice to save memorie
    }
}
