package io.github.seiya_matsuoka.treeheapgraph.algorithms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 木の走査と木に対する DFS / BFS を確認するためのクラス
 *
 * <p>このクラスは今回の学習テーマの中心となる部分。
 *
 * <p>二分木を自前で組み立て、前順 / 中順 / 後順 / DFS / BFS の流れを確認できる形にしている。
 */
public class TreeTraversalBasics {

  /** 走査結果をまとめて返す結果クラス */
  public static class TreeTraversalSummary {
    private final Integer rootValue;
    private final int nodeCount;
    private final int height;
    private final List<String> parentChildLines;
    private final List<Integer> preorderValues;
    private final List<Integer> inorderValues;
    private final List<Integer> postorderValues;
    private final List<Integer> dfsValues;
    private final List<Integer> bfsValues;
    private final List<String> levelOrderLines;
    private final List<String> traceLines;

    public TreeTraversalSummary(
        Integer rootValue,
        int nodeCount,
        int height,
        List<String> parentChildLines,
        List<Integer> preorderValues,
        List<Integer> inorderValues,
        List<Integer> postorderValues,
        List<Integer> dfsValues,
        List<Integer> bfsValues,
        List<String> levelOrderLines,
        List<String> traceLines) {
      this.rootValue = rootValue;
      this.nodeCount = nodeCount;
      this.height = height;
      this.parentChildLines = parentChildLines;
      this.preorderValues = preorderValues;
      this.inorderValues = inorderValues;
      this.postorderValues = postorderValues;
      this.dfsValues = dfsValues;
      this.bfsValues = bfsValues;
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

    public List<Integer> getPreorderValues() {
      return preorderValues;
    }

    public List<Integer> getInorderValues() {
      return inorderValues;
    }

    public List<Integer> getPostorderValues() {
      return postorderValues;
    }

    public List<Integer> getDfsValues() {
      return dfsValues;
    }

    public List<Integer> getBfsValues() {
      return bfsValues;
    }

    public List<String> getLevelOrderLines() {
      return levelOrderLines;
    }

    public List<String> getTraceLines() {
      return traceLines;
    }
  }

  /** 二分木ノード */
  public static class TraversalNode {
    private final int value;
    private TraversalNode left;
    private TraversalNode right;

    public TraversalNode(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }

    public TraversalNode getLeft() {
      return left;
    }

    public TraversalNode getRight() {
      return right;
    }
  }

  /** 入力値から二分木を作り、走査結果をまとめる。 */
  public TreeTraversalSummary summarize(int[] values, boolean trace) {
    if (values.length == 0) {
      return new TreeTraversalSummary(
          null, 0, 0, List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), List.of(),
          List.of());
    }

    List<String> traceLines = new ArrayList<>();
    TraversalNode root = buildBinaryTree(values, traceLines);

    List<String> parentChildLines = new ArrayList<>();
    collectParentChildLines(root, parentChildLines);

    List<Integer> preorderValues = new ArrayList<>();
    collectPreorderValues(root, preorderValues, trace ? traceLines : null);

    List<Integer> inorderValues = new ArrayList<>();
    collectInorderValues(root, inorderValues, null);

    List<Integer> postorderValues = new ArrayList<>();
    collectPostorderValues(root, postorderValues, null);

    List<Integer> dfsValues = new ArrayList<>();
    collectDfsValues(root, dfsValues, trace ? traceLines : null);

    List<Integer> bfsValues = collectBfsValues(root, trace ? traceLines : null);
    List<String> levelOrderLines = collectLevelOrderLines(root);
    int height = calculateHeight(root);

    return new TreeTraversalSummary(
        root.getValue(),
        values.length,
        height,
        parentChildLines,
        preorderValues,
        inorderValues,
        postorderValues,
        dfsValues,
        bfsValues,
        levelOrderLines,
        trace ? traceLines : List.of());
  }

