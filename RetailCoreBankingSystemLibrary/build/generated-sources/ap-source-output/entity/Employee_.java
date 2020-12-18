package entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import util.enumeration.EmployeeAccessRightEnum;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-23T22:21:08")
@StaticMetamodel(Employee.class)
public class Employee_ { 

    public static volatile SingularAttribute<Employee, String> firstName;
    public static volatile SingularAttribute<Employee, String> lastName;
    public static volatile SingularAttribute<Employee, String> password;
    public static volatile SingularAttribute<Employee, EmployeeAccessRightEnum> accessRight;
    public static volatile SingularAttribute<Employee, Long> employeeId;
    public static volatile SingularAttribute<Employee, String> username;

}