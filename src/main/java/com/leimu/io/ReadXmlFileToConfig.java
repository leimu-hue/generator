package com.leimu.io;

import com.leimu.config.ConnectionConfig;
import com.leimu.config.FileBuilderOfConfig;
import com.leimu.constant.Constant;
import com.leimu.utils.StringUtils;
import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * 读取文件信息
 */

public class ReadXmlFileToConfig {

    private static final Logger logger = LoggerFactory.getLogger(ReadXmlFileToConfig.class);
    private static String pathXml;
    private static Document document;

    //直接配置datasource
    private static Node databaseSource;

    /**
     * 根据xml文件创建一个Document
     *
     * @param pathXml xml配置文件路径
     */
    public static void createDocument(String pathXml) throws ParserConfigurationException, IOException, SAXException {
        ReadXmlFileToConfig.pathXml = pathXml;
        //创建DOM解析工厂
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactoryImpl.newInstance();
        //创建DOM解析器
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        //创建一个DOM树
        document = documentBuilder.parse(pathXml);
    }

    /**
     * 已配置文件信息创建一个配置文件实体类
     *
     * @return 文件配置类
     */
    public static FileBuilderOfConfig getConfigByDocument() {
        FileBuilderOfConfig fileBuilderOfConfig = new FileBuilderOfConfig();
        //解析DOM树，获取文档内容
        //获取根元素
        NodeList childNodes = document.getChildNodes();
        Node root = childNodes.item(0);
        if (Objects.isNull(root)) {
            throw new NullPointerException("In the '" + pathXml.substring(pathXml.indexOf(File.separator) + 1) + "', there is no root element");
        }
        childNodes = root.getChildNodes();
        if (Objects.isNull(childNodes) || childNodes.getLength() == 0) {
            throw new NullPointerException("In the '" + pathXml.substring(pathXml.indexOf(File.separator) + 1) + "', there is no child element");
        }
        logger.info("当前需要处理的节点数量：" + childNodes.getLength());
        for (int index = 0; index < childNodes.getLength(); index++) {
            Node willHandlerNode = childNodes.item(index);
            if (Constant.author.
                    equalsIgnoreCase(willHandlerNode.getNodeName())) {
                fileBuilderOfConfig.setAuthor(
                        StringUtils.toHandlerContent(willHandlerNode.getTextContent())
                );
            } else if (Constant.outputFilePath.equalsIgnoreCase(willHandlerNode.getNodeName())) {
                fileBuilderOfConfig.setOutputFilePath(
                        StringUtils.toHandlerContent(willHandlerNode.getTextContent())
                );
            } else if (Constant.jarLocation.equalsIgnoreCase(willHandlerNode.getNodeName())) {
                ConnectionConfig.jarLocation = StringUtils.toHandlerContent(willHandlerNode.getTextContent());
            } else if (Constant.databaseSource.equalsIgnoreCase(willHandlerNode.getNodeName())) {
                ReadXmlFileToConfig.databaseSource = willHandlerNode;
            } else if (Constant.basePackage.equalsIgnoreCase(willHandlerNode.getNodeName())) {
                fileBuilderOfConfig.setBasePackage(
                        StringUtils.toHandlerContent(willHandlerNode.getTextContent().trim())
                );
            }
        }
        if (StringUtils.isEmpty(fileBuilderOfConfig.getOutputFilePath())) {
            throw new RuntimeException("Make sure the '" + pathXml.substring(pathXml.indexOf(File.separator) + 1) + "' contains the 'outputFilePath' property");
        }
        return fileBuilderOfConfig;
    }


    public static void getConnectionConfigByDocument() {
        if (Objects.isNull(databaseSource)) {
            throw new RuntimeException("Make sure the '" + pathXml.substring(pathXml.indexOf(File.separator) + 1) + "' contains the 'databaseSource' property");
        }
        if (StringUtils.isEmpty(ConnectionConfig.jarLocation)) {
            throw new RuntimeException("jar location must be transmission");
        }
        NodeList childNodes = databaseSource.getChildNodes();
        int val = 0;
        for (int index = 0; index < childNodes.getLength(); index++) {
            Node willHandlerNode = childNodes.item(index);
            if (Constant.JdbcDriver.equalsIgnoreCase(willHandlerNode.getNodeName())) {
                if (StringUtils.isEmpty(ConnectionConfig.JdbcDriver)) {
                    val++;
                }
                ConnectionConfig.JdbcDriver = StringUtils.toHandlerContent(willHandlerNode.getTextContent());
            } else if (Constant.jdbcConnection.equalsIgnoreCase(willHandlerNode.getNodeName())) {
                if (StringUtils.isEmpty(ConnectionConfig.jdbcConnection)) {
                    val++;
                }
                ConnectionConfig.jdbcConnection = StringUtils.toHandlerContent(willHandlerNode.getTextContent());
            } else if (Constant.username.equalsIgnoreCase(willHandlerNode.getNodeName())) {
                if (StringUtils.isEmpty(ConnectionConfig.username)) {
                    val++;
                }
                ConnectionConfig.username = StringUtils.toHandlerContent(willHandlerNode.getTextContent());
            } else if (Constant.password.equalsIgnoreCase(willHandlerNode.getNodeName())) {
                if (StringUtils.isEmpty(ConnectionConfig.password)) {
                    val++;
                }
                ConnectionConfig.password = StringUtils.toHandlerContent(willHandlerNode.getTextContent());
            }
        }
        if (val != 4) {
            throw new RuntimeException(Constant.JdbcDriver +
                    File.separator + Constant.jdbcConnection +
                    File.separator + Constant.username +
                    File.separator + Constant.password + " one of them is missing, please check");
        }
    }

}
