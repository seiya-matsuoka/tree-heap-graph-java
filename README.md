# Tree / Heap / Graph - Java

<p>
  <img alt="Java" src="https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=ffffff">
  <img alt="Algorithm" src="https://img.shields.io/badge/Algorithm-Study-1F6FEB">
  <img alt="Data Structure" src="https://img.shields.io/badge/Data%20Structure-Study-7C3AED">
</p>

木・ヒープ・グラフの基礎のうち、**木の基礎 / 二分木 / 二分探索木 / 木の走査 / ヒープ / PriorityQueue / グラフの基礎 / グラフの走査** を Java で学習するためのリポジトリ。  
コードを読み、実行し、出力を確認しながら、階層構造やネットワーク構造の持ち方とたどり方を段階的に理解することを目的とする。  
各トピックごとにドキュメントを用意し、実装と対応づけながら見返せる形で整理している。

---

## 学習目的

このリポジトリでは、主に次の内容を目的として学習を行う。

- 木の基本構造と、親子関係でデータを表現する考え方を理解する
- 二分木と二分探索木の違いを理解する
- 木に対する前順 / 中順 / 後順走査、DFS / BFS の違いを理解する
- ヒープの構造と、PriorityQueue の基本的な使い方を理解する
- グラフの基本構造と、DFS / BFS を使った到達可能性・連結性の確認方法を理解する
- Java のコードを読みながら、処理の流れを追えるようにする
- 実行結果や途中経過を見ながら、挙動を確認できるようにする

---

## 学習範囲

このリポジトリで扱うトピックは次の通り。

- 木の基礎
- 二分木
- 二分探索木
- 木の走査
- ヒープ
- PriorityQueue
- グラフの基礎
- グラフの走査

### 各トピックの位置づけ

- **木の基礎**  
  ルート、親、子、葉、深さ、高さなど、木構造を読むための基本概念を確認する

- **二分木**  
  左の子と右の子を持つ構造を通して、木を配列やノードで表現する流れを確認する

- **二分探索木**  
  値の大小関係を使って左 / 右へ進む構造を通して、挿入・探索の基本を確認する

- **木の走査**  
  前順 / 中順 / 後順走査と、木に対する DFS / BFS の違いを確認する

- **ヒープ**  
  完全二分木と親子の大小関係を通して、最大値 / 最小値を効率よく扱う考え方を確認する

- **PriorityQueue**  
  標準ライブラリを使った優先度付きキューの基本的な使い方を確認する

- **グラフの基礎**  
  頂点、辺、隣接リスト、有向 / 無向といった基本構造を確認する

- **グラフの走査**  
  DFS / BFS による訪問順、到達可能性、連結性、連結成分の考え方を確認する

---

## 学習の進め方

基本的な進め方は次の通り。

1. `docs/guide.md` を読み、このリポジトリ全体の構成と実行方法を把握する
2. `docs/topics/` 配下の対象トピックのドキュメントを読む
3. `App.java` から `--topic` を指定して実行する
4. 必要に応じて `--input`、`--trace`、`--target`、`--size` を使って挙動を変える
5. `runner/` と `datastructures/` / `algorithms/` 配下のコードを読み、コメントと出力を対応させながら理解する

---

## 前提環境

- Java 21
- VS Code などの Java を扱えるエディタ
- ビルドツールは使用しない
- `javac` と `java` でコンパイル・実行を行う

---

## 実行方法

### 1. コンパイル

#### bash / Git Bash

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
```

#### PowerShell

```powershell
$files = Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d out $files
```

### 2. 実行

基本形は次の通り。

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic <topic>
```

