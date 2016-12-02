package mybatis.generator.plugin;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.internal.util.StringUtility;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 插件功能：修改生成XML是否合并的配置
 * <p>
 * Created by loyalove on 2016/11/9.
 */
public class SqlMapperMergeAblePlugin extends PluginAdapter {
    private boolean isMergeable = true; // 生成xml是否合并

    /**
     * 读取generatorConfig配置文件中参数配置的值
     *
     * @param list
     * @return
     */
    @Override
    public boolean validate(List<String> list) {
        String proIsMergeable = this.properties.getProperty("isMergeable");
        if (StringUtility.stringHasValue(proIsMergeable)) {
            this.isMergeable = Boolean.valueOf(proIsMergeable);
        }

        return true;
    }

    /**
     * 修改生成sqlMap的xml文件是否合并字段，默认值是合并
     *
     * @param sqlMap
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        try {
            Field field = sqlMap.getClass().getDeclaredField("isMergeable");
            field.setAccessible(true);
            field.setBoolean(sqlMap, isMergeable);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

}
