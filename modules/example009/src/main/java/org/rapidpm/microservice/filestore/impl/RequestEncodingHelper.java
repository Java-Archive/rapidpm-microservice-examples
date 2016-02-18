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

package org.rapidpm.microservice.filestore.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.rapidpm.microservice.filestore.api.FileStoreServiceMessage;

import javax.xml.bind.JAXB;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class RequestEncodingHelper {


    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Encoder URL_ENCODER = Base64.getUrlEncoder();
    private static final Decoder URL_DECODER = Base64.getUrlDecoder();

    private RequestEncodingHelper() {
    }


    public static <T> String serializeMessageToXml(T message) {
        StringWriter writer = new StringWriter();
        JAXB.marshal(message, writer);
        return writer.toString();
    }

    public static FileStoreServiceMessage parseXmlToMessage(String xml) {
        StringReader reader = new StringReader(xml);
        return JAXB.unmarshal(reader, FileStoreServiceMessage.class);
    }


    public static <T> String serializeToJson(T message) {
        try {
            return OBJECT_MAPPER.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Unable to Serialize Class <%s> into JSON  Reason:<%s>",
                    FileStoreServiceMessage.class.getSimpleName(),
                    e.getMessage()));
        }
    }

    public static <T> T parseJsonToMessage(String json, Class<T> classToParse) {
        try {
            return OBJECT_MAPPER.readValue(json, classToParse);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Unable to deserialize JSON into Class <%s> String:<%s> Reason:<%s>",
                    FileStoreServiceMessage.class.getSimpleName(),
                    json,
                    e.getMessage()));
        }
    }

    public static String decodeFromBase64(String json) {
        final byte[] decodedBytes = URL_DECODER.decode(json.getBytes());
        return new String(decodedBytes);
    }

    public static String encodeIntoBase64(String toEncode) {

        byte[] encode = URL_ENCODER.encode(toEncode.getBytes());
        return new String(encode);
    }
}
