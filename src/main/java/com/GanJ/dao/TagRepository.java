package com.GanJ.dao;

import com.GanJ.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author GanJ
 * @date 2021/4/5 17:09
 * Describe:
 */
public interface TagRepository extends JpaRepository<Tag, Integer> {

    List<Tag> findAllByOrderByIdDesc();

    @Query(value = "select IFNULL(max(id),0) from tags where tagName=?1", nativeQuery = true)
    int findIsExistByTagName(String tagName);

    @Query(value = "select tagSize from tags where tagName=?1", nativeQuery = true)
    int getTagsSizeByTagName(String tagName);

}
