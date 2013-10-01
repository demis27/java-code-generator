package org.demis.codegen.core.generator;

import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.demis.codegen.core.generator.configuration.CodeGeneratorConfiguration;

public class ConfigurationReader {

    public static CodeGeneratorConfiguration readJSONFile(String inputFilename) {
        try {
            FileReader reader = new FileReader(inputFilename);

            return readJSON(reader);
        } catch (IOException ex) {
            Logger.getLogger(ConfigurationReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static CodeGeneratorConfiguration readJSON(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CodeGeneratorConfiguration result = mapper.readValue(json, CodeGeneratorConfiguration.class);
        return result;
    }

    public static CodeGeneratorConfiguration readJSON(FileReader reader) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CodeGeneratorConfiguration result = mapper.readValue(reader, CodeGeneratorConfiguration.class);
        return result;
    }

}
