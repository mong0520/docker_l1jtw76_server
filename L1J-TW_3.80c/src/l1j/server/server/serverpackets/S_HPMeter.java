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
package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;
import l1j.server.server.model.L1Character;

public class S_HPMeter extends ServerBasePacket {
	private static final String _typeString = "[S] S_HPMeter";

	private byte[] _byte = null;

	public S_HPMeter(int objId, int hpRatio) {
		buildPacket(objId, hpRatio);
	}

	public S_HPMeter(L1Character cha) {
		int objId = cha.getId();
		int hpRatio = 100;
		if (0 < cha.getMaxHp()) {
			hpRatio = 100 * cha.getCurrentHp() / cha.getMaxHp();
		}
		buildPacket(objId, hpRatio);
	}
	// 怪物血條判斷功能  溝想來源99NETS网游模拟娱乐社区
	public S_HPMeter(L1Character cha, boolean MobHPBar) {
		int objId = cha.getId();
		int hpRatio = 100;
		if(MobHPBar){
			if (0 < cha.getMaxHp()) {
				hpRatio = 100 * cha.getCurrentHp() / cha.getMaxHp();
			}
			buildPacket(objId, hpRatio);
		} else {
			buildPacket(objId, 0xFF);
		}
	}
	// end
	private void buildPacket(int objId, int hpRatio) {
		writeC(Opcodes.S_OPCODE_HPMETER);
		writeD(objId);
		writeH(hpRatio);
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = _bao.toByteArray();
		}

		return _byte;
	}

	@Override
	public String getType() {
		return _typeString;
	}
}
