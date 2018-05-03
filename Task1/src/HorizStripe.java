/**
 * Created by danil on 02.05.2018.
 */
public class HorizStripe {
    private int size;
    private int colorCode;
    private int row;
    private long firstEdge;
    private long secondEdge;

    /**
     *  startCoordinate и finishCoordinate номера первого и последнего пикселя в строке
     */
    public HorizStripe(int size, int colorCode, int row, int startCoordinate, int finishCoordinate, int imageWidth) {
        this.size = size;
        this.colorCode = colorCode;
        this.row = row;
        this.firstEdge = row * imageWidth + startCoordinate;
        this.secondEdge = row * imageWidth + finishCoordinate;
    }

    public int getSize() {
        return size;
    }

    public int getColorCode() {
        return colorCode;
    }

    public int getRow() {
        return row;
    }

    public long getFirstEdge() {
        return firstEdge;
    }

    public long getSecondEdge() {
        return secondEdge;
    }
}
