/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 * 
 */

/**
 * 循環公告
 * @return bymca
 */

package l1j.server.server;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.ArrayList;
import l1j.server.Config;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SystemMessage;

public class Announcecycle {
	private static Logger _log = Logger.getLogger(Announcecycle.class.getName());

	private static Announcecycle _instance;

	private List<String> _Announcecycle = new ArrayList<String>();//TODO 加入循環公告元件型字串陣列組

	private int _Announcecyclesize = 0;

	private Announcecycle() {
		loadAnnouncecycle();
	}

	public static Announcecycle getInstance() {
		if (_instance == null) {
			_instance = new Announcecycle();
		}

		return _instance;
	}

	public void loadAnnouncecycle() { // 讀取循環公告
		_Announcecycle.clear();
		File file = new File("data/Announcecycle.txt");
		if (file.exists()) {
			readFromDiskmulti(file);
			doAnnouncecycle(); // 若有載入檔案即開始運作循環公告執行緒
		} else {
			_log.config("data/Announcecycle.txt 檔案不存在");
		}
	}

	private void readFromDiskmulti(File file) { // 循環公告多型法讀取應用
		LineNumberReader lnr = null;
		try {
			int i = 0;
			String line = null;
			lnr = new LineNumberReader(new FileReader(file)); // 實作行數讀取器讀取file檔案內的字串資料
			while ((line = lnr.readLine()) != null) { // 執行LOOP直到最後一行讀取為止
				StringTokenizer st = new StringTokenizer(line, "\n\r"); // 實作字串標記處理
				if (st.hasMoreTokens()) {
					String showAnnouncecycle = st.nextToken(); // 讀取某行後就換下一行
					_Announcecycle.add(showAnnouncecycle); // 將每行的字串載入_Announcecycle元件處理

					i++;
				}
			}

			_log.config("Announcecycle: Loaded " + i + " Announcecycle.");
		} catch (IOException e1) {
			_log.log(Level.SEVERE, "Error reading Announcecycle", e1);
		} finally {
			try {
				lnr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void doAnnouncecycle() {
		AnnouncTask rs = new AnnouncTask();// 建立執行緒
		GeneralThreadPool.getInstance().scheduleAtFixedRate(rs, 180000,
				60000 * Config.Show_Announcecycle_Time);
		// 10分鐘公告一次(60秒*1分*1000毫秒)
	}

	/** The task launching the function doAnnouncCycle() */
	class AnnouncTask implements Runnable {
		@Override
		public void run() {
			try {
				ShowAnnounceToAll(_Announcecycle.get(_Announcecyclesize)); // 輪迴式公告發布
				_Announcecyclesize++;
				if (_Announcecyclesize >= _Announcecycle.size()) {
					_Announcecyclesize = 0;
				}
			} catch (Exception e) {
				_log.log(Level.WARNING, "", e);
			}
		}
	}

	private void ShowAnnounceToAll(String msg) {
		Collection<L1PcInstance> allpc = L1World.getInstance().getAllPlayers();
		for (L1PcInstance pc : allpc) {
			pc.sendPackets(new S_SystemMessage(msg));
		}
	}
}
