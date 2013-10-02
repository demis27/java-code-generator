package org.demis.codegen.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.demis.codegen.core.db.reader.DatabaseReadingException;
import org.demis.codegen.core.generator.ConfigurationReader;
import org.demis.codegen.core.generator.configuration.CodeGeneratorConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.demis.codegen.core.generator.CodeGenerator;

import java.io.File;
import java.io.IOException;

@Mojo( name = "generate-files")
public class CodeGenPlugin extends AbstractMojo {

    private final Logger logger = LoggerFactory.getLogger(CodeGenPlugin.class);

    @Parameter
    private String configurationFileName;

    /**
     * Location of the file.
     * @parameter expression="${project.basedir}"
     * @required
     */
    @Parameter
    private File outputDirectory;

    /**
     * Execute the plugin.
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    @Override
    public void execute() throws MojoExecutionException {
        logger.debug("CodeGenPlugin parameters: outputDirectory = " + outputDirectory + ", configurationFileName = " + configurationFileName);
        if (outputDirectory == null) {
            outputDirectory = new File("");
        }
        logger.debug("CodeGenPlugin parameters processed: outputDirectory = " + outputDirectory.getAbsolutePath() + ", configurationFileName = " + configurationFileName);
        // Procedd complete path and read and update configuration
        String completePath = outputDirectory.getAbsolutePath() + File.separator + configurationFileName;
        CodeGeneratorConfiguration configuration = ConfigurationReader.readJSONFile(completePath);
        if (configuration == null) {
            throw new MojoExecutionException("Configuration file " + completePath + " not found ");
        }
        configuration.setProjectPath(outputDirectory.getAbsolutePath());
        configuration.setTemplatesPath((new File(completePath)).getParent());
        logger.debug("Configuration template path processed =" + configuration.getTemplatesPath());
        // Create the code generator
        CodeGenerator codeGenerator = new CodeGenerator(configuration);
        try {
            codeGenerator.generate();
        } catch (DatabaseReadingException e) {
            throw new MojoExecutionException("Can't read the database", e);
        } catch (IOException e) {
            throw new MojoExecutionException("Error", e);
        }
    }

    public final String getConfigurationFileName() {
        return configurationFileName;
    }

    public final void setConfigurationFileName(String configurationFileName) {
        this.configurationFileName = configurationFileName;
    }

    public final File getOutputDirectory() {
        return outputDirectory;
    }

    public final void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

}
