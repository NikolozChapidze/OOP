// JCount.java

/*
 Basic GUI/Threading exercise.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JCount extends JPanel {
	private JLabel value;
	private Worker worker;
	public JCount() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JTextField bound = new JTextField("100000000");
		value = new JLabel("0");
		JButton start = new JButton("Start");
		JButton stop = new JButton("Stop");
		worker = new Worker(bound.getText());
		add(bound);
		add(value);
		add(start);
		add(stop);
		add(Box.createRigidArea(new Dimension(0,40)));
		start.addActionListener(e -> {
			if (worker != null){
				worker.interrupt();
			}
			worker = new Worker(bound.getText());
			worker.start();
		});

		stop.addActionListener(e -> {
			if(worker != null){
				worker.interrupt();
				worker = null;
			}
		});
	}

	public class Worker extends Thread{

		private final int bound;

		public Worker(String number){
			this.bound = Integer.parseInt(number);
		}

		@Override
		public void run() {
			for (int i = 0; i <= bound; i++) {
				if (isInterrupted()) break;
				if ( i % 1000 == 0){
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						break;
					}
					final int nextToDisplay = i;
					SwingUtilities.invokeLater(() -> value.setText(String.valueOf(nextToDisplay)));
				}
			}
		}
	}
	
	static public void main(String[] args)  {
		SwingUtilities.invokeLater(JCount::createAndShowGUI);

	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("The Count");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

