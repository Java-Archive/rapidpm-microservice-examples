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

package org.rapidpm.microservice.filestore.rest;

import org.rapidpm.microservice.filestore.api.FileStoreResponse;
import org.rapidpm.microservice.filestore.api.FileStoreServiceMessage;
import org.rapidpm.microservice.filestore.impl.FileStoreService;
import org.rapidpm.microservice.filestore.impl.RequestEncodingHelper;
import org.rapidpm.microservice.filestore.impl.UnknownActionException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("filestore")
public class RestService {
    @Inject
    FileStoreService fileStoreService;


    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public String handleRequest(@QueryParam("json") String json) throws IOException, UnknownActionException {
        final String realJson = RequestEncodingHelper.decodeFromBase64(json);

        final FileStoreServiceMessage message = RequestEncodingHelper.parseJsonToMessage(realJson, FileStoreServiceMessage.class);

        final FileStoreResponse fileStoreResponse = fileStoreService.handleMessage(message);
        return RequestEncodingHelper.serializeToJson(fileStoreResponse);
    }
}
