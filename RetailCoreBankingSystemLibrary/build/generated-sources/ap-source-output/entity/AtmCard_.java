package entity;

import entity.Customer;
import entity.DepositAccount;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-23T22:21:08")
@StaticMetamodel(AtmCard.class)
public class AtmCard_ { 

    public static volatile ListAttribute<AtmCard, DepositAccount> depositAccounts;
    public static volatile SingularAttribute<AtmCard, String> pin;
    public static volatile SingularAttribute<AtmCard, String> nameOnCard;
    public static volatile SingularAttribute<AtmCard, Long> atmCardId;
    public static volatile SingularAttribute<AtmCard, String> cardNumber;
    public static volatile SingularAttribute<AtmCard, Boolean> enabled;
    public static volatile SingularAttribute<AtmCard, Customer> customer;

}