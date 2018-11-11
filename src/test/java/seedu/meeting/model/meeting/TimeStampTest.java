package seedu.meeting.model.meeting;

import java.time.Month;
import java.time.Year;

import org.junit.Assert;
import org.junit.Test;

// @@author NyxF4ll
public class TimeStampTest {

    private static final Year SMALLER_YEAR = Year.of(2017);
    private static final Year LARGER_YEAR = Year.of(2018);
    private static final Year INVALID_YEAR_NEGATIVE = Year.of(-2);
    private static final Year INVALID_YEAR_POSITVE = Year.of(40999);

    private static final Month SMALLER_MONTH = Month.JUNE;
    private static final Month LARGER_MONTH = Month.NOVEMBER;

    private static final Integer SMALLER_DATE = 10;
    private static final Integer LARGER_DATE = 12;
    private static final Integer INVALID_DATE_NEGATIVE = -1;
    private static final Integer INVALID_DATE_POSITIVE = 32;

    private static final Integer SMALLER_HOUR = 11;
    private static final Integer LARGER_HOUR = 21;
    private static final Integer INVALID_HOUR_NEGATIVE = -12;
    private static final Integer INVALID_HOUR_POSITIVE = 24;

    private static final Integer SMALLER_MINUTE = 30;
    private static final Integer LARGER_MINUTE = 58;
    private static final Integer INVALID_MINUTE_NEGATIVE = -5;
    private static final Integer INVALID_MINUTE_POSITIVE = 60;

    private static final TimeStamp SMALLER = new TimeStamp(SMALLER_YEAR, SMALLER_MONTH,
            SMALLER_DATE, SMALLER_HOUR, SMALLER_MINUTE);

    private static final TimeStamp LARGER = new TimeStamp(LARGER_YEAR, LARGER_MONTH,
            LARGER_DATE, LARGER_HOUR, LARGER_MINUTE);

    @Test
    public void checkValidArgument_returnsTrue() {
        Assert.assertTrue(TimeStamp.isValidArgument(SMALLER_YEAR, SMALLER_MONTH,
                SMALLER_DATE, SMALLER_HOUR, SMALLER_MINUTE));
        Assert.assertTrue(TimeStamp.isValidArgument(LARGER_YEAR, LARGER_MONTH,
                LARGER_DATE, LARGER_HOUR, LARGER_MINUTE));
    }

    @Test
    public void checkInvalidArgument_returnsFalse() {
        // Invalid year -> returns false
        Assert.assertFalse(TimeStamp.isValidArgument(INVALID_YEAR_NEGATIVE, SMALLER_MONTH,
                SMALLER_DATE, SMALLER_HOUR, SMALLER_MINUTE));
        Assert.assertFalse(TimeStamp.isValidArgument(INVALID_YEAR_POSITVE, SMALLER_MONTH,
                SMALLER_DATE, SMALLER_HOUR, SMALLER_MINUTE));

        // Invalid date -> returns false
        Assert.assertFalse(TimeStamp.isValidArgument(SMALLER_YEAR, SMALLER_MONTH,
                INVALID_DATE_NEGATIVE, SMALLER_HOUR, SMALLER_MINUTE));
        Assert.assertFalse(TimeStamp.isValidArgument(SMALLER_YEAR, SMALLER_MONTH,
                INVALID_DATE_POSITIVE, SMALLER_HOUR, SMALLER_MINUTE));

        // Invalid hour -> returns false
        Assert.assertFalse(TimeStamp.isValidArgument(SMALLER_YEAR, SMALLER_MONTH,
                SMALLER_DATE, INVALID_HOUR_NEGATIVE, SMALLER_MINUTE));
        Assert.assertFalse(TimeStamp.isValidArgument(SMALLER_YEAR, SMALLER_MONTH,
                SMALLER_DATE, INVALID_HOUR_POSITIVE, SMALLER_MINUTE));

        // Invalid minute -> returns false
        Assert.assertFalse(TimeStamp.isValidArgument(SMALLER_YEAR, SMALLER_MONTH,
                SMALLER_DATE, SMALLER_HOUR, INVALID_MINUTE_NEGATIVE));
        Assert.assertFalse(TimeStamp.isValidArgument(SMALLER_YEAR, SMALLER_MONTH,
                SMALLER_DATE, SMALLER_HOUR, INVALID_MINUTE_POSITIVE));
    }

