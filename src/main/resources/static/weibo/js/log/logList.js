layui.use(['layer','form','table'], function() {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        t;                  //表格数据变量

    t = {
        elem: '#logTable',
        url:'/bzLog/list',
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
            {field:'taskId', title: '任务id', width:'6%'},
            {field:'accountUser',    title: '关联账号',width:'12%' },
            {field:'regionId',    title: '城市id',width:'6%'},
            {field:'proxyIp', title: '代理ip', width:'8%'},
            {field:'loginSystemStatus',    title: '登录',width:'6%'},
            {field:'commentStatus',    title: '评论',width:'6%'},
            {field:'commentContent',    title: '评论内容',width:'12%'},
            {field:'focusStatus',    title: '关注',width:'6%'},
            {field:'raisesStatus',    title: '点赞',width:'6%'},
            {field:'forwardStatus',    title: '发转发无评论',width:'7%'},
            {field:'forwardCommentStatus',    title: '转发有评论',width:'6%'},
            {field:'remark',    title: '备注',width:'12%',templet: function(d){
                return d.remark.replace(new RegExp("\n","gm"),"<br/>");
                }},
            {field:'createDate',  title: '创建时间',width:'10%'}//单元格内容水平居中
        ]]
    };
    table.render(t);



    //搜索
    form.on("submit(searchForm)",function(data){
        t.where = data.field;
        table.reload('logTable', t);
        return false;
    });

});