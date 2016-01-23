package com.cherrypicker.openCVPilot;

import java.io.File;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class DetectFaceDemo {
	public void run() {
		System.out.println("\nRunning DetectFaceDemo");
		// Create a face detector from the cascade file in the resources
		// directory.
		// CascadeClassifier faceDetector = new
		// CascadeClassifier(getClass().getResource("lbpcascade_frontalface.xml").getPath());
		// Mat image =
		// Highgui.imread(getClass().getResource("lena.png").getPath());
		// 注意：源程序的路径会多打印一个‘/’，因此总是出现如下错误
		/*
		 * Detected 0 faces Writing faceDetection.png libpng warning: Image
		 * width is zero in IHDR libpng warning: Image height is zero in IHDR
		 * libpng error: Invalid IHDR data
		 */
		// 因此，我们将第一个字符去掉
		String xmlfilePath = getClass()
				.getResource("haarcascade_frontalface_alt.xml").getPath();
		
		CascadeClassifier faceDetector = new CascadeClassifier(xmlfilePath);
		Mat image = Imgcodecs.imread(getClass().getResource("rgb-color-scale.jpg").getPath());
		// Detect faces in the image.
		// MatOfRect is a special container class for Rect.
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);

		System.out.println(String.format("Detected %s faces",
				faceDetections.toArray().length));

		// Draw a bounding box around each face.
		for (Rect rect : faceDetections.toArray()) {
			Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x
					+ rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
		}

		// Save the visualized detection.
		String filename = "faceDetection.png";
		System.out.println(String.format("Writing %s", filename));
		Imgcodecs.imwrite(filename, image);
	}
	
	public static void main(String[] args) {
		System.load(new File("/Users/henrylian/opencv-3.1.0/build/lib/libopencv_java310.so").getAbsolutePath());
		DetectFaceDemo dfd = new DetectFaceDemo();
		dfd.run();
	}
}
