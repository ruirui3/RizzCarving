import java.awt.Color;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private Picture picture;
    private double[][] energyField;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {

        this.picture = picture;
        energyField = new double[height()][width()];
        // calculate initial energy

        for (int row = 0; row < picture.height(); row++) {

            for (int col = 0; col < picture.width(); col++) {

                energyField[row][col] = calculateEnergy(row, col);

            }

        }

        // energyField = new double[picture.height()][picture.width()];
        // transposePicture();

    }

    private double calculateEnergy(int row, int col) {
        // no need bounds for row/col is lower than 0 or higher than height-1/width-1
        if (row == 0 || row == picture.height() - 1 || col == 0 || col == picture.width() - 1) {
            return 1000;
        }

        int x1 = picture.get(col - 1, row).getRed() - picture.get(col + 1, row).getRed();
        int y1 = picture.get(col - 1, row).getGreen() - picture.get(col + 1, row).getGreen();
        int z1 = picture.get(col - 1, row).getBlue() - picture.get(col + 1, row).getBlue();
        int x2 = picture.get(col, row - 1).getRed() - picture.get(col, row + 1).getRed();
        int y2 = picture.get(col, row - 1).getGreen() - picture.get(col, row + 1).getGreen();
        int z2 = picture.get(col, row - 1).getBlue() - picture.get(col, row + 1).getBlue();

        return Math.sqrt(Math.pow(x1, 2) + Math.pow(y1, 2) + Math.pow(z1, 2)
                + Math.pow(x2, 2) + Math.pow(y2, 2) + Math.pow(z2, 2));

    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int col, int row) {
        return energyField[row][col];
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[][] edgeTo = new int[height()][width()]; // index based
        double[][] distTo = new double[height()][width()]; // distTo energy
        for (int row = 0; row < height(); row++) {

            for (int col = 0; col < width(); col++) {

                if (col == 0) {
                    distTo[row][col] = 1000;
                } else {
                    distTo[row][col] = Double.POSITIVE_INFINITY;
                }

            }

        }

        for (int col = 0; col < picture.width() - 1; col++) {

            for (int row = 0; row < picture.height(); row++) {

                // up
                if (row != 0 && energy(col + 1, row - 1) + distTo[row][col] < distTo[row - 1][col + 1]) {
                    distTo[row - 1][col + 1] = energy(col + 1, row - 1) + distTo[row][col];
                    edgeTo[row - 1][col + 1] = row;
                }

                // middle
                if (energy(col + 1, row) + distTo[row][col] < distTo[row][col + 1]) {
                    distTo[row][col + 1] = energy(col + 1, row) + distTo[row][col];
                    edgeTo[row][col + 1] = row;
                }

                // down
                if (row != height() - 1 && energy(col + 1, row + 1) + distTo[row][col] < distTo[row + 1][col + 1]) {
                    distTo[row + 1][col + 1] = energy(col + 1, row + 1) + distTo[row][col];
                    edgeTo[row + 1][col + 1] = row;
                }

            }

        }

        double shortestPath = Integer.MAX_VALUE;
        int shortestPathRow = -1;
        for (int i = 0; i < height(); i++) {
            if (distTo[i][width() - 1] < shortestPath) {
                shortestPath = distTo[i][width() - 1];
                shortestPathRow = i;
            }
        }

        int[] returnList = new int[width()];

        int col = width() - 1;
        while (col >= 0) {
            returnList[col] = shortestPathRow;
            shortestPathRow = edgeTo[shortestPathRow][col];
            col--;
        }

        return returnList;

    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        // Traverse matric to build the distTo and edgeTo
        int[][] edgeTo = new int[height()][width()]; // index based
        double[][] distTo = new double[height()][width()]; // distTo energy
        for (int row = 0; row < height(); row++) {

            for (int col = 0; col < width(); col++) {

                if (row == 0) {
                    distTo[row][col] = 1000;
                } else {
                    distTo[row][col] = Double.POSITIVE_INFINITY;
                }

            }

        }

        /*
         * for (int col = 1; col < width()-1; col++) {
         * 
         * for (int row = 1; row<height(); row++) { //next row
         * 
         * if (col >= width()-1 || row >= height()) {
         * System.out.println(" a ");
         * }
         * 
         * 
         * //distTo[row][prevCol] + edgeTo[row][prevCol] < distTo[row][col-1]
         * if (distTo[row-1][col-1] + energy(col, row) < distTo[row][col]) {
         * distTo[row][col] = distTo[row-1][col-1] + energy(col, row);
         * edgeTo[row][col] = col-1;
         * }
         * if (distTo[row-1][col] + energy(col, row) < distTo[row][col]) {
         * distTo[row][col] = distTo[row-1][col] + energy(col, row);
         * edgeTo[row][col] = col;
         * }
         * if (distTo[row-1][col+1] + energy(col, row) < distTo[row][col]) {
         * distTo[row][col] = distTo[row-1][col+1] + energy(col, row);
         * edgeTo[row][col] = col+1;
         * }
         * 
         * }
         * 
         * }
         */

        for (int row = 0; row < picture.height() - 1; row++) {

            for (int col = 0; col < picture.width(); col++) {

                // left
                if (col != 0 && energy(col - 1, row + 1) + distTo[row][col] < distTo[row + 1][col - 1]) {
                    distTo[row + 1][col - 1] = energy(col - 1, row + 1) + distTo[row][col];
                    edgeTo[row + 1][col - 1] = col;
                }

                // mid
                if (energy(col, row + 1) + distTo[row][col] < distTo[row + 1][col]) {
                    distTo[row + 1][col] = energy(col, row + 1) + distTo[row][col];
                    edgeTo[row + 1][col] = col;
                }

                // right
                if (col != width() - 1 && energy(col + 1, row + 1) + distTo[row][col] < distTo[row + 1][col + 1]) {
                    distTo[row + 1][col + 1] = energy(col + 1, row + 1) + distTo[row][col];
                    edgeTo[row + 1][col + 1] = col;
                }

            }

        }

        double shortestPath = Integer.MAX_VALUE;
        int shortestPathColumn = -1;
        for (int i = 0; i < width(); i++) {
            if (distTo[height() - 1][i] < shortestPath) {
                shortestPath = distTo[height() - 1][i];
                shortestPathColumn = i;
            }
        }

        int[] returnList = new int[height()];

        int row = height() - 1;
        while (row >= 0) {
            returnList[row] = shortestPathColumn;
            shortestPathColumn = edgeTo[row][shortestPathColumn];
            row--;
        }

        return returnList;

    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        /*
         * if (vertical) {
         * transposePicture();
         * vertical = false;
         * }
         * removeSeam(seam);
         */
    }

    private void removeSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("Null argument to removeSeam.\n");
        }
        if (seam.length != picture.height()) {
            throw new IllegalArgumentException("seam wrong length.\n");
        }

        Picture tempPicture = new Picture(picture.width() - 1, picture.height());
        // seam removal in 2D for loop
        picture = tempPicture;

    }

    // unit testing (optional)
    public static void main(String[] args) {

    }

}