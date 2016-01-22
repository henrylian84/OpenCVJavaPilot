package com.cherrypicker.openCVPilot;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.opencv.core.Core;

public class Application {
	
	private static JFrame win;

	private static JPanel videoPanel;
	
	private static JLabel label;
	
	private static final Executor executor = Executors.newSingleThreadExecutor();

	private static void initializeUI() {
		win = new JFrame();
		win.setLayout(new BorderLayout());
		win.setState(Frame.NORMAL);
		win.setVisible(true);
		win.setSize(800, 600);
		// win.setExtendedState(JFrame.MAXIMIZED_BOTH);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		videoPanel = new JPanel();
		label = new JLabel();
		videoPanel.add(label);
		videoPanel.setPreferredSize(new Dimension(640, 480));
		win.add(videoPanel, BorderLayout.CENTER);

		JPanel buttonPl = new JPanel();
		win.add(buttonPl, BorderLayout.SOUTH);
		buttonPl.setPreferredSize(new Dimension(640, 50));
		buttonPl.setLayout(new BoxLayout(buttonPl, BoxLayout.X_AXIS));

		JButton start = new JButton("Start");
		JButton stop = new JButton("Stop");
		buttonPl.add(start);
		buttonPl.add(stop);
		start.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				start.setEnabled(false);
				CamPreparation cp = new CamPreparation(videoPanel, label);
				cp.execute();
				
			}
		});

	}
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Application.initializeUI();
			}
		});
	}
}
