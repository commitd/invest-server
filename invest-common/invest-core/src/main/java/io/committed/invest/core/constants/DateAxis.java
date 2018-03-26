package io.committed.invest.core.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class DateAxis {

  private DateAxis() {
    // Singleton
  }

  public static final List<String> MONTHS_OF_YEAR =
      Collections.unmodifiableList(Arrays.asList("January", "Febraury", "March", "April", "May", "June",
          "July", "August", "September", "October", "November", "December"));


  public static final List<String> DAYS_OF_WEEK =
      Collections.unmodifiableList(
          Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));


  public static final List<String> HOURS_OF_DAY =
      Collections.unmodifiableList(Arrays.asList("0000", "0100", "0200", "0300", "0400", "0500", "0600",
          "0700", "0800", "0900", "1000", "1100", "1200", "1300", "1400", "1500", "1600", "1700",
          "1800", "1900", "2000", "2100", "2200", "2300"));


  public static final List<String> MINUTES_OF_HOUR =
      Collections.unmodifiableList(Arrays.asList("00", "01", "02", "03", "04", "05", "06", "07", "08",
          "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
          "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38",
          "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53",
          "54", "55", "56", "57", "58", "59"));

  public static final List<String> SECONDS_OF_MINUTE =
      Collections.unmodifiableList(Arrays.asList("00", "01", "02", "03", "04", "05", "06", "07", "08",
          "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
          "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38",
          "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53",
          "54", "55", "56", "57", "58", "59"));
}
