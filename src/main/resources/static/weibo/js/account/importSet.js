layui.use(['layer','form','upload'], function() {
    var $ = layui.$;
    var upload = layui.upload;
    upload.render({
        elem: '#uploadExcel',
        data:{
            im_city:$("#im_city").val(),
            im_source:$("#im_source").val(),
            im_tags:$("#im_tags").val()
        },
        url:"bzAccount/uploadExcel",
        accept:"file",
        acceptMime:"application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        auto:false,
        bindAction:"#confirmUpload",
        done: function(res, index, upload){
            //获取当前触发上传的元素，一般用于 elem 绑定 class 的情况，注意：此乃 layui 2.1.0 新增
            var item = this.item;
        }
    })
})