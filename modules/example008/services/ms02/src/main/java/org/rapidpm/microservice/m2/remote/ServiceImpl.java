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

package org.rapidpm.microservice.m2.remote;

import org.rapidpm.microservice.MainUndertow;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.time.LocalDateTime;

public class ServiceImpl implements Service {
  private static final String SERVICE_URL = "http://127.0.0.1:" + MainUndertow.DEFAULT_REST_PORT + MainUndertow.CONTEXT_PATH_REST + "/demoservice";
  private static final Client CLIENT = ClientBuilder.newClient(); // to expensive

  public ServiceImpl() {
    System.out.println("ServiceImpl.now() = " + LocalDateTime.now());
  }

  @Override
  public int remoteAdd(final int a, final int b) {
    final WebTarget webTarget = CLIENT.target(SERVICE_URL);
    return (Integer) webTarget.path(a + "").path(b + "").request().get().getEntity();
  }


}
