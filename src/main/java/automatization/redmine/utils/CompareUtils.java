package automatization.redmine.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.Lists;
import org.testng.Assert;

import static java.time.format.DateTimeFormatter.ofPattern;

public class CompareUtils {
    private static final Comparator<String> DATE_DESC_COMPARATOR = (s1, s2) -> {
        LocalDateTime d1 = LocalDateTime.parse(s1, ofPattern("dd.MM.yyyy HH:mm"));
        LocalDateTime d2 = LocalDateTime.parse(s2, ofPattern("dd.MM.yyyy HH:mm"));
        return d2.compareTo(d1);
    };
    private static final Comparator<String> DATE_ASC_COMPARATOR = DATE_DESC_COMPARATOR.reversed();

    private static final Comparator<String> STRING_DESC_COMPARATOR = (s1, s2) -> {
        String d1 = s1.toLowerCase();
        String d2 = s2.toLowerCase();
        return d2.compareTo(d1);
    };

    private static final Comparator<String> STRING_ASC_COMPARATOR = STRING_DESC_COMPARATOR.reversed();

    public static void assertListSortedByDateDesc(List<String> dates) {
        List<String> datesCopy = new ArrayList<>(dates);
        datesCopy.sort(DATE_DESC_COMPARATOR);
        Assert.assertEquals(dates, datesCopy);
    }

    public static void assertListSortedByDateAsc(List<String> dates) {
        List<String> datesCopy = new ArrayList<>(dates);
        datesCopy.sort(DATE_ASC_COMPARATOR);
        Assert.assertEquals(dates, datesCopy);
    }

    public static void assertListSortedByNameDesc(List<String> names) {
        List<String> namesCopy = new ArrayList<>(names);
        namesCopy.sort(STRING_DESC_COMPARATOR);
        Assert.assertEquals(names, namesCopy);
    }

    public static void assertListSortedByNameAsc(List<String> names) {
        List<String> namesCopy = new ArrayList<>(names);
        namesCopy.sort(STRING_ASC_COMPARATOR);
        Assert.assertEquals(names, namesCopy);
    }

    public static void assertIsNotSorted(List<String> strings) {
        List<String> stringsCopy = new ArrayList<>(strings);
        stringsCopy.sort(String::compareTo);
        Assert.assertNotEquals(strings, stringsCopy);

        stringsCopy = Lists.reverse(stringsCopy);
        Assert.assertNotEquals(strings, stringsCopy);
    }
}
