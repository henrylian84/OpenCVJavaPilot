package com.cherrypicker.openCVPilot;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class CamPlay extends Thread {

	private final JPanel videoPanel;
	private final JLabel stateJLabel;
	private final VideoCapture videoCapture;

	public CamPlay(JPanel videoPanel, JLabel stateJLabel,
			VideoCapture videoCapture) {
		this.videoPanel = videoPanel;
		this.stateJLabel = stateJLabel;
		this.videoCapture = videoCapture;
	}

	@Override
	public void run() {
		Mat frame = new Mat();
		MatOfByte mem = new MatOfByte();

		while (true) {
			if(stateJLabel.getText().equalsIgnoreCase("off")){
				videoCapture.release();
				videoPanel.repaint();
				break;
			}
			videoCapture.grab();
			videoCapture.retrieve(frame);
			videoCapture.read(frame);
			Imgcodecs.imencode(".bmp", frame, mem);

			Image im = null;
			try {
				im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			BufferedImage buff = resizeImage((BufferedImage) im, videoPanel.getWidth(), videoPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics g = videoPanel.getGraphics();
			g.drawImage(buff, 0, 0, videoPanel.getWidth(), videoPanel.getHeight(), 0, 0,
					buff.getWidth(), buff.getHeight(), null);
		}
	}
	
	private BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int type) {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();

		return resizedImage;
	}
}
