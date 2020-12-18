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
public class InvalidAtmCardException extends Exception {

    /**
     * Creates a new instance of <code>InvalidAtmCardException</code> without
     * detail message.
     */
    public InvalidAtmCardException() {
    }

    /**
     * Constructs an instance of <code>InvalidAtmCardException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidAtmCardException(String msg) {
        super(msg);
    }
}
