package by.nikita.attractions.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Custom error response.
 */
@Getter
@Setter
public class CustomErrorResponse {
    private int status;
    private String message;
    private long timeStamp;

    /**
     * Instantiates a new Custom error response.
     */
    public CustomErrorResponse() {
    }

    /**
     * Instantiates a new Custom error response.
     *
     * @param status    the status
     * @param message   the message
     * @param timeStamp the time stamp
     */
    public CustomErrorResponse(int status, String message, long timeStamp){
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }
}
