package ru.utility.statistics;

public class FullStatisticsFloatsNumber extends FullStatisticsNumbers<Double> {

  public FullStatisticsFloatsNumber() {
    super(0, 0.0);
  }

  public void add(double number) {
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
    setAverage(getAmount() / getCount());
  }
}
