import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;


import javax.swing.*;


public class FlappyBird extends JPanel implements ActionListener, KeyListener{
    int boardWidth = 360;
    int boardHeight = 640;

    //Image to store
    Image backgroundImg;
    Image kanyeImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //Kanye
    int kanyeX = boardWidth/8;
    int kanyeY = boardHeight/2;
    int kanyeWidth = 60;
    int kanyeHeight = 34;

    class Kanye{
        int x = kanyeX;
        int y = kanyeY;
        int width = kanyeWidth;
        int height = kanyeHeight;
        Image img;

        Kanye(Image img){
            this.img = img;
        }
    
    }

    //Pipes

    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe{
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img){
            this.img = img;
        }


    }

    //gamelogic
    Kanye kanye;
    int velocityX = -4;
    int velocityY = 0;
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    //timer
    Timer gameLoop;
    Timer placePipesTimer;
    boolean gameOver;
    double score = 0;
    int counter = 0;


    FlappyBird(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        //setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);
        //ladda bilderna                                        //jeff
        backgroundImg = new ImageIcon(getClass().getResource("./image.png")).getImage();
        kanyeImg = new ImageIcon(getClass().getResource("./kanye.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottomPipe.png")).getImage();


        //bird
        kanye = new Kanye(kanyeImg);
        pipes = new ArrayList<Pipe>();


        //pipetimers
        placePipesTimer = new Timer(1500, new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        
        placePipesTimer.start();
       

        //timer
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    public void placePipes(){


        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace = boardHeight/4;


        //toppipe
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        //bottompipe
        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight+ + openingSpace;
        pipes.add(bottomPipe);

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g){
   
        //bakgrund
        g.drawImage(backgroundImg,0, 0, boardWidth, boardHeight, null);

        //kanye
        g.drawImage(kanye.img,kanye.x, kanye.y, kanye.width, kanye.height, null);

        for(int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }
        //score
        g.setColor(Color.red);
        g.setFont(new Font("BOLD", Font.PLAIN, 32));

    
        if(gameOver){
            g.drawString("Lmao noob: " + String.valueOf((int) score), 10, 35);
        System.out.println(counter);
        if (counter > 0) {
            g.drawString("Try again?", 10, 70);
        }
        }
        else{
            g.drawString(String.valueOf((int)score), 10, 35);
        }

    }

    public void move(){

        //bird
        velocityY += gravity;
        kanye.y += velocityY;
        kanye.y = Math.max(kanye.y, 0);

        //pipe
        for(int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if(!pipe.passed && kanye.x > pipe.x + pipe.width){
                    pipe.passed = true;
                    score += 0.5;
            }
            if(collision(kanye, pipe)){
                gameOver = true;
            }

        }
        
        if(kanye.y > boardHeight) {
            gameOver = true;
        }
    }

    public boolean collision(Kanye a, Pipe b) {
        return a.x < b.x + b.width && 
        a.x + a.width > b.x && 
        a.y < b.y + b.height &&
        a.y + a.height > b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver) {
            placePipesTimer.stop();
            gameLoop.stop(); 
        }
     
    }



    @Override
    public void keyPressed(KeyEvent e) {
       if(e.getKeyCode() == KeyEvent.VK_SPACE) {
        velocityY = -9;
        if (gameOver) {
            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                counter++;
               if(counter == 1){
                System.out.println("Try Again?");
             
               } 
            }
            if(counter == 2){
                counter = 0;
                kanye.y = kanyeY;
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipesTimer.start();
                
            }
        }

     }
    }
        
        

    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

}
