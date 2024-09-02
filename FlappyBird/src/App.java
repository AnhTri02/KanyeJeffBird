import javax.swing.*;
public class App {
    
    public static void main(String[] args) throws Exception {
      int boardWidth = 360;
      int boardHeight = 640;
      //bakgrund dimensions 

      JFrame frame = new JFrame("Kanye Bird");

      frame.setSize(boardWidth, boardHeight);
      frame.setLocationRelativeTo(null);
      //center screen
      frame.setResizable(false);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    //turn off X
    FlappyBird bird = new FlappyBird();
    frame.add(bird);
    frame.pack();
    //flappybird.requestFocus();
    frame.setVisible(true);
    
    }

    
    

}
