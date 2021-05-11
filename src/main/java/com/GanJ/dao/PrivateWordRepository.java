package com.GanJ.dao;

import com.GanJ.entity.PrivateWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author GanJ
 * @date 2021/4/5 17:04
 * Describe:
 */
public interface PrivateWordRepository extends JpaRepository<PrivateWord, Long> {

    List<PrivateWord> findAllByPublisherIdOrderByIdDesc(int publisherId);

    @Transactional
    @Modifying
    @Query(value = "update privateword set replyContent=?1,replierId=?2 where id=?3", nativeQuery = true)
    void replyPrivateWord(String replyContent, int replierId, int id);
}
