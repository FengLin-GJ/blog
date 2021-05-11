package com.GanJ.service;

import com.GanJ.utils.DataMap;

/**
 * @author: GanJ
 * @Date: 2021/2/18 12:07
 * Describe: 归档业务操作
 */
public interface ArchiveService {

    /**
     * 获得归档信息
     * @return
     */
    DataMap findArchiveNameAndArticleNum();

    /**
     * 添加归档日期
     * @param archiveName
     */
    void addArchiveName(String archiveName);

}
