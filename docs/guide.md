# tree-heap-graph-java ガイド

## 1. このリポジトリで学ぶこと

このリポジトリでは、木・ヒープ・グラフの基礎を扱う。

対象トピックは次の8つ。

- 木の基礎
- 二分木
- 二分探索木
- 木の走査
- ヒープ
- PriorityQueue
- グラフの基礎
- グラフに対する DFS / BFS + 到達可能性・連結性

ここでの目的は、**木構造をどのように表現するか・木をどのようにたどるか・ヒープをどのように使うか・グラフをどのように表現して探索するか** を、Java のコードを読みながら確認すること。

---

## 2. 学習対象トピック一覧

### 2-1. 木の基礎

- ルート、親、子、葉の考え方を確認する
- 木構造が階層を持つデータ構造であることを確認する
- 深さと高さの考え方を確認する
- ノードをたどりながら木全体を見る流れを確認する

対応ドキュメント:

- `docs/topics/tree-basics.md`

### 2-2. 二分木

- 各ノードが左の子と右の子を持てる構造を確認する
- 左右の子をたどる基本的な見方を確認する
- レベルごとの並びを確認する
- 二分木が他の木構造の土台になることを確認する

対応ドキュメント:

- `docs/topics/binary-tree.md`

### 2-3. 二分探索木

- 値の大小関係で左部分木と右部分木が分かれることを確認する
- 挿入時にどちらへ進むかを確認する
- 探索時に比較しながら木を下る流れを確認する
- 中順走査で昇順になることを確認する

対応ドキュメント:

- `docs/topics/binary-search-tree.md`

### 2-4. 木の走査

- 前順走査を確認する
- 中順走査を確認する
- 後順走査を確認する
- 木に対する DFS / BFS の訪問順の違いを確認する

対応ドキュメント:

- `docs/topics/tree-traversal.md`

### 2-5. ヒープ

- 完全二分木としてのヒープ構造を確認する
- 親子の大小関係が保たれることを確認する
- 追加時の上方向調整を確認する
- 取り出し時の下方向調整を確認する

対応ドキュメント:

- `docs/topics/heap.md`

### 2-6. PriorityQueue

- 優先度付きで値を取り出す考え方を確認する
- 最小優先キューと最大優先キューの違いを確認する
- 追加順ではなく優先度順で取り出されることを確認する
- 標準ライブラリでヒープ構造を利用できることを確認する

対応ドキュメント:

- `docs/topics/priority-queue.md`

### 2-7. グラフの基礎

- 頂点と辺の考え方を確認する
- 隣接リストによる表現を確認する
- 各頂点がどの頂点とつながっているかを確認する
- 木より一般的なつながりを表現できることを確認する

対応ドキュメント:

- `docs/topics/graph-basics.md`

### 2-8. グラフに対する DFS / BFS + 到達可能性・連結性

- グラフに対する DFS を確認する
- グラフに対する BFS を確認する
- ある頂点から別の頂点へ到達できるかを確認する
- 連結成分を通して連結性を確認する

対応ドキュメント:

- `docs/topics/graph-traversal.md`

---

## 3. 推奨する学習順

このリポジトリ内では、次の順で進めるのがおすすめ。

1. 木の基礎
2. 二分木
3. 二分探索木
4. 木の走査
5. ヒープ
6. PriorityQueue
7. グラフの基礎
8. グラフに対する DFS / BFS + 到達可能性・連結性

### 理由

- まず木の基礎と二分木で、階層構造と親子関係の見方を押さえる
- 次に二分探索木で、値の比較を使って木をたどる考え方を確認する
- その後で木の走査を見て、DFS / BFS の違いを整理する
- ヒープと PriorityQueue で、木構造を使った優先度処理を確認する
- 最後にグラフへ進み、木より一般的なつながりと探索を整理する

---

## 4. ディレクトリ構成

