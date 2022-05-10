package com.tui.proof.helper;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestResourceHelper {

    private static final ObjectMapper OBJECT_MAPPER;

    public TestResourceHelper() {}

    public static <T> T readObject(String file, Class<T> objectType) throws Exception {
        return OBJECT_MAPPER.readValue(readFileAsString(file), objectType);
    }

    public static InputStream readInputStream (String file) throws Exception {
        return Files.newInputStream(toPath(file));
    }

    public static Path toPath (String file) throws Exception {
        return Paths.get(TestResourceHelper.class.getClassLoader().getResource(file).toURI());

    }

    public static String readFileAsString (String file) throws Exception {
        return new String (Files.readAllBytes (toPath (file)));
    }

    static {
        OBJECT_MAPPER = (new ObjectMapper()).enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }

}
