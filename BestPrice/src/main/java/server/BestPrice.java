package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import support.*;
import interfaces.BestPriceInterface;

public class BestPrice extends UnicastRemoteObject implements BestPriceInterface {
    private final ArrayList<Market> markets = new ArrayList<>();
    private final ArrayList<Product> catalog = new ArrayList<>();
    private final ArrayList<String> groceries = new ArrayList<>();
    private final ArrayList<Market> consulted_markets = new ArrayList<>();

    protected BestPrice() throws RemoteException{}

    @Override
    public String createMarket(String name) throws RemoteException{
        if(searchMarket(name).equals(null)){
           markets.add(new Market(markets.size(), name));
            return "Market successfully registered.";
        }
        return "Market already registered.";
    }

    @Override
    public String deleteMarket(String name) throws RemoteException{
        Market temp = searchMarket(name);

        if(!temp.equals(null)){
            markets.remove(markets.indexOf(name));
            return "Market successfully deleted.";
        }
        return "Market " + name + " doesn't exists.";
    }

    @Override
    public Market searchMarket(String name) throws RemoteException{
        for(int i = 0; i < markets.size(); i++){
            if(markets.get(i).getName().equals(name)){
                return markets.get(i);
            }
        }
        return null;
    }

    @Override
    public String registerProduct(String name, String brand, String measure, double price, Market market) throws RemoteException{
        Market temp = searchMarket(market.getName());
        if(!temp.equals(null)) {
            if(searchProduct(name, market.getName()).equals(null)){
                catalog.add(new Product(catalog.size(), name, price, brand, measure, market));
                return "Item successfully registered in market " + market.getName();
            }
            return "Item already registered in market." + market.getName();
        }
        return "Market doesn't exists.";
    }

    @Override
    public String deleteProduct(Product product, String market_name) throws RemoteException{
        Market m_temp = searchMarket(market_name);
        if(!m_temp.equals(null)) {
            Product p_temp = searchProduct(product.getName(), market_name);
            if (!p_temp.equals(null)) {
                catalog.remove(p_temp);
                return "Product successfully deleted from market.";
            }
            return "Product doesn't exists in this market.";
        }
        return "Market doesn't exists.";
    }

    @Override
    public Product searchProduct(String name, String market_name) throws RemoteException{
        for(int i = 0; i < catalog.size(); i++){
            if((catalog.get(i).getName().equals(name)) && (catalog.get(i).getSeller().getName().equals(market_name))){
                return catalog.get(i);
            }
        }
        return null;
    }

    @Override
    public ArrayList<Product> searchProductInAllMarkets(String name) throws RemoteException{
        ArrayList<Product> temp = new ArrayList<>();
        for(int i = 0; i < catalog.size(); i++){
            if(catalog.get(i).getName().equals(name)){
                temp.add(catalog.get(i));
            }
        }
        if(temp.isEmpty()){
            return null;
        }
        return temp;
    }

    @Override
    public String addItem(String name) throws RemoteException{
        groceries.add(name);
        return "Item successfully added in grocery list.";
    }

    @Override
    public String deleteItem(String name) throws RemoteException{
        groceries.remove(groceries.indexOf(name));
        return "Item successfully deleted from grocery list.";
    }

    @Override
    public ArrayList<Product> bestPrices(ArrayList<String> customer_list) throws RemoteException{
        ArrayList<Product> search = new ArrayList<>();
        ArrayList<Product> best_prices = new ArrayList<>();
        for(int i = 0; i < customer_list.size(); i++){
            search = searchProductInAllMarkets(customer_list.get(i));
            best_prices.add(search.get(0));
            consulted_markets.add(search.get(0).getSeller());
            for(int j = 1; j < search.size(); j++){
                consulted_markets.add(search.get(j).getSeller());
                if(search.get(j).getPrice() < best_prices.get(best_prices.size()-1).getPrice()){
                    best_prices.remove(best_prices.size()-1);
                    best_prices.add(search.get(j));

                }
            }
        }
        return best_prices;
    }

    @Override
    public ArrayList<Market> consultedMarkets() throws RemoteException{
        return consulted_markets;
    }

    @Override
    public ArrayList<Market> markets() throws RemoteException{
        return markets;
    }

    @Override
    public ArrayList<Product> catalog() throws RemoteException{
        return catalog;
    }

    @Override
    public ArrayList<String> groceries() throws RemoteException{
        return groceries;
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        try{
            Registry r = LocateRegistry.createRegistry(1089);
            BestPrice best_price = new BestPrice();
            Naming.rebind("rmi://localhost/BestPriceServer", best_price);
            System.out.println("Server is ready and registered in the RMI registry.");
        } catch (RemoteException e){
            System.out.println("\nFailed to start the server. " + e.getMessage());
            e.printStackTrace();
        } catch (MalformedURLException e){
            System.out.println("\nWrong address input. " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("\nError. " + e.getMessage());
            e.printStackTrace();
        }
    }
}
