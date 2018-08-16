package l1j.william;


import java.util.ArrayList;
import java.util.StringTokenizer;
import java.sql.*;

import l1j.server.L1DatabaseFactory;
import l1j.server.Server;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;



public class _Level {

	private static ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
	private static boolean GET_ITEM = false;
	public static final String TOKEN = ",";
	
	
	public static void main(String a[]) {
		while(true) {
			try {
			Server.main(null);
			} catch(Exception ex) {
			}
		}
	}
	
	private _Level() {
	}
	
	public static void getItem(L1PcInstance pc) {
		ArrayList<?> data = null;
		if(!GET_ITEM) {
			GET_ITEM = true;
			getItemData();
		}

		for (int i=0; i <array.size(); i++) {
			data = (ArrayList<?>) array.get(i);			

			if (pc.getLevel() >= ((Integer) data.get(0)).intValue() && 
					(int[])data.get(8) != null && 
					(int[])data.get(9) != null && 
					(int[])data.get(10) != null && 
					pc.getQuest().get_step(((Integer) data.get(11)).intValue()) != ((Integer) data.get(12)).intValue()) { // 等級符合

				if (((Integer) data.get(1)).intValue() != 0 && pc.isCrown()) {//王族
					boolean isGet = false;
					int[] materials = (int[]) data.get(8);
					int[] counts = (int[]) data.get(9);
					int[] enchantLevel = (int[]) data.get(10);

					for (int j = 0; j < materials.length; j++) {
						L1ItemInstance item = ItemTable.getInstance().createItem(materials[j]);

						if (item.isStackable()) {//可重疊
							item.setCount(counts[j]);//數量
						} else {
							item.setCount(1);
						}

						if (item.getItem().getType2() == 1 || // 武器類
								item.getItem().getType2() == 2) { // 防具類
							item.setEnchantLevel(enchantLevel[j]);// 強化數
						} else {
							item.setEnchantLevel(0);
						}

						if (item != null) {
							if ((String) data.get(13) != null && isGet == false) {
								pc.sendPackets(new S_SystemMessage((String) data.get(13)));//訊息
								isGet = true;
							}

							if (pc.getInventory().checkAddItem(item, (counts[j])) == L1Inventory.OK) {
								pc.getInventory().storeItem(item);
							} else { // 持てない場合は地面に落とす 處理のキャンセルはしない（不正防止）
								L1World.getInstance().getInventory(pc.getX(), pc.getY(),
										pc.getMapId()).storeItem(item);
							}

							pc.sendPackets(new S_ServerMessage(403, item.getLogName()));

							//紀錄
							pc.getQuest().set_step(((Integer) data.get(11)).intValue(), ((Integer) data.get(12)).intValue());
						}
					}
				}

				if (((Integer) data.get(2)).intValue() != 0 && pc.isKnight()) {//騎士
					boolean isGet = false;
					int[] materials = (int[]) data.get(8);
					int[] counts = (int[]) data.get(9);
					int[] enchantLevel = (int[]) data.get(10);

					for (int j = 0; j < materials.length; j++) {
						L1ItemInstance item = ItemTable.getInstance().createItem(materials[j]);

						if (item.isStackable()) {//可重疊
							item.setCount(counts[j]);//數量
						} else {
							item.setCount(1);
						}

						if (item.getItem().getType2() == 1 || // 武器類
								item.getItem().getType2() == 2) { // 防具類
							item.setEnchantLevel(enchantLevel[j]);// 強化數
						} else {
							item.setEnchantLevel(0);
						}

						if (item != null) {
							if ((String) data.get(13) != null && isGet == false) {
								pc.sendPackets(new S_SystemMessage((String) data.get(13)));//訊息
								isGet = true;
							}

							if (pc.getInventory().checkAddItem(item, (counts[j])) == L1Inventory.OK) {
								pc.getInventory().storeItem(item);
							} else { // 持てない場合は地面に落とす 處理のキャンセルはしない（不正防止）
								L1World.getInstance().getInventory(pc.getX(), pc.getY(),
										pc.getMapId()).storeItem(item);
							}

							pc.sendPackets(new S_ServerMessage(403, item.getLogName()));

							//紀錄
							pc.getQuest().set_step(((Integer) data.get(11)).intValue(), ((Integer) data.get(12)).intValue());
						}
					}
				}

				if (((Integer) data.get(3)).intValue() != 0 && pc.isWizard()) {//法師
					boolean isGet = false;
					int[] materials = (int[]) data.get(8);
					int[] counts = (int[]) data.get(9);
					int[] enchantLevel = (int[]) data.get(10);

					for (int j = 0; j < materials.length; j++) {
						L1ItemInstance item = ItemTable.getInstance().createItem(materials[j]);

						if (item.isStackable()) {//可重疊
							item.setCount(counts[j]);//數量
						} else {
							item.setCount(1);
						}

						if (item.getItem().getType2() == 1 || // 武器類
								item.getItem().getType2() == 2) { // 防具類
							item.setEnchantLevel(enchantLevel[j]);// 強化數
						} else {
							item.setEnchantLevel(0);
						}

						if (item != null) {
							if ((String) data.get(13) != null && isGet == false) {
								pc.sendPackets(new S_SystemMessage((String) data.get(13)));//訊息
								isGet = true;
							}

							if (pc.getInventory().checkAddItem(item, (counts[j])) == L1Inventory.OK) {
								pc.getInventory().storeItem(item);
							} else { // 持てない場合は地面に落とす 處理のキャンセルはしない（不正防止）
								L1World.getInstance().getInventory(pc.getX(), pc.getY(),
										pc.getMapId()).storeItem(item);
							}

							pc.sendPackets(new S_ServerMessage(403, item.getLogName()));

							//紀錄
							pc.getQuest().set_step(((Integer) data.get(11)).intValue(), ((Integer) data.get(12)).intValue());
						}
					}
				}

				if (((Integer) data.get(4)).intValue() != 0 && pc.isElf()) {//妖精
					boolean isGet = false;
					int[] materials = (int[]) data.get(8);
					int[] counts = (int[]) data.get(9);
					int[] enchantLevel = (int[]) data.get(10);

					for (int j = 0; j < materials.length; j++) {
						L1ItemInstance item = ItemTable.getInstance().createItem(materials[j]);

						if (item.isStackable()) {//可重疊
							item.setCount(counts[j]);//數量
						} else {
							item.setCount(1);
						}

						if (item.getItem().getType2() == 1 || // 武器類
								item.getItem().getType2() == 2) { // 防具類
							item.setEnchantLevel(enchantLevel[j]);// 強化數
						} else {
							item.setEnchantLevel(0);
						}

						if (item != null) {
							if ((String) data.get(13) != null && isGet == false) {
								pc.sendPackets(new S_SystemMessage((String) data.get(13)));//訊息
								isGet = true;
							}

							if (pc.getInventory().checkAddItem(item, (counts[j])) == L1Inventory.OK) {
								pc.getInventory().storeItem(item);
							} else { // 持てない場合は地面に落とす 處理のキャンセルはしない（不正防止）
								L1World.getInstance().getInventory(pc.getX(), pc.getY(),
										pc.getMapId()).storeItem(item);
							}

							pc.sendPackets(new S_ServerMessage(403, item.getLogName()));

							//紀錄
							pc.getQuest().set_step(((Integer) data.get(11)).intValue(), ((Integer) data.get(12)).intValue());
						}
					}
				}
				
				if (((Integer) data.get(5)).intValue() != 0 && pc.isDarkelf()) {//黑妖
					boolean isGet = false;
					int[] materials = (int[]) data.get(8);
					int[] counts = (int[]) data.get(9);
					int[] enchantLevel = (int[]) data.get(10);

					for (int j = 0; j < materials.length; j++) {
						L1ItemInstance item = ItemTable.getInstance().createItem(materials[j]);

						if (item.isStackable()) {//可重疊
							item.setCount(counts[j]);//數量
						} else {
							item.setCount(1);
						}

						if (item.getItem().getType2() == 1 || // 武器類
								item.getItem().getType2() == 2) { // 防具類
							item.setEnchantLevel(enchantLevel[j]);// 強化數
						} else {
							item.setEnchantLevel(0);
						}

						if (item != null) {
							if ((String) data.get(13) != null && isGet == false) {
								pc.sendPackets(new S_SystemMessage((String) data.get(13)));//訊息
								isGet = true;
							}

							if (pc.getInventory().checkAddItem(item, (counts[j])) == L1Inventory.OK) {
								pc.getInventory().storeItem(item);
							} else { // 持てない場合は地面に落とす 處理のキャンセルはしない（不正防止）
								L1World.getInstance().getInventory(pc.getX(), pc.getY(),
										pc.getMapId()).storeItem(item);
							}

							pc.sendPackets(new S_ServerMessage(403, item.getLogName()));

							//紀錄
							pc.getQuest().set_step(((Integer) data.get(11)).intValue(), ((Integer) data.get(12)).intValue());
						}
					}
				}
				
				if (((Integer) data.get(6)).intValue() != 0 && pc.isDragonKnight()) {//龍騎士
					boolean isGet = false;
					int[] materials = (int[]) data.get(8);
					int[] counts = (int[]) data.get(9);
					int[] enchantLevel = (int[]) data.get(10);

					for (int j = 0; j < materials.length; j++) {
						L1ItemInstance item = ItemTable.getInstance().createItem(materials[j]);

						if (item.isStackable()) {//可重疊
							item.setCount(counts[j]);//數量
						} else {
							item.setCount(1);
						}

						if (item.getItem().getType2() == 1 || // 武器類
								item.getItem().getType2() == 2) { // 防具類
							item.setEnchantLevel(enchantLevel[j]);// 強化數
						} else {
							item.setEnchantLevel(0);
						}

						if (item != null) {
							if ((String) data.get(13) != null && isGet == false) {
								pc.sendPackets(new S_SystemMessage((String) data.get(13)));//訊息
								isGet = true;
							}

							if (pc.getInventory().checkAddItem(item, (counts[j])) == L1Inventory.OK) {
								pc.getInventory().storeItem(item);
							} else { // 持てない場合は地面に落とす 處理のキャンセルはしない（不正防止）
								L1World.getInstance().getInventory(pc.getX(), pc.getY(),
										pc.getMapId()).storeItem(item);
							}

							pc.sendPackets(new S_ServerMessage(403, item.getLogName()));

							//紀錄
							pc.getQuest().set_step(((Integer) data.get(11)).intValue(), ((Integer) data.get(12)).intValue());
						}
					}
				}
				
				if (((Integer) data.get(7)).intValue() != 0 && pc.isIllusionist()) {//幻術師
					boolean isGet = false;
					int[] materials = (int[]) data.get(8);
					int[] counts = (int[]) data.get(9);
					int[] enchantLevel = (int[]) data.get(10);

					for (int j = 0; j < materials.length; j++) {
						L1ItemInstance item = ItemTable.getInstance().createItem(materials[j]);

						if (item.isStackable()) {//可重疊
							item.setCount(counts[j]);//數量
						} else {
							item.setCount(1);
						}

						if (item.getItem().getType2() == 1 || // 武器類
								item.getItem().getType2() == 2) { // 防具類
							item.setEnchantLevel(enchantLevel[j]);// 強化數
						} else {
							item.setEnchantLevel(0);
						}

						if (item != null) {
							if ((String) data.get(13) != null && isGet == false) {
								pc.sendPackets(new S_SystemMessage((String) data.get(13)));//訊息
								isGet = true;
							}

							if (pc.getInventory().checkAddItem(item, (counts[j])) == L1Inventory.OK) {
								pc.getInventory().storeItem(item);
							} else { // 持てない場合は地面に落とす 處理のキャンセルはしない（不正防止）
								L1World.getInstance().getInventory(pc.getX(), pc.getY(),
										pc.getMapId()).storeItem(item);
							}

							pc.sendPackets(new S_ServerMessage(403, item.getLogName()));

							//紀錄
							pc.getQuest().set_step(((Integer) data.get(11)).intValue(), ((Integer) data.get(12)).intValue());
						}
					}
				}

			}
		}
	}

