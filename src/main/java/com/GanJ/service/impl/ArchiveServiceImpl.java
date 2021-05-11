package com.GanJ.service.impl;

import com.GanJ.dao.ArchiveRepository;
import com.GanJ.entity.Archive;
import com.GanJ.service.ArchiveService;
import com.GanJ.service.ArticleService;
import com.GanJ.utils.DataMap;
import com.GanJ.utils.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: GanJ
 * @Date: 2021/2/13 12:08
 * Describe:
 */
@Service
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired
    ArchiveRepository archiveRepository;
    @Autowired
    ArticleService articleService;

    @Override
    public DataMap findArchiveNameAndArticleNum() {
        List<String> archives = archiveRepository.findArchives();
        JSONArray archivesJsonArray = new JSONArray();
        JSONObject archiveJson;
        TimeUtil timeUtil = new TimeUtil();
        for(String archiveName : archives){
            archiveJson = new JSONObject();
            archiveJson.put("archiveName",archiveName);
            archiveName = timeUtil.timeYearToWhippletree(archiveName);
            archiveJson.put("archiveArticleNum",articleService.countArticleArchiveByArchive(archiveName));
            archivesJsonArray.add(archiveJson);
        }
        JSONObject returnJson = new JSONObject();
        returnJson.put("result", archivesJsonArray);
        return DataMap.success().setData(returnJson);
    }

    @Override
    public void addArchiveName(String archiveName) {
        int archiveNameIsExist = archiveRepository.findArchiveNameByArchiveName(archiveName);
        if(archiveNameIsExist == 0){
            Archive archive=new Archive();
            archive.setArchiveName(archiveName);
            archiveRepository.save(archive);
        }
    }

}
