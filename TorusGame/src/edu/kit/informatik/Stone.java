package edu.kit.informatik;

public class Stone {

    private final int index;
    private final String color, edge, size, shape;

    public Stone(int index, String color, String edge, String size, String shape) {
        this.index = index;
        this.color = color;
        this.edge = edge;
        this.size = size;
        this.shape = shape;
    }

    public int getIndex() {
        return index;
    }

    public String getColor() {
        return color;
    }

    public String getEdge() {
        return edge;
    }

    public String getSize() {
        return size;
    }

    public String getShape() {
        return shape;
    }
}