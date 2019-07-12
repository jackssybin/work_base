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
            limits:[ 20, 50]
        },
        width: $(parent.window).width()-223,
        cols: [[
            {type:'checkbox'},
            {field:'productId', title: '产品id', width:'5%'},
            {field:'prodctName',  title: '产品名称',    width:'10%'},
            {field:'productUrl',     title: '静态链接',    width:'16%' },
            {field:'phoneCount',  title: '手机号数量',    width:'9%'},
            {field:'status',       title: '状态',    width:'8%',templet:function (d) {
                    if(0==d.status){
                       return "已创建";
                    }else if(1==d.status){
                        return "跑批中";
                    }else{
                        return "已完成"
                    }
                }},
            {field:'remark', title: '备注', width:'20%'},
            {field:'gmtCreate',  title: '创建时间',width:'10%', templet:'<span>{{ layui.laytpl.toDateString(d.gmtCreate) }}</span>'}, //单元格内容水平居中
            {field:'gmtModified',  title: '更新时间',width:'10%', templet:'<span>{{ layui.laytpl.toDateString(d.gmtModified) }}</span>'}, //单元格内容水平居中
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
            var innerDiv ="";
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
                        innerDiv += "<div class='sort_div layui-btn-normal' ondrop=\"drop(event,this)\" ondragover=\"allowDrop(event)\" draggable=\"true\" ondragstart=\"drag(event, this)\" id='"+data[i].productId+"'>"+data[i].prodctName+"</div>"
                    }
                    productIds=productIds.substr(0,productIds.length-1);
                    console.log("productIds:"+productIds)
                }
                if(data.length > 1) {
                    layer.open({
                        type: 1
                        , title: "请拖动调整顺序" //不显示标题栏
                        , closeBtn: false
                        , area: '300px;'
                        , shade: 0.8
                        , id: 'LAY_layuipro' //设定一个id，防止重复弹出
                        , btn: ['确定导出', '取消']
                        , btnAlign: 'c'
                        , moveType: 1 //拖拽模式，0或者1
                        , content: innerDiv
                        , yes: function (layero) {
                            var div = document.getElementsByClassName("sort_div");
                            productIds = "";
                            for (var i = 0; i < div.length; i++) {
                                productIds += div[i].id + ","
                            }
                            productIds = productIds.substr(0, productIds.length - 1);

                            var url = "/admin/product/writeProductTxt?productIds=" + productIds;
                            var dom = document.getElementById('ifile');
                            dom.src = url;
                            layer.closeAll();
                        }

                    });
                }else{
                    var url ="/admin/product/writeProductTxt?productIds="+productIds;
                    var dom=document.getElementById('ifile');
                    dom.src=url;
                }


                // $('<form method="get" action="' + url + '"></form>').appendTo('body').submit().remove();
                // window.location.href=url;


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
function allowDrop(ev) {
    ev.preventDefault();
}
var srcdiv = null;
var temp = null;
var tempId = null;
//当拖动时触发
function drag(ev, divdom) {
    srcdiv = divdom;
    temp = divdom.innerHTML;
    tempId = divdom.id;
}
//当拖动完后触发
function drop(ev, divdom) {
    ev.preventDefault();
    if (srcdiv !== divdom) {
        srcdiv.innerHTML = divdom.innerHTML;
        srcdiv.id = divdom.id
        divdom.innerHTML = temp;
        divdom.id=tempId;
    }
}
