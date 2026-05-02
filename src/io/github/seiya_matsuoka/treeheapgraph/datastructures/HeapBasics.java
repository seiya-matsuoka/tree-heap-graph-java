package io.github.seiya_matsuoka.treeheapgraph.datastructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ヒープの基本動作を確認するためのクラス
 *
 * <p>このクラスは今回の学習テーマの中心となる部分。
 *
 * <p>配列を使って最大ヒープを自前実装し、追加、先頭確認、最大値の取り出しの流れを確認できる形にしている。
 */
public class HeapBasics {

  /** ヒープ確認結果をまとめて返す結果クラス */
  public static class HeapSummary {
    private final Integer rootValue;
    private final int nodeCount;
    private final int height;
    private final List<String> insertionLines;
    private final List<String> parentChildLines;
    private final List<String> levelOrderLines;
    private final int[] heapArray;
    private final List<Integer> pollOrder;
    private final List<String> traceLines;

    public HeapSummary(
        Integer rootValue,
        int nodeCount,
        int height,
        List<String> insertionLines,
        List<String> parentChildLines,
        List<String> levelOrderLines,
        int[] heapArray,
        List<Integer> pollOrder,
        List<String> traceLines) {
      this.rootValue = rootValue;
      this.nodeCount = nodeCount;
      this.height = height;
      this.insertionLines = insertionLines;
      this.parentChildLines = parentChildLines;
      this.levelOrderLines = levelOrderLines;
      this.heapArray = heapArray;
      this.pollOrder = pollOrder;
      this.traceLines = traceLines;
    }

    public Integer getRootValue() {
      return rootValue;
    }

    public int getNodeCount() {
      return nodeCount;
    }

    public int getHeight() {
      return height;
    }

    public List<String> getInsertionLines() {
      return insertionLines;
    }

    public List<String> getParentChildLines() {
      return parentChildLines;
    }

    public List<String> getLevelOrderLines() {
      return levelOrderLines;
    }

    public int[] getHeapArray() {
      return heapArray;
    }

    public List<Integer> getPollOrder() {
      return pollOrder;
    }

    public List<String> getTraceLines() {
      return traceLines;
    }
  }

  /** 最大ヒープを作り、取り出し順まで含めて確認する。 */
  public HeapSummary summarize(int[] values, boolean trace) {
    if (values.length == 0) {
      return new HeapSummary(
          null, 0, 0, List.of(), List.of(), List.of(), new int[0], List.of(), List.of());
    }

    List<String> traceLines = new ArrayList<>();
    MaxHeap heap = new MaxHeap(trace ? traceLines : null);
    List<String> insertionLines = new ArrayList<>();

    // ここが今回の学習本体の前半。
    // 値を 1 つずつ最大ヒープへ追加し、上方向へ調整する流れを確認する。
    for (int value : values) {
      heap.add(value);
      insertionLines.add("追加後: value=" + value + " -> " + Arrays.toString(heap.toArray()));
    }

    int[] heapArray = heap.toArray();
    List<String> parentChildLines = collectParentChildLines(heapArray);
    List<String> levelOrderLines = collectLevelOrderLines(heapArray);
    int height = calculateHeight(heapArray.length);

    // ここが今回の学習本体の後半。
    // 先頭の最大値を順に取り出すと、値が大きい順に並ぶことを確認する。
    MaxHeap pollHeap = new MaxHeap(trace ? traceLines : null);
    for (int value : values) {
      pollHeap.add(value);
    }

    List<Integer> pollOrder = new ArrayList<>();
    while (!pollHeap.isEmpty()) {
      pollOrder.add(pollHeap.pollMax());
    }

    return new HeapSummary(
        heapArray[0],
        heapArray.length,
        height,
        insertionLines,
        parentChildLines,
        levelOrderLines,
        heapArray,
        pollOrder,
        trace ? traceLines : List.of());
  }

  /** 親子関係を文字列へ整形する。 */
  private List<String> collectParentChildLines(int[] heapArray) {
    List<String> lines = new ArrayList<>();
    for (int parentIndex = 0; parentIndex < heapArray.length; parentIndex++) {
      int leftChildIndex = parentIndex * 2 + 1;
      int rightChildIndex = parentIndex * 2 + 2;
      String leftValue =
          leftChildIndex < heapArray.length ? String.valueOf(heapArray[leftChildIndex]) : "null";
      String rightValue =
          rightChildIndex < heapArray.length ? String.valueOf(heapArray[rightChildIndex]) : "null";
      lines.add(heapArray[parentIndex] + " -> left=" + leftValue + ", right=" + rightValue);
    }
    return lines;
  }

