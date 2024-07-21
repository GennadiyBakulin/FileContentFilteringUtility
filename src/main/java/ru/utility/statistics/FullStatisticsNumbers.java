package ru.utility.statistics;

public abstract class FullStatisticsNumbers<T> {

  private int count;
  private T min;
  private T max;
  private T amount;
  private double average;

  public FullStatisticsNumbers(int count, T amount) {
    this.count = count;
    this.amount = amount;
  }

  public void countIncrement() {
    this.count++;
  }

  public int getCount() {
    return count;
  }

  public T getMin() {
    return min;
  }

  public void setMin(T min) {
    this.min = min;
  }

  public T getMax() {
    return max;
  }

  public void setMax(T max) {
    this.max = max;
  }

  public T getAmount() {
    return amount;
  }

  public void setAmount(T amount) {
    this.amount = amount;
  }

  public double getAverage() {
    return average;
  }

  public void setAverage(double average) {
    this.average = average;
  }
}
