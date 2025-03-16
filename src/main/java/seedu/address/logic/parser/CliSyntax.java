package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("/name");
    public static final Prefix PREFIX_NRIC = new Prefix("/nric");
    public static final Prefix PREFIX_PHONE = new Prefix("/phone");
    public static final Prefix PREFIX_EMAIL = new Prefix("/email");
    public static final Prefix PREFIX_ADDRESS = new Prefix("/address");
    public static final Prefix PREFIX_HIRE = new Prefix("/hire");
    /* For Leave Command */
    public static final Prefix PREFIX_LEAVE_START = new Prefix("/start");
    public static final Prefix PREFIX_LEAVE_END = new Prefix("/end");
    public static final Prefix PREFIX_REASON = new Prefix("/reason");
}
