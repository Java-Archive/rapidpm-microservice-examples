package org.rapidpm.microservice.filestoredemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.rapidpm.microservice.filestoredemo.api.FileStoreResponse;
import org.rapidpm.microservice.filestoredemo.api.FileStoreServiceMessage;
import org.rapidpm.microservice.filestoredemo.impl.FileStoreService;
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

    @GET()
    @Produces("application/json")
    public String handleRequest(@QueryParam("json") String json) throws IOException, UnknownActionException {
        ObjectMapper mapper = new ObjectMapper();
        byte[] jsonParameter = DatatypeConverter.parseBase64Binary(json);
        FileStoreServiceMessage fileStoreServiceMessage = mapper.readValue(jsonParameter, FileStoreServiceMessage.class);

        FileStoreResponse fileStoreResponse = fileStoreService.handleMessage(fileStoreServiceMessage);
        return mapper.writeValueAsString(fileStoreResponse);
    }
}
