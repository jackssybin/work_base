


layui.use(['layer','form','upload'], function() {
    var $ = layui.$;
    var upload = layui.upload;

    var regionName="";
    var regionId="";
    var source="";
    var tags = "";

    upload.render({
        elem: '#uploadExcel',
        url:"uploadExcel",
        accept:"file",
        acceptMime:"application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        auto:false,
        bindAction:"#confirmUpload"
        ,before: function(input){
        var data = {};
        data.ac_region_name = $("#ac_regions").find("option:selected").text();
        data.ac_region_id = $("#ac_regions").val();
        data.ac_source =$("#ac_source").val();
        data.ac_tags =$("#ac_tags").val();
        this.data=data;
        },
        done: function(res, index, upload){
            if(res.success){
                layer.msg("导入成功",{time: 1000},function(){
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.location.reload();//刷新父页面，注意一定要在关闭当前iframe层之前执行刷新
                    parent.layer.close(index); //再执行关闭
                });
            }else{
                layer.msg(res.message);
            }
        }
    })

    $('.layui-inline .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

})