/**
 * Created by Loyal on 2016/12/9.
 */
layui.use(['element', 'form'], function () {
    //导航的hover效果、二级菜单等功能，需要依赖element模块
    var $ = layui.jquery,
        element = layui.element(),
        form = layui.form(),
        layer = layui.layer;
    form.on('submit(login-btn)', function (data) {
        $.post('/login', data.field, function (data) {
            layer.msg(data.message, {time: 1000}, function () {
                if (data.status === 'OK') window.location.href = "/";
                layer.closeAll();
            });
        });
        return false;
    });

});