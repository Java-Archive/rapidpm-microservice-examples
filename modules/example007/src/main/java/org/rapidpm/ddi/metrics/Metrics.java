package org.rapidpm.ddi.metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Sven Ruppert on 20.08.15.
 */
public class Metrics {
  private static final Metrics OUR_INSTANCE = new Metrics();
  public List<KeyValue> data = new ArrayList<>();

  private Metrics() {
  }

  public static Metrics getInstance() {
    return OUR_INSTANCE;
  }

  public static class KeyValue {
    private String key;
    private String value;

    @Override
    public int hashCode() {
      return Objects.hash(key, value);
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) return true;
      if (!(o instanceof KeyValue)) return false;
      final KeyValue keyValue = (KeyValue) o;
      return Objects.equals(key, keyValue.key) &&
          Objects.equals(value, keyValue.value);
    }

    @Override
    public String toString() {
      return "KeyValue{" +
          "key='" + key + '\'' +
          ", value='" + value + '\'' +
          '}';
    }

    public KeyValue key(final String key) {
      this.key = key;
      return this;
    }

    public KeyValue value(final String value) {
      this.value = value;
      return this;
    }


    private String getKey() {
      return key;
    }

    private void setKey(final String key) {
      this.key = key;
    }

    private String getValue() {
      return value;
    }

    private void setValue(final String value) {
      this.value = value;
    }
  }

}
