# 木の走査

## 1. 概要

木の走査は、木構造を一定の順序でたどり、ノードを順番に見る処理。  
木では一直線ではなく左右や子へ分岐するため、**どの順で訪問するか** を決める必要がある。

今回のトピックでは、次の内容を確認する。

- 前順走査
- 中順走査
- 後順走査
- 木に対する DFS
- 木に対する BFS
- レベル順の並び

## 2. 基本の動き

木の走査では、同じ木でも訪問順が異なる。  
どの順でノードを見るかによって、得られる並びが変わる。

このトピックでは、次の流れで木の走査を確認する。

1. 入力値から二分木を作る
2. 前順 / 中順 / 後順の違いを確認する
3. DFS を通して深く進む流れを確認する
4. BFS を通して上の段から順に見る流れを確認する
5. レベル順の並びと BFS の関係を確認する

## 3. 処理の流れ

このトピックでは、次の順で走査の結果を確認する。

1. 入力値から二分木を構築する
2. 親子関係を整理する
3. 前順走査を行う
4. 中順走査を行う
5. 後順走査を行う
6. DFS を行う
7. BFS を行う
8. レベル順の並びを確認する

trace あり実行では、どのノードをどの順で記録しているかを確認できる。  
そのため、走査順ごとの違いを出力から追える。

## 4. 計算量

### 前順 / 中順 / 後順走査

- 時間計算量: O(n)
- 空間計算量: O(n)

### DFS

- 時間計算量: O(n)
- 空間計算量: O(n)

### BFS

- 時間計算量: O(n)
- 空間計算量: O(n)

ノード数を n としたとき、基本的にはどの走査も各ノードを一度ずつ訪問する。

## 5. メリット / デメリット

### メリット

- 木全体を体系的にたどれる
- 訪問順の違いによって用途を分けられる
- DFS / BFS の考え方はグラフにもつながる

### デメリット

- 配列や連結リストより順序の種類が多く、最初は混乱しやすい
- どの順序で見るかを意識しないと、出力の意味が分かりづらい
- BFS ではキューが必要になる

## 6. Java実装のポイント

- ノードは `TraversalNode` クラスで表現する
- 前順 / 中順 / 後順は再帰で実装する
- DFS は今回、前順走査と同じ訪問順で確認する
- BFS は `Queue` を使ってレベル順にたどる
- runner 側では入力決定と結果表示を担当し、学習本体は `algorithms/` に寄せる

## 7. コアとなる処理・重要なコードの見どころ

木の走査で重要なのは、**どのタイミングで現在ノードを記録するか** と、**DFS / BFS の進み方の違い**。

### 前順走査

前順走査では、自分、左部分木、右部分木の順でたどる。

```java
values.add(node.getValue());
collectPreorderValues(node.getLeft(), values, traceLines);
collectPreorderValues(node.getRight(), values, traceLines);
```

今見ているノードを先に記録するため、ルートから順に深くたどる流れが見えやすい。

### 中順走査

中順走査では、左部分木、自分、右部分木の順でたどる。

```java
collectInorderValues(node.getLeft(), values, traceLines);
values.add(node.getValue());
collectInorderValues(node.getRight(), values, traceLines);
```

記録する位置が前順と違うため、同じ木でも並びが変わる。

### 後順走査

後順走査では、左部分木、右部分木、自分の順でたどる。

```java
collectPostorderValues(node.getLeft(), values, traceLines);
collectPostorderValues(node.getRight(), values, traceLines);
values.add(node.getValue());
```

子を先に見た後で自分を処理するため、部分木の処理結果を使いたい場面と相性がよい。

### BFS

BFS ではキューを使い、上の段から順にノードを見る。

```java
Queue<TraversalNode> queue = new ArrayDeque<>();
queue.add(root);
```

ここで見たいのは、DFS が深く進むのに対し、BFS は同じ深さのノードを先に見る点。

## 8. 実行例

### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic tree-traversal --trace
```

### 任意入力

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic tree-traversal --input 10,20,30,40,50,60,70 --trace
```

### size 指定

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic tree-traversal --size 10
```

### 出力で確認する内容

- 入力値
- ルート
- ノード数
- 高さ
- 親子関係
- 前順走査
- 中順走査
- 後順走査
- DFS
- BFS
- レベル順の並び
- trace 時の訪問順

## 9. 関連トピック

- 二分木
- 二分探索木
- グラフの基礎
- グラフに対する DFS / BFS

二分木の形を理解しておくと、木の走査順の違いを追いやすくなる。  
また、木に対する DFS / BFS の考え方は、後で学ぶグラフ探索にもそのままつながる。
