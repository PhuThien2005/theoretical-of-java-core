# Practice Exercises: Stream API

This folder contains hands-on practice exercises to reinforce your understanding of Java Stream API pipelines, grouping/partitioning collectors, and building custom stream Collectors.

## Exercises

### 1. Sales Reporting Aggregator (`sales-reporting-aggregator`)
Streams simplify data aggregation. Using grouping and partitioning collectors, you can categorize and aggregate elements quickly.
- **Goal**: Implement query aggregations on a list of `Sale` objects using `Collectors.groupingBy()` and `Collectors.partitioningBy()`.

#### Directory Structure
- [SalesReportingAggregator.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no23_stream_api/practice/sales-reporting-aggregator/src/SalesReportingAggregator.java)
- [SalesReportingAggregatorTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no23_stream_api/practice/sales-reporting-aggregator/test/SalesReportingAggregatorTest.java)
- [SalesReportingAggregator.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no23_stream_api/practice/sales-reporting-aggregator/solution/SalesReportingAggregator.java)

---

### 2. Stream Custom Collector (`stream-custom-collector`)
Java allows you to implement custom reduction operations by writing your own `Collector` using the `Collector.of()` factory.
- **Goal**: Implement a custom stream Collector to calculate the standard deviation of a stream of double values.

#### Directory Structure
- [StreamCustomCollector.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no23_stream_api/practice/stream-custom-collector/src/StreamCustomCollector.java)
- [StreamCustomCollectorTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no23_stream_api/practice/stream-custom-collector/test/StreamCustomCollectorTest.java)
- [StreamCustomCollector.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no23_stream_api/practice/stream-custom-collector/solution/StreamCustomCollector.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py no23_stream_api
```
