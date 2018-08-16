/**
 *                            License
 * THE WORK (AS DEFINED BELOW) IS PROVIDED UNDER THE TERMS OF THIS  
 * CREATIVE COMMONS PUBLIC LICENSE ("CCPL" OR "LICENSE"). 
 * THE WORK IS PROTECTED BY COPYRIGHT AND/OR OTHER APPLICABLE LAW.  
 * ANY USE OF THE WORK OTHER THAN AS AUTHORIZED UNDER THIS LICENSE OR  
 * COPYRIGHT LAW IS PROHIBITED.
 * 
 * BY EXERCISING ANY RIGHTS TO THE WORK PROVIDED HERE, YOU ACCEPT AND  
 * AGREE TO BE BOUND BY THE TERMS OF THIS LICENSE. TO THE EXTENT THIS LICENSE  
 * MAY BE CONSIDERED TO BE A CONTRACT, THE LICENSOR GRANTS YOU THE RIGHTS CONTAINED 
 * HERE IN CONSIDERATION OF YOUR ACCEPTANCE OF SUCH TERMS AND CONDITIONS.
 * 
 */
package l1j.server.server.clientpackets;

import l1j.server.Config;
import l1j.server.server.ClientThread;
import l1j.server.server.GetNowTime;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_NPCTalkReturn;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_WhoAmount;
import l1j.server.server.serverpackets.S_WhoCharinfo;
import l1j.william.L1GameReStart;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket

/**
 * 處理收到由客戶端傳來查詢線上人數的封包
 */
public class C_Who extends ClientBasePacket {

	private static final String C_WHO = "[C] C_Who";

	public C_Who(byte[] decrypt, ClientThread client) {
		super(decrypt);
		String amount = String.valueOf(L1World.getInstance().getAllPlayers().size());
		L1PcInstance pc = client.getActiveChar();
		if (pc == null) {
			return;
		}
		
		String s = readS();
		L1PcInstance find = L1World.getInstance().getPlayer(s);
		

		if (find != null) {
			S_WhoCharinfo s_whocharinfo = new S_WhoCharinfo(find);
			pc.sendPackets(s_whocharinfo);
		} else {
			if (Config.ALT_WHO_COMMAND|| pc.isGm()) {
				switch (Config.ALT_WHO_TYPE) {
				case 1:// 對話視窗顯示
				pc.sendPackets(new S_SystemMessage("經驗值:"+Config.RATE_XP+"倍 掉寶:"+Config.RATE_DROP_ITEMS+"倍 金幣:"+Config.RATE_DROP_ADENA+"倍。"));
				pc.sendPackets(new S_SystemMessage("友好:"+Config.RATE_KARMA+"倍 正義:"+Config.RATE_LA+"倍 。"));
				pc.sendPackets(new S_SystemMessage("武器:"+Config.ENCHANT_CHANCE_WEAPON+"%/防具:"+Config.ENCHANT_CHANCE_ARMOR+"% 屬性卷:"+Config.ATTR_ENCHANT_CHANCE+"%。"));
			        //TODO 今天日期
				int Mon = GetNowTime.GetNowMonth();//TODO 月份錯誤補正
				pc.sendPackets(new S_SystemMessage("今天:"+GetNowTime.GetNowYear()+"年"+(Mon+1)+"月"+GetNowTime.GetNowDay()+"日"+GetNowTime.GetNowWeek()+"。"));
				//TODO 目前時間
				pc.sendPackets(new S_SystemMessage("現在時間(24h):"+GetNowTime.GetNowHour()+"時"+GetNowTime.GetNowMinute()+"分"+GetNowTime.GetNowSecond()+"秒。"));
				int second = L1GameReStart.getInstance().GetRemnant();
				pc.sendPackets(new S_SystemMessage("伺服器重啟時間還有:"+(second/60)/60+"時"+(second/60)%60+"分"+second%60+"秒。"));
				//TODO 線上資訊
				S_WhoAmount s_whoamount = new S_WhoAmount(amount);
				pc.sendPackets(s_whoamount);
				break;
			  }
			}
		}
	}

	@Override
	public String getType() {
		return C_WHO;
	}
}
