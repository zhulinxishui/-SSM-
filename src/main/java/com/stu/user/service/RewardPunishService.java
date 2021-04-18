package com.stu.user.service;

import com.stu.user.dao.RewardPunishDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RewardPunishService {
    @Autowired
    private RewardPunishDao rewardPunishDao;

    public Map<String,Object>getStuReward(int stuId,int page, int rows)
    {
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> data = new ArrayList<>();
        int begin = (page-1)*rows;
        int count = rewardPunishDao.getStuRewardCount(stuId);
        data=rewardPunishDao.getStuReward(stuId,begin,rows);
        result.put("total",count);
        result.put("rows",data);
        return result;
    }

    public Map<String,Object>getStuPunish(int stuId,int page, int rows)
    {
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> data = new ArrayList<>();
        int begin = (page-1)*rows;
        int count = rewardPunishDao.getStuPunishCount(stuId);
        data=rewardPunishDao.getStuPunish(stuId,begin,rows);
        result.put("total",count);
        result.put("rows",data);
        return result;
    }


    public Map<String,Object>getStuRewardCount(int stuId,int page, int rows)
    {
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> data = new ArrayList<>();
        int begin = (page-1)*rows;
        int count = rewardPunishDao.getStuQualityCount(stuId);
        data=rewardPunishDao.getStuQuality(stuId,begin,rows);
        result.put("total",count);
        result.put("rows",data);
        return result;
    }




}
