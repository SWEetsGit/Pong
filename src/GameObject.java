import java.awt.*;

public abstract class GameObject {
    private float x, y, velX, velY;

    private int width, height;

    private ID id;

    private final Handler handler;

    public GameObject(float x, float y, int width, int height, float velX, float velY, ID id, Handler handler) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velX = velX;
        this.velY = velY;
        this.id = id;
        this.handler = handler;

        handler.addObject(this);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), getWidth(), getHeight());
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public void setX(float x) {
        this.x = x;
    }

    public float getX() {
        return x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public float getVelY() {
        return velY;
    }

    public void setID(ID id) {
        this.id = id;
    }

    public ID getID() {
        return id;
    }

    public Handler getHandler() {
        return handler;
    }
}
