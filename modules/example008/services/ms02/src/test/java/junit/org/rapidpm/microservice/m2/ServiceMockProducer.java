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

package junit.org.rapidpm.microservice.m2;

import org.rapidpm.ddi.Produces;
import org.rapidpm.ddi.producer.Producer;
import org.rapidpm.microservice.m2.remote.Service;
import org.rapidpm.microservice.m2.remote.ServiceAdapterBuilder;

@Produces(Service.class)
public class ServiceMockProducer implements Producer<Service> {
  @Override
  public Service create() {
    return ServiceAdapterBuilder.newBuilder()
        .setOriginal(null)
        .withRemoteAdd((a, b) -> a + b)
        .buildForTarget(Service.class);
  }
}
