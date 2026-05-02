package io.github.seiya_matsuoka.treeheapgraph.algorithms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 二分探索木を確認するためのクラス
 *
 * <p>このクラスは今回の学習テーマの中心となる部分。
 *
 * <p>値の大小関係に従ってノードを左 / 右へ入れていき、 探索・挿入・中順走査の流れを確認できる形にしている。
 */
public class BinarySearchTreeBasics {

  /** 二分探索木の状態をまとめて返す結果クラス */
  public static class BinarySearchTreeSummary {
    private final Integer rootValue;
    private final int nodeCount;
    private final int height;
    private final List<String> insertionLines;
    private final String searchResultLine;
    private final String missingSearchResultLine;
    private final List<String> parentChildLines;
    private final List<Integer> inorderValues;
    private final List<String> levelOrderLines;
    private final List<String> traceLines;

    public BinarySearchTreeSummary(
        Integer rootValue,
        int nodeCount,
        int height,
        List<String> insertionLines,
        String searchResultLine,
        String missingSearchResultLine,
        List<String> parentChildLines,
        List<Integer> inorderValues,
        List<String> levelOrderLines,
        List<String> traceLines) {
      this.rootValue = rootValue;
      this.nodeCount = nodeCount;
      this.height = height;
      this.insertionLines = insertionLines;
      this.searchResultLine = searchResultLine;
      this.missingSearchResultLine = missingSearchResultLine;
      this.parentChildLines = parentChildLines;
      this.inorderValues = inorderValues;
      this.levelOrderLines = levelOrderLines;
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

    public String getSearchResultLine() {
      return searchResultLine;
    }

    public String getMissingSearchResultLine() {
      return missingSearchResultLine;
    }

    public List<String> getParentChildLines() {
      return parentChildLines;
    }

    public List<Integer> getInorderValues() {
      return inorderValues;
    }

    public List<String> getLevelOrderLines() {
      return levelOrderLines;
    }

    public List<String> getTraceLines() {
      return traceLines;
    }
  }

  /**
   * 二分探索木ノード
   *
   * <p>値の大小関係に従って左 / 右へ進むため、左右の子を別フィールドで持つ。
   */
  public static class BinarySearchTreeNode {
    private final int value;
    private BinarySearchTreeNode left;
    private BinarySearchTreeNode right;

    public BinarySearchTreeNode(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }

    public BinarySearchTreeNode getLeft() {
      return left;
    }

    public BinarySearchTreeNode getRight() {
      return right;
    }
  }

  /** 入力値から二分探索木を作り、表示用の情報をまとめる。 */
  public BinarySearchTreeSummary summarize(
      int[] values, int target, int missingTarget, boolean trace) {
    if (values.length == 0) {
      return new BinarySearchTreeSummary(
          null, 0, 0, List.of(), "未実行", "未実行", List.of(), List.of(), List.of(), List.of());
    }

    List<String> traceLines = new ArrayList<>();
    List<String> insertionLines = new ArrayList<>();
    BinarySearchTreeNode root = buildBinarySearchTree(values, insertionLines, traceLines);

    SearchResult foundResult = search(root, target, traceLines, trace, false);
    SearchResult missingResult = search(root, missingTarget, traceLines, trace, true);

    List<String> parentChildLines = new ArrayList<>();
    collectParentChildLines(root, parentChildLines);

    List<Integer> inorderValues = new ArrayList<>();
    collectInorderValues(root, inorderValues);

    List<String> levelOrderLines = collectLevelOrderLines(root);
    int height = calculateHeight(root);

    return new BinarySearchTreeSummary(
        root.getValue(),
        values.length,
        height,
        insertionLines,
        foundResult.message,
        missingResult.message,
        parentChildLines,
        inorderValues,
        levelOrderLines,
        trace ? traceLines : List.of());
  }

  /**
   * 二分探索木を構築する。
   *
   * <p>ここが学習本体の中心。
   *
   * <p>値を 1 つずつ挿入し、 「小さければ左、大きければ右へ進む」というルールで木を作る。
   */
  private BinarySearchTreeNode buildBinarySearchTree(
      int[] values, List<String> insertionLines, List<String> traceLines) {
    BinarySearchTreeNode root = new BinarySearchTreeNode(values[0]);
    insertionLines.add(values[0] + " を root として追加");
    traceLines.add("root を作成: value=" + values[0]);

    for (int i = 1; i < values.length; i++) {
      insert(root, values[i], insertionLines, traceLines);
    }

    return root;
  }

