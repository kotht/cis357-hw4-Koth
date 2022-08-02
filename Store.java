import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class is meant to represent a store
 */
public class Store {
    /** Member Variables */
    private String name = "The Koth Register System";
    private ProductCatalog catalog;
    private Register register;
    private String filepath = "itemsTable.csv";
    Scanner sc = new Scanner(System.in);

    /**
     * Default Constructor - initializing/loading in our product catalog before the register is ready for sales
     */
    public Store(){
        catalog = new ProductCatalog(filepath);
    }

    /**
     * When invoked this method prompts the user with a welcome message to the register system. The register then is
     * initiated with the catalog that has previously been documented. It then will continue to call the initSale method
     * in the register class until the sales are completed for the day. Then it will invoke the administratorOptions.
     */
    public void initiateRegisters(){
        System.out.println("Welcome to " + name);
        register = new Register(catalog);
        while(!register.getSaleCompleted()){
            register.initSale();
        }
        administratorOptions(sc);
    }

    /**
     * This method contains a switch statement that allows the user to add an item, delete an item, modify an item,
     * or quit the program. For each option, there will be either a method invocation to process the argument, or the
     * program will exit on code Q.
     * @param sc Scanner Object
     */
    public void administratorOptions(Scanner sc){
        char code= '\u0000';
        while(code != 'Q'){
            System.out.print("\nDo you want to update the items data? (A/D/M/Q): ");
            code = Character.toUpperCase(sc.next().charAt(0)); // obtain the code - char index 0

            switch (code) {
                case 'A':
                    addItemData(sc);
                    break;
                case 'D':
                    deleteItemData(sc);
                    break;
                case 'M':
                    modifyItemData(sc);
                    break;
            }
        }

        // When Q is selected -- Quit
        catalog.printProdTable(); // print product table
        System.out.println("Thanks for using Koth cash register system. Goodbye. ");
        catalog.writeFile(filepath); // write to file
        sc.close(); // Close scanner obj
        System.exit(0);
    }

    /**
     * This method will addItemData to the catalog. The method will invoke prompt methods to collect all the information
     * required for this process. The method then checks to see if the itemCode already exists. If item exists the
     * method will prompt the user the error that has occurred.
     * @param sc Scanner Object
     */
    public void addItemData(Scanner sc){
        String itemCode = promptItemCode(sc);
        String itemName = promptItemName(sc);
        float unitPrice = promptUnitPrice(sc);

        // Check if item already exists - negative logic (!)
        if(!errCheckItemCode(itemCode)){
            System.out.println("!!! Item code not unique");
        }
        else{
            // If item does not exist
            catalog.addItem(new ProductSpecification(itemCode, unitPrice, itemName));
            System.out.println("Item add successful!");
        }
    }

    /**
     * This method will deleteItemData in the catalog if all conditions are met. The method will invoke a method to
     * collect the itemCode, then will check if the itemCode exists. If true, the item will be deleted, else it will
     * prompt the user that the item does not exist.
     * @param sc Scanner Object
     */
    public void deleteItemData(Scanner sc){
        String itemCode = promptItemCode(sc);

        // Check if item exists and prompt user with corresponding output
        if(errCheckItemCode(itemCode)) {
            catalog.removeItem(itemCode);
            System.out.println("Item delete successful!");
        }
    }

    /**
     * This method will modifyItemData in the catalog if all conditions are met. The method will invoke the proper
     * methods to collect the information needed to satisfy the conditions. Once the information is collected we check
     * if the itemCode exists, and process accordingly to this condition.
     * @param sc Scanner Object
     */
    public void modifyItemData(Scanner sc){
        String itemCode = promptItemCode(sc);
        String itemName = promptItemName(sc);
        float unitPrice = promptUnitPrice(sc);

        // Check if item exists and prompt user with corresponding output
        if(errCheckItemCode(itemCode)){
            catalog.modifyItem(itemCode, new ProductSpecification(itemCode, unitPrice, itemName));
            System.out.println("Item modify successful!");
        }
    }

    /**
     * This method will prompt the user for an itemCode. This item code will then be returned to the caller.
     * @param sc Scanner Object
     * @return String - Represents ItemCode
     */
    public String promptItemCode(Scanner sc){
        System.out.print("item code:  ");
        return sc.next().trim();
    }

    /**
     * This method will prompt the user for an itemName. The item name will then be returned as a String to the caller.
     * @param sc Scanner Object
     * @return String - Represents ItemName
     */
    public String promptItemName(Scanner sc){
        System.out.print("item name:  ");
        return sc.next().trim();
    }

    /**
     * This method will prompt the user for the itemPrice. This method implements a try-catch-finally block for error
     * checking as the price must be a number.
     * @param sc Scanner Object
     * @return float - represents UnitPrice
     */
    public float promptUnitPrice(Scanner sc){
        float unitPrice = 0;
        System.out.print("item price:  ");
        try{
            unitPrice = sc.nextFloat();
            if(unitPrice <= 0){
                System.out.println("!!! Invalid price");
            }
        } catch (InputMismatchException e){
            // Must be a number
            System.out.println("!!! Invalid data type");
        }
        finally {
            return unitPrice;
        }
    }

    /**
     * This method implements the use of a try-catch-finally block for error checking purposes if the product can, or
     * cannot be found in the catalog. This method will return a boolean value that corresponds to the logic presented.
     * @param itemCode - String
     * @return boolean - product exists/or does not exist
     */
    public boolean errCheckItemCode(String itemCode){
        boolean valid = false;
        try{
            if(catalog.prodExists(itemCode))
                valid = true;

        } catch (NullPointerException e){
            System.out.println("!!! Item not found");
        }
        finally {
            return valid;
        }
    }

}
