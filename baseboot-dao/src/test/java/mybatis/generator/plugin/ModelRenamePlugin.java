package mybatis.generator.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.List;

/**
 * 插件功能：生成BEAN文件添加自定义后缀
 *
 * Created by loyalove on 2016/11/9.
 */
public class ModelRenamePlugin extends PluginAdapter {
    private String modelNameSuffix = "DO";

    /**
     * 读取generatorConfig配置文件中参数配置的值
     * @param list
     * @return
     */
    @Override
    public boolean validate(List<String> list) {
        String proModelNameSuffix = this.properties.getProperty("modelNameSuffix");
        if(StringUtility.stringHasValue(proModelNameSuffix)){
            this.modelNameSuffix = proModelNameSuffix;
        }

        return true;
    }

    /**
     * 初始化生成BEAN的后缀参数
     * @param introspectedTable
     */
    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        String defaultType =   introspectedTable.getBaseRecordType();
        if(StringUtility.stringHasValue(defaultType)){
            introspectedTable.setBaseRecordType(defaultType + this.modelNameSuffix);
        }
    }

    public String getModelNameSuffix() {
        return modelNameSuffix;
    }

    public void setModelNameSuffix(String modelNameSuffix) {
        this.modelNameSuffix = modelNameSuffix;
    }
}
