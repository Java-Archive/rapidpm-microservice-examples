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

package org.rapidpm.microservice.m2;

import org.rapidpm.ddi.Proxy;
import org.rapidpm.microservice.m2.remote.Service;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.time.LocalDateTime;

@Path("/businesservice")
public class BusinessService {

  @Inject @Proxy(virtual = true) Service service;

  @GET()
  @Path("{a}/{b}")
  @Produces("text/plain")
  public int add(@PathParam("a") int a, @PathParam("b") int b) {
    System.out.println("add called = " + LocalDateTime.now());
    return service.remoteAdd(a, b);
  }
}
