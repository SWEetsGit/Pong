import java.awt.*;

public class Paddle extends GameObject {
    private final boolean isPlayer;

    public Paddle(float x, float y, int width, int height, float velX, float velY, ID id, Handler handler, boolean isPlayer) {
        super(x, y, width, height, velX, velY, id, handler);

        this.isPlayer = isPlayer;
        setVelX(0);
    }

    private void movement() {
        if(isPlayer) {
            if (Main.downKeyPressed) {
                setVelY(Main.PADDLE_VEL_Y);
            } else if (Main.upKeyPressed) {
                setVelY(-Main.PADDLE_VEL_Y);
            } else {
                int velYSign = (int) (getVelY() / Math.abs(getVelY()));
                setVelY(velYSign * (Math.abs(getVelY()) - 1));
            }
        } else {
            float ballCenter = Main.ball.getY() + (float) Main.ball.getHeight() / 2;
            float paddleCenter = getY() + (float) getHeight() / 2;
            if(ballCenter > paddleCenter) // Ball is below CPU paddle
                setVelY(Main.OPP_VEL_Y);
            else if(ballCenter < paddleCenter) // Ball is above CPU paddle
                setVelY(-Main.OPP_VEL_Y);
            else
                setVelY(0);

//            int ballVelYSign = (int) (Main.ball.getVelY() / Math.abs(Main.ball.getVelY()));
//            setVelY(ballVelYSign * Main.OPP_VEL_Y);
        }
    }

    private void wallDetection() {
        if(getY() < 0)
            setY(0);
        else if(getY() + getHeight() > Main.SCREEN_HEIGHT)
            setY(Main.SCREEN_HEIGHT - getHeight());
    }

    @Override
    public void tick() {
        setY(getY() + getVelY());

        movement();
        wallDetection();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect((int) getX(), (int) getY(), getWidth(), getHeight());
    }
}
