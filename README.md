4.1学生模块的实现
4.1.1登录模块实现
（1） 功能描述
学生与管理员共用一个登录模块，根据权限的不同登陆后的页面显示的也不相同，学生在登录的时候，使用的用户名也就是该学生的学号，管理员的则是admin，登陆的时候前端使用JQuery进行校验，后端校验则是从数据库拿到的数据是否与前端输入的一致，有一方校验不成功则提示登录失败，如果登陆成功则跳转到主页面。
（2） 登录界面设计如图 4-1
 
图 4-1 登录设计
（3） 代码实现   
                                                  	                Login.js
$.ajax({
            async: false,    //登陆时关闭异步，否则登陆成功提示不成功
            url: "/stu/user/userLogin.do",
            type: "POST",
            data: {
                username: username,
                password: password,
                role: role
            },
            success: function (result) {
                if (result.code) {
                    //登陆成功
                    window.location.href = result.url;
                } else {
                    $.messager.alert("提示消息", result.msg);
                }
            }
        });
4.1.2学生个人资料模块实现
（1） 功能描述
学生登陆之后，进入到学生个人信息主界面。个人信息界面主要是学生的名字，身份证号以及一些基本的信息，首次登录会提示修改密码，管理员未开启权限时，不能修改个人信息，开启权限后才可以修改个人信息。
（2） 界面展示如图4-2、图 4-3所示：
 
图 4-2 个人信息主界面
 
图 4-3 首次登录
（3） 核心代码
	StudentService
Map<String,Object> result = new HashMap<String,Object>();
        //查看开关状态
        int flag = studentDao.getSwitch();
        if(flag==1) {
            studentDao.updateMsg(stuId, idcard, sex, phone, qq, email, address);
            result.put("msg", "修改成功！");
            return result;
        }else{
            result.put("msg", "修改资料暂时未开通，请联系管理员开通");
            return result;
        }
4.1.3学生住宿模块实现 
（1）	功能描述
学生住宿信息由管理员添加，住宿信息分别为姓名，园区，楼栋和房间，未添加的时候会提示该学生还没有住宿信息，添加后直接显示该学生住宿信息。
（2）	页面展示如图 4-4和图 4-5所示：
 
图 4-4 住宿已添加
 
图 4-5 住宿未添加
（1）	核心代码
	stuDormitory.js
    $.ajax({
        url:"/stu/dormitory/getStuDormitoryInfo.do",
        type:"POST",
        data:{
            stuId:stuId
        },
        success:function(result){
            if(result.data!=undefined) {
                $("#zone").val(result.data.zone_name);
                $("#building").val(result.data.building);
                $("#room").val(result.data.room);
            }
            else
            {
                alert("该学生目前没有住宿信息！");
                $("#zone").val("没有住宿信息!");
                $("#building").val("没有住宿信息!");
                $("#room").val("没有住宿信息!");
            }
        }
    });
4.1.4学生选课模块实现
（1） 功能概述
学生选课模块可以进行搜索课程，选择所要选的课程，点击选课可以在之后获得成绩与相对应的学分绩点，，并在自己的成绩信息中显示出。
（2） 主页页面设计如图4-6所示：
 
图 4-6 点击选课
（3） 核心代码
	StuCourseService
    Map<String,Object> result = new HashMap<String,Object>();
        //查看学生选课是否开通
        int flag = courseDao.getCanGetCourse();
        if(flag==0){
            //没开通选课
            result.put("code",false);
            result.put("msg","选课暂未开通,请等待管理员开通后再试！");
            return result;
        }else {
            //开通了选课
            courseDao.getCourse(stuId,courseId);
            result.put("code",true);
            result.put("msg","选课成功！");
            return result;
        }
4.1.5学生成绩模块实现
（1） 功能描述
学生端的成绩模块可以查看学生自己所选课程的得分与获得的学分绩点情况，以及自己所选课程所属的课程类型。
（2） 我的学生管理页面设计与实现如图4-7所示：
 
图 4-7 成绩信息
（3） 核心代码
	GradeService
