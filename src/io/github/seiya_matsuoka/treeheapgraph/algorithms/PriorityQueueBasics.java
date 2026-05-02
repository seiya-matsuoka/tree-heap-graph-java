package io.github.seiya_matsuoka.treeheapgraph.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * PriorityQueue の基本動作を確認するためのクラス
 *
 * <p>このクラスは今回の学習テーマの中心となる部分。
 *
 * <p>Java 標準ライブラリの PriorityQueue を使い、値の追加、先頭確認、取り出し順を確認できる形にしている。
 */
public class PriorityQueueBasics {

  /** PriorityQueue の確認結果をまとめて返す結果クラス */
  public static class PriorityQueueSummary {
    private final int nodeCount;
    private final Integer minQueuePeek;
    private final List<String> minQueueInsertionLines;
    private final List<Integer> minQueuePollOrder;
    private final Integer maxQueuePeek;
    private final List<String> maxQueueInsertionLines;
    private final List<Integer> maxQueuePollOrder;
    private final List<String> traceLines;

    public PriorityQueueSummary(
        int nodeCount,
        Integer minQueuePeek,
        List<String> minQueueInsertionLines,
        List<Integer> minQueuePollOrder,
        Integer maxQueuePeek,
        List<String> maxQueueInsertionLines,
        List<Integer> maxQueuePollOrder,
        List<String> traceLines) {
      this.nodeCount = nodeCount;
      this.minQueuePeek = minQueuePeek;
      this.minQueueInsertionLines = minQueueInsertionLines;
      this.minQueuePollOrder = minQueuePollOrder;
      this.maxQueuePeek = maxQueuePeek;
      this.maxQueueInsertionLines = maxQueueInsertionLines;
      this.maxQueuePollOrder = maxQueuePollOrder;
      this.traceLines = traceLines;
    }

    public int getNodeCount() {
      return nodeCount;
    }

    public Integer getMinQueuePeek() {
      return minQueuePeek;
    }

    public List<String> getMinQueueInsertionLines() {
      return minQueueInsertionLines;
    }

    public List<Integer> getMinQueuePollOrder() {
      return minQueuePollOrder;
    }

    public Integer getMaxQueuePeek() {
      return maxQueuePeek;
    }

    public List<String> getMaxQueueInsertionLines() {
      return maxQueueInsertionLines;
    }

    public List<Integer> getMaxQueuePollOrder() {
      return maxQueuePollOrder;
    }

    public List<String> getTraceLines() {
      return traceLines;
    }
  }

  /** 自然順と逆順の PriorityQueue を作り、挙動を比較する。 */
  public PriorityQueueSummary summarize(int[] values, boolean trace) {
    List<String> traceLines = new ArrayList<>();

    PriorityQueue<Integer> minQueue = new PriorityQueue<>();
    List<String> minQueueInsertionLines = new ArrayList<>();

    // ここが今回の学習本体の前半。
    // Java 標準の PriorityQueue は最小値が先頭になることを確認する。
    for (int value : values) {
      minQueue.offer(value);
      minQueueInsertionLines.add("追加後: value=" + value + " -> " + snapshot(minQueue, true));
      if (trace) {
        traceLines.add("最小優先キューへ追加: value=" + value + " / peek=" + minQueue.peek());
      }
    }

    Integer minQueuePeek = minQueue.peek();
    List<Integer> minQueuePollOrder = new ArrayList<>();
    while (!minQueue.isEmpty()) {
      Integer polled = minQueue.poll();
      minQueuePollOrder.add(polled);
      if (trace) {
        traceLines.add("最小優先キューから取り出し: value=" + polled);
      }
    }

    PriorityQueue<Integer> maxQueue = new PriorityQueue<>(Collections.reverseOrder());
    List<String> maxQueueInsertionLines = new ArrayList<>();

    // ここが今回の学習本体の後半。
    // 比較器を渡すと、大きい値を先に取り出す使い方もできることを確認する。
    for (int value : values) {
      maxQueue.offer(value);
      maxQueueInsertionLines.add("追加後: value=" + value + " -> " + snapshot(maxQueue, false));
      if (trace) {
        traceLines.add("最大優先キューへ追加: value=" + value + " / peek=" + maxQueue.peek());
      }
    }

    Integer maxQueuePeek = maxQueue.peek();
    List<Integer> maxQueuePollOrder = new ArrayList<>();
    while (!maxQueue.isEmpty()) {
      Integer polled = maxQueue.poll();
      maxQueuePollOrder.add(polled);
      if (trace) {
        traceLines.add("最大優先キューから取り出し: value=" + polled);
      }
    }

    return new PriorityQueueSummary(
        values.length,
        minQueuePeek,
        minQueueInsertionLines,
        minQueuePollOrder,
        maxQueuePeek,
        maxQueueInsertionLines,
        maxQueuePollOrder,
        trace ? traceLines : List.of());
  }

  /**
   * PriorityQueue の内容を学習用に見やすい並びへ変換する。
   *
   * <p>PriorityQueue の iterator 順は優先順そのものではないため、 今回は学習しやすいよう並び替えたスナップショットを表示する。
   */
  private String snapshot(PriorityQueue<Integer> queue, boolean ascending) {
    List<Integer> values = new ArrayList<>(queue);
    if (ascending) {
      Collections.sort(values);
    } else {
      values.sort(Collections.reverseOrder());
    }
    return values.toString();
  }
}
