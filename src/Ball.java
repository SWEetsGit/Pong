import java.awt.*;
import java.util.ListIterator;

public class Ball extends GameObject {
    private final float xStart, yStart;

    public Ball(float x, float y, int width, int height, float velX, float velY, ID id, Handler handler) {
        super(x, y, width, height, velX, velY, id, handler);

        xStart = x;
        yStart = y;

        setDirection();
    }

    private void setDirection() {
        int directionX = Math.random() >= 0.5 ? 1 : -1;
        int directionY = Math.random() >= 0.5 ? 1 : -1;

        setVelX(directionX * Main.BALL_VEL);
        setVelY(directionY * Main.BALL_VEL);
    }

    private void wallDetection() {
        if(getX() < 0) {
            setX(xStart);
            setY(yStart);
            setDirection();

            Main.opponentScore++;
        } else if(getX() + getWidth() > Main.SCREEN_WIDTH) {
            setX(xStart);
            setY(yStart);
            setDirection();

            Main.playerScore++;
        }

        if(getY() < 0 || getY() + getHeight() > Main.SCREEN_HEIGHT)
            setVelY(-getVelY());
    }

    private void collisions() {
        ListIterator<GameObject> iterator = getHandler().getGameObjectListIterator();
        while(iterator.hasNext()) {
            GameObject object = iterator.next();
            if(object.getID() == ID.Paddle) {
                if(getBounds().intersects(object.getBounds())) {
                    setVelX(-getVelX());

                    /* Creates less predictability in ball trajectory */
                    // Ball
                    float ballCenter = getY() + (float) getHeight() / 2;

                    // Paddle
                    float paddleCenter = object.getY() + (float) object.getHeight() / 2;

                    // Calculate angle
                    // Percent as decimal
                    float percentFromCenter = Math.abs(paddleCenter - ballCenter) / ((float) object.getHeight() / 2);
                    if(percentFromCenter > 0.9)
                        percentFromCenter = (float) 0.9;

                    setVelY(-Main.OPP_VEL_Y * percentFromCenter);
                }
            }
        }
    }

    @Override
    public void tick() {
        setX(getX() + getVelX());
        setY(getY() + getVelY());

        wallDetection();
        collisions();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect((int) getX(), (int) getY(), getWidth(), getHeight());
    }
}
