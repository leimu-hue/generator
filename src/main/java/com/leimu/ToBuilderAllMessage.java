package com.leimu;

import com.leimu.config.ConnectionConfig;
import com.leimu.config.FileBuilderOfConfig;
import com.leimu.constant.TableConstant;
import com.leimu.io.GeneratorFileToTargetPath;
import com.leimu.io.ReadXmlFileToConfig;
import com.leimu.utils.JDBCUtils;
import com.leimu.utils.LoadJarUtils;
import com.leimu.utils.StringUtils;
import com.leimu.utils.ToGeneratorBaseMessageUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

public class ToBuilderAllMessage {

    private String configXmlPath;

    private FileBuilderOfConfig fileBuilderOfConfig;

    public void start(FileBuilderOfConfig fileBuilderOfConfig) {
        this.fileBuilderOfConfig = fileBuilderOfConfig;
        if (Objects.isNull(this.fileBuilderOfConfig)) {
            throw new NullPointerException("The configuration of entity must be transferred！！！！");
        }
    }

    public void start(String configXml) throws Exception {
        this.configXmlPath = configXml;
        if (StringUtils.isEmpty(this.configXmlPath)) {
            throw new NullPointerException("The configuration file must be transferred！！！！");
        }
        start_01();
    }

    private void start_01() throws Exception {
        //进行初始化配置类
        if (Objects.isNull(fileBuilderOfConfig)) {
            ReadXmlFileToConfig.createDocument(this.configXmlPath);
            this.fileBuilderOfConfig = ReadXmlFileToConfig.getConfigByDocument();
            //处理路径
            String path = System.getProperty("user.dir")+ File.separator+ (fileBuilderOfConfig.getOutputFilePath().trim().startsWith("/")?
                    (fileBuilderOfConfig.getOutputFilePath().substring(1)):fileBuilderOfConfig.getOutputFilePath());
            File file = new File(path);
            //是文件夹，并且不存在 则创建
            if (file.isDirectory() && !file.exists()){
                file.mkdirs();
            }
            fileBuilderOfConfig.setOutputFilePath(path);
        }
        //配置数据库参数
        ReadXmlFileToConfig.getConnectionConfigByDocument();
        //加载外部jar包
        LoadJarUtils.loadJar(ConnectionConfig.jarLocation);
        //加载数据库
        TableConstant tableConstant = JDBCUtils.showAllTables();
        tableConstant = JDBCUtils.showAllColumnInTable(tableConstant, JDBCUtils.getDatabaseType());
        //将需要生成的信息放入hashmap
        String content = ToGeneratorBaseMessageUtils.toGeneratorBaseMessage(fileBuilderOfConfig);
        //开始生成文件
        GeneratorFileToTargetPath.toGenerator(tableConstant,fileBuilderOfConfig,content);
    }

}
