package com.GanJ.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.GanJ.dao.FeedBackRepository;
import com.GanJ.entity.FeedBack;
import com.GanJ.service.FeedBackService;
import com.GanJ.service.UserService;
import com.GanJ.utils.DataMap;
import com.GanJ.utils.StringUtil;
import com.GanJ.utils.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: GanJ
 * @Date: 2021/2/23 17:21
 * Describe:
 */
@Service
public class FeedBackServiceImpl implements FeedBackService {

    @Autowired
    FeedBackRepository feedBackRepository;
    @Autowired
    UserService userService;

    @Override
    public void submitFeedback(FeedBack feedBack) {
        TimeUtil timeUtil = new TimeUtil();
        feedBack.setFeedbackDate(timeUtil.getFormatDateForSix());
        feedBackRepository.save(feedBack);
    }

    @Override
    public DataMap getAllFeedback(int rows, int pageNum) {
        PageHelper.startPage(pageNum, rows);
        List<FeedBack> feedBacks = feedBackRepository.findAllByOrderByIdDesc();
        PageInfo<FeedBack> pageInfo = new PageInfo<>(feedBacks);

        JSONObject returnJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject feedbackJson;

        for(FeedBack feedBack : feedBacks){
            feedbackJson = new JSONObject();
            feedbackJson.put("feedbackContent", feedBack.getFeedbackContent());
            feedbackJson.put("person", userService.findUsernameById(feedBack.getPersonId()));
            feedbackJson.put("feedbackDate", feedBack.getFeedbackDate());
            if(feedBack.getContactInfo() == null){
                feedbackJson.put("contactInfo", StringUtil.BLANK);
            } else {
                feedbackJson.put("contactInfo", feedBack.getContactInfo());
            }
            jsonArray.add(feedbackJson);
        }

        returnJson.put("result",jsonArray);

        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());
        returnJson.put("pageInfo",pageJson);
        return DataMap.success().setData(returnJson);
    }
}