  /** 配列をレベル順の並びとして二分木を構築する。 */
  private TraversalNode buildBinaryTree(int[] values, List<String> traceLines) {
    TraversalNode[] nodes = new TraversalNode[values.length];
    for (int i = 0; i < values.length; i++) {
      nodes[i] = new TraversalNode(values[i]);
    }

    TraversalNode root = nodes[0];
    traceLines.add("root を作成: value=" + root.getValue());

    for (int parentIndex = 0; parentIndex < nodes.length; parentIndex++) {
      TraversalNode parent = nodes[parentIndex];
      int leftChildIndex = parentIndex * 2 + 1;
      int rightChildIndex = parentIndex * 2 + 2;

      if (leftChildIndex < nodes.length) {
        parent.left = nodes[leftChildIndex];
        traceLines.add("左の子を接続: parent=" + parent.value + ", child=" + parent.left.value);
      }
      if (rightChildIndex < nodes.length) {
        parent.right = nodes[rightChildIndex];
        traceLines.add("右の子を接続: parent=" + parent.value + ", child=" + parent.right.value);
      }
    }

    return root;
  }

  /** 親子関係を文字列へ整形する。 */
  private void collectParentChildLines(TraversalNode node, List<String> lines) {
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
   * 前順走査を行う。
   *
   * @param node 現在見ているノード
   * @param values 走査結果の格納先
   * @param traceLines trace 出力格納先。不要な場合は null
   */
  private void collectPreorderValues(
      TraversalNode node, List<Integer> values, List<String> traceLines) {
    if (node == null) {
      return;
    }

    values.add(node.getValue());
    if (traceLines != null) {
      traceLines.add("前順走査: node=" + node.getValue() + " を先に記録");
    }
    collectPreorderValues(node.getLeft(), values, traceLines);
    collectPreorderValues(node.getRight(), values, traceLines);
  }

  /** 中順走査を行う。 */
  private void collectInorderValues(
      TraversalNode node, List<Integer> values, List<String> traceLines) {
    if (node == null) {
      return;
    }

    collectInorderValues(node.getLeft(), values, traceLines);
    values.add(node.getValue());
    if (traceLines != null) {
      traceLines.add("中順走査: 左部分木の後で node=" + node.getValue() + " を記録");
    }
    collectInorderValues(node.getRight(), values, traceLines);
  }

  /** 後順走査を行う。 */
  private void collectPostorderValues(
      TraversalNode node, List<Integer> values, List<String> traceLines) {
    if (node == null) {
      return;
    }

    collectPostorderValues(node.getLeft(), values, traceLines);
    collectPostorderValues(node.getRight(), values, traceLines);
    values.add(node.getValue());
    if (traceLines != null) {
      traceLines.add("後順走査: 子を見た後で node=" + node.getValue() + " を記録");
    }
  }

  /**
   * 木に対する DFS を行う。
   *
   * <p>今回は再帰版の前順走査と同じ訪問順になる。
   */
  private void collectDfsValues(TraversalNode node, List<Integer> values, List<String> traceLines) {
    if (node == null) {
      return;
    }

    values.add(node.getValue());
    if (traceLines != null) {
      traceLines.add("DFS: node=" + node.getValue() + " を訪問");
    }
    collectDfsValues(node.getLeft(), values, traceLines);
    collectDfsValues(node.getRight(), values, traceLines);
  }

  /** 木に対する BFS を行う。 */
  private List<Integer> collectBfsValues(TraversalNode root, List<String> traceLines) {
    List<Integer> values = new ArrayList<>();
    Queue<TraversalNode> queue = new ArrayDeque<>();
    queue.add(root);

    while (!queue.isEmpty()) {
      TraversalNode current = queue.poll();
      values.add(current.getValue());
      if (traceLines != null) {
        traceLines.add("BFS: node=" + current.getValue() + " を取り出して訪問");
      }

      if (current.getLeft() != null) {
        queue.add(current.getLeft());
      }
      if (current.getRight() != null) {
        queue.add(current.getRight());
      }
    }

    return values;
  }

  /** レベル順の並びを集める。 */
  private List<String> collectLevelOrderLines(TraversalNode root) {
    List<String> lines = new ArrayList<>();
    Queue<TraversalNode> queue = new ArrayDeque<>();
    queue.add(root);

    int level = 0;
    while (!queue.isEmpty()) {
      int levelSize = queue.size();
      List<Integer> values = new ArrayList<>();

      for (int i = 0; i < levelSize; i++) {
        TraversalNode current = queue.poll();
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

  /** 木の高さを求める。 */
  private int calculateHeight(TraversalNode node) {
    if (node == null) {
      return -1;
    }
    return Math.max(calculateHeight(node.getLeft()), calculateHeight(node.getRight())) + 1;
  }
}
