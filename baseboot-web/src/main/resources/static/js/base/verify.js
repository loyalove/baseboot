/**
 * Created by Loyal on 2016/12/9.
 */

//表单验证自定义规则
layui.define(function (exports) {

    var verify = {
        //密码校验
        password: [
            /^[\S]{6,12}$/
            , '密码必须6到12位，且不能出现空格'
        ],
        //用户名校验
        username: [
            /^\S{6,}$/
            , '用户名不能小于6个字符'
        ],
        //其他校验
        other: function (value) {
            if (value == 'demo')
                return '';
        }
    };

    exports('verify', verify);
});