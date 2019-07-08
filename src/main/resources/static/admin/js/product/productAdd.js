layui.use(['form','jquery','layer'],function(){
    var form = layui.form,
        $    = layui.jquery,
        layer = layui.layer;   //默认启用用户

    form.on("submit(addUser)",function(data){
        var loadIndex = layer.load(2, {
            shade: [0.3, '#333']
        });

        var formData = new FormData();

        formData.append("phoneFile",$("#phoneFile")[0].files[0]);
        formData.append("prodctName",$("#prodctName").val())
        formData.append("productUrl",$("#productUrl").val())
        formData.append("remark",$("#remark").val())

        $.ajax({
            type:"POST",
            url:"/admin/product/add",
            data:formData,
            dataType:"json",
            mimeType:"multipart/form-data",
            cache:false,
            processData:false,
            contentType:false,
            success:function(res){
                layer.close(loadIndex);
                if(res.success){
                    parent.layer.msg("用户添加成功!",{time:1500},function(){
                        //刷新父页面
                        parent.location.reload();
                    });
                }else{
                    layer.msg(res.message);
                }

            }
        });

        // $.ajax({
        //     type:"POST",
        //     url:"/admin/product/add",
        //     dataType:"json",
        //     contentType:"application/json",
        //     data:JSON.stringify(data.field),
        //     success:function(res){
        //         layer.close(loadIndex);
        //         if(res.success){
        //             parent.layer.msg("用户添加成功!",{time:1500},function(){
        //                 //刷新父页面
        //                 parent.location.reload();
        //             });
        //         }else{
        //             layer.msg(res.message);
        //         }
        //     }
        // });
        return false;
    });



});