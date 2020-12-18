/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author eviehwy
 */
public class CustomerExistException extends Exception {

    /**
     * Creates a new instance of <code>CustomerUsernameExistException</code>
     * without detail message.
     */
    public CustomerExistException() {
    }

    /**
     * Constructs an instance of <code>CustomerUsernameExistException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public CustomerExistException(String msg) {
        super(msg);
    }
}
