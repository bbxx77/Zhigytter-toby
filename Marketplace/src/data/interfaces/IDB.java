package data.interfaces;

import entities.Buyer;
import entities.Product;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDB {
    Connection getConnection() throws SQLException, ClassNotFoundException;
    Product getProductById(int id);
    Buyer getBuyerById(int id);
}