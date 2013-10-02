package org.demis.codegen.maven.plugin;

import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CodeGenPluginTest {

    @Test
    public void execute() throws MojoExecutionException, IOException {
        CodeGenPlugin plugin = new CodeGenPlugin();
        File outputDirectory = new File("");
        plugin.setOutputDirectory(outputDirectory);

        plugin.setConfigurationFileName("/src/test/configuration/configuration.json");
        plugin.execute();
    }
}
