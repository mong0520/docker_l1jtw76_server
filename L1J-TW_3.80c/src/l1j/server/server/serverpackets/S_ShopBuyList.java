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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import l1j.server.Config;
import l1j.server.server.Opcodes;
import l1j.server.server.datatables.ShopTable;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.shop.L1AssessedItem;
import l1j.server.server.model.shop.L1Shop;
import l1j.william.L1WilliamItemPrice;

// Referenced classes of package l1j.server.server.serverpackets:
// ServerBasePacket, S_SystemMessage

public class S_ShopBuyList extends ServerBasePacket {

	private static final String S_SHOP_BUY_LIST = "[S] S_ShopBuyList";

	public S_ShopBuyList(int objid, L1PcInstance pc) {
		L1Object object = L1World.getInstance().findObject(objid);
		if (!(object instanceof L1NpcInstance)) {
			return;
		}
		L1NpcInstance npc = (L1NpcInstance) object;
		int npcId = npc.getNpcTemplate().get_npcId();
		// 全道具販賣 
		if (Config.ALL_ITEM_SELL) {
			int tax_rate = L1CastleLocation.getCastleTaxRateByNpcId(npcId);

			ArrayList<L1ItemInstance> sellItems = new ArrayList<L1ItemInstance>();  
			for(Iterator<L1ItemInstance> iterator = pc.getInventory().getItems().iterator(); iterator.hasNext();) {
				Object iObject = iterator.next();
				L1ItemInstance itm = (L1ItemInstance)iObject;
				if(itm != null && !itm.isEquipped() && itm.getItemId() != 40308 && L1WilliamItemPrice.getItemId(itm.getItem().getItemId()) != 0) {
					sellItems.add(itm);
				}
			}

			int sell = sellItems.size();
			if (sell > 0) {
				writeC(Opcodes.S_OPCODE_SHOWSHOPSELLLIST);
				writeD(objid);
				writeH(sell);
				for (Object itemObj : sellItems) {
					L1ItemInstance item = (L1ItemInstance) itemObj;
					int getPrice = L1WilliamItemPrice.getItemId(item.getItem().getItemId());
					int price = 0;
					if (getPrice > 0) {
						price = getPrice;
					} else {
						price = 0;
					}
					if (tax_rate != 0) {
						double tax = (100 + tax_rate) / 100.0;
						price = (int)(price * tax);
					}
					writeD(item.getId());
					writeD(price / 2);
				}
			} else {
				pc.sendPackets(new S_NoSell(npc));
			}
		} else {
			L1Shop shop = ShopTable.getInstance().get(npcId);
			if (shop == null) {
				pc.sendPackets(new S_NoSell(npc));
				return;
			}

			List<L1AssessedItem> assessedItems = shop
					.assessItems(pc.getInventory());
			if (assessedItems.isEmpty()) {
				pc.sendPackets(new S_NoSell(npc));
				return;
			}

			writeC(Opcodes.S_OPCODE_SHOWSHOPSELLLIST);
			writeD(objid);
			writeH(assessedItems.size());

			for (L1AssessedItem item : assessedItems) {
				writeD(item.getTargetId());
				writeD(item.getAssessedPrice());
			}
		}
		// 全道具販賣  end
		writeH(0x0007); // 7 = 金幣為單位 顯示總金額
	}

	@Override
	public byte[] getContent() {
		return getBytes();
	}

	@Override
	public String getType() {
		return S_SHOP_BUY_LIST;
	}
}