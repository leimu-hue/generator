package com.leimu;

import com.leimu.config.ConnectionConfig;
import com.leimu.config.FileBuilderOfConfig;
import com.leimu.constant.TableConstant;
import com.leimu.io.GeneratorFileToTargetPath;
import com.leimu.io.ReadXmlFileToConfig;
import com.leimu.utils.JDBCUtils;
import com.leimu.utils.LoadJar;
import com.leimu.utils.StringUtils;

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
        }
        //配置数据库参数
        ReadXmlFileToConfig.getConnectionConfigByDocument();
        //加载外部jar包
        LoadJar.loadJar(ConnectionConfig.jarLocation);
        //加载数据库
        TableConstant tableConstant = JDBCUtils.showAllTables();
        tableConstant = JDBCUtils.showAllColumnInTable(tableConstant, JDBCUtils.getDatabaseType());
        //开始生成文件
        GeneratorFileToTargetPath.toGenerator(tableConstant);
    }

}
