
$(document).ready(function(){
    initDataGrid();
    initJD();
});
var initDataGrid = function(){
    var stuId = $("#userId").val();
    $("#studentGradeBox").datagrid({
        url:"/stu/rewardpunish/getQuality.do",
        width:'auto',   //表格宽度
        height:'500px',
        columns:[[
            {field:'id',title:'编号',width:'auto',hidden:'true'},
            {field:'name',title:'课程名称',width:'200',align : 'center'},
            {field:'score',title:'分数',width:'100',align : 'center',
                formatter:function(value, row, index) {
                    if (row.score == undefined || row.score == "") {
                        var str = '暂无';
                        return str;
                    }else{
                        var str = row.score;
                        return str;
                    }
                }
            },
            {field:'time',title:'时间',width:'200',align : 'center',
                formatter:function(value, row, index) {
                    if (row.time == undefined || row.time == "") {
                        var str = '暂无时间记录';
                        return str;
                    }else{
                        var str = row.time;
                        return str;
                    }
                }
            }
        ]],
        border:false,
        pagination:true,
        pageList:[18,25,30,40,50],
        pageSize:18,
        striped:true,
        nowrap:true,
        autoRowHeight:true,
        checkOnSelect:true,
        fitColumns:true,
        loadMsg:'怕是等一下哦',
        toolbar:"#toolBar",
        queryParams:{
            stuId:stuId
        }
    });
};