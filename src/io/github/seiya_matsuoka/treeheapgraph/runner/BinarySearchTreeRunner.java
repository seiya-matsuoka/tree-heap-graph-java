package io.github.seiya_matsuoka.treeheapgraph.runner;

import io.github.seiya_matsuoka.treeheapgraph.RunnerOptions;
import io.github.seiya_matsuoka.treeheapgraph.algorithms.BinarySearchTreeBasics;
import java.util.Arrays;

/**
 * 二分探索木を実行する runner
 *
 * <p>このクラスは、入力データの決定、target の決定、trace 表示、結果表示など、 「実際に学習用デモとして動かすための周辺処理」を担当する。
 *
 * <p>二分探索木そのものの本体ロジックは {@link BinarySearchTreeBasics} に置いている。
 */
public class BinarySearchTreeRunner implements TopicRunner {
  private static final int DEFAULT_TARGET = 30;
  private static final int DEFAULT_MISSING_TARGET = 999;

  private final BinarySearchTreeBasics binarySearchTreeBasics = new BinarySearchTreeBasics();

  @Override
  public void run(RunnerOptions options) {
    int[] values = resolveValues(options);
    int target = resolveTarget(options, DEFAULT_TARGET);
    int missingTarget = resolveMissingTarget(options, DEFAULT_MISSING_TARGET, target);

    System.out.println("=== 二分探索木 ===");
    System.out.println("入力値: " + formatArray(values));
    System.out.println("探索 target: " + target);
    System.out.println("未発見確認用 target: " + missingTarget);
    System.out.println();

    long start = System.nanoTime();
    BinarySearchTreeBasics.BinarySearchTreeSummary summary =
        binarySearchTreeBasics.summarize(values, target, missingTarget, options.isTrace());
    long elapsed = System.nanoTime() - start;

    if (options.isTrace() && !summary.getTraceLines().isEmpty()) {
      System.out.println("途中経過:");
      for (String line : summary.getTraceLines()) {
        System.out.println("- " + line);
      }
      System.out.println();
    }

    System.out.println("基本情報:");
    System.out.println("- ルート: " + summary.getRootValue());
    System.out.println("- ノード数: " + summary.getNodeCount());
    System.out.println("- 高さ: " + summary.getHeight());
    System.out.println("- 参考実行時間(ns): " + elapsed);
    System.out.println();

    System.out.println("挿入結果:");
    for (String line : summary.getInsertionLines()) {
      System.out.println("- " + line);
    }
    System.out.println();

    System.out.println("探索結果:");
    System.out.println("- target=" + target + " -> " + summary.getSearchResultLine());
    System.out.println("- target=" + missingTarget + " -> " + summary.getMissingSearchResultLine());
    System.out.println();

    System.out.println("親子関係:");
    for (String line : summary.getParentChildLines()) {
      System.out.println("- " + line);
    }
    System.out.println();

    System.out.println("中順走査:");
    System.out.println("- " + summary.getInorderValues());
    System.out.println();

    System.out.println("レベル順の並び:");
    for (String line : summary.getLevelOrderLines()) {
      System.out.println("- " + line);
    }
  }

  /** 実行に使う入力配列を決定する。 */
  private int[] resolveValues(RunnerOptions options) {
    if (options.getInput() != null && !options.getInput().isBlank()) {
      return parseIntArray(options.getInput());
    }

    if (options.getSize() != null) {
      return createGeneratedValues(options.getSize());
    }

    return new int[] {40, 20, 60, 10, 30, 50, 70};
  }

  /** target 値を決定する。 */
  private int resolveTarget(RunnerOptions options, int defaultValue) {
    if (options.getTarget() != null && !options.getTarget().isBlank()) {
      return Integer.parseInt(options.getTarget().trim());
    }
    return defaultValue;
  }

  /**
   * 未発見ケース確認用の target を決める。
   *
   * <p>既定値が通常 target と重ならないように補正する。
   */
  private int resolveMissingTarget(RunnerOptions options, int defaultValue, int target) {
    if (options.getInput() != null && !options.getInput().isBlank()) {
      int candidate = target + 1;
      int[] values = parseIntArray(options.getInput());
      while (contains(values, candidate)) {
        candidate++;
      }
      return candidate;
    }

    if (options.getSize() != null) {
      return options.getSize() * 10 + 5;
    }

    if (defaultValue == target) {
      return target + 1;
    }
    return defaultValue;
  }

  private boolean contains(int[] values, int target) {
    for (int value : values) {
      if (value == target) {
        return true;
      }
    }
    return false;
  }

  /** カンマ区切り文字列を int 配列へ変換する。 */
  private int[] parseIntArray(String raw) {
    String[] tokens = raw.split(",");
    int[] values = new int[tokens.length];
    for (int i = 0; i < tokens.length; i++) {
      values[i] = Integer.parseInt(tokens[i].trim());
    }
    return values;
  }

  /**
   * 指定サイズの入力配列を生成する。
   *
   * <p>二分探索木が偏りすぎないよう、中央値から入れる順序の配列を作る。
   */
  private int[] createGeneratedValues(int size) {
    int actualSize = Math.max(1, size);
    int[] sorted = new int[actualSize];
    for (int i = 0; i < actualSize; i++) {
      sorted[i] = (i + 1) * 10;
    }

    int[] order = new int[actualSize];
    fillBalancedInsertionOrder(sorted, 0, actualSize - 1, order, new int[] {0});
    return order;
  }

  /**
   * ソート済み配列から、比較的バランスのよい挿入順を作る。
   *
   * @param sortedValues ソート済み値配列
   * @param leftIndex 現在処理する左端 index
   * @param rightIndex 現在処理する右端 index
   * @param insertionOrder 出力先配列
   * @param writeIndex 現在の書き込み位置を保持する 1 要素配列
   */
  private void fillBalancedInsertionOrder(
      int[] sortedValues, int leftIndex, int rightIndex, int[] insertionOrder, int[] writeIndex) {
    if (leftIndex > rightIndex) {
      return;
    }

    int middleIndex = (leftIndex + rightIndex) / 2;
    insertionOrder[writeIndex[0]++] = sortedValues[middleIndex];
    fillBalancedInsertionOrder(
        sortedValues, leftIndex, middleIndex - 1, insertionOrder, writeIndex);
    fillBalancedInsertionOrder(
        sortedValues, middleIndex + 1, rightIndex, insertionOrder, writeIndex);
  }

  /** 長い配列でもコンソール出力が見やすいように文字列化する。 */
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
