/**
 * Model class.
 *
 * @author Evan Wagle
 * @version 20200711
 */
public final class NewsModel {
    /*
     * Model variables.
     */
    private String output;

    /**
     * Default constructor.
     */
    public NewsModel() {
        /*
         * Initialize model; both variables start as empty strings
         */
        this.output = "";
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String output() {
        return this.output;
    }

}
