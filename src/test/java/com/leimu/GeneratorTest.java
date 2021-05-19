package com.leimu;

import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class GeneratorTest {

    @Test
    public void generator() throws Exception {
        File file = null;
        try {
            URL resource = getClass().getClassLoader().getResource("connectionConfig.xml");
            file = new File(resource.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        ToBuilderAllMessage toBuilderAllMessage = new ToBuilderAllMessage();
        toBuilderAllMessage.start(file.getAbsolutePath());
    }

}
