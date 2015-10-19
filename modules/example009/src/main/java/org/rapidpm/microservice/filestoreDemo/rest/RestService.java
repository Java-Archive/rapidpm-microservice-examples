package org.rapidpm.microservice.filestoredemo.rest;

import org.rapidpm.microservice.filestoredemo.api.FileStoreResponse;
import org.rapidpm.microservice.filestoredemo.api.FileStoreServiceMessage;
import org.rapidpm.microservice.filestoredemo.impl.FileStoreService;
import org.rapidpm.microservice.filestoredemo.impl.RequestEncodingHelper;
import org.rapidpm.microservice.filestoredemo.impl.UnknownActionException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.io.IOException;

/**
 * Created by b.bosch on 13.10.2015.
 */
@Path("filestore")
public class RestService {
    @Inject
    FileStoreService fileStoreService;


    @GET()
    @Produces("application/json")
    public String handleRequest(@QueryParam("json") String json) throws IOException, UnknownActionException {
        final String realJson = RequestEncodingHelper.decodeFromBase64(json);

        final FileStoreServiceMessage message = RequestEncodingHelper.parseJsonToMessage(realJson, FileStoreServiceMessage.class);

        final FileStoreResponse fileStoreResponse = fileStoreService.handleMessage(message);
        return RequestEncodingHelper.serializeToJson(fileStoreResponse);
    }
}
