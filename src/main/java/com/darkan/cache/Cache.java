package com.darkan.cache;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import com.darkan.api.util.JsonFileManager;
import com.darkan.cache.dto.ItemInfo;
import com.darkan.cache.dto.NPCInfo;
import com.darkan.cache.dto.ObjectInfo;
import com.darkan.cache.dto.RegionInfo;

/**
 * Remote API for pulling information from Trent's cache library.
 * 
 * @author trent
 */
public class Cache {

	private static final String CACHE_URL = "http://testlobby.darkan.org:5001/cache.json";
	private static File CACHE_FILE = new File(System.getProperty("user.home") + "/.freedev/cache.json");
	private static Cache instance;

	private Map<Integer, NPCInfo> npcs = new HashMap<>();
	private Map<Integer, ItemInfo> items = new HashMap<>();
	private Map<Integer, ObjectInfo> objects = new HashMap<>();
	private Map<Integer, RegionInfo> regions = new HashMap<>();

	public static NPCInfo getNPC(int id) {
		return instance.npcs.get(id);
	}

	public static ItemInfo getItem(int id) {
		return instance.items.get(id);
	}

	public static ObjectInfo getObject(int id) {
		return instance.objects.get(id);
	}

	public static RegionInfo getRegion(int id) {
		return instance.regions.get(id);
	}

	public static void loadCache() {
		File dir = new File(CACHE_FILE.getParent());
		if (!dir.exists()) {
			dir.mkdirs();
		}
		if (!CACHE_FILE.exists())
			downloadCache();
		try {
			instance = JsonFileManager.loadJsonFile(CACHE_FILE, Cache.class);
		} catch (Exception e) {
			CACHE_FILE.delete();
			e.printStackTrace();
			JFrame err = new JFrame();
			err.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			JOptionPane.showMessageDialog(err, "Error loading cache. " + e.getMessage(), "Dialog", JOptionPane.ERROR_MESSAGE);
		}
		System.out.println("Loaded cache: " + instance.regions.size());
	}

	public static void downloadCache() {
		final JProgressBar jProgressBar = new JProgressBar();
		jProgressBar.setMaximum(100000);
		JFrame frame = new JFrame();
		frame.setContentPane(jProgressBar);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setTitle("Downloading Cache");
		frame.setSize(300, 70);
		frame.setVisible(true);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int winX = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int winY = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(winX, winY);
		try {
			URL url = new URL(CACHE_URL);
			HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
			long completeFileSize = httpConnection.getContentLength();

			BufferedInputStream in = new BufferedInputStream(httpConnection.getInputStream());
			FileOutputStream fos = new FileOutputStream(CACHE_FILE);
			BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
			byte[] data = new byte[1024];
			long downloadedFileSize = 0;
			int x = 0;
			while ((x = in.read(data, 0, 1024)) >= 0) {
				downloadedFileSize += x;
				final int currentProgress = (int) ((((double) downloadedFileSize) / ((double) completeFileSize)) * 100000d);
				jProgressBar.setValue(currentProgress);
				bout.write(data, 0, x);
			}
			bout.close();
			in.close();
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		} catch (Exception e) {
			e.printStackTrace();
			JFrame err = new JFrame();
			err.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			JOptionPane.showMessageDialog(err, "Error downloading cache." + e.getMessage(), "Dialog", JOptionPane.ERROR_MESSAGE);
		}
	}
}
