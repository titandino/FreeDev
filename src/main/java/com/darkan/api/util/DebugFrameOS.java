// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright © 2021 Trenton Kress
//  This file is part of project: FreeDev
//
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
