layui.use(['form','jquery','layer','laydate'],function(){
    var form = layui.form,
        $    = layui.jquery,
        layer = layui.layer;
    var laydate = layui.laydate;




    form.on("submit(addTask)",function(data){
        var vbRealTime =  $("#realTimeGroup").val();
        var remark =  data.field.remark;
        remark = "#"+vbRealTime+"#"+remark;
        data.field.remark = remark;
        data.field.realtimeGroup = null;

        var loadIndex = layer.load(2, {
            shade: [0.3, '#333']
        });

        console.log(JSON.stringify(data.field))
        $.ajax({
            type:"POST",
            url:"/bzSend/add",
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



    //日期时间范围
    laydate.render({
        elem: '#startTimeStr'
        ,type: 'datetime'
    });

});