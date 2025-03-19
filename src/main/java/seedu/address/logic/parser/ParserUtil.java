package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Hire;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String nric} into an {@code Nric}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param nric The input NRIC string.
     * @return A valid {@code Nric} object.
     * @throws ParseException if the given {@code nric} does not meet the NRIC format constraints.
     */
    public static Nric parseNric(String nric) throws ParseException {
        requireNonNull(nric);
        String trimmedNric = nric.trim();
        if (!Nric.isValidNric(trimmedNric)) {
            throw new ParseException(Nric.MESSAGE_CONSTRAINTS);
        }
        return new Nric(trimmedNric);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String hire} into a {@code Hire}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param hire The input hire date string in YYYY-MM-DD format.
     * @return A valid {@code Hire} object.
     * @throws ParseException if the given {@code hire} does not match the expected date format.
     */
    public static Hire parseHire(String hire) throws ParseException {
        requireNonNull(hire);
        String trimmedHire = hire.trim();
        if (!Hire.isValidHire(trimmedHire)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Hire(trimmedHire);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String leaveStart} and {@code String leaveEnd} into a {@code Leave}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param leaveStart The input leave start date string in YYYY-MM-DD format.
     * @param leaveEnd The input leave end date string in YYYY-MM-DD format.
     * @param reason The input reason for leave.
     * @return A valid {@code Leave} object.
     * @throws ParseException if the given {@code leaveStart}, {@code leaveEnd} or {@code reason} does not match format.
     */
    public static Leave parseLeave(String leaveStart, String leaveEnd, String reason) throws ParseException {
        requireNonNull(leaveStart);
        requireNonNull(leaveEnd);
        requireNonNull(reason);
        if (!Leave.isValidLeave(leaveStart, leaveEnd, reason)) {
            throw new ParseException(Leave.MESSAGE_CONSTRAINTS);
        }
        return new Leave(leaveStart, leaveEnd, reason);
    }

    /**
     * Parses a {@code String nricListAsAWhole} into a list of NRICs for attendance.
     * Leading and trailing whitespaces will be trimmed. The NRICs are expected to be separated by at least a space.
     *
     * @param nricListAsAWhole The input string containing NRICs separated by spaces.
     * @return A list of valid NRICs, where each NRIC is represented as a string instead of NRIC object in the list.
     * @throws ParseException if any NRIC in the list is invalid.
     */
    public static List<String> parseAttendance(String nricListAsAWhole) throws ParseException {
        if (nricListAsAWhole.trim().isEmpty()) {
            return Collections.emptyList();
        }
        List<String> nricList = Arrays.asList(nricListAsAWhole.trim().split(" "));
        for (String nric: nricList) {
            if (!Nric.isValidNric(nric)) {
                throw new ParseException(Attendance.MESSAGE_CONSTRAINTS);
            }
        }
        return nricList;
    }
}
