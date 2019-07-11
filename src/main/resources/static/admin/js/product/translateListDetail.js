layui.use(['layer','form','table'], function() {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        t;                  //表格数据变量

    t = {
        elem: '#userTable',
        url:'/admin/product/translateList',
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
            {field:'productId', title: '产品id', width:'10%'},
            {field:'productName',  title: '产品名称',    width:'10%'},
            {field:'phoneNumber',     title: '手机号',    width:'16%' },
            {field:'clickNumber',     title: 'pv',    width:'16%' },
            {field:'mobileSystem',       title: '操作系统',    width:'12%'},
            {field:'mobileVersion', title: '浏览器内核', width:'8%'},
            {field:'mobileBrand', title: '手机品牌', width:'8%'},
            {field:'gmtCreate',  title: '创建时间',width:'14%', templet:'<span>{{ layui.laytpl.toDateString(d.gmtCreate) }}</span>'}
        ]]
    };
    table.render(t);
    //功能按钮
    var active={
        exportTranslateExcel : function(){
            var url ="/admin/product/writeTranslateExcel";
            $('<form method="get" action="' + url + '"></form>').appendTo('body').submit().remove();
        },

    };

    $('.layui-inline .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    //搜索
    form.on("submit(searchForm)",function(data){
        t.where = data.field;
        table.reload('userTable', t);
        return false;
    });

});