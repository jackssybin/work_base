layui.use(['form','jquery','layer','laydate'],function(){
    var form = layui.form,
        $    = layui.jquery,
        layer = layui.layer;
    var laydate = layui.laydate;
    $("#phoneArea").hide();

    form.verify({
        showAndHide : function(value,item) {
            if($(item).is(":visible")){
                if (value == "" || value == null || value == undefined) {
                    return '必填项不能为空';
                }
            }

        }
    });


    form.on("submit(addRegister)",function(data){
        // var rndContent = document.getElementById("rndContent").value;
        // data.field.rndContent = rndContent;
        // console.log(data.field.rndTargetSelect)

        var loadIndex = layer.load(2, {
            shade: [0.3, '#333']
        });

        if(data.field.cardType  == undefined ){
            layer.alert("请选择卡商类型")
            layer.close(loadIndex);
            return false;
        }
        console.log(JSON.stringify(data.field))
        $.ajax({
            type:"POST",
            url:"/bzRegister/add",
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


    form.on('select(cardType)', function(data){
        console.log(data.elem); //得到select原始DOM对象
        console.log(data.value); //得到被选中的值
        console.log(data.othis); //得到美化后的DOM对象
        if("KA_NONG"==data.value){
            $("#phoneArea").show();
        }else{
            $("#phoneArea").hide();
        }
    });


    //日期时间范围
    laydate.render({
        elem: '#startTimeStr'
        ,type: 'datetime'
    });

});