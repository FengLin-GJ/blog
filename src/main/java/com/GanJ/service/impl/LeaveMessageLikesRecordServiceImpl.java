package com.GanJ.service.impl;

import com.GanJ.dao.LeaveMessageLikesRecordRepository;
import com.GanJ.entity.LeaveMessageLikesRecord;
import com.GanJ.service.LeaveMessageLikesRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: GanJ
 * @Date: 2021/2/13 15:32
 * Describe:
 */
@Service
public class LeaveMessageLikesRecordServiceImpl implements LeaveMessageLikesRecordService {

    @Autowired
    LeaveMessageLikesRecordRepository leaveMessageLikesRecordRepository;


    @Override
    public boolean isLiked(String pageName, int pId, int likeId) {

        return leaveMessageLikesRecordRepository.isLiked(pageName, pId, likeId) != null;
    }

    @Override
    public void insertLeaveMessageLikesRecord(LeaveMessageLikesRecord leaveMessageLikesRecord) {
        leaveMessageLikesRecordRepository.save(leaveMessageLikesRecord);
    }
}
