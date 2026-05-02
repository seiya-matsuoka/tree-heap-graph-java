package io.github.seiya_matsuoka.treeheapgraph.datastructures;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 二分木の基礎を確認するためのクラス
 *
 * <p>このクラスは今回の学習テーマの中心となる部分。
 *
 * <p>配列をレベル順の並びとみなし、左右の子を持つ二分木を自前で構築して確認できる形にしている。
 */
public class BinaryTreeBasics {

  /** 二分木の状態をまとめて返す結果クラス */
  public static class BinaryTreeSummary {
    private final Integer rootValue;
    private final int nodeCount;
    private final int height;
    private final List<String> parentChildLines;
    private final List<Integer> leafValues;
    private final List<String> depthLines;
    private final List<String> levelOrderLines;
    private final List<String> traceLines;

    public BinaryTreeSummary(
        Integer rootValue,
        int nodeCount,
        int height,
        List<String> parentChildLines,
        List<Integer> leafValues,
        List<String> depthLines,
        List<String> levelOrderLines,
        List<String> traceLines) {
      this.rootValue = rootValue;
      this.nodeCount = nodeCount;
      this.height = height;
      this.parentChildLines = parentChildLines;
      this.leafValues = leafValues;
      this.depthLines = depthLines;
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

    public List<String> getParentChildLines() {
      return parentChildLines;
    }

    public List<Integer> getLeafValues() {
      return leafValues;
    }

    public List<String> getDepthLines() {
      return depthLines;
    }

    public List<String> getLevelOrderLines() {
      return levelOrderLines;
    }

    public List<String> getTraceLines() {
      return traceLines;
    }
  }

  /**
   * 二分木ノード
   *
   * <p>左右の子をそれぞれ 1 つずつ持てる点が一般木との違い。
   */
  public static class BinaryTreeNode {
    private final int value;
    private BinaryTreeNode left;
    private BinaryTreeNode right;

    public BinaryTreeNode(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }

    public BinaryTreeNode getLeft() {
      return left;
    }

    public BinaryTreeNode getRight() {
      return right;
    }
  }

  /** 入力値から二分木を作り、表示用の情報をまとめる。 */
  public BinaryTreeSummary summarize(int[] values, boolean trace) {
    if (values.length == 0) {
      return new BinaryTreeSummary(
          null, 0, 0, List.of(), List.of(), List.of(), List.of(), List.of());
    }

    List<String> traceLines = new ArrayList<>();
    BinaryTreeNode root = buildBinaryTree(values, traceLines);

    List<String> parentChildLines = new ArrayList<>();
    collectParentChildLines(root, parentChildLines);

    List<Integer> leafValues = new ArrayList<>();
    collectLeafValues(root, leafValues);

    List<String> depthLines = new ArrayList<>();
    collectDepthLines(root, 0, depthLines);

    List<String> levelOrderLines = collectLevelOrderLines(root);
    int height = calculateHeight(root);

    return new BinaryTreeSummary(
        root.getValue(),
        values.length,
        height,
        parentChildLines,
        leafValues,
        depthLines,
        levelOrderLines,
        trace ? traceLines : List.of());
  }

  /**
   * 配列をレベル順の並びとして二分木を構築する。
   *
   * <p>index から left=2*i+1, right=2*i+2 を求める形にしている。
   *
   * <p>ここは二分木を分かりやすく再現するための学習用の作り方。
   */
  private BinaryTreeNode buildBinaryTree(int[] values, List<String> traceLines) {
    BinaryTreeNode[] nodes = new BinaryTreeNode[values.length];
    for (int i = 0; i < values.length; i++) {
      nodes[i] = new BinaryTreeNode(values[i]);
    }

    BinaryTreeNode root = nodes[0];
    traceLines.add("root を作成: value=" + root.getValue());

    for (int parentIndex = 0; parentIndex < nodes.length; parentIndex++) {
      BinaryTreeNode parent = nodes[parentIndex];
      int leftChildIndex = parentIndex * 2 + 1;
      int rightChildIndex = parentIndex * 2 + 2;

      if (leftChildIndex < nodes.length) {
        parent.left = nodes[leftChildIndex];
        traceLines.add("左の子を接続: parent=" + parent.getValue() + ", child=" + parent.left.getValue());
      }
      if (rightChildIndex < nodes.length) {
        parent.right = nodes[rightChildIndex];
        traceLines.add(
            "右の子を接続: parent=" + parent.getValue() + ", child=" + parent.right.getValue());
      }
    }

    return root;
  }

  /** 親子関係を文字列へ整形する。 */
  private void collectParentChildLines(BinaryTreeNode node, List<String> lines) {
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

  /** 葉ノードを集める。 */
  private void collectLeafValues(BinaryTreeNode node, List<Integer> values) {
    if (node == null) {
      return;
    }
    if (node.getLeft() == null && node.getRight() == null) {
      values.add(node.getValue());
      return;
    }

    collectLeafValues(node.getLeft(), values);
    collectLeafValues(node.getRight(), values);
  }

  /**
   * 各ノードの深さを集める。
   *
   * @param node 現在見ているノード
   * @param depth root から何段下かを表す深さ
   * @param lines 表示用の結果格納先
   */
  private void collectDepthLines(BinaryTreeNode node, int depth, List<String> lines) {
    if (node == null) {
      return;
    }

    lines.add(node.getValue() + " : depth=" + depth);
    collectDepthLines(node.getLeft(), depth + 1, lines);
    collectDepthLines(node.getRight(), depth + 1, lines);
  }

  /** 二分木の高さを求める。 */
  private int calculateHeight(BinaryTreeNode node) {
    if (node == null) {
      return -1;
    }
    return Math.max(calculateHeight(node.getLeft()), calculateHeight(node.getRight())) + 1;
  }

  /** レベル順の並びを集める。 */
  private List<String> collectLevelOrderLines(BinaryTreeNode root) {
    List<String> lines = new ArrayList<>();
    Queue<BinaryTreeNode> queue = new ArrayDeque<>();
    queue.add(root);

    int level = 0;
    while (!queue.isEmpty()) {
      int levelSize = queue.size();
      List<Integer> values = new ArrayList<>();

      for (int i = 0; i < levelSize; i++) {
        BinaryTreeNode current = queue.poll();
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
}
