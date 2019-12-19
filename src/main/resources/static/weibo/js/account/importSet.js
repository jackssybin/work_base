layui.use(['layer','form','upload'], function() {
    var $ = layui.$;
    var upload = layui.upload;
    upload.render({
        elem: '#uploadExcel',
        data:{
            ac_region_name:$("#ac_regions").text(),
            ac_region_id:$("#ac_regions").val(),
            ac_source:$("#ac_source").val(),
            ac_tags:$("#ac_tags").val()
        },
        url:"uploadExcel",
        accept:"file",
        acceptMime:"application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        auto:false,
        bindAction:"#confirmUpload",
        done: function(res, index, upload){
            if(res.success){
                layer.msg("启用成功",{time: 1000},function(){
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.location.reload();//刷新父页面，注意一定要在关闭当前iframe层之前执行刷新
                    parent.layer.close(index); //再执行关闭
                });
            }else{
                layer.msg(res.message);
            }
        }
    })
})