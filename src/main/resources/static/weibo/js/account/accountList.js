layui.use(['layer','form','table'], function() {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        t;                  //表格数据变量

    t = {
        elem: '#accountTable',
        url:'/bzAccount/list',
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
            {field:'accountUser', title: '账号id', width:'15%'},
            {field:'accountPwd',  title: '账号密码',width:'15%'},
            {field:'statusName',    title: '账号状态',width:'6%' },
            {field:'regionName',    title: '归属地区',width:'6%'},
            {field:'useNumber', title: '任务执行', width:'6%'},
            {field:'userSource',    title: '账号来源',width:'8%'},
            {field:'createDate',    title: '导入时间',width:'10%'},
            {field:'updateDate',    title: '更新时间',width:'10%'},
            {field:'invalidDate',  title: '失效时间',width:'10%'}, //单元格内容水平居中
            {fixed: 'right', align: 'center', toolbar: '#accountBar'}
        ]]
    };
    table.render(t);

    //监听工具条
    table.on('tool(accountBar)', function(obj){
        var data = obj.data;
        if(obj.event === "del"){
            layer.confirm("你确定要删除该账号么？",{btn:['是的,我确定','我再想想']},
                function(){
                    $.post("/bzAccount/delete",{"id":data.id},function (res){
                        if(res.success){
                            layer.msg("删除成功",{time: 1000},function(){
                                table.reload('accountTable', t);
                            });
                        }else{
                            layer.msg(res.message);
                        }

                    });
                }
            );
        }
    });



    //搜索
    form.on("submit(searchForm)",function(data){
        t.where = data.field;
        table.reload('accountTable', t);
        return false;
    });

});