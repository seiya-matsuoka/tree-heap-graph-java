package io.github.seiya_matsuoka.treeheapgraph.runner;

import io.github.seiya_matsuoka.treeheapgraph.RunnerOptions;
import io.github.seiya_matsuoka.treeheapgraph.algorithms.PriorityQueueBasics;
import java.util.Arrays;

/**
 * PriorityQueue を実行する runner
 *
 * <p>このクラスは、入力データの決定、trace 表示、結果表示など、 「実際に学習用デモとして動かすための周辺処理」を担当する。
 *
 * <p>PriorityQueue の学習本体ロジックは {@link PriorityQueueBasics} に置いている。
 */
public class PriorityQueueRunner implements TopicRunner {
  private final PriorityQueueBasics priorityQueueBasics = new PriorityQueueBasics();

  @Override
  public void run(RunnerOptions options) {
    int[] values = resolveValues(options);

    System.out.println("=== PriorityQueue ===");
    System.out.println("入力値: " + formatArray(values));
    if (options.getTarget() != null) {
      System.out.println("--target は priority-queue では使用しないため無視する");
    }
    System.out.println();

    long start = System.nanoTime();
    PriorityQueueBasics.PriorityQueueSummary summary =
        priorityQueueBasics.summarize(values, options.isTrace());
    long elapsed = System.nanoTime() - start;

    if (options.isTrace() && !summary.getTraceLines().isEmpty()) {
      System.out.println("途中経過:");
      for (String line : summary.getTraceLines()) {
        System.out.println("- " + line);
      }
      System.out.println();
    }

    System.out.println("基本情報:");
    System.out.println("- 要素数: " + summary.getNodeCount());
    System.out.println("- 最小優先キューの先頭: " + summary.getMinQueuePeek());
    System.out.println("- 最大優先キューの先頭: " + summary.getMaxQueuePeek());
    System.out.println("- 参考実行時間(ns): " + elapsed);
    System.out.println();

    System.out.println("最小優先キューへの追加結果:");
    for (String line : summary.getMinQueueInsertionLines()) {
      System.out.println("- " + line);
    }
    System.out.println();

    System.out.println("最小優先キューの取り出し順:");
    System.out.println("- " + summary.getMinQueuePollOrder());
    System.out.println();

    System.out.println("最大優先キューへの追加結果:");
    for (String line : summary.getMaxQueueInsertionLines()) {
      System.out.println("- " + line);
    }
    System.out.println();

    System.out.println("最大優先キューの取り出し順:");
    System.out.println("- " + summary.getMaxQueuePollOrder());
  }

  private int[] resolveValues(RunnerOptions options) {
    if (options.getInput() != null && !options.getInput().isBlank()) {
      return parseIntArray(options.getInput());
    }

    if (options.getSize() != null) {
      return createGeneratedValues(options.getSize());
    }

    return new int[] {50, 20, 70, 10, 40, 60, 30};
  }

  private int[] parseIntArray(String raw) {
    String[] tokens = raw.split(",");
    int[] values = new int[tokens.length];
    for (int i = 0; i < tokens.length; i++) {
      values[i] = Integer.parseInt(tokens[i].trim());
    }
    return values;
  }

  private int[] createGeneratedValues(int size) {
    int actualSize = Math.max(1, size);
    int[] values = new int[actualSize];
    for (int i = 0; i < actualSize; i++) {
      values[i] = (i + 1) * 10;
    }
    return values;
  }

  private String formatArray(int[] values) {
    if (values.length <= 20) {
      return Arrays.toString(values);
    }
    int[] head = Arrays.copyOfRange(values, 0, 10);
    int[] tail = Arrays.copyOfRange(values, values.length - 5, values.length);
    return Arrays.toString(head)
        + " ... "
        + Arrays.toString(tail)
        + " (length="
        + values.length
        + ")";
  }
}
