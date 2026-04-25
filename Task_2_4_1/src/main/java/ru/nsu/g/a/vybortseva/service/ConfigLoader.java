package ru.nsu.g.a.vybortseva.service;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import org.codehaus.groovy.control.CompilerConfiguration;
import ru.nsu.g.a.vybortseva.dsl.ConfigDelegate;
import ru.nsu.g.a.vybortseva.model.Config;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {
    public Config load(String filePath) throws IOException {
        CompilerConfiguration configuration = new CompilerConfiguration();
        configuration.setScriptBaseClass(DelegatingScript.class.getName());

        GroovyShell shell = new GroovyShell(this.getClass().getClassLoader(), new Binding(), configuration);

        DelegatingScript script = (DelegatingScript) shell.parse(new File(filePath));

        Config config = new Config();
        ConfigDelegate delegate = new ConfigDelegate(config);

        script.setDelegate(delegate);
        script.run();

        return config;
    }
}
