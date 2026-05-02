package io.github.seiya_matsuoka.treeheapgraph.runner;

import io.github.seiya_matsuoka.treeheapgraph.RunnerOptions;

/**
 * 各 topic の runner が実装する共通インターフェース
 *
 * <p>App.java からはこのインターフェース越しに runner を呼び出すことで、 topic ごとの実装差を意識せずに実行できるようにしている。
 */
public interface TopicRunner {
  void run(RunnerOptions options);
}
