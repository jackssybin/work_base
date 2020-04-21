var index = parent.layer.getFrameIndex(window.name); //当前窗口索引
layui.use(['form','jquery','layer'],function(){
    var form = layui.form,
        $    = layui.jquery,
        layer = layui.layer;
    layer.ready(function(){
        var value = $("#taskType input[name = 'taskType']:checked");
        var flag = false;
        var rndFlag = false;
        for(var i =0;i<value.length;i++){
            if($(value[i]).val() == 1){
                flag = true;
            }
            if($(value[i]).val()==5){
                rndFlag = true;
            }
        }

        if(rndFlag){
            $("#rndArea").show();
        }else{
            $("#rndArea").hide();
        }
        if(flag){
            $("#commentArea").show();
        }else{
            $("#commentArea").hide();
        }
    });





});