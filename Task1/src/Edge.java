/**
 * Created by danil on 03.05.2018.
 */
public class Edge {
    private int pixNumber;
    private int stripeSize;
    private int colorCode;
    private Edge anotherEdge;
    private Edge cornerEdge;
    private boolean notInLineYet;

    public Edge(int pixNumber, int stripeSize, int colorCode) {
        this.pixNumber = pixNumber;
        this.stripeSize = stripeSize;
        this.colorCode = colorCode;
        anotherEdge = null;
        cornerEdge = null;
        notInLineYet = true;
    }

    public boolean isNotInLineYet() {
        return notInLineYet;
    }

    public void setNotInLineYet(boolean notInLineYet) {
        this.notInLineYet = notInLineYet;
    }

    public Edge getCornerEdge() {
        return cornerEdge;
    }

    public void setCornerEdge(Edge cornerEdge) {
        this.cornerEdge = cornerEdge;
    }

    public void setAnotherEdge(Edge anotherEdge) {
        this.anotherEdge = anotherEdge;
    }

    public int getPixNumber() {
        return pixNumber;
    }

    public int getStripeSize() {
        return stripeSize;
    }

    public int getColorCode() {
        return colorCode;
    }

    public Edge getAnotherEdge() {
        return anotherEdge;
    }
}
