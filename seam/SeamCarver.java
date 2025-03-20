import java.awt.Color;

import edu.princeton.cs.algs4.Picture;


public class SeamCarver {

    private Picture picture;
    private double[][] energyField;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {

        this.picture = picture;

        //calculate initial energy

        calculateEnergyField();

    }

    private void calculateEnergyField() {

        energyField = new double[picture.height()][picture.width()];
        //for each row
        //for each column
            //if row == 0 or row = picture.height()-1 or col == 0 or col = picture.width-1, energy = 1000
        //else
            //do math

    }
 
    // current picture
    public Picture picture() {
        return picture;
    }
 
    // width of current picture
    public int width() {
        return 0;
    }
 
    // height of current picture
    public int height() {
        return 0;
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
        return null;
    }
 
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

    }
 
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {

    }
 
    //  unit testing (optional)
    public static void main(String[] args) {

    }
 
 }