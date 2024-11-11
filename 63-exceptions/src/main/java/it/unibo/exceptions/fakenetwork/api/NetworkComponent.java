package it.unibo.exceptions.fakenetwork.api;

import java.io.IOException;

/**
 * Interface for (simulated) communication over an unreliable network.
 */
public interface NetworkComponent {

    /**
     * Attempts to send data away. May fail.
     *
     * @param data the data to be sent to the networked service.
     * @throws IOException if there is any (simulated) network error.
     */
    void sendData(String data) throws IOException;

    /**
     * Tries to receive a response. May fail.
     *
     * @return the response, if any is provided.
     * @throws IOException if there is any (simulated) network error.
     */
    String receiveResponse() throws IOException;

}