Map<String,Object> result = new HashMap<String,Object>();
        //绩点公式：每一科绩点乘以每一科学分加起来。除总学分
        //得到学生所有课程信息
        List<Map<String,Object>> data = new ArrayList<>();
        data = gradeDao.getAllGrade(stuId);
        if(data.size()==0){
            result.put("code",true);
            result.put("msg","暂时没有成绩!");
            return result;
        }
        int sum = 0;//每一科绩点乘以每一科学分加起来
        int countScore = 0;//总学分
        for(int i=0;i<data.size();i++){
            if(data.get(i).get("score")!=null) {
                int courseScore = Integer.parseInt(data.get(i).get("courseScore").toString());
                int grade = (Integer) data.get(i).get("score");
                double score = getCourseScore(grade);
                sum += courseScore * score;
                countScore += Integer.parseInt((String) data.get(i).get("courseScore"));
            }
        }
        double s = (double)sum/(double)countScore;
        float aa = new Double(s).floatValue();
        result.put("score",aa);
        result.put("code",true);
        return result;
4.1.6学生奖惩模块实现
（1） 功能概述
学生奖惩信息是由管理员端手动添加的，当管理员添加了基本的奖励或者惩罚信息，学生端对应的模块就会显示出该信息。
（2） 页面设计如图 4-8、4-9所示：
 
图 4-8 奖励信息
 
图 4-9 惩罚信息
（3） 核心代码
	PunishService
   Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> data = new ArrayList<>();
        int begin = (page-1)*rows;
        int count = rewardPunishDao.getStuRewardCount(stuId);
        data=rewardPunishDao.getStuReward(stuId,begin,rows);
        result.put("total",count);
        result.put("rows",data);
   return result;
4.1.7学生素拓模块实现
（1） 功能概述
素拓信息显示的是该同学所参加的活动以及每个活动所获得的的学分，同时也显示了该学生参加该活动的时间。
（2） 页面设计如图4-10所示：
 
图 4-10 素拓信息
（3） 核心代码
	RewardService
   Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> data = new ArrayList<>();
        int begin = (page-1)*rows;
        int count = rewardPunishDao.getStuQualityCount(stuId);
        data=rewardPunishDao.getStuQuality(stuId,begin,rows);
        result.put("total",count);
        result.put("rows",data);
   return result;
4.1.8学生请假模块实现
（1） 功能概述
学生请假模块是学生在一些必要情况不能正常去上课的情况，可以向管理员提出请假请求，在请假页面，点击我要请假，填上理由，请假开始时间和结束时间，点击提交，管理员可以对该申请进行批准或者删除。
（2） 页面设计如图4-11、4-12所示：
 
图 4-11 提出申请
 
图4-12 页面展示
（3） 核心代码
	AFLSservice
  public Map<String,Object>addStuAFL(String stuId,String reason,String start_time,String end_time)
    {
        Map<String,Object> result = new HashMap<String,Object>();
        aflDao.addStuAFL(stuId,reason,start_time,end_time);
        result.put("code",true);
        return result;
    }
4.2 管理员模块的实现
4.2.1 学生信息管理设计与实现
（1） 概要介绍
管理员用户登陆后的页面为学生信息管理页面，在该页面可以对所有学生的基本信息进行修改删除以及查找，该页面也可以每次添加一个学生，也可以直接使用Excel批量导入学生，该页面也有学生权限控制，点击开启学生可以自己修改信息，同时该页面可以给学生做奖惩信息和素拓信息的添加删除，也可以对学生的住宿信息进行修改。
（2） 登录界面设计如图 4-13、4-14所示：
 
图 4-13 批量导入学生信息
 
图 4-14 单个添加学生

