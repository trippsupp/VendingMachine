/**
 * Processes transactions and handles inventory
 * 
 * @author Vance Field
 * @version 18-Feb-2016
 */
public class Machine {
    
    // instance variables //   
    
    // remaining products 
    public int remCoke;
    public int remDiet;
    public int remSprite;
    public int remDr;
    public int remWater;
    
    // amount of money stored in the machine
    public double amountInMachine = 0.0;
    
    /**
     * Constructor
     */
    public Machine(){
        remCoke   = 10;
        remDiet   = 10;
        remSprite = 10;
        remDr     = 10;
        remWater  = 10;
    }     
    
    /**
     * Returns whether or not the selected product is in stock
     * 
     * @param productNum the product
     * @return whether the product is in stock
     */
    public boolean inStock(int productNum) {
        boolean inStock = false;
        
        // Coke
        if(productNum == 0) {
            if(remCoke > 0){
                inStock = true;
            }
        }
        
        // Diet coke
        if(productNum == 1) {
            if(remDiet > 0) {
                inStock = true;
            }
        }
        
        // Sprite        
        if(productNum == 2) {
            if(remSprite > 0) {
                inStock = true;
            }
        }
        
        // Dr Pepper
        if(productNum == 3) {
            if(remDr > 0) {
                inStock = true;
            }
        }
        
        // Water
        if(productNum == 4) {
            if(remWater > 0) {
                inStock = true;    
            }
        }
        
        return inStock;
    }
    
    /**
     * Returns the appropriate change
     * 
     * @param productNum the product
     * @param amountEntered the amount entered
     * @return appropriate change
     */
    public double getChange(int productNum, double amountEntered) {
        double change = 0.0;
        // the amount of money required to purchase the product
        double amountReq = 0.0;
        
        // productNum is the unique identifier for each product
        if(productNum == 0)    // coke
            amountReq = 1.50;
        if(productNum == 1)    // diet coke
            amountReq = 1.75;
        if (productNum == 2)   // sprite
            amountReq = 1.50;
        if(productNum == 3)    // dr pepper
            amountReq = 1.50;
        if(productNum == 4)    // water
            amountReq = 1.00;
        
        // debug
        // System.out.println("Amount required is: " + amountReq);
        // System.out.println("Amount entered is: " + amountEntered);
        
        
        // user must also enter more than or equal to the amount required
        if(amountEntered < amountReq){
            change = amountEntered;
            //System.out.println("Amount entered is not acceptable");
        }
        
        // user cannot enter more than $5 or less than $0
        else if(amountEntered > 5.0 || amountEntered < 0) {
            change = amountEntered;
            //System.out.println("Amount entered is not acceptable");
        }
        
        // runs if the amount entered is a valid entry
        else { 
            //System.out.println("Amount entered is acceptable");
            
            // calculates change
            change = amountEntered - amountReq;    
            
            // adds money to the machine 
            amountInMachine += amountReq;
            
            if(productNum == 0)
                remCoke--;      // one coke taken from inventory            
            if(productNum == 1)
                remDiet--;      // one diet coke taken 
            if(productNum == 2)
                remSprite--;    // one sprite taken
            if(productNum == 3)
                remDr--;        // one dr pepper taken
            if(productNum == 4)
                remWater--;     // one water taken
        }        
        return change;
    }
    
    /**
     * Restocks products
     * 
     * @param productNum UID of each product
     * @param amount the amount of products to restock
     */
    public void restock(int productNum, int amount){
        if(productNum == 0){
            if(remCoke + amount <= 10)
                remCoke += amount;      // adds coke to inventory
            else
                remCoke = 10;           // maxes out coke inventory (10)
        }
        if(productNum == 1){
            if(remDiet + amount <= 10)
                remDiet += amount;      // adds diet coke 
            else
                remCoke = 10;
        }
        if(productNum == 2){
            if(remSprite + amount <= 10)
                remSprite += amount;    // adds sprite 
            else
                remCoke = 10;
        }
        if(productNum == 3){
            if(remDr + amount <= 10)
                remDr += amount;        // adds dr pepper 
            else
                remCoke = 10;
        }
        if(productNum == 4){
            if(remWater + amount <= 10)
                remWater += amount;     // adds water 
            else
                remCoke = 10;
        }        
    }
    
    /**
     * Withdraws money from the machine
     * 
     * @param amount the amount to withdraw
     */
    public void withdraw(double amount){
        if(amountInMachine - amount < 0)
            amountInMachine = 0.0;
        else
            amountInMachine = amountInMachine - amount;
       // System.out.println("You have successfully withdrawn " + amount);
       // System.out.println("You have " + amountInMachine + " left in machine"); 
    }
    
    /**
     * Returns the remaining coke count
     * 
     * @return coke count
     */
    public int remainingCoke() {
        return remCoke;
    }
    
    /**
     * Returns the remaining diet coke count
     * 
     * @return diet coke count
     */
    public int remainingDiet() {
        return remDiet;
    }
    
    /**
     * Returns the remaining sprite count
     * 
     * @return sprite count
     */
    public int remainingSprite() {
        return remSprite;
    }
    
    /**
     * Returns the remaining dr pepper count
     * 
     * @return dr pepper count
     */
    public int remainingDr() {
        return remDr;
    }
    
    /**
     * Returns the remaining water count
     * 
     * @return water count
     */
    public int remainingWater() {
        return remWater;
    }
    
    /**
     * Returns the amount of money in the machine
     * 
     * @return amount in machine 
     */
    public double getAmountInMachine(){
        return amountInMachine;
    }    
} // machine
