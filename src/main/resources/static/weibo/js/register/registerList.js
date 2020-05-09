layui.use(['layer','form','table'], function() {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        t;                  //表格数据变量

    t = {
        elem: '#registerTable',
        url:'/bzRegister/list',
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
            {field:'id',title:'任务Id',width:'5%'},
            {field:'cartTypeName',  title: '卡商类型',    width:'12%'},
            {field:'registerCount',     title: '任务进度',   width:'12%', templet:function(d){
                return d.finishCount+"/"+d.registerCount;
                } },
            {field:'statusName',       title: '任务状态',    width:'12%'},
            {field:'regionName',       title: '代理地区',    width:'12%'},
            {field:'startTime', title: '任务开始时间', width:'12%',templet:'<span>{{ layui.laytpl.toDateString(d.startTime) }}</span>'},
            // {field:'createDate',    title: '任务创建时间',width:'12%'},
            {field:'updateDate',    title: '任务更新时间',width:'12%',templet:'<span>{{ layui.laytpl.toDateString(d.updateDate) }}</span>'},
            {fixed: 'right',title:'操作', align: 'center', toolbar: '#registerBar',width:'12%'}
        ]]
    };
    table.render(t);

    //监听工具条
    table.on('tool(registerList)', function(obj){
        var data = obj.data;
        if(obj.event === "checkLog2"){
            var editIndex = layer.open({
                title : "任务日志",
                type : 2,
                content : "/bzLog/listParam?id="+obj.data.id,
                success : function(layero, index){
                    setTimeout(function(){
                        layer.tips('点击此处返回任务列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    },500);
                }
            });
            //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
            $(window).resize(function(){
                layer.full(editIndex);
            });
            layer.full(editIndex);
        }

        if(obj.event === "del"){
            layer.confirm("你确定要删除该任务么？",{btn:['是的,我确定','我再想想']},
                function(){
                    $.post("/bzRegister/delete",{"id":data.id},function (res){
                        if(res.success){
                            layer.msg("删除成功",{time: 1000},function(){
                                table.reload('registerTable', t);
                            });
                        }else{
                            layer.msg(res.message);
                        }

                    });
                }
            );
        }
        if(obj.event === "stop" || obj.event === "continue" ||obj.event ===  "finish"){
            console.log(obj.event)
            var status =0 ;
            if(obj.event === "stop" ){
                status = 2;
            }else if(obj.event === "continue"){
                status = 1;
            }else{
                status =3;
            }
            layer.confirm("你确定要改变任务状态么？",{btn:['是的,我确定','我再想想']},
                function(){
                    $.post("/bzRegister/updateStatus",{"id":data.id,"status":status},function (res){
                        if(res.success){
                            layer.msg("操作成功",{time: 1000},function(){
                                table.reload('registerTable', t);
                            });
                        }else{
                            layer.msg(res.message);
                        }

                    });
                }
            );
        }
    });
    
    //功能按钮
    var active={
        addTask : function(){
            var addIndex = layer.open({
                title : "添加任务",
                type : 2,
                content : "/bzRegister/add",
                success : function(layero, addIndex){
                    setTimeout(function(){
                        layer.tips('点击此处返回任务列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    },500);
                }
            });
            //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
            $(window).resize(function(){
                layer.full(addIndex);
            });
            layer.full(addIndex);
        }
    };

    $('.layui-inline .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    //搜索
    form.on("submit(searchForm)",function(data){
        t.where = data.field;
        table.reload('registerTable', t);
        return false;
    });

});