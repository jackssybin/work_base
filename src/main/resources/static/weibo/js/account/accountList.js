layui.use(['layer','form','table'], function() {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        t;                  //表格数据变量

    t = {
        id:"accountTable",
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
            {field:'accountUser', title: '账号id', width:'15%',event:"checkLog",style:"color:blue;cursor:pointer"},
            {field:'accountPwd',  title: '账号密码',width:'15%'},
            {field:'statusName',    title: '账号状态',width:'6%' },
            {field:'regionName',    title: '归属地区',width:'6%'},
            {field:'useNumber', title: '任务执行', width:'6%'},
            {field:'userSource',    title: '账号来源',width:'8%'},
            {field:'createDate',    title: '导入时间',width:'10%',templet:'<span>{{ layui.laytpl.toDateString(d.createDate) }}</span>'},
            {field:'updateDate',    title: '更新时间',width:'10%',templet:'<span>{{ layui.laytpl.toDateString(d.updateDate) }}</span>'},
            {field:'invalidDate',  title: '失效时间',width:'10%',templet:'<span>{{ layui.laytpl.toDateString(d.invalidDate) }}</span>'}, //单元格内容水平居中
            {fixed: 'right', align: 'center', title:"操作", toolbar: '#accountBar',width:'15%'}
        ]]
    };
    table.render(t);

    //监听工具条
    table.on('tool(accountList)', function(obj){
        var data = obj.data;
        if(obj.event === "checkLog"){
            var editIndex = layer.open({
                title : "账号日志",
                type : 2,
                content : "/bzLog/listParam?id="+obj.data.accountUser,
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
        if(obj.event === "use"){
            layer.confirm("你确定要启用该账号么？",{btn:['是的,我确定','我再想想']},
                function(){
                    $.post("/bzAccount/updateStatus",{"id":data.id,"status":1},function (res){
                        if(res.success){
                            layer.msg("启用成功",{time: 1000},function(){
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




    var active = {
        exportAccountList:function(){
            $.post("/bzAccount/exportAccountList",{"ac_key":$("#ac_key").val(),"ac_status":$("#ac_status").val()},function (res){

            });
        },
        updateStatus:function () {

            var checkStatus = table.checkStatus('accountTable');
            var ids = [];
            $(checkStatus.data).each(function (i, o) {//o即为表格中一行的数据
                ids.push(o.id);
            });
            if (ids.length < 1) {
                layer.msg('无选中项');
                return false;
            }
            console.log(ids.join(","))

            layer.confirm("确定要批量修改账号状态为正常吗？",{btn:['是的,我确定','我再想想']},
                function(){
                    $.post("/bzAccount/batchUpdateStatus",{"ids":ids.join(","),"status":1},function (res){
                        if(res.success){
                            layer.msg("修改成功",{time: 1000},function(){
                                table.reload('accountTable', t);
                            });
                        }else{
                            layer.msg(res.message);
                        }

                    });
                }
            );
        },
        importAccount: function(){
            var addIndex = layer.open({
                title : "导入设置",
                type : 2,
                content : "/bzAccount/importSet",
                success : function(layero, addIndex){
                    setTimeout(function(){
                        layer.tips('点击此处返回账号列表', '.layui-layer-setwin .layui-layer-close', {
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
    }

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