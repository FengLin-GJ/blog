package com.GanJ.dao;

import com.GanJ.entity.Archive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author GanJ
 * @date 2021/4/5 10:42
 * Describe: 归档sql语句
 */
@Repository
public interface ArchiveRepository extends JpaRepository<Archive, Integer> {

    @Query(value="select archiveName from archives order by id desc", nativeQuery = true)
    List<String> findArchives();

    @Query(value = "select IFNULL(max(id),0) from archives where archiveName=?1", nativeQuery = true)
    int findArchiveNameByArchiveName(String archiveName);
}
