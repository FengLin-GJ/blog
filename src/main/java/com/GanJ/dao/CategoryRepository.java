package com.GanJ.dao;

import com.GanJ.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author GanJ
 * @date 2021/4/5 16:15
 * Describe: 分类sql
 */
public interface CategoryRepository extends JpaRepository<Categories, Integer> {

    @Transactional
    @Modifying
    void deleteByCategoryName(String categoryName);

    @Transactional
    @Modifying
    @Query(value = "insert into categories(categoryName) value(:#{#categories.categoryName})", nativeQuery = true)
    void saveCategories(Categories categories);

    @Query(value = "select categoryName from categories", nativeQuery = true)
    List<String> findCategoriesName();

    @Query(value = "select IFNULL((select id from categories where categoryName=?1),0)", nativeQuery = true)
    int findIsExistByCategoryName(String categoryName);
}
