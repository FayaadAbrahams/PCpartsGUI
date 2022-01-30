package za.ac.cput.adp2.mymavendb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import za.ac.cput.adp2.mymavendb.connection.DBConnection;
import za.ac.cput.adp2.mymavendb.domain.Parts;

public class partDAO {

    private final Connection con;

    public partDAO() throws SQLException {
        this.con = DBConnection.connectDerby();
    }

    //====================================================================================================================
    //Method to add records to Database
    public Parts save(Parts parts) throws SQLException {
        String insertSQL = "INSERT INTO parts (part_id, part_name, part_price)" + "VALUES (?,?,?)";

        PreparedStatement ps = this.con.prepareStatement(insertSQL);
        ps.setString(1, parts.getPcPartID());
        ps.setString(2, parts.getPcPartName());
        ps.setString(3, parts.getPcPartPrice());

        int ok = ps.executeUpdate();
        if (ok > 0) {
            ps.close();
            return parts;
        } else {
            return null;
        }

    }

//====================================================================================================================
//Method to remove record from Database
    public void delete(String string) {
        String delSQL = "DELETE FROM parts WHERE part_id = ?";
        try {
            try ( PreparedStatement ps = this.con.prepareStatement(delSQL)) {
                ps.setString(1, string);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("Issue with the Delete method");
        }

    }

    //====================================================================================================================
//Method to update record from Database  
    public void update(String pcPartID, String pcPartPrice) throws SQLException {
        String updateQuery = "UPDATE parts SET part_price = ? WHERE part_id = ?";
        try ( PreparedStatement ps = this.con.prepareStatement(updateQuery)) {
            ps.setString(1, pcPartPrice);
            ps.setString(2, pcPartID);
            ps.executeUpdate();
        }
    }

    //====================================================================================================================
    //Method to pull all records from Database
    public ArrayList<Parts> getAll() {
        ArrayList<Parts> partsList = new ArrayList();
        String getAll_SQL = "SELECT * FROM parts";

        try ( PreparedStatement ps = this.con.prepareStatement(getAll_SQL);  ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                String pcPartID = rs.getString("part_id");
                String pcPartName = rs.getString("part_name");
                String pcPartPrice = rs.getString("part_price");
                System.out.println("DB Table Record: " + rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));

                partsList.add(new Parts(rs.getString("part_id"), rs.getString("part_name"), rs.getString("part_price")));
            }
            System.out.println("Table is Updated");
            rs.close();
            ps.close();
        } catch (Exception ex) {
            System.out.println("Error in the getAll() method: " + ex.toString());
        }
        return partsList;
    }

    //====================================================================================================================
    public void closeResources() throws SQLException {
        this.con.close();
    }

}
