layui.use(['layer','form','table'], function() {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        t;                  //表格数据变量

    t = {
        elem: '#tagTable',
        url:'/bzTags/list',
        method:'post',
        page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            groups: 6, //只显示 1 个连续页码
            first: "首页", //显示首页
            last: "尾页", //显示尾页
            limits:[3,10, 20, 30]
        },
        width: $(parent.window).width()-223,
        cols: [[
            {type:'checkbox'},
            {field:'tagCode', title: '分组代码', width:'15%'},
            {field:'tagName',  title: '分组名称',width:'40%'},
            {fixed: '', align: 'center', toolBar:"#tagBar"}
        ]]
    };
    table.render(t);


    //监听工具条
    table.on('tool(tagBar)', function(obj){
        var data = obj.data;
        if(obj.event === "del"){
            layer.confirm("你确定要删除该分组么？",{btn:['是的,我确定','我再想想']},
                function(){
                    $.post("/bzTags/delete",{"id":data.id},function (res){
                        if(res.success){
                            layer.msg("删除成功",{time: 1000},function(){
                                table.reload('tagTable', t);
                            });
                        }else{
                            layer.msg(res.message);
                        }

                    });
                }
            );
        }
        if(obj.event === "edit"){
            console.log(obj.id)
        }
    });



    $('.layui-inline .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    //搜索
    form.on("submit(searchForm)",function(data){
        t.where = data.field;
        table.reload('accountTable', t);
        return false;
    });

});