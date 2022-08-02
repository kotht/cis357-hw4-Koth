// Programming Assignment: Cash Register System 4
// Course: CIS357
// Due date: August, 2 2022
// Name: Tanner J Koth
// GitHub:
// Instructor: Il-Hyung Cho
/* Program description: This program emulates a POS (Point-of-Sale) system at a store. The POS system allows the user
   to purchase items from our store. The program will input a .csv file and read that file to the catalog of the store.
   This catalog is then passed to the register allowing it to look at the catalog that contains the products
   specifications. The register captures the sale which contains each salesLineItem and payment. When the sales are all
   completed the store will allow options to add, delete, and modify items in the catalog.
*/

/*
Program features:
Implement OOP Features: Y
Change the item code type to String: Y

Implement exception handling for
    File input: Y
    Checking wrong input data type: Y
    Checking invalid data value: Y
    Tendered amount less than the total amount: P
Use Hashmap for the ProductCatalog data: Y
Adding item data: Y
Deleting item data: Y
Modifying item data: Y
*/


/**
 * Driver class
 * @author Tanner J Koth
 */
public class HW4Koth {
    /**
     * This is the main method for the POS application. This method simply creates an instance of store to read in the
     * product catalog information, then we initialize the register allowing it to process sales. This method only
     * contains code for input, but not any processing for the sales itself.
     * @param args
     */
    public static void main(String[] args){
        // Initialize store
        Store store = new Store(); // open store
        store.initiateRegisters(); // initiate registers
    }
}
