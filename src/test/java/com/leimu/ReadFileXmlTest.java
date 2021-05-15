package com.leimu;

import com.leimu.io.ReadXmlFileToConfig;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class ReadFileXmlTest {

    @Test
    public void testReadXml() throws IOException, SAXException, ParserConfigurationException {
        File file = null;
        try {
            URL resource = getClass().getClassLoader().getResource("connectionConfig.xml");
            file = new File(resource.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        ReadXmlFileToConfig.createDocument(file.getAbsolutePath());
        ReadXmlFileToConfig.getConfigByDocument();
        ReadXmlFileToConfig.getConnectionConfigByDocument();
    }

}
