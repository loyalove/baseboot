package mybatis.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.api.VerboseProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 执行生成Mybatis对应数据库的xml、Map、PO、DAO
 * <p>
 * Created by loyalove on 2016/11/9.
 */
public class MybatisGenerator {

    private static final Logger logger = LoggerFactory.getLogger(MybatisGenerator.class);

    //是否覆盖已有的文件
    private static final boolean OVERWRITE = true;

    //配置文件路径
    private static final String CONFIG_PATH = "/generatorConfig.xml";

    /**
     * 执行DAO生成
     *
     * @throws IOException
     * @throws XMLParserException
     * @throws InvalidConfigurationException
     * @throws SQLException
     * @throws InterruptedException
     */
    public static void doGen() throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
        //警告信息存储
        List<String> warnings = new ArrayList<String>();

        //创建配置解析器
        ConfigurationParser cp = new ConfigurationParser(warnings);

        //读取配置文件、生成配置
        Configuration config = cp.parseConfiguration(MybatisGenerator.class.getResourceAsStream(CONFIG_PATH)); // 生成mybatis的配置文件

        //命令回调
        ShellCallback callback = new DefaultShellCallback(OVERWRITE);

        //进度回调
        ProgressCallback progressCallback = new VerboseProgressCallback();

        //生成器实例创建
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);

        //执行生成操作
        myBatisGenerator.generate(progressCallback);

        //打印警告信息
        logger.warn("{}", warnings);
    }

    public static void main(String[] args) {
        try {
            doGen();
        } catch (Exception e) {
            logger.error("{}", e);
        }
    }
}
