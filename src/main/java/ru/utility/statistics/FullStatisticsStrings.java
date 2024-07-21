package ru.utility.statistics;

public class FullStatisticsStrings {

  private int count;
  private int shortString;
  private int longString;

  public FullStatisticsStrings() {
    count = 0;
  }

  public void add(String line) {
    int lineLength = line.length();
    if (getCount() == 0) {
      shortString = lineLength;
      longString = lineLength;
    } else {
      if (shortString > lineLength) {
        shortString = lineLength;
      }
      if (longString < lineLength) {
        longString = lineLength;
      }
    }
    count++;
  }

  public int getCount() {
    return count;
  }

  public int getShortString() {
    return shortString;
  }

  public int getLongString() {
    return longString;
  }
}
