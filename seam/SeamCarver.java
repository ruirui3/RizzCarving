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

        Picture newPicture = new Picture(width(), height() - 1);

        for (int col = 0; col < newPicture.width(); col++) {

            int rowToBeRemoved = seam[col];

            for (int row = 0; row < newPicture.height(); row++) {

                if (row < rowToBeRemoved) {
                    newPicture.setRGB(col, row, picture.getRGB(col, row));
                } else {
                    newPicture.setRGB(col, row, picture.getRGB(col, row + 1));
                }

            }

        }

        picture = newPicture;

        double[][] newEnergy = new double[height() - 1][width()];
        for (int col = 0; col < newPicture.width(); col++) {

            int rowToBeRemoved = seam[col];

            for (int row = 0; row < newPicture.height(); row++) {

                if (row < rowToBeRemoved - 1) { // shift due to new pic
                    newEnergy[row][col] = energyField[row][col];
                } else if (row > rowToBeRemoved) {
                    newEnergy[row][col] = energyField[row + 1][col];
                } else {
                    newEnergy[row][col] = calculateEnergy(row, col);
                }

            }

        }

        energyField = newEnergy;

    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) { // seam col based

        Picture newPicture = new Picture(width() - 1, height());

        for (int row = 0; row < newPicture.height(); row++) {

            int colToBeRemoved = seam[row];

            for (int col = 0; col < newPicture.width(); col++) {

                if (col < colToBeRemoved) {
                    newPicture.setRGB(col, row, picture.getRGB(col, row));
                } else if (col > colToBeRemoved) {
                    newPicture.setRGB(col - 1, row, picture.getRGB(col, row));
                }

            }

        }

        picture = newPicture;
        double[][] newEnergy = new double[height()][width() - 1];

        for (int row = 0; row < newPicture.height(); row++) {

            int colToBeRemoved = seam[row];

            for (int col = 0; col < newPicture.width(); col++) {

                if (col < colToBeRemoved - 1) { // shift due to new pic
                    newEnergy[row][col] = energyField[row][col];
                } else if (col > colToBeRemoved) {
                    newEnergy[row][col] = energyField[row][col + 1];
                } else {
                    newEnergy[row][col] = calculateEnergy(row, col);
                }

            }

        }

        energyField = newEnergy;

    }

    // unit testing (optional)
    public static void main(String[] args) {
        // [UncommentedEmptyMethodBody]
    }

}