layui.use(['form','jquery','layer','laydate'],function(){
    var form = layui.form,
        $    = layui.jquery,
        layer = layui.layer;
    var laydate = layui.laydate;

    $("#delInput").hide();


    form.on("submit(addTask)",function(data){
        var taskUrlList = $("input[name = 'targetUrl']");
        var taskCountList = $("input[name = 'taskCountList']");

        var targetUrl = "";
        var targetNumber = "";
        for (var i =0;i<taskUrlList.size();i++){
            targetUrl +=$(taskUrlList[i]).val() +",";
            targetNumber +=$(taskCountList[i]).val() +",";
        }

        if(targetUrl != "" ){
            targetUrl = targetUrl.substring(0,targetUrl.length-1);
            targetNumber = targetNumber.substring(0,targetNumber.length-1);
        }

        data.field.targetUrl = targetUrl;
        data.field.taskCountList = targetNumber;



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
            url:"/bzTask/batchAdd",
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


    $("#delInput").click(function () {
        var taskItem = $(".taskItem");
        if(taskItem.size() == 2 ){
            $("#delInput").hide();
        }
        $(taskItem[taskItem.size()-1]).remove();
        return false;
    })


    $("#addInput").click(function () {

        $("#delInput").show();
        var str = ' <div class="layui-form-item taskItem">'+
            '<div class="layui-inline">'+
            '<label class="layui-form-label">任务地址</label>'+
            ' <div class="layui-input-block">'+
            '   <input type="text" class="layui-input" name="targetUrl" lay-verify="required" placeholder="请输入任务地址url">' +
            '   </div>   </div>'+
            '   <div class="layui-inline">'+
            '<label class="layui-form-label">任务总量</label>' +
            '            <div class="layui-input-block">' +
            '            <input type="number" class="layui-input" name="taskCountList" lay-verify="required" placeholder="请输入任务总量">\n' +
            '            </div>' +
            '            </div>' +
            '            </div>';
        // $("#taskItem").html(str)
        $("#btnDiv").before(str);
        form.render();
        return false;

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