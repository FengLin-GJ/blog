package com.GanJ.dao;

import com.GanJ.entity.LeaveMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author GanJ
 * @date 2021/4/5 16:56
 * Describe:
 */
public interface LeaveMessageRepository extends JpaRepository<LeaveMessage, Integer> {

    int countByIsReadAndRespondentIdAndAnswererIdNot(int isRead, int respondentId, int answererId);

    List<LeaveMessage> findAllByPageNameAndPIdOrderByIdDesc(String pageName, int pId);

    List<LeaveMessage> findAllByPageNameAndPId(String pageName, int pId);

    List<LeaveMessage> findAllByRespondentIdAndAnswererIdNotOrderByIdDesc(int respondentId, int answererId);

    List<LeaveMessage> findAllByOrderByIdDesc();

    @Query(value = "select IFNULL(max(likes),0) from leave_message_record where pageName=?1 and id=?2", nativeQuery = true)
    int findLikesByPageNameAndId(String pageName, int id);

    @Transactional
    @Modifying
    @Query(value = "update leave_message_record set likes=likes+1 where pageName=?1 and id=?2", nativeQuery = true)
    void updateLikeByPageNameAndId(String pageName, int id);

    @Transactional
    @Modifying
    @Query(value = "update leave_message_record set isRead=0 where id=?1", nativeQuery = true)
    void readOneLeaveMessageRecord(int id);

    @Transactional
    @Modifying
    @Query(value = "update leave_message_record set isRead=0 where respondentId=?1", nativeQuery = true)
    void readLeaveMessageRecordByRespondentId(int respondentId);
}
