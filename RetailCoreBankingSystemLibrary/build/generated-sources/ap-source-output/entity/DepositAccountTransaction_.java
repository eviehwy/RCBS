package entity;

import entity.DepositAccount;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import util.enumeration.TransactionStatus;
import util.enumeration.TransactionType;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-23T22:21:08")
@StaticMetamodel(DepositAccountTransaction.class)
public class DepositAccountTransaction_ { 

    public static volatile SingularAttribute<DepositAccountTransaction, String> reference;
    public static volatile SingularAttribute<DepositAccountTransaction, BigDecimal> amount;
    public static volatile SingularAttribute<DepositAccountTransaction, String> code;
    public static volatile SingularAttribute<DepositAccountTransaction, Date> transactionDateTime;
    public static volatile SingularAttribute<DepositAccountTransaction, Long> depositAccountTransactionId;
    public static volatile SingularAttribute<DepositAccountTransaction, DepositAccount> depositAccount;
    public static volatile SingularAttribute<DepositAccountTransaction, TransactionType> type;
    public static volatile SingularAttribute<DepositAccountTransaction, TransactionStatus> status;

}