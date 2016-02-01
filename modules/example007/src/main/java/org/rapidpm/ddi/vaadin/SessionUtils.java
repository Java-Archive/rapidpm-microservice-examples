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

package org.rapidpm.ddi.vaadin;

import com.vaadin.server.VaadinSession;

import java.util.Map;

public class SessionUtils {

  public void printSessionAttributes() {
    final VaadinSession session = VaadinSession.getCurrent();
    try {
      final java.lang.reflect.Field attributesField = VaadinSession.class.getDeclaredField("attributes");
      final boolean accessible = attributesField.isAccessible();
      attributesField.setAccessible(true);
      final Object o = attributesField.get(session);
      //Map<String, Object
      if (o instanceof Map) {
        Map attributes = (Map) o;
        attributes.forEach((key, value) -> {
          System.out.println("key = " + key);
          System.out.println("value = " + value);
        });
      }
      attributesField.setAccessible(accessible);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
