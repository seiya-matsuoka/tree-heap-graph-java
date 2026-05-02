package io.github.seiya_matsuoka.treeheapgraph.runner;

import io.github.seiya_matsuoka.treeheapgraph.RunnerOptions;
import io.github.seiya_matsuoka.treeheapgraph.datastructures.BinaryTreeBasics;
import java.util.Arrays;

/**
 * 二分木を実行する runner
 *
 * <p>このクラスは、入力データの決定、trace 表示、結果表示など、 「実際に学習用デモとして動かすための周辺処理」を担当する。
 *
 * <p>二分木そのものの本体ロジックは {@link BinaryTreeBasics} に置いている。
 */
public class BinaryTreeRunner implements TopicRunner {
  private final BinaryTreeBasics binaryTreeBasics = new BinaryTreeBasics();

  @Override
  public void run(RunnerOptions options) {
    int[] values = resolveValues(options);

    System.out.println("=== 二分木 ===");
    System.out.println("入力値: " + formatArray(values));
    if (options.getTarget() != null) {
      System.out.println("--target は binary-tree では使用しないため無視する");
    }
    System.out.println();

    long start = System.nanoTime();
    BinaryTreeBasics.BinaryTreeSummary summary =
        binaryTreeBasics.summarize(values, options.isTrace());
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

    System.out.println("親子関係:");
    for (String line : summary.getParentChildLines()) {
      System.out.println("- " + line);
    }
    System.out.println();

    System.out.println("葉:");
    System.out.println("- " + summary.getLeafValues());
    System.out.println();

    System.out.println("各ノードの深さ:");
    for (String line : summary.getDepthLines()) {
      System.out.println("- " + line);
    }
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

    return new int[] {10, 20, 30, 40, 50, 60, 70};
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

  /** 指定サイズの連番配列を生成する。 */
  private int[] createGeneratedValues(int size) {
    int actualSize = Math.max(1, size);
    int[] values = new int[actualSize];
    for (int i = 0; i < actualSize; i++) {
      values[i] = (i + 1) * 10;
    }
    return values;
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
