package cd123;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_core.cvGetSize;
import static org.bytedeco.javacpp.opencv_core.cvInRangeS;
import static org.bytedeco.javacpp.opencv_core.cvScalar;
import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class Cd123 {
    //color range of red like color
    static AudioStream audios;
    
    
    
     public static void playMusic1(String filepath) {
        InputStream music;
        try {
            music = new FileInputStream(new File(filepath));
            audios = new AudioStream(music);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
    
    
    
    
    
    public int getBinaryData(IplImage gray) {
        int x11=0;
       // int [][] dt=new int[gray.height()][gray.width()];   
        BytePointer bp=gray.arrayData();
        
        for (int y = 0; y < gray.height(); y++) {
            for (int x = 0; x < gray.width(); x++) {
                
                int index=y * gray.widthStep() +x* gray.nChannels();
                int val2=bp.get(index) & 0xFF;
                
                int val3= (val2==0)? 0:1;
                if(val3==1){
                    x11++;
                }
               // System.out.print(val3 );
            
            }
           // System.out.println("");
        }
    return x11;
    }
    
    public Cd123() {
        
        
        
        
        
         playMusic1("strings3.wav");
        
        
        
        
        
        
        
        FrameGrabber grabber = null;
        try {    // create FrameGrabber object using 0th camera   
            grabber = new OpenCVFrameGrabber(0);            //now start the camera    
            grabber.start();
        } catch (Exception ex) {
            System.out.println("Camera can't start");
        }
//Make a canvasframe to display image
        CanvasFrame canvas = new CanvasFrame("My Image");
        CanvasFrame canvas2 = new CanvasFrame("red");
        CanvasFrame canvas3 = new CanvasFrame("blue");
        canvas.setDefaultCloseOperation(CanvasFrame.EXIT_ON_CLOSE );
//Now, capture until window is closed and show it.
while (true) {
            IplImage img = null;
            try {           //grab current image     
                img = grabber.grab();           //show the image   
                canvas.showImage(img);
                
                
                
                
       // CvScalar min1 = cvScalar(150, 150, 75, 0);//BGR-A
       // CvScalar max1 = cvScalar(200, 255, 255, 0);//BGR-A
       // CvScalar min2 = cvScalar(95, 150, 75, 0);//BGR-A
       // CvScalar max2 = cvScalar(145, 255, 255, 0);  //BGR-A
        
        CvScalar min1 = cvScalar(0, 0, 130, 0);//BGR-A
        CvScalar max1 = cvScalar(140, 110, 255, 0);//BGR-A
        CvScalar min2 = cvScalar(95,150,75,0);//BGR-A
        CvScalar max2 = cvScalar(95,255,255,0);//BGR-A
        
        
        
//        CvScalar min1 = cvScalar(0, 0, 130, 0);//BGR-A
//        CvScalar max1 = cvScalar(140, 110, 255, 0);//BGR-A
//        CvScalar min2 = cvScalar(130,0,0,0);//BGR-A
//        CvScalar max2 = cvScalar(255,140,110,0);//BGR-A
        
        
        //read imag1
//        CvCapture c1=cvCreateCameraCapture(0);
//        IplImage img1=cvQueryFrame(c1);
        
        IplImage orgImg = img;
        //create binary image of original size
        IplImage imgThreshold = cvCreateImage(cvGetSize(orgImg), 8, 1);
        IplImage imgThreshold1 = cvCreateImage(cvGetSize(orgImg), 8, 1);
        //apply thresholding
        cvInRangeS(orgImg, min1, max1, imgThreshold);
        cvInRangeS(orgImg, min2, max2, imgThreshold1);
        
         canvas2.showImage(imgThreshold);
          canvas3.showImage(imgThreshold1);
        
        //smooth filter- median
//        cvSmooth(imgThreshold, imgThreshold, CV_MEDIAN, 13);

//         opencv_imgproc.cvSmooth();
        //save
        cvSaveImage("red.jpg", imgThreshold);
        cvSaveImage("blue.jpg", imgThreshold1);
        
        //Color[][] colors = loadPixelsFromImage(new File("hello.jpg"));
        int i11=getBinaryData(imgThreshold);
        int i22=getBinaryData(imgThreshold1);
  /*      
        if(i22 >100){
            AudioPlayer.player.start(audios);
            
        }
        if(i11 >2000){  //Maybe 800;
            AudioPlayer.player.stop(audios);
        }
        
       */ 
        
        
        
        //for web cam
        
      if(i22 >100){
            AudioPlayer.player.start(audios);
            
        }
        if(i11 >500){
            AudioPlayer.player.stop(audios);
        }
        
        
        
//        System.out.println(i11);
//        System.out.println(i22);
                
                
                
                
            } catch (Exception ex) {
                System.out.println("Can't capture");
                break;
            }
            
            
            
            
        } //And Finally close it.
try {
        grabber.stop();
    }
    catch (Exception ex) {  
           System.out.println("Error");
    }
        
 }
    public static void main(String[] args) throws IOException {
        
       
       new Cd123();
       
    }   
}

