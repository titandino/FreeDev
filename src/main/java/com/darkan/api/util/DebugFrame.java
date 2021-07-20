package com.darkan.api.util;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

public class DebugFrame extends JFrame {
	private static final long serialVersionUID = 1374943686017270114L;

	private JTextArea textArea;

	private JButton buttonClear = new JButton("Clear");

	private PrintStream standardOut;

	public DebugFrame() {
		super("Debug Log");

		textArea = new JTextArea(50, 10);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		PrintStream printStream = new PrintStream(new DebugFrameOS(textArea));

		standardOut = System.out;

		System.setOut(printStream);
		System.setErr(printStream);

		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.anchor = GridBagConstraints.WEST;

		add(buttonClear, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;

		add(new JScrollPane(textArea), constraints);

		buttonClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
					textArea.getDocument().remove(0, textArea.getDocument().getLength());
					standardOut.println("Cleared...");
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			}
		});

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(480, 320);
		setLocationRelativeTo(null);
	}
}
