/**
 * Created by Loyal on 2016/12/9.
 */
layui.use(['element', 'layer', 'vue'], function () {
    //导航的hover效果、二级菜单等功能，需要依赖element模块
    var $ = layui.jquery,
        element = layui.element(),
        Vue = layui.vue,
        layer = layui.layer;

    var vm = new Vue();

    //监听导航点击
    element.on('nav(sidebar)', function (elem) {
        //console.log(elem)
        layer.msg(elem.text());
    });

    element.on('nav(logout-btn)', function (elem) {
        //console.log(elem)
        layer.msg(elem.text());
        return false;
    });
});