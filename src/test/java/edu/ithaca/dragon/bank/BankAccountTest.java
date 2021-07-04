package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void isAmountValidTest(){

        assertTrue(BankAccount.isAmountValid(200));
        //Equivalence case, any number larger than or equal to zero is okay
        assertFalse(BankAccount.isAmountValid(-200));
        //Equivalence case, any number smaller than zero is false
        assertTrue(BankAccount.isAmountValid(0.1));
        //Equivalence case, less than or equal to two decimal places is okay
        assertTrue(BankAccount.isAmountValid(0));
        //Boundary case, 0 and greater is okay, so 0 is on the boundary
        assertTrue(BankAccount.isAmountValid(0.01));
        //Boundary case, two decimal places is okay but no more
        assertFalse(BankAccount.isAmountValid(0.001));
        //Boundary case, more than two decimal places is false
        assertFalse(BankAccount.isAmountValid(-0.01));
        //Boundary case, anything less than zero should be false, this is on the edge of that

    }

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());
    }

    @Test
    void depositTest(){
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-100));
        //Equivalence case, same test as before with isAmountValid
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(0.001));
        //Border case, more than 2 decimals not allowed
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-0.01));
        //Border case, just under 0 with 2 decimal places, what is allowed

        bankAccount.deposit(100);
        assertEquals(300, bankAccount.getBalance());
        //Equivalence case, any amount 0 and up is good
        bankAccount.deposit(0);
        assertEquals(300, bankAccount.getBalance());
        //Boundary case, 0 is at the threshold of not okay
        bankAccount.deposit(0.01);
        assertEquals(300.01, bankAccount.getBalance());
        //Boundary case, 2 decimals is the most allowed
        bankAccount.deposit(0.1);
        assertEquals(300.11, bankAccount.getBalance());
        //Equivalence case, 2 decimals and less is allowed, this is not on the edge of that

    }


    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance()); //equivalence case, removing anything less than the balance should be fine/the same
        
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
        
        
        bankAccount.withdraw(100);
        assertEquals(0, bankAccount.getBalance());

        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-100));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(0.001));
    }

    @Test
    void transferTest() throws InsufficientFundsException{
        BankAccount ba1 = new BankAccount("a@b.com", 200);
        BankAccount ba2 = new BankAccount("b@a.com", 200);

        BankAccount.transfer(ba1, ba2, 100);
        //Equivalence case, pulling 100 from an account with 200 and putting it into an account isn't pushing any boundaries
        assertEquals(100, ba1.getBalance());
        assertEquals(300, ba2.getBalance());

        assertThrows(InsufficientFundsException.class, () -> BankAccount.transfer(ba1, ba2, 200));
        //Equivalence, any amount over the balance in the account being withdrawn from is not allowed

        assertThrows(IllegalArgumentException.class, () -> BankAccount.transfer(ba1, ba2, -200));
        //Equivalence, any negative amount should not be allowed

        assertThrows(IllegalArgumentException.class, () -> BankAccount.transfer(ba1, ba2, 0.001));
        //Border, any number with more than 2 decimal points is false, 3 decimal points is on the boundary

        BankAccount.transfer(ba1, ba2, 0.01);
        assertEquals(99.99, ba1.getBalance());
        assertEquals(300.01, ba2.getBalance());
        //Border case, right within acceptable 2 decimal limit

        BankAccount.transfer(ba2, ba1, 100.01);
        assertEquals(200, ba1.getBalance());
        assertEquals(200, ba2.getBalance());
        //Equivalence case, should be able to transfer money either way, to either account
    }

    @Test
    void isEmailValidTest(){
        //It's boundary case is that it needs username and domain name to be at least one character 
        assertTrue(BankAccount.isEmailValid( "a@b.com"));
        //It's missing the @ symbol and period as it's boundary case
        assertFalse( BankAccount.isEmailValid(""));
        //It's boundary case is the same as the first line, it's equivalence class is nominal value, but not entirely sure why
        assertTrue(BankAccount.isEmailValid("abababa@abababa.com"));
        //It's missing it's domain and username as it's boundary case
        assertFalse(BankAccount.isEmailValid("@."));
        //Similar to the last line, it's missing the domian, username, and a period as a boundary case
        assertFalse(BankAccount.isEmailValid("@"));
        //Missing the domain, username, and @ symbol 
        assertFalse(BankAccount.isEmailValid("."));
        //Missing a couple of key factors to make it a proper boundary case
        assertFalse(BankAccount.isEmailValid("asbaba@adbaba"));
        assertFalse(BankAccount.isEmailValid("asdfasdf.asdfasdf"));

        //It's boundary case must have letters and/or number surrounded by at least 1 special character
        assertTrue(BankAccount.isEmailValid("a-a@abcd.com"));
        assertTrue(BankAccount.isEmailValid("a_a@abcd.com"));
        //I guess it's equivalence class is nominal, but I don't know how equivalence class really works
        assertTrue(BankAccount.isEmailValid("a.a@abcd.com"));
        //It's boundary case can have two different special characters
        assertTrue(BankAccount.isEmailValid("a-a.a@abcd.com"));
        //it's equivalence class is maximum I believe, it can't have two of the same character together
        assertFalse(BankAccount.isEmailValid("a--a@abcd.com"));
        //It's equivalence class is also the same, it can't have special characters in front of the username
        assertFalse(BankAccount.isEmailValid("-aaa@abcd.com"));
        //It also can't have special character without letters and/or numbers wrap around it 
        assertFalse(BankAccount.isEmailValid("aaa-@abcd.com"));
        assertFalse(BankAccount.isEmailValid("aa#aa@abcd.com"));

        //It's boundary case must have characters if using more than one period
        assertTrue(BankAccount.isEmailValid("aaa.aaa@abcd.aa"));
        //It's equivalence class is nominal case because you can have special characters within the periods as long it has characters between it
        assertTrue(BankAccount.isEmailValid("aaa.aaa@ab-cd.aa"));
        //It's boundary case must have more than 2 characters after the last period of the email
        assertFalse(BankAccount.isEmailValid("aaa.aaa@abcd.a"));
        //It's boundary case must have a period after the @ symbol, it's equivalence class is minimum
        assertFalse(BankAccount.isEmailValid("aaa.aaa@abcd"));
        //Can't have more than 1 period after the @ symbol as the boundary case
        assertFalse(BankAccount.isEmailValid("aaa.aaa@abc.d.a"));
        assertFalse(BankAccount.isEmailValid("aaa.aaa@ab#cd.com"));
        assertFalse(BankAccount.isEmailValid("aaa.aaa@abcd.c#m"));

        //I'm not sure what boundary case or equivalence class are misssing because
        //I don't fully understand the concept for both really. I'm pretty sure that boundary
        //case has a set of rules that it needs to follow it order to be the proper case, and 
        //equivalence class has set values I believe, but again, I'm not entirely sure.

    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));

        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", -100));
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", 0.001));
    }

}