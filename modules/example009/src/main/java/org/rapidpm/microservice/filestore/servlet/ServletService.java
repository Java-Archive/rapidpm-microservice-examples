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

package org.rapidpm.microservice.filestore.servlet;

import org.rapidpm.microservice.filestore.api.FileStoreResponse;
import org.rapidpm.microservice.filestore.api.FileStoreServiceMessage;
import org.rapidpm.microservice.filestore.api.StorageStatus;
import org.rapidpm.microservice.filestore.impl.FileStoreService;
import org.rapidpm.microservice.filestore.impl.RequestEncodingHelper;
import org.rapidpm.microservice.filestore.impl.UnknownActionException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXB;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns = "/servletservice")
public class ServletService extends HttpServlet {

    @Inject FileStoreService fileStoreService;

    @Override
    public void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    public void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        FileStoreServiceMessage message = extractFileStoreServiceMessage(req);
        FileStoreResponse fileStoreResponse = handleFileStoreMessage(message);

        final PrintWriter writer = resp.getWriter();
        JAXB.marshal(fileStoreResponse, writer);
        writer.close();

    }

    private FileStoreResponse handleFileStoreMessage(FileStoreServiceMessage message) {
        FileStoreResponse fileStoreResponse = new FileStoreResponse();
        try {
            fileStoreResponse = fileStoreService.handleMessage(message);
        } catch (UnknownActionException e) {
            e.printStackTrace();
            fileStoreResponse.status = StorageStatus.ERROR;
            fileStoreResponse.message = e.getMessage();
        }
        return fileStoreResponse;
    }

    private FileStoreServiceMessage extractFileStoreServiceMessage(HttpServletRequest req) {
        String xmlBase64 = req.getParameter("xml");

        final String fromBase64 = RequestEncodingHelper.decodeFromBase64(xmlBase64);

        return RequestEncodingHelper.parseXmlToMessage(fromBase64);


    }


}
