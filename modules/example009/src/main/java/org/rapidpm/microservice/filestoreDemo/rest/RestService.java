package org.rapidpm.microservice.filestoredemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.rapidpm.microservice.filestoredemo.api.FileStoreResponse;
import org.rapidpm.microservice.filestoredemo.api.FileStoreServiceMessage;
import org.rapidpm.microservice.filestoredemo.impl.FileStoreService;
import org.rapidpm.microservice.filestoredemo.impl.RequestEncoder;
import org.rapidpm.microservice.filestoredemo.impl.UnknownActionException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

/**
 * Created by b.bosch on 13.10.2015.
 */
@Path("filestore")
public class RestService {
    @Inject
    FileStoreService fileStoreService;
    private ObjectMapper mapper = new ObjectMapper();

    @GET()
    @Produces("application/json")
    public String handleRequest(@QueryParam("json") String json) throws IOException, UnknownActionException {
        final String realJson = RequestEncoder.decodeFromBase64(json);

        final FileStoreServiceMessage message = mapper.readValue(realJson, FileStoreServiceMessage.class);

        final FileStoreResponse fileStoreResponse = fileStoreService.handleMessage(message);
        return mapper.writeValueAsString(fileStoreResponse);
    }
}
