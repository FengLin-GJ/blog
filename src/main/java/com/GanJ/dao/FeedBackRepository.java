package com.GanJ.dao;

import com.GanJ.entity.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author GanJ
 * @date 2021/4/5 16:43
 * Describe:
 */
public interface FeedBackRepository extends JpaRepository<FeedBack, Integer> {

    List<FeedBack> findAllByOrderByIdDesc();
}
