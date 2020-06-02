layui.use(['form','jquery','layer','laydate'],function(){
    var form = layui.form,
        $    = layui.jquery,
        layer = layui.layer;
    var laydate = layui.laydate;

    form.verify({
        showAndHide : function(value,item) {
            if($(item).is(":visible")){
                if (value == "" || value == null || value == undefined) {
                    return '必填项不能为空';
                }
            }
        }
    });

    form.on("submit(addLastRead)",function(data){
        var loadIndex = layer.load(2, {
            shade: [0.3, '#333']
        });

        console.log(JSON.stringify(data.field))
        $.ajax({
            type:"POST",
            url:"/bzRegister/addLastRead",
            dataType:"json",
            contentType:"application/json",
            data:JSON.stringify(data.field),
            success:function(res){
                layer.close(loadIndex);
                if(res.success){
                    parent.layer.msg("任务添加成功!",{time:1500},function(){
                        //刷新父页面
                        parent.location.reload();
                    });
                }else{
                    layer.msg(res.message);
                }
            }
        });
        return false;
    });



});