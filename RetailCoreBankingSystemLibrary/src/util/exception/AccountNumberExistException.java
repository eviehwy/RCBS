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
public class AccountNumberExistException extends Exception {

    /**
     * Creates a new instance of <code>AccountNumberExistException</code>
     * without detail message.
     */
    public AccountNumberExistException() {
    }

    /**
     * Constructs an instance of <code>AccountNumberExistException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public AccountNumberExistException(String msg) {
        super(msg);
    }
}
