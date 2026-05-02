package io.github.seiya_matsuoka.treeheapgraph.runner;

import io.github.seiya_matsuoka.treeheapgraph.RunnerOptions;
import io.github.seiya_matsuoka.treeheapgraph.datastructures.TreeBasics;
import java.util.Arrays;

/**
 * 木の基礎を実行する runner
 *
 * <p>このクラスは、入力データの決定、trace 表示、結果表示など、 「実際に学習用デモとして動かすための周辺処理」を担当する。
 *
 * <p>木構造そのものの本体ロジックは {@link TreeBasics} に置いている。
 */
public class TreeBasicsRunner implements TopicRunner {
  private final TreeBasics treeBasics = new TreeBasics();

  @Override
  public void run(RunnerOptions options) {
    int[] values = resolveValues(options);

    System.out.println("=== 木の基礎 ===");
    System.out.println("入力値: " + formatArray(values));
    if (options.getTarget() != null) {
      System.out.println("--target は tree-basics では使用しないため無視する");
    }
    System.out.println();

    long start = System.nanoTime();
    TreeBasics.TreeSummary summary = treeBasics.summarize(values, options.isTrace());
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

  /**
   * 実行に使う入力配列を決定する。
   *
   * <p>このメソッドは学習テーマそのものではなく、デモを動かすための入力準備。
   */
  private int[] resolveValues(RunnerOptions options) {
    if (options.getInput() != null && !options.getInput().isBlank()) {
      return parseIntArray(options.getInput());
    }

    if (options.getSize() != null) {
      return createGeneratedValues(options.getSize());
    }

    return new int[] {10, 20, 30, 40, 50, 60, 70};
  }

  /**
   * カンマ区切り文字列を int 配列へ変換する。
   *
   * <p>これは入力準備のための補助処理。
   */
  private int[] parseIntArray(String raw) {
    String[] tokens = raw.split(",");
    int[] values = new int[tokens.length];
    for (int i = 0; i < tokens.length; i++) {
      values[i] = Integer.parseInt(tokens[i].trim());
    }
    return values;
  }

  /**
   * 指定サイズの連番配列を生成する。
   *
   * <p>大きめのデータで構造を見たい場合のための補助機能。
   */
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
