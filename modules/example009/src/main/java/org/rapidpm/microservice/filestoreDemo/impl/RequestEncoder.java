package org.rapidpm.microservice.filestoredemo.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.rapidpm.microservice.filestoredemo.api.FileStoreServiceMessage;

import javax.xml.bind.JAXB;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * Created by b.bosch on 14.10.2015.
 */
public class RequestEncoder {


    private static final ObjectMapper mapper = new ObjectMapper();

    public static String encodeIntoBase64(String toEncode) {
        byte[] encode = Base64.getUrlEncoder().encode(toEncode.getBytes());
        return new String(encode);
    }

    public static String serializeMessageToXml(FileStoreServiceMessage message) {
        StringWriter writer = new StringWriter();
        JAXB.marshal(message, writer);
        return writer.toString();
    }

    public static FileStoreServiceMessage parseXmlToMessage(String xml){
        StringReader reader = new StringReader(new String(xml));
        return JAXB.unmarshal(reader, FileStoreServiceMessage.class);
    }


    public static String serializeToJson(FileStoreServiceMessage message)  {
        try {
            return mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Unable to Serialize Class <%s> into JSON  Reason:<%s>",
                    FileStoreServiceMessage.class.getSimpleName(),
                    e.getMessage()));
        }
    }

    public static FileStoreServiceMessage parseJsonToMessage(String json) {
        try {
            return mapper.readValue(json, FileStoreServiceMessage.class);
        }
        catch (IOException e) {
            throw new RuntimeException(String.format("Unable to deserialize JSON into Class <%s> String:<%s> Reason:<%s>",
                    FileStoreServiceMessage.class.getSimpleName(),
                    json,
                    e.getMessage()));
        }
    }

    public static String encodeForUrl(String toEncode){
        try {
            return URLEncoder.encode(toEncode,"UTF8");
        } catch (UnsupportedEncodingException e) {
            // UTF8 should always be possible
        }
        return "";
    }


    public static String decodeFromBase64(String json) {
        final byte[] decodedBytes = Base64.getUrlDecoder().decode(json.getBytes());
        return new String(decodedBytes);
    }
}
