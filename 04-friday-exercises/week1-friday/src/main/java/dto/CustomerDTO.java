package dto;

import entities.BankCustomer;

public class CustomerDTO {

    private Long customerID;
    private String fullName;
    private String accountNumber;
    private double balance;

    public CustomerDTO(BankCustomer BC) {
        this.customerID = BC.getId();
        this.fullName = BC.getFirstName() + ", " + BC.getLastName();
        this.accountNumber = BC.getAccountNumber();
        this.balance = BC.getBalance();
    }

    public CustomerDTO() {
    }

    
    
    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    
    
}
