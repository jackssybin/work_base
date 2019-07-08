layui.use(['layer','form','table'], function() {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        t;                  //表格数据变量

    t = {
        elem: '#userTable',
        url:'/admin/product/list',
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
            {field:'productId', title: '产品id', width:'10%'},
            {field:'prodctName',  title: '产品名称',    width:'10%'},
            {field:'productUrl',     title: '静态链接',    width:'16%' },
            {field:'phoneCount',  title: '手机号数量',    width:'10%'},
            {field:'status',       title: '状态',    width:'12%',templet:function (d) {
                    if(0==d.status){
                       return "已创建";
                    }else if(1==d.status){
                        return "跑批中";
                    }else{
                        return "已完成"
                    }
                }},
            {field:'remark', title: '备注', width:'20%'},
            {field:'gmtCreate',  title: '创建时间',width:'14%', templet:'<span>{{ layui.laytpl.toDateString(d.gmtCreate) }}</span>'}, //单元格内容水平居中
            {fixed: 'right', align: 'center', toolbar: '#userBar'}
        ]]
    };
    table.render(t);
    //监听工具条
    table.on('tool(userList)', function(obj){
        var data = obj.data;
        console.log(data)
        if(obj.event === "del"){
            layer.confirm("你确定要删除该产品任务么？",{btn:['是的,我确定','我再想想']},
                function(){
                    $.post("/admin/product/delete",{"productId":data.productId},function (res){
                        if(res.success){
                            layer.msg("删除成功",{time: 1000},function(){
                                table.reload('userTable', t);
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
        addUser : function(){
            var addIndex = layer.open({
                title : "添加产品任务",
                type : 2,
                content : "/admin/product/add",
                success : function(layero, addIndex){
                    setTimeout(function(){
                        layer.tips('点击此处返回会员列表', '.layui-layer-setwin .layui-layer-close', {
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
        },
        exportProductTxt : function(){
            var checkStatus = table.checkStatus('userTable'),
                data = checkStatus.data;
            if(data.length > 0){
                console.log(data)
                var productIds=$("#productIds").val()
                if(!productIds){
                    productIds="";
                    var phoneCount=0;
                    for(var i=0 ;i<data.length ;i++){
                        productIds+=data[i].productId+","
                        if(i==0){
                            phoneCount=data[i].phoneCount
                        }
                        if(0==phoneCount){
                            layer.msg("传入的产品手机号数量为空，无法导出",{time:1000});
                            return;
                        }
                        if(phoneCount!=data[i].phoneCount){
                            layer.msg("传入的产品手机号数量不匹配，无法导出",{time:1000});
                            return;
                        }

                    }
                    productIds=productIds.substr(0,productIds.length-1);
                    console.log("productIds:"+productIds)
                }

                var url ="/uv/writeProductTxt?productIds="+productIds;
                // $('<form method="get" action="' + url + '"></form>').appendTo('body').submit().remove();
                // window.location.href=url;
                var dom=document.getElementById('ifile');
                dom.src=url;

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