import java.awt.*;
import java.util.LinkedList;
import java.util.ListIterator;

public class Handler {
    private final LinkedList<GameObject> gameObjects = new LinkedList<>();

    public void tick() {
        for(GameObject gameObject : gameObjects) {
            gameObject.tick();
        }
    }

    public void render(Graphics g) {
        for(GameObject gameObject : gameObjects) {
            gameObject.render(g);
        }
    }

    public void addObject(GameObject object) {
        gameObjects.add(object);
    }

    public void removeObject(GameObject object) {
        gameObjects.remove(object);
    }

    public ListIterator<GameObject> getGameObjectListIterator() {
        return gameObjects.listIterator();
    }
}
