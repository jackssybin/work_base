layui.use(['form','element','layer','jquery'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        element = layui.element,
        $ = layui.jquery;

    var md = new MobileDetect(window.navigator.userAgent);
    console.log( md.mobile() );          // 'Sony'   手机型号
    // console.log( md.phone() );           // 'Sony'
    console.log( md.tablet() );          // null
    console.log( md.userAgent() );       // 'Safari'    浏览器型号
    console.log( md.os() );              // 'AndroidOS'  操作系统
    console.log( md.is('iPhone') );      // false
    console.log( md.is('bot') );         // false
    console.log( md.version('Webkit') );         // 534.3
    console.log( md.versionStr('Build') );       // '4.1.A.0.562'
    console.log( md.match('playstation|xbox') ); // false
    console.log("===")
    console.log(window.navigator.userAgent)
    console.log($("#productUrl").val())
    // private Integer productId;
    // private String phoneNumber;
    // private Double pvNumber;
    // private Integer clickNumber;
    // private String mobileSystem;
    // private String mobileVersion;
    // private String mobileBrand;
    var dataJson={};
    dataJson.productId=$("#productId").val();
    dataJson.phoneNumber=$("#phoneNumber").val();
    var mobileSystem=md.os();
    var mobileVersion=md.userAgent();
    var mobileBrand=md.mobile();
    console.log("mobileSystem="+mobileSystem+" mobileVersion="+mobileVersion+" mobileBrand="+mobileBrand)
    if(!mobileSystem){
        mobileSystem="windows/unix/mac";
        mobileBrand="computer"
        mobileVersion=window.navigator.userAgent;
    }

    dataJson.mobileSystem=mobileSystem;
    dataJson.mobileBrand=mobileBrand;
    dataJson.mobileVersion =mobileVersion;
    console.log(dataJson)
    $.ajax({
        type:"POST",
        url:"/uv/saveTranslate",
        dataType:"json",
        contentType:"application/json",
        data:JSON.stringify(dataJson),
        success:function(res){

                alert(res)
        }
    });
    // window.location.href=$("#productUrl").val();


});