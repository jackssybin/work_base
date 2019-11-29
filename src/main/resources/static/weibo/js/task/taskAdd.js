layui.use(['form','jquery','layer','laydate'],function(){
    var form = layui.form,
        $    = layui.jquery,
        layer = layui.layer;   //默认启用用户
    var laydate = layui.laydate;

        form.on("submit(addTask)",function(data){
        var loadIndex = layer.load(2, {
            shade: [0.3, '#333']
        });


        //判断用户是否启用


        $.ajax({
            type:"POST",
            url:"/bzTask/add",
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

    form.on('switch(taskSpeed)', function(data){
        if(data.elem.checked){
            $("#taskSpeed").val(1);
        }else{
            $("#taskSpeed").val(2);
        }
    });

    form.on('switch(comment)', function(data){
        console.log("comment:{}"+data)
        if(data.elem.checked){
            $("#comment").val(1);
        }else{
            $("#comment").val(0);
        }
    });

    form.on('switch(commentType)', function(data){
        console.log("commentType:{}"+data)
        if(data.elem.checked){//万能
            $("#commentType").val(1);
        }else{
            $("#commentType").val(2);
        }
    });

    form.on('switch(focus)', function(data){
        console.log("focus:{}"+data)
        if(data.elem.checked){
            $("#focus").val(1);
        }else{
            $("#focus").val(0);
        }
    });

    form.on('switch(raises)', function(data){
        console.log("raises:{}"+data)
        if(data.elem.checked){
            $("#raises").val(1);
        }else{
            $("#raises").val(0);
        }
    });

    form.on('switch(forward)', function(data){
        console.log("forward:{}"+data)
        if(data.elem.checked){
            $("#forward").val(1);
        }else{
            $("#forward").val(0);
        }
    });

    form.on('switch(forwardComment)', function(data){
        console.log("forwardComment:{}"+data)
        if(data.elem.checked){
            $("#forwardComment").val(1);
        }else{
            $("#forwardComment").val(0);
        }
    });

    form.on('switch(collect)', function(data){
        console.log("collect:{}"+data)
        if(data.elem.checked){
            $("#collect").val(1);
        }else{
            $("#collect").val(0);
        }
    });







    //日期时间范围
    laydate.render({
        elem: '#test10'
        ,type: 'datetime'
        ,range: true
    });

});