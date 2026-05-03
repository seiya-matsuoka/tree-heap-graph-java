package io.github.seiya_matsuoka.treeheapgraph.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * グラフの基礎を確認するためのクラス
 *
 * <p>このクラスは今回の学習テーマの中心となる部分。
 *
 * <p>頂点と辺から隣接リストを作り、各頂点のつながり方や次数を確認できる形にしている。
 */
public class GraphBasics {

  /** グラフの確認結果をまとめて返す結果クラス */
  public static class GraphSummary {
    private final int vertexCount;
    private final int edgeCount;
    private final List<String> adjacencyLines;
    private final List<String> degreeLines;
    private final List<String> traceLines;

    public GraphSummary(
        int vertexCount,
        int edgeCount,
        List<String> adjacencyLines,
        List<String> degreeLines,
        List<String> traceLines) {
      this.vertexCount = vertexCount;
      this.edgeCount = edgeCount;
      this.adjacencyLines = adjacencyLines;
      this.degreeLines = degreeLines;
      this.traceLines = traceLines;
    }

    public int getVertexCount() {
      return vertexCount;
    }

    public int getEdgeCount() {
      return edgeCount;
    }

    public List<String> getAdjacencyLines() {
      return adjacencyLines;
    }

    public List<String> getDegreeLines() {
      return degreeLines;
    }

    public List<String> getTraceLines() {
      return traceLines;
    }
  }

  /** グラフを構築し、隣接リストと次数をまとめる。 */
  public GraphSummary summarize(int vertexCount, int[][] edges, boolean trace) {
    List<String> traceLines = new ArrayList<>();
    List<List<Integer>> adjacencyList =
        buildAdjacencyList(vertexCount, edges, trace ? traceLines : null);

    List<String> adjacencyLines = new ArrayList<>();
    collectAdjacencyLines(adjacencyList, adjacencyLines);

    List<String> degreeLines = new ArrayList<>();
    collectDegreeLines(adjacencyList, degreeLines);

    return new GraphSummary(
        vertexCount, edges.length, adjacencyLines, degreeLines, trace ? traceLines : List.of());
  }

  /**
   * 隣接リストを構築する。
   *
   * <p>ここが今回の学習本体の中心。
   *
   * <p>辺を 1 本ずつ読み取り、 無向グラフとして両方向へ接続している。
   */
  private List<List<Integer>> buildAdjacencyList(
      int vertexCount, int[][] edges, List<String> traceLines) {
    List<List<Integer>> adjacencyList = new ArrayList<>();
    for (int vertex = 0; vertex < vertexCount; vertex++) {
      adjacencyList.add(new ArrayList<>());
    }

    for (int[] edge : edges) {
      int fromVertex = edge[0];
      int toVertex = edge[1];

      adjacencyList.get(fromVertex).add(toVertex);
      adjacencyList.get(toVertex).add(fromVertex);

      if (traceLines != null) {
        traceLines.add("辺を追加: " + fromVertex + " - " + toVertex);
        traceLines.add("  " + fromVertex + " の隣接頂点: " + adjacencyList.get(fromVertex));
        traceLines.add("  " + toVertex + " の隣接頂点: " + adjacencyList.get(toVertex));
      }
    }

    return adjacencyList;
  }

  /** 各頂点の隣接リストを表示用の文字列へ変換する。 */
  private void collectAdjacencyLines(List<List<Integer>> adjacencyList, List<String> lines) {
    for (int vertex = 0; vertex < adjacencyList.size(); vertex++) {
      lines.add(vertex + " -> " + adjacencyList.get(vertex));
    }
  }

  /** 各頂点の次数を表示用の文字列へ変換する。 */
  private void collectDegreeLines(List<List<Integer>> adjacencyList, List<String> lines) {
    for (int vertex = 0; vertex < adjacencyList.size(); vertex++) {
      lines.add(vertex + " : degree=" + adjacencyList.get(vertex).size());
    }
  }
}
