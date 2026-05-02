package io.github.seiya_matsuoka.treeheapgraph;

/**
 * 実行時オプションを保持するクラス
 *
 * <p>App.java で解析した値をまとめて持ち、各 runner に引き渡す。
 *
 * <p>オプションの意味は runner ごとに必要なものだけ利用し、不要なものは無視する。
 */
public class RunnerOptions {
  private final String topic;
  private final String input;
  private final boolean trace;
  private final String target;
  private final Integer size;

  public RunnerOptions(String topic, String input, boolean trace, String target, Integer size) {
    this.topic = topic;
    this.input = input;
    this.trace = trace;
    this.target = target;
    this.size = size;
  }

  /** 実行対象の topic 名を返す。 */
  public String getTopic() {
    return topic;
  }

  /** 直接指定された入力値を返す。 */
  public String getInput() {
    return input;
  }

  /** trace 表示の有無を返す。 */
  public boolean isTrace() {
    return trace;
  }

  /** target 用に指定された値を返す。 */
  public String getTarget() {
    return target;
  }

  /** size 用に指定された値を返す。 */
  public Integer getSize() {
    return size;
  }
}
