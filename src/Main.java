import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable {
    private final Thread thread;

    private final Handler handler;

    public static int SCREEN_WIDTH, SCREEN_HEIGHT;

    private boolean running = false;

    public static Ball ball;

    public final static float PADDLE_VEL_Y = 14;

    public final static float OPP_VEL_Y = 6;

    public final static float BALL_VEL = 10;

    public static int playerScore = 0, opponentScore = 0;

    public static boolean downKeyPressed = false;

    public static boolean upKeyPressed = false;

    public Main() {
        SCREEN_WIDTH = 1_000;
        SCREEN_HEIGHT = 800;

        thread = new Thread(this);
        handler = new Handler();
        KeyInput keyInput = new KeyInput();

        final int PADDLE_WIDTH = 30;
        final int PADDLE_HEIGHT = 120;
        final int BALL_LENGTH = 30;
        final int PADDLE_DIST_FROM_SIDE = 25;

        new Paddle(PADDLE_DIST_FROM_SIDE, (float) (SCREEN_HEIGHT - PADDLE_HEIGHT) / 2, PADDLE_WIDTH, PADDLE_HEIGHT, 0, 0, ID.Paddle, handler, true);
        new Paddle(SCREEN_WIDTH - PADDLE_WIDTH - PADDLE_DIST_FROM_SIDE, (float) (SCREEN_HEIGHT - PADDLE_HEIGHT) / 2, PADDLE_WIDTH, PADDLE_HEIGHT, 0, 0, ID.Paddle, handler, false);
        ball = new Ball((float) (SCREEN_WIDTH - BALL_LENGTH) / 2, (float) (SCREEN_HEIGHT - BALL_LENGTH) / 2, BALL_LENGTH, BALL_LENGTH, 0, 0, ID.Ball, handler);

        JFrame frame = new JFrame("Pong");
        frame.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
        this.addKeyListener(keyInput);
        this.start();
    }

    public synchronized void start() {
        running = true;
        thread.start();
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void run(){
        this.requestFocus();

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1_000_000_000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
//                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    public void tick() {
        handler.tick();
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        HUD.render(g);
        handler.render(g);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        new Main();
    }

    private class KeyInput extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();

            if(key == KeyEvent.VK_W){ // Up
                upKeyPressed = true;
            }
//            if(key == KeyEvent.VK_A){ // Left
//                leftPaddle.setVelX(0);
//            }
            if(key == KeyEvent.VK_S){ // Down
                downKeyPressed = true;
            }
//            if(key == KeyEvent.VK_D){ // Right
//                leftPaddle.setVelX(0);
//            }
        }

        public void keyReleased(KeyEvent e){
            int key = e.getKeyCode();

            if(key == KeyEvent.VK_W){ // Up
                upKeyPressed = false;
            }
//            if(key == KeyEvent.VK_A){ // Left
//                leftPaddle.setVelX(0);
//            }
            if(key == KeyEvent.VK_S){ // Down
                downKeyPressed = false;
            }
//            if(key == KeyEvent.VK_D){ // Right
//                leftPaddle.setVelX(0);
//            }
        }
    }
}
