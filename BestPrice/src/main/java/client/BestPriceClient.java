package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.ArrayList;
import interfaces.BestPriceInterface;
import support.*;

import javax.sound.midi.Soundbank;

public class BestPriceClient {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException{
        Scanner input = new Scanner(System.in);
        int option_1 = 1, option_2;
        String market = new String(), name, brand, measure, item;
        double price;
        ArrayList<Product> best_prices = new ArrayList<>();
        ArrayList<Market> consulted_markets = new ArrayList<>();

        try{
            BestPriceInterface stub = (BestPriceInterface) Naming.lookup("rmi://localhost/BestPriceServer");
            System.out.println("BEST PRICE! Your wallet best friend\n\n");
            System.out.println("Enter 1 if you want to use BEST PRICE market's functions. Enter 2 if you want to use BEST PRICE costumer's functions.");
            option_1 = Integer.parseInt(input.next().toString());
            System.out.println("\n\n___________________________________________\n\n");
            if(option_1 == 1){
                System.out.println("Choose your option:\n1: Register a new market\n2: Delete an existing market\n" +
                        "3: Register a product\n4: Delete a product from your market\n\n");
                option_2 = Integer.parseInt(input.next().toString());
                switch (option_2){
                    case 1:
                        System.out.println("\nEnter the market's name: ");
                        market = input.next().toString();
                        System.out.println("\n" + stub.createMarket(market) + "\n");
                        break;
                    case 2:
                        System.out.println("\nEnter the market's name: ");
                        market = input.next().toString();
                        System.out.println("\n" + stub.deleteMarket(market) + "\n");
                        break;
                    case 3:
                        System.out.println("\nEnter the product's name: ");
                        name = input.next().toString();
                        System.out.println("\nEnter the product's brand: ");
                        brand = input.next().toString();
                        System.out.println("\nEnter the product's measure: ");
                        measure = input.next().toString();
                        System.out.println("\nEnter the product's price: ");
                        price = Double.parseDouble(input.next().toString());
                        System.out.println("\n" + stub.registerProduct(name, brand, measure, price, stub.searchMarket(market)));
                        break;
                    case 4:
                        System.out.println("\nEnter the product's name:");
                        name = input.next().toString();
                        Product product = stub.searchProduct(name, market);
                        System.out.println("\n" + stub.deleteProduct(product, market));
                        break;
                    default:
                        System.out.println("Invalid option.");
                        break;
                }
            }
            else if(option_1 == 2){
                System.out.println("Choose your option:\n1: Add an item to your groceries list\n" +
                        "2: Delete an item from your groceries list\n" +
                        "3: Search the BEST PRICES to your groceries list's products\n" +
                        "4: List the consulted markets");
                option_2 = Integer.parseInt(input.next().toString());
                switch (option_2){
                    case 1:
                        System.out.println("\nEnter the item's name: ");
                        item = input.next().toString();
                        System.out.println("\n" + stub.addItem(item));
                        break;
                    case 2:
                        System.out.println("\nEnter the item's name: ");
                        item = input.next().toString();
                        System.out.println("\n" + stub.deleteItem(item));
                        break;
                    case 3:
                        System.out.println("\nSearching the BEST PRICES for you...");
                        best_prices = stub.bestPrices(stub.groceries());
                        for(int i = 0; i < best_prices.size(); i++){
                            System.out.println("\n" + best_prices.get(i).toString() + "\n");
                        }
                        break;
                    case 4:
                        System.out.println("\nThe consulted markets to make that list were:\n");
                        consulted_markets = stub.consultedMarkets();
                        for(int j = 0; j < consulted_markets.size(); j++){
                            System.out.println("\n" + consulted_markets.get(j).getName() + "\n");
                        }
                        break;
                    default:
                        System.out.println("Invalid option.");
                        break;
                }
            }
            else{
                System.out.println("Invalid option.");
            }
        } catch (RemoteException e){
            System.out.println("\nError while executing remote method." + e.getMessage());
            e.printStackTrace();
        } catch (NotBoundException e){
            System.out.println("\nFail at searching the server." + e.getMessage());
            e.printStackTrace();
        } catch (MalformedURLException e){
            System.out.println("\nWrong addres input." + e.getMessage());
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("\nError." + e.getMessage());
            e.printStackTrace();
        }
        input.close();
    }
}
