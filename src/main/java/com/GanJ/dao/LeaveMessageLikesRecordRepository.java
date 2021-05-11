package com.GanJ.dao;

import com.GanJ.entity.LeaveMessageLikesRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author GanJ
 * @date 2021/4/5 16:52
 * Describe: 留言点赞sql
 */
public interface LeaveMessageLikesRecordRepository extends JpaRepository<LeaveMessageLikesRecord, Long> {

    @Query(value = "select likeDate from leave_message_likes_record where pageName=?1 and pId=?2 and likerId=?3", nativeQuery = true)
    String isLiked(String pageName, int pId, int likerId);
}