```text
tree-heap-graph-java/
├─ src/
│  └─ io/
│     └─ github/
│        └─ seiya_matsuoka/
│           └─ treeheapgraph/
│              ├─ App.java
│              ├─ RunnerOptions.java
│              ├─ runner/
│              │  ├─ TopicRunner.java
│              │  ├─ TreeBasicsRunner.java
│              │  ├─ BinaryTreeRunner.java
│              │  ├─ BinarySearchTreeRunner.java
│              │  ├─ TreeTraversalRunner.java
│              │  ├─ HeapRunner.java
│              │  ├─ PriorityQueueRunner.java
│              │  ├─ GraphBasicsRunner.java
│              │  └─ GraphTraversalRunner.java
│              ├─ datastructures/
│              │  ├─ TreeBasics.java
│              │  ├─ BinaryTreeBasics.java
│              │  └─ HeapBasics.java
│              └─ algorithms/
│                 ├─ BinarySearchTreeBasics.java
│                 ├─ TreeTraversalBasics.java
│                 ├─ PriorityQueueBasics.java
│                 ├─ GraphBasics.java
│                 └─ GraphTraversalBasics.java
├─ docs/
│  ├─ guide.md
│  └─ topics/
│     ├─ tree-basics.md
│     ├─ binary-tree.md
│     ├─ binary-search-tree.md
│     ├─ tree-traversal.md
│     ├─ heap.md
│     ├─ priority-queue.md
│     ├─ graph-basics.md
│     └─ graph-traversal.md
└─ .gitignore
```

### 4-1. 各ファイル・ディレクトリの役割

#### `App.java`

共通エントリーポイント。  
コマンドライン引数を受け取り、指定された topic に応じて対応する runner に処理を振り分ける。

#### `RunnerOptions.java`

実行時オプション保持用クラス。  
`--topic`、`--input`、`--trace`、`--target`、`--size` の値をまとめて runner に渡す。

#### `runner/`

実行用クラス群。  
入力データの決定、trace 表示、表示用メッセージの整形など、学習用デモとして動かすための周辺処理を担当する。

#### `datastructures/`

学習テーマのうち、データ構造のコアとなる実装本体。  
木の基礎、二分木、ヒープの実装はここに置く。

#### `algorithms/`

学習テーマのうち、探索や走査のコアとなる実装本体。  
二分探索木、木の走査、PriorityQueue、グラフの基礎、グラフ探索の処理はここに置く。

#### `docs/topics/`

各学習トピックの説明ドキュメント。  
コードと対応づけながら読むことを前提とする。

---

## 5. 実行方法

このリポジトリは Gradle などのビルドツールを使わず、素の Java でコンパイル・実行する。

### 5-1. 前提

- Java 21 を使用
- VS Code では、フォルダを開いてターミナルからコンパイル・実行すればよい

### 5-2. bash / zsh でのコンパイル

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
```

### 5-3. PowerShell でのコンパイル

```powershell
$files = Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d out $files
```

### 5-4. 実行コマンドの基本形

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic <topic> [options]
```

---

## 6. 共通オプション

このリポジトリでは、次の5つの共通オプションを使う。

### 6-1. `--topic`

実行する学習トピックを指定する。

指定できる値:

- `tree-basics`
- `binary-tree`
- `binary-search-tree`
- `tree-traversal`
- `heap`
- `priority-queue`
- `graph-basics`
- `graph-traversal`

### 6-2. `--input`

入力値を直接指定する。

例:

- `--input 10,20,30,40,50,60,70`
- `--input 0-1,0-2,1-3,2-4,4-5`

### 6-3. `--trace`

処理途中の流れを表示する。

使いどころ:

- 木や二分木の構造確認
- 二分探索木の挿入と探索の流れ確認
- 木に対する DFS / BFS の訪問順確認
- ヒープの上方向調整と下方向調整の確認
- PriorityQueue の追加順と取り出し順の確認
- グラフ探索の訪問順や到達確認の流れ確認

### 6-4. `--target`

探索や到達確認で使う対象値を指定する。

使いどころ:

- 二分探索木で探索対象値を指定する
- グラフ探索で到達確認の対象頂点を指定する

不要なトピックでは、指定されていても使わずに無視する。

### 6-5. `--size`

大きい入力データを生成したい場合のサイズを指定する。

使いどころ:

- 木や二分木に大きめの連番入力を与える
- 二分探索木のノード数を増やす
- ヒープや PriorityQueue に大きめ入力を与える
- グラフの頂点数を増やした入力を生成する

