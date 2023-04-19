package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import support.*;

public interface BestPriceInterface extends Remote {
    public String createMarket(String name) throws RemoteException;
    public String deleteMarket(String name) throws RemoteException;
    public Market searchMarket(String name) throws RemoteException;
    public String registerProduct(String name, String brand, String measure, double price, Market market) throws RemoteException;
    public String deleteProduct(Product product, String market_name) throws RemoteException;
    public Product searchProduct(String name, String market_name) throws RemoteException;
    public ArrayList<Product> searchProductInAllMarkets(String name) throws RemoteException;

    public String addItem(String name) throws RemoteException;
    public String deleteItem(String name) throws RemoteException;

    public ArrayList<Product> bestPrices(ArrayList<String> customer_list) throws RemoteException;
    public ArrayList<Market> consultedMarkets() throws RemoteException;
    public ArrayList<Market> markets() throws RemoteException;
    public ArrayList<Product> catalog() throws RemoteException;
    public ArrayList<String> groceries() throws RemoteException;
}