    @Test
    public void compareTo() {
        // smaller year -> returns positive
        TimeStamp other = new TimeStamp(SMALLER_YEAR, LARGER_MONTH,
                LARGER_DATE, LARGER_HOUR, LARGER_MINUTE);
        Assert.assertTrue(LARGER.compareTo(other) > 0);

        // larger year -> returns negative
        other = new TimeStamp(LARGER_YEAR, SMALLER_MONTH,
                SMALLER_DATE, SMALLER_HOUR, SMALLER_MINUTE);
        Assert.assertTrue(SMALLER.compareTo(other) < 0);

        // same year but smaller month -> returns positive
        other = new TimeStamp(LARGER_YEAR, SMALLER_MONTH,
                LARGER_DATE, LARGER_HOUR, LARGER_MINUTE);
        Assert.assertTrue(LARGER.compareTo(other) > 0);

        // same year but larger month -> returns negative
        other = new TimeStamp(SMALLER_YEAR, LARGER_MONTH,
                SMALLER_DATE, SMALLER_HOUR, SMALLER_MINUTE);
        Assert.assertTrue(SMALLER.compareTo(other) < 0);

        // same year and month but smaller date -> returns positive
        other = new TimeStamp(LARGER_YEAR, LARGER_MONTH,
                SMALLER_DATE, LARGER_HOUR, LARGER_MINUTE);
        Assert.assertTrue(LARGER.compareTo(other) > 0);

        // same year and month but larger date -> returns negative
        other = new TimeStamp(SMALLER_YEAR, SMALLER_MONTH,
                LARGER_DATE, SMALLER_HOUR, SMALLER_MINUTE);
        Assert.assertTrue(SMALLER.compareTo(other) < 0);

        // same year, month and date but smaller hour -> returns positive
        other = new TimeStamp(LARGER_YEAR, LARGER_MONTH,
                LARGER_DATE, SMALLER_HOUR, LARGER_MINUTE);
        Assert.assertTrue(LARGER.compareTo(other) > 0);

        // same year, month and date but larger hour -> returns negative
        other = new TimeStamp(SMALLER_YEAR, SMALLER_MONTH,
                SMALLER_DATE, LARGER_HOUR, SMALLER_MINUTE);
        Assert.assertTrue(SMALLER.compareTo(other) < 0);

        // same year, month, date and hour but smaller minute -> returns positive
        other = new TimeStamp(LARGER_YEAR, LARGER_MONTH,
                LARGER_DATE, LARGER_HOUR, SMALLER_MINUTE);
        Assert.assertTrue(LARGER.compareTo(other) > 0);

        // same year, month, date and hour but larger minute -> returns negative
        other = new TimeStamp(SMALLER_YEAR, SMALLER_MONTH,
                SMALLER_DATE, SMALLER_HOUR, LARGER_MINUTE);
        Assert.assertTrue(SMALLER.compareTo(other) < 0);

        // smaller minute but larger hour -> returns positive
        TimeStamp first = new TimeStamp(SMALLER_YEAR, SMALLER_MONTH,
                SMALLER_DATE, LARGER_HOUR, SMALLER_MINUTE);
        TimeStamp second = new TimeStamp(SMALLER_YEAR, SMALLER_MONTH,
                SMALLER_DATE, SMALLER_HOUR, LARGER_MINUTE);
        Assert.assertTrue(first.compareTo(second) > 0);

        // larger date but smaller year -> returns negative
        first = new TimeStamp(SMALLER_YEAR, SMALLER_MONTH,
                LARGER_DATE, SMALLER_HOUR, SMALLER_MINUTE);
        second = new TimeStamp(LARGER_YEAR, SMALLER_MONTH,
                SMALLER_DATE, SMALLER_HOUR, LARGER_MINUTE);
        Assert.assertTrue(first.compareTo(second) < 0);

        // same value -> returns 0
        TimeStamp timeStampWithSameValues = new TimeStamp(SMALLER_YEAR, SMALLER_MONTH,
                SMALLER_DATE, SMALLER_HOUR, SMALLER_MINUTE);
        Assert.assertEquals(0, SMALLER.compareTo(timeStampWithSameValues));
    }

