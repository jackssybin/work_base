layui.use(['form','jquery','layer','laydate'],function(){
    var form = layui.form,
        $    = layui.jquery,
        layer = layui.layer;
    var laydate = layui.laydate;




    form.on("submit(addTask)",function(data){


        var loadIndex = layer.load(2, {
            shade: [0.3, '#333']
        });

        if(data.field.taskType  == undefined ){
            layer.alert("请选择任务类型")
            layer.close(loadIndex);
            return false;
        }
        var taskType= "";
        var value = $("#taskType input[name = 'taskType']:checked");
        for(var i =0;i<value.length;i++){
            if(i == 0){
                taskType =  $(value[i]).val()
            }else{
                taskType +=","+  $(value[i]).val();
            }
        }
        data.field.taskType = taskType;
        if(taskType.indexOf("1") > -1){
            if(data.field.commentType == 2){
                console.log(data.field.commentContent)
                if(data.field.commentContent  ==  ""){
                    layer.alert("请输入评论内容")
                    layer.close(loadIndex);
                    return false;
                }
            }else{
                data.field.commentContent = "";
            }
        }else{
            data.field.commentContent = "";
        }
        console.log(JSON.stringify(data.field))
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



    $("#rndTargetSelect").click(function () {
        var value = $("#rndTargetSelect input[name = 'rndTargetSelect']:checked").val();
        if(value == 1){
            $("#rndIdInput").show();
        }else{
            $("#rndIdInput").hide();
        }
    })


    /**
     * 开启回复
     */
    $("#rndComment").click(function () {
        var value = $("#commentType input[name = 'rndComment']:checked").val();
        if(value == 1){
            console.log("rnd SHOW");
            $("#rndArea").show();
        }else{
            $("#rndArea").hide();
        }
    })

    $("#commentType").click(function () {
       var value = $("#commentType input[name = 'commentType']:checked").val();
        if(value == 1){
            $("#commentContent").hide();
        }else{
            $("#commentContent").show();
        }
    })




    $("#taskType").click(function(){
        var value = $("#taskType input[name = 'taskType']:checked");
        var flag = false;
        for(var i =0;i<value.length;i++){
            if($(value[i]).val() == 1){
                flag = true;
            }
        }

        if(flag){
            $("#commentArea").show();
        }else{
            $("#commentArea").hide();
        }

    })




    //日期时间范围
    laydate.render({
        elem: '#startTimeStr'
        ,type: 'datetime'
    });

});