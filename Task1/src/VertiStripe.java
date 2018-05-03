/**
 * Created by danil on 02.05.2018.
 */
public class VertiStripe {
    private int size;
    private int colorCode;
    private int column;
    private long firstEdge;
    private long secondEdge;

    /**
     *  startCoordinate и finishCoordinate номера первого и последнего пикселя в столбце
     */
    public VertiStripe(int size, int colorCode, int column, int startCoordinate, int finishCoordinate, int imageWidth) {
        this.size = size;
        this.colorCode = colorCode;
        this.column = column;
        this.firstEdge = startCoordinate * imageWidth + column;
        this.secondEdge = finishCoordinate * imageWidth + column;
    }

    public VertiStripe(int size, int colorCode, int column, long firstEdge, long secondEdge) {
        this.size = size;
        this.colorCode = colorCode;
        this.column = column;
        this.firstEdge = firstEdge;
        this.secondEdge = secondEdge;
    }

    public int getSize() {
        return size;
    }

    public int getColorCode() {
        return colorCode;
    }

    public int getColumn() {
        return column;
    }

    public long getFirstEdge() {
        return firstEdge;
    }

    public long getSecondEdge() {
        return secondEdge;
    }
}