例:

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic tree-basics
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic binary-tree
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic binary-search-tree
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic tree-traversal
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic heap
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic priority-queue
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic graph-basics
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic graph-traversal
```

---

## 共通オプション

このリポジトリでは、共通で次のオプションを使う。

- `--topic`  
  実行するトピックを指定する

- `--input`  
  任意の入力値を直接指定する  
  例: `--input 10,20,30,40,50,60,70` / `--input 0-1,0-2,1-3,2-4`

- `--trace`  
  処理途中の流れを表示する

- `--target`  
  探索対象値や到達確認の対象を渡す  
  必要なトピックのみで使用する

- `--size`  
  大きい入力データを自動生成したい場合のサイズ指定に使う  
  比較確認や差分把握で使用する

---

## 実行例

### 木の基礎

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic tree-basics --input 10,20,30,40,50,60,70 --trace
```

### 二分木

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic binary-tree --input 10,20,30,40,50,60,70 --trace
```

### 二分探索木

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic binary-search-tree --input 40,20,60,10,30,50,70 --target 30 --trace
```

### 木の走査

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic tree-traversal --input 10,20,30,40,50,60,70 --trace
```

### ヒープ

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic heap --input 50,20,70,10,40,60,30 --trace
```

### PriorityQueue

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic priority-queue --input 50,20,70,10,40,60,30 --trace
```

### グラフの基礎

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic graph-basics --input 0-1,0-2,1-3,2-4,4-5 --trace
```

### グラフの走査

```bash
java -cp out io.github.seiya_matsuoka.treeheapgraph.App --topic graph-traversal --input 0-1,0-2,1-3,2-4,4-5 --target 5 --trace
```

---

## リポジトリ構成

```text
.
├─ src/
│  └─ io/
│     └─ github/
│        └─ seiya_matsuoka/
│           └─ treeheapgraph/
│              ├─ App.java
│              ├─ RunnerOptions.java
│              ├─ runner/
│              ├─ datastructures/
│              └─ algorithms/
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
└─ README.md
```

### 各ディレクトリ・ファイルの役割

- `App.java`  
  共通エントリーポイント  
  引数を読み取り、対象の runner に振り分ける

- `RunnerOptions.java`  
  実行オプションを保持する

- `runner/`  
  入力の決定、実装呼び出し、出力表示を担当する

- `datastructures/`  
  学習対象となる木構造やヒープの実装本体を置く

- `algorithms/`  
  学習対象となる木・グラフの走査や PriorityQueue の処理本体を置く

- `docs/guide.md`  
  リポジトリ全体の案内と実行方法をまとめる

- `docs/topics/`  
  各トピックの個別ドキュメントを置く

---

## ドキュメント

- ガイド: [`docs/guide.md`](docs/guide.md)
- 木の基礎: [`docs/topics/tree-basics.md`](docs/topics/tree-basics.md)
- 二分木: [`docs/topics/binary-tree.md`](docs/topics/binary-tree.md)
- 二分探索木: [`docs/topics/binary-search-tree.md`](docs/topics/binary-search-tree.md)
- 木の走査: [`docs/topics/tree-traversal.md`](docs/topics/tree-traversal.md)
- ヒープ: [`docs/topics/heap.md`](docs/topics/heap.md)
- PriorityQueue: [`docs/topics/priority-queue.md`](docs/topics/priority-queue.md)
- グラフの基礎: [`docs/topics/graph-basics.md`](docs/topics/graph-basics.md)
- グラフの走査: [`docs/topics/graph-traversal.md`](docs/topics/graph-traversal.md)

---

## このリポジトリで確認できること

このリポジトリを一通り進めることで、次の状態を目指す。

- 木の基本構造と二分木の違いを説明できる
- 二分探索木の挿入と探索の基本が説明できる
- 木に対する前順 / 中順 / 後順走査と DFS / BFS の違いを説明できる
- ヒープの基本構造と PriorityQueue の使い方を理解できる
- グラフの基本構造と、DFS / BFS による到達可能性・連結性の確認方法を説明できる
- Java の実装と出力を対応させながら読める
- `--topic` や各オプションを使って、自分で入力を変えながら確認できる
