package edu.ithaca.dragon.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount <= 0){
            throw new NegativeDrawException("Can't have a negative amount");
        }
        else if(amount <= balance){
            balance -= amount;
        }
        else{
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1){
            return false;
        }
        else if(email.indexOf('.') == -1){
            return false;
        }

        String[] invalidNumChar = {"-","_"};
        for (int i = 0; i < invalidNumChar.length; i++){
            String symbol = invalidNumChar[i];
            long count = email.chars().filter(ch -> ch == symbol.charAt(0)).count();
            if (count > 1){
                return false;
            }
        }

        String[] session = email.split("@", 2);
        
        if(session[0].indexOf(".") == 0){
            return false;
        }
        else if(session[0].indexOf("-") == 0){
            return false;
        }
        else if(session[0].indexOf("_") == 0){
            return false;
        }
        else if (session[0].indexOf('.') == session[0].length()-1){
            return false;
        }
        else if (session[0].indexOf('-') == session[0].length()-1){
            return false;
        }
        else if (session[0].indexOf('_') == session[0].length()-1){
            return false;
        }
        
        if(session[1].chars().filter(en -> en == '.').count() > 1){
            return false;
        }
        
        String[] endPiece = session[1].split("\\.");
        if(endPiece[endPiece.length - 1].length() < 2){
            return false;
        }
        else{
            return true;
        }
    }

}