  /**
   * 二分探索木へ値を 1 つ挿入する。
   *
   * @param currentNode 現在見ているノード
   * @param valueToInsert 追加したい値
   * @param insertionLines 表示用の挿入結果格納先
   * @param traceLines trace 表示用の格納先
   */
  private void insert(
      BinarySearchTreeNode currentNode,
      int valueToInsert,
      List<String> insertionLines,
      List<String> traceLines) {
    BinarySearchTreeNode current = currentNode;

    while (true) {
      if (valueToInsert < current.value) {
        traceLines.add("挿入判定: " + valueToInsert + " < " + current.value + " のため左へ進む");
        if (current.left == null) {
          current.left = new BinarySearchTreeNode(valueToInsert);
          insertionLines.add(valueToInsert + " を " + current.value + " の左の子として追加");
          traceLines.add("左の子として追加: parent=" + current.value + ", child=" + valueToInsert);
          return;
        }
        current = current.left;
      } else {
        traceLines.add("挿入判定: " + valueToInsert + " >= " + current.value + " のため右へ進む");
        if (current.right == null) {
          current.right = new BinarySearchTreeNode(valueToInsert);
          insertionLines.add(valueToInsert + " を " + current.value + " の右の子として追加");
          traceLines.add("右の子として追加: parent=" + current.value + ", child=" + valueToInsert);
          return;
        }
        current = current.right;
      }
    }
  }

  /** 二分探索木で値を探す。 */
  private SearchResult search(
      BinarySearchTreeNode root,
      int target,
      List<String> traceLines,
      boolean traceEnabled,
      boolean missingCase) {
    BinarySearchTreeNode current = root;
    int stepCount = 0;

    while (current != null) {
      stepCount++;
      if (traceEnabled) {
        traceLines.add((missingCase ? "未発見確認" : "探索") + ": node=" + current.value + " を確認");
      }

      if (target == current.value) {
        return new SearchResult(stepCount + " ノード目で発見");
      }

      if (target < current.value) {
        if (traceEnabled) {
          traceLines.add(target + " < " + current.value + " のため左へ進む");
        }
        current = current.left;
      } else {
        if (traceEnabled) {
          traceLines.add(target + " > " + current.value + " のため右へ進む");
        }
        current = current.right;
      }
    }

    return new SearchResult(stepCount + " ノード確認しても見つからない");
  }

  /** 親子関係を文字列へ整形する。 */
  private void collectParentChildLines(BinarySearchTreeNode node, List<String> lines) {
    if (node == null) {
      return;
    }

    String leftValue = node.getLeft() == null ? "null" : String.valueOf(node.getLeft().getValue());
    String rightValue =
        node.getRight() == null ? "null" : String.valueOf(node.getRight().getValue());
    lines.add(node.getValue() + " -> left=" + leftValue + ", right=" + rightValue);

    collectParentChildLines(node.getLeft(), lines);
    collectParentChildLines(node.getRight(), lines);
  }

  /**
   * 中順走査で値を集める。
   *
   * <p>二分探索木では、中順走査を行うと昇順に並ぶ。
   */
  private void collectInorderValues(BinarySearchTreeNode node, List<Integer> values) {
    if (node == null) {
      return;
    }

    collectInorderValues(node.getLeft(), values);
    values.add(node.getValue());
    collectInorderValues(node.getRight(), values);
  }

  /** 高さを求める。 */
  private int calculateHeight(BinarySearchTreeNode node) {
    if (node == null) {
      return -1;
    }
    return Math.max(calculateHeight(node.getLeft()), calculateHeight(node.getRight())) + 1;
  }

  /** レベル順の並びを集める。 */
  private List<String> collectLevelOrderLines(BinarySearchTreeNode root) {
    List<String> lines = new ArrayList<>();
    Queue<BinarySearchTreeNode> queue = new ArrayDeque<>();
    queue.add(root);

    int level = 0;
    while (!queue.isEmpty()) {
      int levelSize = queue.size();
      List<Integer> values = new ArrayList<>();

      for (int i = 0; i < levelSize; i++) {
        BinarySearchTreeNode current = queue.poll();
        values.add(current.getValue());

        if (current.getLeft() != null) {
          queue.add(current.getLeft());
        }
        if (current.getRight() != null) {
          queue.add(current.getRight());
        }
      }

      lines.add("level " + level + ": " + values);
      level++;
    }

    return lines;
  }

  /** 探索結果を表す補助クラス */
  private static class SearchResult {
    private final String message;

    private SearchResult(String message) {
      this.message = message;
    }
  }
}
