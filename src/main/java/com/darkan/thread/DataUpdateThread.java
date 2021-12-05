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
package com.darkan.thread;

import com.darkan.api.accessors.GroundItems;
import com.darkan.api.accessors.NPCs;
import com.darkan.api.accessors.SpotAnims;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.inter.chat.Chatbox;
import com.darkan.api.util.Logger;

public class DataUpdateThread extends Thread {

	public static volatile boolean UPDATING_DATA = false;
	
	public DataUpdateThread() {
		setPriority(Thread.MAX_PRIORITY);
		setName("Data Update Thread");
		this.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable ex) {
				Logger.handle(ex);
			}
		});
	}
	
	@Override
	public void run() {
		try {
			if (UPDATING_DATA)
				return;
			UPDATING_DATA = true;
			Chatbox.update();
			NPCs.update();
			WorldObjects.update();
			SpotAnims.update();
			GroundItems.update();
			UPDATING_DATA = false;
		} catch(Exception e) {
			Logger.handle(e);
		}
	}
}
