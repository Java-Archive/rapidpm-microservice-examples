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

/**
 * Created by Sven Ruppert on 13.10.2015.
 */
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
