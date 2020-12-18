package entity;

import entity.AtmCard;
import entity.DepositAccount;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-23T22:21:08")
@StaticMetamodel(Customer.class)
public class Customer_ { 

    public static volatile SingularAttribute<Customer, String> firstName;
    public static volatile SingularAttribute<Customer, String> lastName;
    public static volatile ListAttribute<Customer, DepositAccount> depositAccounts;
    public static volatile SingularAttribute<Customer, AtmCard> atmCard;
    public static volatile SingularAttribute<Customer, String> postalCode;
    public static volatile SingularAttribute<Customer, Long> customerId;
    public static volatile SingularAttribute<Customer, String> contactNumber;
    public static volatile SingularAttribute<Customer, String> identificationNumber;
    public static volatile SingularAttribute<Customer, String> addressLine1;
    public static volatile SingularAttribute<Customer, String> addressLine2;

}