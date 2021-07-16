package com.darkan.api.util;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

public class DebugFrameOS extends OutputStream {
	private JTextArea textArea;

	public DebugFrameOS(JTextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void write(int b) throws IOException {
		textArea.append(String.valueOf((char) b));
		textArea.setCaretPosition(textArea.getDocument().getLength());
		if (textArea.getDocument().getLength() > 10000)
			try {
				textArea.getDocument().remove(0, 1);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
