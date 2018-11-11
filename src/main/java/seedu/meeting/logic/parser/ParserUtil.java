package seedu.meeting.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.meeting.model.meeting.TimeStamp.DATE_SPLIT_REGEX;
import static seedu.meeting.model.meeting.TimeStamp.EXPECTED_SPLIITTED_LENGTH;
import static seedu.meeting.model.meeting.TimeStamp.MESSAGE_TIMESTAMP_CONSTRAINT;
import static seedu.meeting.model.meeting.TimeStamp.SPLITTED_DAY_INDEX;
import static seedu.meeting.model.meeting.TimeStamp.SPLITTED_HOUR_INDEX;
import static seedu.meeting.model.meeting.TimeStamp.SPLITTED_MINUTE_INDEX;
import static seedu.meeting.model.meeting.TimeStamp.SPLITTED_MONTH_INDEX;
import static seedu.meeting.model.meeting.TimeStamp.SPLITTED_YEAR_INDEX;
import static seedu.meeting.model.meeting.TimeStamp.isValidArgument;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.time.Month;
import java.time.Year;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.meeting.commons.core.index.Index;
import seedu.meeting.commons.util.StringUtil;
import seedu.meeting.logic.parser.exceptions.ParseException;
import seedu.meeting.model.meeting.TimeStamp;
import seedu.meeting.model.person.Email;
import seedu.meeting.model.person.Name;
import seedu.meeting.model.person.Phone;
import seedu.meeting.model.person.util.PersonPropertyComparator;
import seedu.meeting.model.shared.Address;
import seedu.meeting.model.shared.Description;
import seedu.meeting.model.shared.Title;
import seedu.meeting.model.tag.Tag;

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
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
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
            throw new ParseException(Phone.MESSAGE_PHONE_CONSTRAINTS);
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
            throw new ParseException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
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
            throw new ParseException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
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
            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
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

    /* @@author NyxF4ll */
    /**
     * Parse a {@code String} representation of a timestamp into a {@code TimeStamp} object.
     */
    public static TimeStamp parseTimeStamp(String timeStamp) throws ParseException {
        requireNonNull(timeStamp);
        String[] splitted = timeStamp.split(DATE_SPLIT_REGEX);

        if (splitted.length != EXPECTED_SPLIITTED_LENGTH) {
            throw new ParseException(MESSAGE_TIMESTAMP_CONSTRAINT);
        }

        Year year;
        Month month;
        Integer date;
        Integer hour;
        Integer minute;

        try {
            year = Year.of(Integer.parseInt(splitted[SPLITTED_YEAR_INDEX]));
            month = Month.of(Integer.parseInt(splitted[SPLITTED_MONTH_INDEX]));
            date = Integer.parseInt(splitted[SPLITTED_DAY_INDEX]);
            hour = Integer.parseInt(splitted[SPLITTED_HOUR_INDEX]);
            minute = Integer.parseInt(splitted[SPLITTED_MINUTE_INDEX]);
        } catch (DateTimeException | NumberFormatException e) {
            throw new ParseException(MESSAGE_TIMESTAMP_CONSTRAINT);
        }
        if (!isValidArgument(year, month, date, hour, minute)) {
            throw new ParseException(MESSAGE_TIMESTAMP_CONSTRAINT);
        }

        return new TimeStamp(year, month, date, hour, minute);
    }
    /* @@author */

    // @@author Derek-Hardy
    /**
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static Title parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (!Title.isValidTitle(trimmedTitle)) {
            throw new ParseException(Title.MESSAGE_TITLE_CONSTRAINTS);
        }
        return new Title(trimmedTitle);
    }


    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    // @@author

    /**
     * Parses a {@code String personProperty} into a {@code PersonPropertyComparator}.
     * Leading and trailing whitespaces will be trimmed.
     * @param personProperty the property of person.
     * @return the PersonPropertyComparator representing the comparator for that property.
     * @throws ParseException if the given {@code personProperty} is invalid.
     */
    public static PersonPropertyComparator parsePersonPropertyComparator(String personProperty) throws ParseException {
        requireNonNull(personProperty);
        String trimmedPersonProperty = personProperty.trim();
        try {
            return PersonPropertyComparator.getPersonPropertyComparator(trimmedPersonProperty);
        } catch (IllegalArgumentException e) {
            throw new ParseException(PersonPropertyComparator.MESSAGE_PERSON_PROPERTY_CONSTRAINTS);
        }
    }

    /**
     * Parse {@code String filepath} into {@code Path}.
     */
    public static Path parsePath(String filepath) throws ParseException {
        requireNonNull(filepath);
        String trimmed = filepath.trim();
        Path path = Paths.get(trimmed);
        return path;
    }
}
