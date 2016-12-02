package mybatis.generator.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Title: AnnotationPlugin.java
 * Description: 给dao增加注解增加注解
 * Company: ysh
 *
 * @author: sailuo@yiji.com
 * @date: 2016-11-29 13:29
 */
public class AnnotationPlugin extends PluginAdapter {

    public AnnotationPlugin() {
        super();
    }

    @Override
    public boolean validate(List<String> warnings) {

        for (String name : properties.stringPropertyNames()) {
            String val = this.properties.getProperty(name);
            if (name == null || name == "" || val == null || val == "")
                return false;
        }

        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        for (String name : properties.stringPropertyNames()) {
            String val = this.properties.getProperty(name);
            interfaze.addAnnotation(name);
            interfaze.addImportedType(new FullyQualifiedJavaType(val));
        }
        return true;
    }

}
