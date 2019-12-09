layui.use(['layer','form','upload'], function() {
    var $ = layui.$;
    var upload = layui.upload;
    upload.render({
        elem: '#uploadExcel',
        data:{
            ac_city:$("#ac_region").val(),
            ac_region_name:$("#ac_region").innerText(),
            ac_source:$("#ac_source").val(),
            ac_tags:$("#ac_tags").val()
        },
        url:"uploadExcel",
        accept:"file",
        acceptMime:"application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        auto:false,
        bindAction:"#confirmUpload",
        done: function(res, index, upload){
            console.log(res);
            //获取当前触发上传的元素，一般用于 elem 绑定 class 的情况，注意：此乃 layui 2.1.0 新增
            var item = this.item;
        }
    })
})