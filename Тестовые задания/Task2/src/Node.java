import java.io.Serializable;

/**
 * Created by danil on 04.05.2018.
 */

public class Node implements Serializable{
    private String path;
    private long whenCreated;
    private long whenModified;

    @Override
    public String toString() {
        return path;
    }

    public Node(){}

    public Node(String path, long whenCreated, long whenModified) {
        this.path = path;
        this.whenCreated = whenCreated;
        this.whenModified = whenModified;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(long whenCreated) {
        this.whenCreated = whenCreated;
    }

    public long getWhenModified() {
        return whenModified;
    }

    public void setWhenModified(long whenModified) {
        this.whenModified = whenModified;
    }
}
