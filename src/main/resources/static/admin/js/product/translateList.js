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
            limits:[ 20, 50]
        },
        width: $(parent.window).width()-223,
        cols: [[
            {type:'checkbox'},
            {field:'productId', title: '产品id', width:'8%'},
            {field:'productName',  title: '产品名称',    width:'20%'},
            {field:'phoneCount',  title: '发送量',    width:'18%'},
            {field:'pvNumber',     title: 'pv',    width:'15%' },
            {field:'uvNumber',     title: 'uv',    width:'15%' },
            {field:'uvPercent',     title: 'uv转化率',    width:'24%' },

        ]]
    };
    table.render(t);
    //功能按钮
    var active={
        exportTranslateExcel : function(){

            var checkStatus = table.checkStatus('userTable'),
                data = checkStatus.data;
            console.log(data)
            if(data.length > 0){
                if(data.length > 1){
                    layer.msg("只能导出一个产品得转化率详情",{time:1000});
                    return ;
                }
                var productId=data[0].productId;

                // var url ="/uv/writeTranslateExcel?productId="+productId;
                // $('<form method="get" action="' + url + '"></form>').appendTo('body').submit().remove();

                var url = "/admin/product/writeTranslateExcel?productId=" + productId;
                var dom = document.getElementById('ifile');
                dom.src = url;
            }else{
                layer.msg("请选择需要导出的产品",{time:1000});
            }


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
