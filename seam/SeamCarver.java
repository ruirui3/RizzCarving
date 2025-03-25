import java.awt.Color;

import edu.princeton.cs.algs4.Picture;


public class SeamCarver {

    private Picture picture;
    private double[][] energyField;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {

        this.picture = picture;

        //calculate initial energy

        for (int row = 0; row<picture.height(); row++) {

            for (int col = 0; col < picture.width(); col++) {

                energyField[row][col] = calculateEnergy(row, col);

            }

        }

        calculateEnergyField();

        energyField = new double[picture.height()][picture.width()];
        transposePicture();


    }

    private double calculateEnergy(int row, int col) {
        //fetch RGB values for top bottom, left right? how to fetch RGB

        if (row == 0 || row == picture.height()-1 || col == 0 || col == picture.width()) {
            return 1000;
        }

        int x1 = picture.get(col-1, row).getRed() - picture.get(col+1, row).getRed();
        int y1 = picture.get(col-1, row).getGreen() - picture.get(col+1, row).getGreen();
        int z1 = picture.get(col-1, row).getBlue() - picture.get(col+1, row).getBlue();
        int x2 = picture.get(col, row-1).getRed() - picture.get(col, row+1).getRed();
        int y2 = picture.get(col, row-1).getGreen() - picture.get(col, row+1).getGreen();
        int z2 = picture.get(col, row-1).getBlue() - picture.get(col, row+1).getBlue();

        return Math.sqrt(Math.pow(x1, 2) + Math.pow(y1, 2) + Math.pow(z1, 2)
        + Math.pow(x2, 2) + Math.pow(y2, 2) + Math.pow(z2, 2));




    }

    private void calculateEnergyField() {

        energyField = new double[picture.height()][picture.width()];
        //for each row
        //for each column
            //if row == 0 or row = picture.height()-1 or col == 0 or col = picture.width-1, energy = 1000
        //else
            //do math

    }

    private void transposePicture() {
        Picture tempPicture = new Picture(picture.height(), picture.width());
        for (int row = 0; row < picture.width(); row++) {
            for (int col = 0; col < picture.height(); col++) {

                tempPicture.setARGB(col, row, picture.getARGB(row, col));

            }
        }
        picture = tempPicture;
    }
 
    // current picture
    public Picture picture() {
        return picture;
    }
 
    // width of current picture
    public int width() {
        return picture.width();
    }
 
    // height of current picture
    public int height() {
        return picture().height();
    }
 
    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        return 0;
    }
 
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return null;
    }
 
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        //Traverse matric to build the distTo and edgeTo
        int[][] edgeTo = new int[picture.height()][picture.width()];
        double[][] distTo = new double[picture.height()][picture.width()];

        //for all rows
        //for all cols
        //set initial diskTo as POSITIVE INFINITY
        //if row == 0 - sset diskTo to 1000 since it is on the border

        //Consider all verticies in topological order
        //Relas all edges pointing from that vertex
        
        //for all rows and cols, 
        //Check below, left, right, above

        return null;
    }
 
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

    }
 
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (vertical) {
            transposePicture();
            vertical = false;
        }
        removeSeam(seam);
    }

    private void removeSeam (int [] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("Null argument to removeSeam.\n");
        }
        if (seam.length != picture.height()) {
            throw new IllegalArgumentException("seam wrong length.\n");
        }

        Picture tempPicture = new Picture(picture.width() - 1, picture.height());
        //seam removal in 2D for loop
        picture = tempPicture;
        

    }
 
    //  unit testing (optional)
    public static void main(String[] args) {

    }
 
 }