package io.github.seiya_matsuoka.treeheapgraph.datastructures;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * 木の基礎を確認するためのクラス
 *
 * <p>このクラスは今回の学習テーマの中心となる部分。
 *
 * <p>一般木を自前で組み立て、親子関係、葉、深さ、高さ、レベル順の並びを確認できる形にしている。
 */
public class TreeBasics {

  /**
   * 木の状態をまとめて返す結果クラス
   *
   * <p>runner 側で表示しやすくするための補助データ。
   */
  public static class TreeSummary {
    private final Integer rootValue;
    private final int nodeCount;
    private final int height;
    private final List<String> parentChildLines;
    private final List<Integer> leafValues;
    private final List<String> depthLines;
    private final List<String> levelOrderLines;
    private final List<String> traceLines;

    public TreeSummary(
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
   * 一般木のノード
   *
   * <p>今回の学習では、1つのノードが複数の子を持てる形を確認したい。
   *
   * <p>そのため、子ノード一覧を List で持つ。
   */
  public static class TreeNode {
    private final int value;
    private TreeNode parent;
    private final List<TreeNode> children = new ArrayList<>();

    public TreeNode(int value) {
      this.value = value;
    }

    /**
     * 子ノードを追加する。
     *
     * <p>ここが木構造の基本となる接続処理。
     *
     * <p>親側の children に追加し、子側には parent を持たせる。
     */
    public void addChild(TreeNode child) {
      child.parent = this;
      children.add(child);
    }

    public int getValue() {
      return value;
    }

    public TreeNode getParent() {
      return parent;
    }

    public List<TreeNode> getChildren() {
      return children;
    }
  }

  /** 入力値から一般木を作り、表示用の情報をまとめる。 */
  public TreeSummary summarize(int[] values, boolean trace) {
    if (values.length == 0) {
      return new TreeSummary(null, 0, 0, List.of(), List.of(), List.of(), List.of(), List.of());
    }

    List<String> traceLines = new ArrayList<>();
    TreeNode root = buildTree(values, traceLines);

    List<String> parentChildLines = new ArrayList<>();
    collectParentChildLines(root, parentChildLines);

    List<Integer> leafValues = new ArrayList<>();
    collectLeafValues(root, leafValues);

    List<String> depthLines = new ArrayList<>();
    collectDepthLines(root, 0, depthLines);

    List<String> levelOrderLines = collectLevelOrderLines(root);
    int height = calculateHeight(root);

    return new TreeSummary(
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
   * 入力配列から一般木を構築する。
   *
   * <p>今回の学習では、一般木の形を作るため、1つの親に最大 3 子まで付けるルールで構築する。
   *
   * <p>ここは木の概念を確認するための再現方法であり、一般木の唯一の作り方ではない。
   */
  private TreeNode buildTree(int[] values, List<String> traceLines) {
    List<TreeNode> nodes = new ArrayList<>();
    for (int value : values) {
      nodes.add(new TreeNode(value));
    }

    TreeNode root = nodes.get(0);
    traceLines.add("root を作成: value=" + root.getValue());

    for (int childIndex = 1; childIndex < nodes.size(); childIndex++) {
      // 親ノードの位置を決める補助ルール。
      // childIndex 1,2,3 は root の子、4,5,6 は 2 番目のノードの子、という形になる。
      int parentIndex = (childIndex - 1) / 3;
      TreeNode parent = nodes.get(parentIndex);
      TreeNode child = nodes.get(childIndex);

      // ここが学習本体の接続処理。
      parent.addChild(child);
      traceLines.add("親子を接続: parent=" + parent.getValue() + ", child=" + child.getValue());
    }

    return root;
  }

  /** 親子関係を文字列へ整形する。 */
  private void collectParentChildLines(TreeNode node, List<String> lines) {
    List<TreeNode> children = node.getChildren();
    if (children.isEmpty()) {
      lines.add(node.getValue() + " -> []");
      return;
    }

    List<Integer> childValues = new ArrayList<>();
    for (TreeNode child : children) {
      childValues.add(child.getValue());
    }
    lines.add(node.getValue() + " -> " + childValues);

    for (TreeNode child : children) {
      collectParentChildLines(child, lines);
    }
  }

  /**
   * 葉ノードを集める。
   *
   * <p>子を持たないノードが葉となる。
   */
  private void collectLeafValues(TreeNode node, List<Integer> values) {
    if (node.getChildren().isEmpty()) {
      values.add(node.getValue());
      return;
    }

    for (TreeNode child : node.getChildren()) {
      collectLeafValues(child, values);
    }
  }

  /**
   * 各ノードの深さを集める。
   *
   * @param node 現在見ているノード
   * @param depth root から何段下かを表す深さ
   * @param lines 表示用の結果格納先
   */
  private void collectDepthLines(TreeNode node, int depth, List<String> lines) {
    lines.add(node.getValue() + " : depth=" + depth);
    for (TreeNode child : node.getChildren()) {
      collectDepthLines(child, depth + 1, lines);
    }
  }

  /**
   * 木の高さを求める。
   *
   * <p>高さは、そのノードから一番深い葉までの段数。
   */
  private int calculateHeight(TreeNode node) {
    if (node.getChildren().isEmpty()) {
      return 0;
    }

    int maxChildHeight = 0;
    for (TreeNode child : node.getChildren()) {
      maxChildHeight = Math.max(maxChildHeight, calculateHeight(child));
    }
    return maxChildHeight + 1;
  }

  /**
   * レベル順の並びを集める。
   *
   * <p>ここではキューを使い、幅優先に各深さのノードを確認する。
   */
  private List<String> collectLevelOrderLines(TreeNode root) {
    List<String> lines = new ArrayList<>();
    Queue<TreeNode> queue = new ArrayDeque<>();
    Map<TreeNode, Integer> depthMap = new HashMap<>();

    queue.add(root);
    depthMap.put(root, 0);

    while (!queue.isEmpty()) {
      int currentLevel = depthMap.get(queue.peek());
      int levelSize = queue.size();
      List<Integer> values = new ArrayList<>();

      for (int i = 0; i < levelSize; i++) {
        TreeNode current = queue.poll();
        values.add(current.getValue());

        for (TreeNode child : current.getChildren()) {
          queue.add(child);
          depthMap.put(child, currentLevel + 1);
        }
      }

      lines.add("level " + currentLevel + ": " + values);
    }

    return lines;
  }
}
