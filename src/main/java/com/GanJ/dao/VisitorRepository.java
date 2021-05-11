package com.GanJ.dao;

import com.GanJ.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author GanJ
 * @date 2021/4/5 17:19
 * Describe:
 */
public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    Visitor findByPageName(String pageName);

    @Query(value = "select visitorNum from visitor where pageName='totalVisitor'", nativeQuery = true)
    long getTotalVisitor();

    @Transactional
    @Modifying
    @Query(value = "insert into visitor(visitorNum,pageName) values(0,?1)", nativeQuery = true)
    void save(String pageName);

    @Transactional
    @Modifying
    @Query(value = "update visitor set pageName=?1 where visitorNum=?2", nativeQuery = true)
    void updateVisitorNumByPageName(String pageName, String visitorNum);
}
