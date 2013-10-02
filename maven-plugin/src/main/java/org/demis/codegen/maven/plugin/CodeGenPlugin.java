package org.demis.codegen.maven.plugin;

import org.apache.commons.io.IOUtils;
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
import org.stringtemplate.v4.ST;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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

        if (outputDirectory == null) {
            outputDirectory = new File("");
        }

        logger.info("outputDirectory : " + outputDirectory.getAbsolutePath());
        logger.info("configurationFileName : " + configurationFileName);


        String completePath = outputDirectory.getAbsolutePath() + File.separator + configurationFileName;
        CodeGeneratorConfiguration configuration = ConfigurationReader.readJSONFile(completePath);
        configuration.setProjectPath(outputDirectory.getAbsolutePath());

        File file = new File(completePath);

        configuration.setTemplatesPath(file.getParent());
        logger.info("templatePath : " + file.getParent());

        CodeGenerator codeGenerator = new CodeGenerator(configuration);
        try {
            codeGenerator.generate();
        } catch (DatabaseReadingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
