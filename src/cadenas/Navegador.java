package cadenas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import java.awt.BorderLayout;

public class Navegador {

	 JFrame frame;
	JFileChooser fileChooser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Navegador window = new Navegador();
					
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Navegador() {
		initialize();
		frame.setVisible(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		frame.setResizable(false);
		
		frame.setBounds(100, 100, 652, 418);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		fileChooser = new JFileChooser();
		
		frame.getContentPane().add(fileChooser, BorderLayout.NORTH);
		
	}
	
}
