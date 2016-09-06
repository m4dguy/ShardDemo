import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

/**
 * Main class to test and call methods of the ShardDemo outside ImageJ.
 * Note that this snippet has no dependencies to ImageJ.
 * Loads an image denoted by the first console argument.
 * The image is filtered and dumped with the "_filtered" appended to its name.
 */
public class Main {
    public static void main(String[] args){
        // change this path to match your configuration
        String workingDir = System.getProperty("user.dir");
        System.load(workingDir + "/lib/x64/opencv_java2412.dll");

        if(args.length == 0) {
            System.out.println("No file given. Exiting...");
            System.exit(0);
        }

        String pathSrc = args[0];
        int ext = pathSrc.lastIndexOf(".");
        if(ext == -1) {
            System.out.println("Given file has no file extension. Exiting...");
            System.exit(0);
        }
        String pathDst = pathSrc.substring(0, ext) + "_filtered" + pathSrc.substring(ext, pathSrc.length());

        Mat src = Highgui.imread(pathSrc);
        Mat dst = Mat.zeros(src.size(), src.type());
        ShardDemo.ShardDemo.differenceOfGaussians(src, dst, 6, 18);
        Highgui.imwrite(pathDst, dst);

        System.out.println("Filtered image saved as:");
        System.out.println(pathDst);
    }
}