（3） 核心代码
	Excelutil
    public static List<Object[]> importExcel(String fileName) {
        log.info("导入解析开始，fileName:{}",fileName);
        try {
            List<Object[]> list = new ArrayList<>();
            InputStream inputStream = new FileInputStream(fileName);
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            //获取sheet的行数
            int rows = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < rows; i++) {
                //过滤表头行
                if (i == 0) {
                    continue;
                }
                //获取当前行的数据
                Row row = sheet.getRow(i);
                Object[] objects = new Object[row.getPhysicalNumberOfCells()];
                int index = 0;
                for (Cell cell : row) {
                    if (cell.getCellType().equals(CellType.NUMERIC)) {
                        objects[index] = cell.getNumericCellValue();
                    }
                    if (cell.getCellType().equals(CellType.STRING)) {
                        objects[index] = cell.getStringCellValue();
                    }
                    index++;
                }
                list.add(objects);
            }
            log.info("导入文件解析成功！");
            return list;
        }catch (Exception e){
            log.info("导入文件解析失败！");
            e.printStackTrace();
        }
        return null;
}
4.2.2 课程管理模块设计与实现
（1） 模块功能概述
课程管理模块主要主要显示的是所有的课程名、课程种类、学分和选课人数，当我们选择一个课程，点击选课的时候会出现没有选这门课的所有学生，选择一个学生，点击确定，该学生就确定选了这门课，点击课程对应的详细信息时，显示的是所有已选该门课程的学生个人基本信息，成绩按钮可以给学生录入成绩，点击删除按钮可以删除该门课程，该页面有一个滚轮，选择是否开启，开启后学生可选课，未开启，学生不可以选课。这个页面下可以增加课程分类以及添加对应分类下的课程，点击添加课程，输入课程名，学分，以及所属分类，点击添加，添加成功则页面就会显示该门课程。
（2） 统计信息模块页面设计如图 4-15、4-16所示：
 
图 4-15 选课
 
图 4-16 录入成绩
（3） 核心代码
CourseManagerService
    public List<Map<String,Object>> getStudentCombox(int courseId) {
        List<Map<String,Object>> combox = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
        //查找该课程所有已选择学生，例如：已选择2
        List<Integer> stus = courseManagerDao.getAllStuByCourse(courseId);
        //查找所有的学生，例如：全部学生有1,2,3
        data= courseManagerDao.getStudentCombox();
        //下拉列表中过滤已选择该课程的学生
        for(int i=0;i<stus.size();i++){
            for(int j=0;j<data.size();j++){
                if(stus.get(i)==(Integer) data.get(j).get("id")){
                    //把已选择选择的学生从列表中删除
                    data.remove(j);
                    //后面不可能还有，直接跳出循环，开始下一次遍历
                    break;
                }
            }
        }
        combox=data;
        return combox;
    }
4.2.3 请假管理模块设计与实现
（1） 请假管理模块概述
管理员模块中的请假信息管理模块，整体界面主要显示的是所有请假学生的姓名，请假理由，开始时间，结束时间，学生在学生端提交请假信息审核，管理员可以在管理员模块中点击批准，如果是已批准状态，再点已批准则显示的是已经批准过的不可以在批准，批准成功的话学生端是可以查看到自己的请假信息被批准了的，点击删除，可以删除掉该学生的请假信息。
（2） 用户管理页面设计如图 4-17、4-18所示：
 
图 4-17 批准
 
图 4-18 删除
（3） 核心代码
	AFLManage.rxml
<update id="approveStuAFL" parameterType="int">
        update stu_afl
        set stu_afl.approve='1'
        where stu_afl.student_id=#{param1} and stu_afl.id=#{param2}
    </update>
4.2.4 住宿管理模块设计与实现
（1） 住宿管理模块概述
管理员模块中的住宿信息管理，整体界面显示的是学生的名字，所在的片区，楼栋以及房间，分别可以点击添加园区和删除园区，在学生信息模块添加好的住宿信息，可以在该界面显示出该学生所住宿的地方。
（2） 住宿管理模块页面设计如图 4-19、4-20所示：
 
图 4-19 添加园区
 
图 4-20 删除园区
（3） 核心代码
	StuManagerController
    public Map<String,Object> getAllStuDormitory(String keywords, int page, int rows) {
        Map<String,Object> result = new HashMap<>();
        List<Map<String,Object>> data = new ArrayList<>();
        if(keywords==null){
            keywords="";
        }
        int begin = (page-1)*rows;
        int total = dormitoryDao.getTotalStuDormitory(keywords);
        data = dormitoryDao.getAllStuDormitory(keywords,begin,rows);
        result.put("total",total);
        result.put("rows",data);
        return result;
    }



