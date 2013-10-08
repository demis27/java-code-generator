package org.demis.codegen.core.generator;

import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.demis.codegen.core.generator.configuration.CodeGeneratorConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @version 1.0
 * @author <a href="mailto:demis27@demis27.net">Stéphane kermabon</a>
 */
public class ConfigurationWriter {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationWriter.class);

    public static void write(CodeGeneratorConfiguration configuration, String outputFilename) {
        try {
            FileWriter writer = new FileWriter(outputFilename);

            write(configuration, writer);
        } catch (IOException ex) {
            logger.error(ConfigurationWriter.class.getName(), ex);
        }
    }

    public static void write(CodeGeneratorConfiguration configuration, FileWriter writer) {
        try {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write(toXml(configuration));
        } catch (IOException ex) {
            logger.error(ConfigurationWriter.class.getName(), ex);
        }

    }

    public static String toXml(CodeGeneratorConfiguration configuration) {
        return null;
    }

    public static String toJSON(CodeGeneratorConfiguration configuration) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(configuration);
        logger.debug("Json result: " + result);
        return result;
    }
}
