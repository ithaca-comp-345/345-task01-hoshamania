package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));
        assertFalse( BankAccount.isEmailValid(""));
        assertTrue(BankAccount.isEmailValid("abababa@abababa.com"));
        assertFalse(BankAccount.isEmailValid("@."));
        assertFalse(BankAccount.isEmailValid("@"));
        assertFalse(BankAccount.isEmailValid("."));
        assertFalse(BankAccount.isEmailValid("asbaba@adbaba"));
        assertFalse(BankAccount.isEmailValid("asdfasdf.asdfasdf"));

        assertTrue(BankAccount.isEmailValid("a-a@abcd.com"));
        assertTrue(BankAccount.isEmailValid("a_a@abcd.com"));
        assertTrue(BankAccount.isEmailValid("a.a@abcd.com"));
        assertTrue(BankAccount.isEmailValid("a-a.a@abcd.com"));
        assertFalse(BankAccount.isEmailValid("a--a@abcd.com"));
        assertFalse(BankAccount.isEmailValid("-aaa@abcd.com"));
        assertFalse(BankAccount.isEmailValid("aaa-@abcd.com"));

        assertTrue(BankAccount.isEmailValid("aaa.aaa@abcd.aa"));
        assertTrue(BankAccount.isEmailValid("aaa.aaa@ab-cd.aa"));
        assertFalse(BankAccount.isEmailValid("aaa.aaa@abcd.a"));
        assertFalse(BankAccount.isEmailValid("aaa.aaa@abcd"));
        assertFalse(BankAccount.isEmailValid("aaa.aaa@abc.d.a"));

    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}