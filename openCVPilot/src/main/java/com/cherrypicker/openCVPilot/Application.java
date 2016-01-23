package com.cherrypicker.openCVPilot;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Application extends JFrame {
	
	private JPanel videoPanel;
	
	private JLabel label;
	
	private JLabel stateIndicator;
	
	private CamPreparation camPreparation; 

	public Application() {
		this.setLayout(new BorderLayout());
		this.setState(Frame.NORMAL);
		this.setVisible(true);
		this.setSize(800, 600);
		// this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		videoPanel = new JPanel(){
			@Override
	        public void paintComponent(Graphics g) {
	            super.paintComponent(g);
			}   
		};
		
		label = new JLabel();
		videoPanel.add(label);
		videoPanel.setPreferredSize(new Dimension(640, 360));
		this.add(videoPanel, BorderLayout.CENTER);

		JPanel buttonPl = new JPanel();
		this.add(buttonPl, BorderLayout.SOUTH);
		buttonPl.setPreferredSize(new Dimension(640, 50));
		buttonPl.setLayout(new BoxLayout(buttonPl, BoxLayout.X_AXIS));

		JButton start = new JButton("Start");
		JButton stop = new JButton("Stop");
		stateIndicator = new JLabel();
		stateIndicator.setText("Off");
		buttonPl.add(start);
		buttonPl.add(stop);
		buttonPl.add(stateIndicator);
		start.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				start.setEnabled(false);
				stop.setEnabled(true);
				stateIndicator.setText("On");
				camPreparation = new CamPreparation(videoPanel, label, stateIndicator);
				camPreparation.execute();
				
			}
		});
		stop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				stop.setEnabled(false);
				camPreparation.cancel(true);
				start.setEnabled(true);
				label.setText("");
				stateIndicator.setText("Off");
			}
		});

	}
	
	public static void main(String[] args) {
//		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.load(new File("/Users/henrylian/opencv-3.1.0/build/lib/libopencv_java310.so").getAbsolutePath());
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new Application();
			}
		});
	}
}
