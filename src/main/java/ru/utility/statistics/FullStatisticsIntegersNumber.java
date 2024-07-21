package ru.utility.statistics;

public class FullStatisticsIntegersNumber extends FullStatisticsNumbers<Long> {

  public FullStatisticsIntegersNumber() {
    super(0, 0L);
  }

  public void add(Long number) {
    if (getCount() == 0) {
      setMin(number);
      setMax(number);
    } else {
      if (getMin() > number) {
        setMin(number);
      }
      if (getMax() < number) {
        setMax(number);
      }
    }
    countIncrement();
    setAmount(getAmount() + number);
    setAverage((double) getAmount() / getCount());
  }
}
