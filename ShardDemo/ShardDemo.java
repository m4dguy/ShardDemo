package ShardDemo;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import cvforge.CVForgeShard;

/**
 * Shard containing demo functions.
 * Be aware that Shards must implement the CVForgeShard interface.
 * Compile this class into a jar, add the file suffix ".shard.jar" and drop it into ImageJ's plugin folder to call it with CVForge.
 */
public class ShardDemo implements CVForgeShard{
	/**
	 * Simple dilation.
	 * @param src Input image.
	 * @param dst Output image.
	 * @param size Size of dilation kernel.
     */
	public static void quickDilate(Mat src, Mat dst, int size){
		Mat element = Imgproc.getStructuringElement(0, new Size(size, size));
		Imgproc.morphologyEx(src, dst, Imgproc.MORPH_DILATE, element);
	}

	/**
	 * Bandpass filtering with two Gaussians of different variances.
	 * Useful for eliminating colour gradients.
	 * @param src Input image.
	 * @param dst Output image.
	 * @param var1 Variance of first Gaussian.
     * @param var2 Variance of second Gaussian.
     */
	public static void differenceOfGaussians(Mat src, Mat dst, float var1, float var2){
		Mat gauss1 = new Mat(src.size(), src.type());
		Mat gauss2 = new Mat(src.size(), src.type());

		Imgproc.GaussianBlur(src, gauss1, new Size((var1*1.5), (var1*1.5)), var1);
		Imgproc.GaussianBlur(src, gauss2, new Size((var2*1.5), (var2*1.5)), var2);

		Core.subtract(gauss1, gauss2, dst);
	}

	/**
	 * Detect red blood cells with Hough Transform and create a mask.
	 * Uses medianBlur for denoising, then applies Hough Transform.
	 * Found circles are rejected if their radius exceed a maximum size.
	 * @param src Input image.
	 * @param sensi Sensitivity parameter for Hough Transform
	 * @param sizes Size of circles to detect.
	 * @param maxRad Largest permitted circle radius.
     * @return Mask with detected cells.
     */
	public static Mat redBloodCellDetect(Mat src, int sensi, int sizes, int maxRad){
		Mat smooth = Mat.zeros(src.size(), src.type());
		Mat edges = Mat.zeros(src.size(), src.type());
		Mat circles = new Mat();
	
		Mat dst = Mat.zeros(src.size(), src.type());

		// find circles
		Imgproc.medianBlur(src, smooth, 3);
		Imgproc.HoughCircles(src, circles, Imgproc.CV_HOUGH_GRADIENT, sensi, sizes);

		// filter circles
		int found = 0;
		for (int i = 0; i < circles.cols(); i++) {
			double[] vCircle = circles.get(0, i);
			int radius = (int)Math.round(vCircle[2]);
			
			if(radius <= maxRad){
				++found;
				Point pt = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
		        Core.circle(dst, pt, radius, new Scalar(255, 255, 255), -1);
			}
	    }
		System.out.println("circles found: " + found);
		return dst;
	}
}
