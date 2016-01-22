package com.cherrypicker.openCVPilot;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import org.opencv.videoio.VideoCapture;

public class CamPreparation extends SwingWorker<VideoCapture, String> {

	private final JPanel videoPanel;
	private final JLabel label;
	
	@Override
	protected VideoCapture doInBackground() throws Exception {
	    publish("Starting to load camera");
		VideoCapture camera = new VideoCapture(0);
	    Thread.sleep(1000);
	    camera.open(0); //Useless
	    while(!camera.isOpened()){
	    	System.out.println("camera is not ready");
	    }
	    publish("Camera is connected");
		return camera;
	}

	@Override
	protected void process(List<String> chunks) {
		for(String text: chunks){
			label.setText(text);
		}
	}

	public CamPreparation(JPanel videoPanel, JLabel label){
		this.videoPanel = videoPanel;
		this.label = label;
	}

	@Override
	protected void done() {
	}
	
	
}