	private static void getItemData() {
        java.sql.Connection con = null;
        try {
          con = L1DatabaseFactory.getInstance().getConnection(); 
	      Statement stat = con.createStatement();
	      ResultSet rset = stat.executeQuery("SELECT * FROM william_Level");
	      ArrayList<Object> arraylist = null;
	      //String sTemp = null;
          if ( rset!=null)
            while (rset.next()) {
            	arraylist = new ArrayList<Object>();
            	arraylist.add(0, new Integer(rset.getInt("level")));
            	arraylist.add(1, new Integer(rset.getInt("give_royal")));//王族
            	arraylist.add(2, new Integer(rset.getInt("give_knight")));//騎士
            	arraylist.add(3, new Integer(rset.getInt("give_mage")));//法師
            	arraylist.add(4, new Integer(rset.getInt("give_elf")));//妖精
            	arraylist.add(5, new Integer(rset.getInt("give_darkelf")));//黑妖
            	arraylist.add(6, new Integer(rset.getInt("give_dragonknight")));//龍騎士
            	arraylist.add(7, new Integer(rset.getInt("give_illusionist")));//幻術師
            	arraylist.add(8, getArray(rset.getString("getItem"), TOKEN, 1));//獎勵道具
            	arraylist.add(9, getArray(rset.getString("count"), TOKEN, 1));//獎勵道具(數量)
            	arraylist.add(10, getArray(rset.getString("enchantlvl"), TOKEN, 1));//獎勵道具(強化值、次數)
            	arraylist.add(11, new Integer(rset.getInt("quest_id")));//紀錄
            	arraylist.add(12, new Integer(rset.getInt("quest_step")));//紀錄
            	arraylist.add(13, rset.getString("message"));//訊息
            	array.add(arraylist);
            }
          if (con!=null && !con.isClosed())
        	  con.close();
        }
        catch (Exception ex) {	
        }
	}
    
    private static Object getArray(String s, String sToken, int iType) {
     StringTokenizer st = new StringTokenizer(s, sToken);
     int iSize = st.countTokens();
     String  sTemp = null;
     if (iType == 1) { // int
     	int[] iReturn = new int[iSize];
     	for(int i = 0; i < iSize; i++) {
       	  sTemp = st.nextToken();
     	  iReturn[i] = Integer.parseInt(sTemp);
     	}
     	return iReturn;
      }
     if(iType == 2) { // String
      	String[] sReturn = new String[iSize];
      	for(int i = 0; i < iSize; i++) {
       	  sTemp = st.nextToken();
      	  sReturn[i] = sTemp;
      	}
      	return sReturn;
       }
     if(iType == 3) { // String
      	String sReturn = null;
      	for(int i=0; i < iSize; i++) {
       	  sTemp = st.nextToken();
      	  sReturn = sTemp;
      	}
      	return sReturn;
       }
     return null;
   }
}
