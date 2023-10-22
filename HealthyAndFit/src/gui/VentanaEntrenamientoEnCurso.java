package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class VentanaEntrenamientoEnCurso extends JFrame{
	
	 private static final long serialVersionUID = 1L;

	  
	    private JPanel panel;
	    private JLabel panelTiempo;

	    private JPanel buttonPanel;
	    private JButton botonStart;
	    private JButton botonReset;
	    private JButton botonStop;

	  
	    private byte centiseconds = 0;
	    private byte seconds = 20;
	    private short minutes = 0;

	    private DecimalFormat timeFormatter;

	    private Timer timer;

	    public VentanaEntrenamientoEnCurso() {
	        panel = new JPanel();
	        panel.setLayout(new BorderLayout());

	        panelTiempo = new JLabel();
	        panelTiempo.setFont(new Font("Consolas", Font.PLAIN, 30));
	        panelTiempo.setHorizontalAlignment(JLabel.CENTER);
	        panel.add(panelTiempo);

	        buttonPanel = new JPanel();
	        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

	        botonStart = new JButton("Start");
	        botonStart.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {

	                timer.start();

	            }
	        });
	        buttonPanel.add(botonStart);

	        botonReset = new JButton("Reset");
	        botonReset.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {

	                timer.stop();

	                centiseconds = 0;
	                seconds = 30;
	                minutes = 0;

	                panelTiempo.setText(timeFormatter.format(minutes) + ":"
	                        + timeFormatter.format(seconds) + "."
	                        + timeFormatter.format(centiseconds));
	            }
	        });

	        buttonPanel.add(botonReset);

	        botonStop = new JButton("Stop");
	        botonStop.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                timer.stop();
	            }
	        });

	        buttonPanel.add(botonStop);

	        panel.add(buttonPanel, BorderLayout.SOUTH);

	        timeFormatter = new DecimalFormat("00");

	        timer = new Timer(10, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (centiseconds > 0) {
	                    centiseconds--;
	                } else {
	                    if (seconds == 0 && minutes == 0) {
	                        timer.stop();
	                    } else if (seconds > 0) {
	                        seconds--;
	                        centiseconds = 99;
	                    } else if (minutes > 0) {
	                        minutes--;
	                        seconds = 59;
	                        centiseconds = 99;
	                    }
	                }
	                panelTiempo.setText(timeFormatter.format(minutes) + ":"
	                        + timeFormatter.format(seconds) + "."
	                        + timeFormatter.format(centiseconds));
	            }
	        });

	        panelTiempo.setText(timeFormatter.format(minutes) + ":"
	                + timeFormatter.format(seconds) + "."
	                + timeFormatter.format(centiseconds));

	        add(panel);

	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        setTitle("StopwatchGUI.java");

	        pack();
	        setVisible(true);
	    }

	    public static void main(String[] args) {
	        EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	             

	                new VentanaEntrenamientoEnCurso();
	            }
	        });
	    }

}
