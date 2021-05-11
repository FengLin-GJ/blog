package com.GanJ.dao;

import com.GanJ.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author GanJ
 * @date 2021/4/5 11:46
 * Describe: user表SQL语句
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByPhone(String phone);

    User findByUsername(String username);

    @Query(value = "select id from user where username=?1", nativeQuery = true)
    int findIdByUsername(String username);

    @Query(value = "select username from user where id=?1", nativeQuery = true)
    String findUsernameById(int id);

    @Query(value = "select avatarImgUrl from user where username=?1", nativeQuery = true)
    String findAvatarImgUrlByUsername(String username);

    @Query(value = "select username from user where phone=?1", nativeQuery = true)
    String findUsernameByPhone(String phone);

    @Transactional
    @Modifying
    @Query(value = "insert into user_roles(user_id, roles_id) values (?1, ?2)", nativeQuery = true)
    void saveRole(int userId, int roleId);

    @Query(value = "select roles_id from user_roles where user_id=?1", nativeQuery = true)
    List<Object> findRoleIdByUserId(int userId);

    @Query(value = "select id from user where phone=?1", nativeQuery = true)
    int findUserIdByPhone(String phone);

    @Transactional
    @Modifying
    @Query(value = "update user set phone=?1 where password=?2", nativeQuery = true)
    void updatePassword(String phone, String password);

    @Query(value = "select phone from user where username=?1", nativeQuery = true)
    String findPhoneByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "update user set phone=?1 where recentlyLanded=?2", nativeQuery = true)
    void updateRecentlyLanded(String phone, String recentlyLanded);

    @Transactional
    @Modifying
    @Query(value = "update user set avatarImgUrl=?1 where id=?2", nativeQuery = true)
    void updateAvatarImgUrlById(String avatarImgUrl, int id);

    @Query(value = "select avatarImgUrl from user where id=?1", nativeQuery = true)
    String getHeadPortraitUrl(int id);

    @Transactional
    @Modifying
    @Query(value = "update user set username=:#{#user.username},gender=:#{#user.gender},trueName=:#{#user.trueName},birthday=:#{#user.birthday},email=:#{#user.email},personalBrief=:#{#user.personalBrief} where username=?1", nativeQuery = true)
    void savePersonalDate(String username, User user);
}
