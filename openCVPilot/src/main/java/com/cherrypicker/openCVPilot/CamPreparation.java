package com.cherrypicker.openCVPilot;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import org.opencv.videoio.VideoCapture;

public class CamPreparation extends SwingWorker<VideoCapture, String> {

	private final JPanel videoPanel;
	private final JLabel statuslabel;
	private final JLabel stateJLabel;
	
	@Override
	protected VideoCapture doInBackground() throws Exception {
	    publish("Starting to load camera");
	    VideoCapture camera = null;
	    if(!isCancelled()){
	    	camera = new VideoCapture(0);
	    }
	    publish("Camera is connected");
		return camera;
	}

	@Override
	protected void process(List<String> chunks) {
		for(String text: chunks){
			if(!isCancelled()){
				statuslabel.setText(text);
			}
		}
	}

	public CamPreparation(JPanel videoPanel, JLabel statuslabel, JLabel stateJLabel){
		this.videoPanel = videoPanel;
		this.statuslabel = statuslabel;
		this.stateJLabel = stateJLabel;
	}

	@Override
	protected void done() {
		if(isCancelled()){
			return;
		}
		try {
			CamPlay cp = new CamPlay(videoPanel, stateJLabel, get());
			cp.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
