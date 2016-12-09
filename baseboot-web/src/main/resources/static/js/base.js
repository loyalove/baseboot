/**
 * Created by Loyal on 2016/12/9.
 */
//layui配置
layui.config({
    base: '/static/js/'
}).extend({
    'vue': 'base/vue',
    'common': 'base/common',
    'verify': 'base/verify'
});

layui.use(['form','verify'], function () {
    var form = layui.form(),
        verify = layui.verify;

    form.verify(verify);
});