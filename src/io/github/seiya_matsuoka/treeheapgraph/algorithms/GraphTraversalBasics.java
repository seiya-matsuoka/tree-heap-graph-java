package io.github.seiya_matsuoka.treeheapgraph.algorithms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

/**
 * グラフに対する DFS / BFS と到達可能性・連結性を確認するためのクラス
 *
 * <p>このクラスは今回の学習テーマの中心となる部分。
 *
 * <p>隣接リストを使ってグラフを表現し、DFS / BFS の訪問順、 target へ到達できるか、グラフ全体が連結かを確認できる形にしている。
 */
public class GraphTraversalBasics {

  /** グラフ走査の確認結果をまとめて返す結果クラス */
  public static class GraphTraversalSummary {
    private final int vertexCount;
    private final int edgeCount;
    private final List<Integer> dfsOrder;
    private final List<Integer> bfsOrder;
    private final String reachabilityLine;
    private final String connectivityLine;
    private final List<String> componentLines;
    private final List<String> traceLines;

    public GraphTraversalSummary(
        int vertexCount,
        int edgeCount,
        List<Integer> dfsOrder,
        List<Integer> bfsOrder,
        String reachabilityLine,
        String connectivityLine,
        List<String> componentLines,
        List<String> traceLines) {
      this.vertexCount = vertexCount;
      this.edgeCount = edgeCount;
      this.dfsOrder = dfsOrder;
      this.bfsOrder = bfsOrder;
      this.reachabilityLine = reachabilityLine;
      this.connectivityLine = connectivityLine;
      this.componentLines = componentLines;
      this.traceLines = traceLines;
    }

    public int getVertexCount() {
      return vertexCount;
    }

    public int getEdgeCount() {
      return edgeCount;
    }

    public List<Integer> getDfsOrder() {
      return dfsOrder;
    }

    public List<Integer> getBfsOrder() {
      return bfsOrder;
    }

    public String getReachabilityLine() {
      return reachabilityLine;
    }

    public String getConnectivityLine() {
      return connectivityLine;
    }

    public List<String> getComponentLines() {
      return componentLines;
    }

    public List<String> getTraceLines() {
      return traceLines;
    }
  }

  /** グラフを構築し、DFS / BFS と到達可能性・連結性をまとめる。 */
  public GraphTraversalSummary summarize(
      int vertexCount, int[][] edges, int startVertex, int targetVertex, boolean trace) {
    List<String> traceLines = new ArrayList<>();
    List<List<Integer>> adjacencyList =
        buildAdjacencyList(vertexCount, edges, trace ? traceLines : null);

    List<Integer> dfsOrder = new ArrayList<>();
    boolean[] dfsVisited = new boolean[vertexCount];
    dfs(startVertex, adjacencyList, dfsVisited, dfsOrder, trace ? traceLines : null);

    List<Integer> bfsOrder = bfs(startVertex, adjacencyList, trace ? traceLines : null);

    boolean reachable = bfsOrder.contains(targetVertex);
    String reachabilityLine =
        startVertex + " から " + targetVertex + " へ " + (reachable ? "到達できる" : "到達できない");

    List<String> componentLines = new ArrayList<>();
    int componentCount =
        collectComponents(adjacencyList, componentLines, trace ? traceLines : null);
    String connectivityLine =
        componentCount == 1 ? "このグラフは連結" : "このグラフは非連結（連結成分数=" + componentCount + "）";

    return new GraphTraversalSummary(
        vertexCount,
        edges.length,
        dfsOrder,
        bfsOrder,
        reachabilityLine,
        connectivityLine,
        componentLines,
        trace ? traceLines : List.of());
  }

  /**
   * 隣接リストを構築する。
   *
   * <p>ここが学習本体の準備部分。無向グラフとして両方向へ辺を追加する。
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
      }
    }

    return adjacencyList;
  }

  /**
   * 深さ優先探索を行う。
   *
   * @param currentVertex 今見ている頂点
   * @param adjacencyList 隣接リスト
   * @param visited 訪問済み管理配列
   * @param order 訪問順の記録先
   * @param traceLines trace 出力用の記録先
   */
  private void dfs(
      int currentVertex,
      List<List<Integer>> adjacencyList,
      boolean[] visited,
      List<Integer> order,
      List<String> traceLines) {
    visited[currentVertex] = true;
    order.add(currentVertex);

    if (traceLines != null) {
      traceLines.add("DFS で頂点を訪問: " + currentVertex);
    }

    for (int nextVertex : adjacencyList.get(currentVertex)) {
      if (visited[nextVertex]) {
        if (traceLines != null) {
          traceLines.add("DFS で確認: " + currentVertex + " -> " + nextVertex + " は訪問済みのためスキップ");
        }
        continue;
      }

      if (traceLines != null) {
        traceLines.add("DFS で次の頂点へ進む: " + currentVertex + " -> " + nextVertex);
      }
      dfs(nextVertex, adjacencyList, visited, order, traceLines);
    }
  }

  /** 幅優先探索を行う。 */
  private List<Integer> bfs(
      int startVertex, List<List<Integer>> adjacencyList, List<String> traceLines) {
    List<Integer> order = new ArrayList<>();
    boolean[] visited = new boolean[adjacencyList.size()];
    Queue<Integer> queue = new ArrayDeque<>();

    queue.add(startVertex);
    visited[startVertex] = true;

    if (traceLines != null) {
      traceLines.add("BFS を開始: start=" + startVertex);
    }

    while (!queue.isEmpty()) {
      int currentVertex = queue.poll();
      order.add(currentVertex);

      if (traceLines != null) {
        traceLines.add("BFS で頂点を取り出し: " + currentVertex + " / queue=" + queue);
      }

      for (int nextVertex : adjacencyList.get(currentVertex)) {
        if (visited[nextVertex]) {
          if (traceLines != null) {
            traceLines.add("BFS で確認: " + currentVertex + " -> " + nextVertex + " は訪問済みのためスキップ");
          }
          continue;
        }

        visited[nextVertex] = true;
        queue.add(nextVertex);
        if (traceLines != null) {
          traceLines.add("BFS でキューへ追加: " + nextVertex + " / queue=" + queue);
        }
      }
    }

    return order;
  }

  /** 連結成分を集める。 */
  private int collectComponents(
      List<List<Integer>> adjacencyList, List<String> componentLines, List<String> traceLines) {
    boolean[] visited = new boolean[adjacencyList.size()];
    int componentCount = 0;

    for (int startVertex = 0; startVertex < adjacencyList.size(); startVertex++) {
      if (visited[startVertex]) {
        continue;
      }

      componentCount++;
      List<Integer> component = new ArrayList<>();
      Deque<Integer> stack = new ArrayDeque<>();
      stack.push(startVertex);
      visited[startVertex] = true;

      if (traceLines != null) {
        traceLines.add("連結成分の探索開始: start=" + startVertex);
      }

      while (!stack.isEmpty()) {
        int currentVertex = stack.pop();
        component.add(currentVertex);

        for (int nextVertex : adjacencyList.get(currentVertex)) {
          if (visited[nextVertex]) {
            continue;
          }
          visited[nextVertex] = true;
          stack.push(nextVertex);
        }
      }

      componentLines.add("component " + componentCount + " : " + component);
    }

    return componentCount;
  }
}