---

## 7. 入力データの決まり方

各トピックで使う入力データは、次の優先順位で決定する。

1. `--input` が指定されていればそれを使う
2. `--input` がなく、`--size` が指定されていれば、そのサイズで入力を生成する
3. どちらもなければ、学習用の小さいデフォルト値を使う

### 7-1. この順にしている理由

- まずは自分で試したい入力を最優先にできる
- 入力サイズだけ変えて差を見たい場合にも対応できる
- 何も指定しなくてもすぐ実行できる

---

## 8. topic ごとの実行例

### 8-1. 木の基礎

#### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic tree-basics --trace
```

#### 入力指定

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic tree-basics --input 10,20,30,40,50,60,70 --trace
```

#### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic tree-basics --size 10
```

### 8-2. 二分木

#### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic binary-tree --trace
```

#### 入力指定

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic binary-tree --input 10,20,30,40,50,60,70 --trace
```

#### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic binary-tree --size 10
```

### 8-3. 二分探索木

#### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic binary-search-tree --trace
```

#### target 指定

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic binary-search-tree --input 40,20,60,10,30,50,70 --target 30 --trace
```

#### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic binary-search-tree --size 15 --target 70
```

### 8-4. 木の走査

#### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic tree-traversal --trace
```

#### 入力指定

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic tree-traversal --input 10,20,30,40,50,60,70 --trace
```

#### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic tree-traversal --size 10
```

### 8-5. ヒープ

#### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic heap --trace
```

#### 入力指定

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic heap --input 50,20,70,10,40,60,30 --trace
```

#### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic heap --size 10
```

### 8-6. PriorityQueue

#### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic priority-queue --trace
```

#### 入力指定

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic priority-queue --input 50,20,70,10,40,60,30 --trace
```

#### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic priority-queue --size 10
```

### 8-7. グラフの基礎

#### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic graph-basics --trace
```

#### 入力指定

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic graph-basics --input 0-1,0-2,1-3,2-4,4-5 --trace
```

#### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic graph-basics --size 10
```

### 8-8. グラフに対する DFS / BFS + 到達可能性・連結性

#### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic graph-traversal --trace
```

#### target 指定

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic graph-traversal --input 0-1,0-2,1-3,2-4,4-5 --target 5 --trace
```

#### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic graph-traversal --size 10 --target 9
```

---

## 9. 学習時の見方

### 9-1. まず見るべき場所

まずは次の順で見るのがおすすめ。

1. `docs/topics/*.md` で概要と処理の流れを読む
2. `runner/` で入力決定や表示の流れを確認する
3. `datastructures/` または `algorithms/` でコアとなる処理を読む
4. 実際に `--trace` 付きで実行して出力を見る

### 9-2. 特に見るべきポイント

- どこが学習テーマの本体処理か
- どこが入力準備や表示のための補助処理か
- 木や二分木で、親子関係がどのように表現されているか
- 二分探索木で、値の比較に応じてどちらへ進むか
- 木に対する DFS / BFS で、訪問順がどう変わるか
- ヒープで、親子の大小関係をどう保っているか
- PriorityQueue で、追加順ではなく優先度順で取り出されること
- グラフで、隣接リストをどう使って探索しているか
- 到達可能性や連結成分をどのように確認しているか

### 9-3. 実行時間の見方

実行時間は参考値として表示することがあるが、学習の中心は次の順。

1. データ構造や探索の考え方
2. 訪問順や比較回数、更新回数
3. 参考実行時間

特にこのリポジトリでは、秒数そのものより「どの順でたどるか」「どの構造をどう使うか」を重視して見る。

---

## 10. このリポジトリを終えた時点で目指す状態

このリポジトリを終えた時点で、次の状態を目指す。

- 木と二分木の基本構造が説明できる
- 二分探索木の基本的な挿入と探索の流れが説明できる
- 木に対する DFS / BFS の違いが説明できる
- ヒープと PriorityQueue の基本的な使い方が説明できる
- グラフの基本的な表現方法が説明できる
- グラフに対する DFS / BFS と到達可能性・連結性が説明できる
- コードを読んだときに、学習本体処理と補助処理を見分けられる
