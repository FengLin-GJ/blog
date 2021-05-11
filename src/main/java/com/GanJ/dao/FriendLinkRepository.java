package com.GanJ.dao;

import com.GanJ.entity.FriendLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author GanJ
 * @date 2021/4/5 16:46
 * Describe:
 */
public interface FriendLinkRepository extends JpaRepository<FriendLink, Integer> {

    @Transactional
    @Modifying
    void deleteById(int id);

    @Transactional
    @Modifying
    @Query(value = "update friendlink set blogger=:#{#friendLink.blogger},url=:#{#friendLink.url} where id=?1", nativeQuery = true)
    void updateFriendLink(int id, FriendLink friendLink);

    @Query(value = "select IFNULL((select id from friendlink where blogger=?1), 0)", nativeQuery = true)
    int findIsExistByBlogger(String blogger);
}
