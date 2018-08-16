package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1PcInstance;

public class S_EquipmentWindow extends ServerBasePacket {
	private static final String S_EQUIPMENTWINDOWS = "[S] S_EquipmentWindow";

	private byte[] _byte = null;

	/** 頭盔 */
	public static final byte EQUIPMENT_INDEX_HEML = 1;
	/** 盔甲 */
	public static final byte EQUIPMENT_INDEX_ARMOR = 2;
	/** T恤 */
	public static final byte EQUIPMENT_INDEX_T = 3;
	/** 斗篷 */
	public static final byte EQUIPMENT_INDEX_CLOAK = 4;
	/** 靴子 */
	public static final byte EQUIPMENT_INDEX_BOOTS = 5;
	/** 手套 */
	public static final byte EQUIPMENT_INDEX_GLOVE = 6;
	/** 盾 */
	public static final byte EQUIPMENT_INDEX_SHIELD = 7;
	/** 武器 */
	public static final byte EQUIPMENT_INDEX_WEAPON = 8;
	/** 項鏈 */
	public static final byte EQUIPMENT_INDEX_NECKLACE = 10;
	/** 腰帶 */
	public static final byte EQUIPMENT_INDEX_BELT = 11;
	/** 耳環 */
	public static final byte EQUIPMENT_INDEX_EARRING = 12;
	/** 戒指1 */
	public static final byte EQUIPMENT_INDEX_RING1 = 18;
	/** 戒指2 */
	public static final byte EQUIPMENT_INDEX_RING2 = 19;
	/** 戒指3 */
	public static final byte EQUIPMENT_INDEX_RING3 = 20;
	/** 戒指4 */
	public static final byte EQUIPMENT_INDEX_RING4 = 21;
	/** 符紋 */
	public static final byte EQUIPMENT_INDEX_RUNE1 = 22;

	public static final byte EQUIPMENT_INDEX_RUNE2 = 23;

	public static final byte EQUIPMENT_INDEX_RUNE3 = 24;

	public static final byte EQUIPMENT_INDEX_RUNE4 = 25;

	public static final byte EQUIPMENT_INDEX_RUNE5 = 26;

	/**
	 * 顯示指定物品到裝備窗口
	 * @param itemObjId 對象ID
	 * @param index 序號
	 * @param isEq 0:脫下 1:使用
	 * @param 0x00 (對應isEq)0:脫下
	 * @param 0x01 (對應isEq)1:使用 
	 */
	public S_EquipmentWindow(L1PcInstance pc, int itemObjId, int index, boolean isEq) {
		writeC(Opcodes.S_OPCODE_CHARRESET);
		writeC(0x42);
		writeD(itemObjId);
		writeC(index);
		if(isEq) {
			writeC(0x01);//TODO 1:使用(0x01=1)
		} else {
			writeC(0x00);//TODO 0:脫下(0x00=0)
		}
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	@Override
	public String getType() {
		return S_EQUIPMENTWINDOWS;
	}
}
