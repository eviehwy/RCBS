/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import util.enumeration.DepositAccountType;

/**
 *
 * @author eviehwy
 */
@Entity
public class DepositAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long depositAccountId;
    @Column(nullable = false, unique = true)
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private DepositAccountType accountType;
    @Column(nullable = false, precision = 11, scale = 2)
    private BigDecimal availableBalance;
    @Column(nullable = false, precision = 11, scale = 2)
    private BigDecimal holdBalance;
    @Column(nullable = false, precision = 11, scale = 2)
    private BigDecimal ledgerBalance;
    private Boolean enabled;
    
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (nullable = false)
    private Customer customer;
    
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (nullable = true)
    private AtmCard atmCard;
    
    @OneToMany
    private List<DepositAccountTransaction> transactions;

    public DepositAccount() {
        transactions = new ArrayList<>();
    }

    public DepositAccount(String accountNumber, DepositAccountType accountType, BigDecimal availableBalance, BigDecimal holdBalance, BigDecimal ledgerBalance, Boolean enabled, Customer customer) {
        this();
        
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.availableBalance = availableBalance;
        this.holdBalance = holdBalance;
        this.ledgerBalance = ledgerBalance;
        this.enabled = enabled;
        this.customer = customer;
    }

    public DepositAccount(String accountNumber, DepositAccountType accountType, BigDecimal availableBalance, BigDecimal holdBalance, BigDecimal ledgerBalance, Boolean enabled, Customer customer, AtmCard atmCard, List<DepositAccountTransaction> transactions) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.availableBalance = availableBalance;
        this.holdBalance = holdBalance;
        this.ledgerBalance = ledgerBalance;
        this.enabled = enabled;
        this.customer = customer;
        this.atmCard = atmCard;
        this.transactions = transactions;
    }
    
    
    public Long getDepositAccountId() {
        return depositAccountId;
    }

    public void setDepositAccountId(Long depositAccountId) {
        this.depositAccountId = depositAccountId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (depositAccountId != null ? depositAccountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the depositAccountId fields are not set
        if (!(object instanceof DepositAccount)) {
            return false;
        }
        DepositAccount other = (DepositAccount) object;
        if ((this.depositAccountId == null && other.depositAccountId != null) || (this.depositAccountId != null && !this.depositAccountId.equals(other.depositAccountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DepositAccount[ id=" + depositAccountId + " ]";
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public DepositAccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(DepositAccountType accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public BigDecimal getHoldBalance() {
        return holdBalance;
    }

    public void setHoldBalance(BigDecimal holdBalance) {
        this.holdBalance = holdBalance;
    }

    public BigDecimal getLedgerBalance() {
        return ledgerBalance;
    }

    public void setLedgerBalance(BigDecimal ledgerBalance) {
        this.ledgerBalance = ledgerBalance;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AtmCard getAtmCard() {
        return atmCard;
    }

    public void setAtmCard(AtmCard atmCard) {
        this.atmCard = atmCard;
    }

    public List<DepositAccountTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<DepositAccountTransaction> transactions) {
        this.transactions = transactions;
    }
    
}
