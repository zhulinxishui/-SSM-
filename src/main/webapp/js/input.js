$("#submit").click(function()
{


    var fileName=document.getElementById("files").value;
    $.ajax
    ({
        url:"/stu/stuManager/importExcel.do",
        type:'POST',
        data:{
            fileName: fileName
        },

        success: function(result)
        {
            if(result.code)
            {
                $.messager.alert({
                    title:"提示",
                    icon:"success",
                    msg:"上传成功"
                });
                setTimeout("location.href = '/index.jsp'",2000);
            }else{
                $.messager.alert({
                    title:"提示",
                    icon:"error",
                    msg:result.msg
                });
                return
            }
        }
    });
})
/*点击返回*/
$("#fan").click(function()
{
    window.location.href="/index.jsp";
})



