package common.Model;

/**
 * This class contains constants used for fetching and quitting operations.
 */
public final class Constants {

    private Constants() {
        // private constructor to prevent instantiation
        throw new UnsupportedOperationException("Constants class cannot be instantiated");
    }

    /**
     * Constant for fetch operation.
     */
    public static final String FETCH = "fetch";

    /**
     * Constant for quit operation.
     */
    public static final String QUIT = "quit";

}
