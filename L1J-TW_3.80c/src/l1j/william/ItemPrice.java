package l1j.william;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.utils.SQLUtil;
import l1j.william.L1WilliamItemPrice;
public class ItemPrice {
	private static Logger _log = Logger.getLogger(ItemPrice.class
			.getName());

	private static ItemPrice _instance;

	private final HashMap<Integer, L1WilliamItemPrice> _itemIdIndex
			= new HashMap<Integer, L1WilliamItemPrice>();

	public static ItemPrice getInstance() {
		if (_instance == null) {
			_instance = new ItemPrice();
		}
		return _instance;
	}

	private ItemPrice() {
		loadItemPrice();
	}

	private void loadItemPrice() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {

			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM william_item_price");
			rs = pstm.executeQuery();
			fillItemPrice(rs);
		} catch (SQLException e) {
			_log.log(Level.SEVERE, "error while creating william_item_price table",
					e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private void fillItemPrice(ResultSet rs) throws SQLException {
		while (rs.next()) {
			int itemId = rs.getInt("item_id");
			int price = rs.getInt("price");
			
			L1WilliamItemPrice item_price = new L1WilliamItemPrice(itemId, price);
			_itemIdIndex.put(itemId, item_price);
		}
	}

	public L1WilliamItemPrice getTemplate(int itemId) {
		return _itemIdIndex.get(itemId);
	}
}