    @Test
    public void equals() {
        // same values -> returns true
        TimeStamp timeStampWithSameValue = new TimeStamp(SMALLER_YEAR, SMALLER_MONTH,
                SMALLER_DATE, SMALLER_HOUR, SMALLER_MINUTE);
        Assert.assertTrue(SMALLER.equals(timeStampWithSameValue));

        // same object -> returns true
        Assert.assertTrue(LARGER.equals(LARGER));

        // null -> returns false
        Assert.assertFalse(LARGER.equals(null));

        // different year -> returns false
        TimeStamp timeStamp = new TimeStamp(LARGER_YEAR, SMALLER_MONTH,
                SMALLER_DATE, SMALLER_HOUR, SMALLER_MINUTE);
        Assert.assertFalse(SMALLER.equals(null));

        // different month -> returns false
        timeStamp = new TimeStamp(SMALLER_YEAR, LARGER_MONTH,
                SMALLER_DATE, SMALLER_HOUR, SMALLER_MINUTE);
        Assert.assertFalse(SMALLER.equals(null));

        // different date -> returns false
        timeStamp = new TimeStamp(SMALLER_YEAR, SMALLER_MONTH,
                LARGER_DATE, SMALLER_HOUR, SMALLER_MINUTE);
        Assert.assertFalse(SMALLER.equals(null));

        // different hour -> returns false
        timeStamp = new TimeStamp(SMALLER_YEAR, SMALLER_MONTH,
                SMALLER_DATE, LARGER_HOUR, SMALLER_MINUTE);
        Assert.assertFalse(SMALLER.equals(null));

        // different minute -> returns false
        timeStamp = new TimeStamp(SMALLER_YEAR, SMALLER_MONTH,
                SMALLER_DATE, SMALLER_HOUR, LARGER_MINUTE);
        Assert.assertFalse(SMALLER.equals(null));
    }

    @Test
    public void string_conversion() {
        // 18 February 2017, 11:31 AM -> 18-2-2017@11:31
        TimeStamp time = new TimeStamp(Year.of(2017), Month.FEBRUARY, 18, 11, 31);
        Assert.assertEquals("18-2-2017@11:31", time.toString());

        // 19 March 1987, 1:13 PM -> 19-3-1987@13:13
        time = new TimeStamp(Year.of(1987), Month.MARCH, 19, 13, 13);
        Assert.assertEquals("19-3-1987@13:13", time.toString());

        // 31 December 9999, 11:59 PM -> 31-12-9999@23:59
        time = new TimeStamp(Year.of(9999), Month.DECEMBER, 31, 23, 59);
        Assert.assertEquals("31-12-9999@23:59", time.toString());

        // 1 January 1, 00:00 -> 1-1-1@0:0
        time = new TimeStamp(Year.of(1), Month.JANUARY, 1, 0, 0);
        Assert.assertEquals("1-1-1@00:00", time.toString());

        //21 December 2012, 1:23 AM -> 21-12-2012@1:23
        time = new TimeStamp(Year.of(2012), Month.DECEMBER, 21, 1, 23);
        Assert.assertEquals("21-12-2012@01:23", time.toString());
    }
}
