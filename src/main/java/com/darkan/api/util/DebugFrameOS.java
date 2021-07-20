package com.darkan.api.util;

import java.io.OutputStream;

import javax.swing.JTextArea;

public class DebugFrameOS extends OutputStream {
	private JTextArea textArea;

	public DebugFrameOS(JTextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void write(int b) {
		try {
			textArea.append(String.valueOf((char) b));
			textArea.setCaretPosition(textArea.getDocument().getLength());
			if (textArea.getDocument().getLength() > 10000)
				textArea.getDocument().remove(0, 1);
		} catch (Exception e) {
			Logger.handle(e);
		}
	}
}
