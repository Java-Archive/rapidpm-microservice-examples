/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.rapidpm.ddi.metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
