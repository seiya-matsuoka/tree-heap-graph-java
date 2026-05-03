package io.github.seiya_matsuoka.treeheapgraph.runner;

import io.github.seiya_matsuoka.treeheapgraph.RunnerOptions;
import io.github.seiya_matsuoka.treeheapgraph.algorithms.GraphBasics;
import java.util.ArrayList;
import java.util.List;

/**
 * グラフの基礎を実行する runner
 *
 * <p>このクラスは、入力データの決定、trace 表示、結果表示など、 「実際に学習用デモとして動かすための周辺処理」を担当する。
 *
 * <p>グラフそのものの本体ロジックは {@link GraphBasics} に置いている。
 */
public class GraphBasicsRunner implements TopicRunner {
  private final GraphBasics graphBasics = new GraphBasics();

  @Override
  public void run(RunnerOptions options) {
    ResolvedGraph graph = resolveGraph(options);

    System.out.println("=== グラフの基礎 ===");
    System.out.println("入力辺: " + formatEdges(graph.edges));
    if (options.getTarget() != null) {
      System.out.println("--target は graph-basics では使用しないため無視する");
    }
    System.out.println();

    long start = System.nanoTime();
    GraphBasics.GraphSummary summary =
        graphBasics.summarize(graph.vertexCount, graph.edges, options.isTrace());
    long elapsed = System.nanoTime() - start;

    if (options.isTrace() && !summary.getTraceLines().isEmpty()) {
      System.out.println("途中経過:");
      for (String line : summary.getTraceLines()) {
        System.out.println("- " + line);
      }
      System.out.println();
    }

    System.out.println("基本情報:");
    System.out.println("- 頂点数: " + summary.getVertexCount());
    System.out.println("- 辺数: " + summary.getEdgeCount());
    System.out.println("- 参考実行時間(ns): " + elapsed);
    System.out.println();

    System.out.println("隣接リスト:");
    for (String line : summary.getAdjacencyLines()) {
      System.out.println("- " + line);
    }
    System.out.println();

    System.out.println("各頂点の次数:");
    for (String line : summary.getDegreeLines()) {
      System.out.println("- " + line);
    }
  }

  private ResolvedGraph resolveGraph(RunnerOptions options) {
    if (options.getInput() != null && !options.getInput().isBlank()) {
      int[][] edges = parseEdges(options.getInput());
      return new ResolvedGraph(resolveVertexCount(edges), edges);
    }

    if (options.getSize() != null) {
      int actualSize = Math.max(2, options.getSize());
      return new ResolvedGraph(actualSize, createGeneratedEdges(actualSize));
    }

    int[][] defaultEdges = {
      {0, 1},
      {0, 2},
      {1, 3},
      {2, 3},
      {4, 5}
    };
    return new ResolvedGraph(6, defaultEdges);
  }

  /** 0-1,0-2,1-3 のような文字列を辺配列へ変換する。 */
  private int[][] parseEdges(String raw) {
    String[] edgeTokens = raw.split(",");
    List<int[]> edges = new ArrayList<>();

    for (String edgeToken : edgeTokens) {
      String token = edgeToken.trim();
      if (token.isBlank()) {
        continue;
      }

      String[] pair = token.split("-");
      if (pair.length != 2) {
        throw new IllegalArgumentException("辺の形式は 0-1,1-2 のように指定する。入力値: " + raw);
      }

      int from = Integer.parseInt(pair[0].trim());
      int to = Integer.parseInt(pair[1].trim());
      edges.add(new int[] {from, to});
    }

    return edges.toArray(new int[0][]);
  }

  /**
   * size 指定時の入力辺を作る。
   *
   * <p>完全二分木に近い形の辺を作り、連結なグラフとして扱えるようにする。
   */
  private int[][] createGeneratedEdges(int vertexCount) {
    List<int[]> edges = new ArrayList<>();
    for (int parent = 0; parent < vertexCount; parent++) {
      int leftChild = parent * 2 + 1;
      int rightChild = parent * 2 + 2;

      if (leftChild < vertexCount) {
        edges.add(new int[] {parent, leftChild});
      }
      if (rightChild < vertexCount) {
        edges.add(new int[] {parent, rightChild});
      }
    }
    return edges.toArray(new int[0][]);
  }

  private int resolveVertexCount(int[][] edges) {
    int maxVertex = 0;
    for (int[] edge : edges) {
      maxVertex = Math.max(maxVertex, Math.max(edge[0], edge[1]));
    }
    return maxVertex + 1;
  }

  private String formatEdges(int[][] edges) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < edges.length; i++) {
      if (i > 0) {
        builder.append(", ");
      }
      builder.append(edges[i][0]).append("-").append(edges[i][1]);
    }
    return builder.toString();
  }

  /** runner 内で扱うグラフ入力のまとまり */
  private static class ResolvedGraph {
    private final int vertexCount;
    private final int[][] edges;

    private ResolvedGraph(int vertexCount, int[][] edges) {
      this.vertexCount = vertexCount;
      this.edges = edges;
    }
  }
}
