package entity;

import entity.AtmCard;
import entity.Customer;
import entity.DepositAccountTransaction;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import util.enumeration.DepositAccountType;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-23T22:21:08")
@StaticMetamodel(DepositAccount.class)
public class DepositAccount_ { 

    public static volatile SingularAttribute<DepositAccount, AtmCard> atmCard;
    public static volatile SingularAttribute<DepositAccount, BigDecimal> ledgerBalance;
    public static volatile SingularAttribute<DepositAccount, DepositAccountType> accountType;
    public static volatile SingularAttribute<DepositAccount, Long> depositAccountId;
    public static volatile SingularAttribute<DepositAccount, BigDecimal> holdBalance;
    public static volatile SingularAttribute<DepositAccount, String> accountNumber;
    public static volatile ListAttribute<DepositAccount, DepositAccountTransaction> transactions;
    public static volatile SingularAttribute<DepositAccount, Boolean> enabled;
    public static volatile SingularAttribute<DepositAccount, BigDecimal> availableBalance;
    public static volatile SingularAttribute<DepositAccount, Customer> customer;

}