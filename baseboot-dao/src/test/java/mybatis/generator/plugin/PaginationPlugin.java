package mybatis.generator.plugin;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * Title: PaginationPlugin.java
 * Description: PaginationPlugin
 * Company: ysh
 *
 * @author: sailuo@yiji.com
 * @date: 2016-12-02 12:29
 */
public class PaginationPlugin extends PluginAdapter {
    private static final String TEST = "%s > -1 and %s > -1";
    private static final String LIMIT = "limit ${%s} , ${%s}";
    private String limitStart;
    private String limit;

    /**
     * 重写参数校验方法
     */
    @Override
    public boolean validate(List<String> warnings) {
        limitStart = properties.getProperty("limitStart");
        limit = properties.getProperty("limit");
        return true;
    }


    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        // addfield, getter, setter for limit clause
        addLimit(topLevelClass, introspectedTable, limitStart);

        addLimit(topLevelClass, introspectedTable, limit);

        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);

    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {

        XmlElement isNotNullElement = new XmlElement("if");//if条件判断标签创建

        isNotNullElement.addAttribute(new Attribute("test", String.format(TEST, limitStart, limit)));//test属性添加

        isNotNullElement.addElement(new TextElement(String.format(LIMIT, limitStart, limit)));//if标签内容添加

        element.addElement(isNotNullElement);//if条件判断标签添加到父元素

        // LIMIT 5;//检索前 5个记录行
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);

    }

    /**
     * 添加分页参数
     *
     * @param topLevelClass
     * @param introspectedTable
     * @param name
     */
    private void addLimit(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String name) {

        CommentGenerator commentGenerator = context.getCommentGenerator();

        Field field = new Field();

        field.setVisibility(JavaVisibility.PROTECTED);

        field.setType(FullyQualifiedJavaType.getIntInstance());

        field.setName(name);

        field.setInitializationString("-1");

        commentGenerator.addFieldComment(field, introspectedTable);

        topLevelClass.addField(field);

        char c = name.charAt(0);

        String camel = Character.toUpperCase(c) + name.substring(1);

        Method method = new Method();

        method.setVisibility(JavaVisibility.PUBLIC);

        method.setName("set" + camel);

        method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), name));

        method.addBodyLine("this." + name + "=" + name + ";");

        commentGenerator.addGeneralMethodComment(method, introspectedTable);

        topLevelClass.addMethod(method);

        method = new Method();

        method.setVisibility(JavaVisibility.PUBLIC);

        method.setReturnType(FullyQualifiedJavaType.getIntInstance());

        method.setName("get" + camel);

        method.addBodyLine("return " + name + ";");

        commentGenerator.addGeneralMethodComment(method, introspectedTable);

        topLevelClass.addMethod(method);

    }


}