  /** レベル順の並びを文字列へ整形する。 */
  private List<String> collectLevelOrderLines(int[] heapArray) {
    List<String> lines = new ArrayList<>();
    int level = 0;
    int index = 0;
    while (index < heapArray.length) {
      int levelCount = 1 << level;
      int endExclusive = Math.min(heapArray.length, index + levelCount);
      List<Integer> levelValues = new ArrayList<>();
      for (int i = index; i < endExclusive; i++) {
        levelValues.add(heapArray[i]);
      }
      lines.add("level " + level + " : " + levelValues);
      index = endExclusive;
      level++;
    }
    return lines;
  }

  /** ヒープの高さを求める。 */
  private int calculateHeight(int nodeCount) {
    if (nodeCount == 0) {
      return 0;
    }

    int height = 0;
    int lastIndex = nodeCount - 1;
    while (lastIndex > 0) {
      lastIndex = (lastIndex - 1) / 2;
      height++;
    }
    return height;
  }

  /**
   * 学習用の最大ヒープ
   *
   * <p>親ノードは子ノード以上である、という最大ヒープ条件を保つ。
   */
  private static class MaxHeap {
    private int[] values = new int[16];
    private int size;
    private final List<String> traceLines;

    MaxHeap(List<String> traceLines) {
      this.traceLines = traceLines;
    }

    boolean isEmpty() {
      return size == 0;
    }

    /**
     * 値を追加する。
     *
     * <p>末尾へ追加した後、親と比較しながら上へ持ち上げて最大ヒープ条件を保つ。
     *
     * @param valueToInsert ヒープへ追加する値
     */
    void add(int valueToInsert) {
      ensureCapacity();
      values[size] = valueToInsert;

      if (traceLines != null) {
        traceLines.add("追加: value=" + valueToInsert + " を末尾 index=" + size + " へ配置");
      }

      siftUp(size);
      size++;
    }

    /**
     * 最大値を取り出す。
     *
     * <p>先頭を返し、末尾要素を root へ移した後、下へ沈めて最大ヒープ条件を保つ。
     */
    int pollMax() {
      int maxValue = values[0];
      size--;

      if (size > 0) {
        values[0] = values[size];
        if (traceLines != null) {
          traceLines.add("取り出し後の root を調整: root=" + values[0] + " から開始");
        }
        siftDown(0);
      }

      return maxValue;
    }

    int[] toArray() {
      return Arrays.copyOf(values, size);
    }

    /**
     * 追加直後の値を上へ持ち上げる。
     *
     * @param childIndex 追加した値が現在入っている位置
     */
    private void siftUp(int childIndex) {
      int currentIndex = childIndex;

      while (currentIndex > 0) {
        int parentIndex = (currentIndex - 1) / 2;

        if (traceLines != null) {
          traceLines.add(
              "上方向の比較: parent=" + values[parentIndex] + " / child=" + values[currentIndex]);
        }

        if (values[parentIndex] >= values[currentIndex]) {
          break;
        }

        swap(parentIndex, currentIndex);

        if (traceLines != null) {
          traceLines.add(
              "交換: parentIndex="
                  + parentIndex
                  + ", childIndex="
                  + currentIndex
                  + " -> "
                  + Arrays.toString(toArray()));
        }

        currentIndex = parentIndex;
      }
    }

    /**
     * root へ移した値を下へ沈める。
     *
     * @param parentIndex 現在比較の基準にしている親ノード位置
     */
    private void siftDown(int parentIndex) {
      int currentParentIndex = parentIndex;

      while (true) {
        int leftChildIndex = currentParentIndex * 2 + 1;
        int rightChildIndex = currentParentIndex * 2 + 2;
        int largestIndex = currentParentIndex;

        if (leftChildIndex < size) {
          if (traceLines != null) {
            traceLines.add(
                "左の子と比較: parent=" + values[largestIndex] + " / left=" + values[leftChildIndex]);
          }
          if (values[leftChildIndex] > values[largestIndex]) {
            largestIndex = leftChildIndex;
          }
        }

        if (rightChildIndex < size) {
          if (traceLines != null) {
            traceLines.add(
                "右の子と比較: currentMax="
                    + values[largestIndex]
                    + " / right="
                    + values[rightChildIndex]);
          }
          if (values[rightChildIndex] > values[largestIndex]) {
            largestIndex = rightChildIndex;
          }
        }

        if (largestIndex == currentParentIndex) {
          break;
        }

        swap(currentParentIndex, largestIndex);

        if (traceLines != null) {
          traceLines.add(
              "交換: parentIndex="
                  + currentParentIndex
                  + ", childIndex="
                  + largestIndex
                  + " -> "
                  + Arrays.toString(toArray()));
        }

        currentParentIndex = largestIndex;
      }
    }

    private void ensureCapacity() {
      if (size < values.length) {
        return;
      }
      values = Arrays.copyOf(values, values.length * 2);
    }

    private void swap(int leftIndex, int rightIndex) {
      int temp = values[leftIndex];
      values[leftIndex] = values[rightIndex];
      values[rightIndex] = temp;
    }
  }
}
