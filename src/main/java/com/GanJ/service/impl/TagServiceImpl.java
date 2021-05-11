package com.GanJ.service.impl;

import com.GanJ.constant.CodeType;
import com.GanJ.dao.TagRepository;
import com.GanJ.entity.Tag;
import com.GanJ.service.TagService;
import com.GanJ.utils.DataMap;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: GanJ
 * @Date: 2021/2/16 19:50
 * Describe:
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Override
    public void addTags(String[] tags, int tagSize) {
        for (String tag : tags) {
            if (tagRepository.findIsExistByTagName(tag) == 0) {
                Tag t = new Tag(tag, tagSize);
                tagRepository.save(t);
            }
        }
    }

    @Override
    public DataMap findTagsCloud() {
        List<Tag> tags = tagRepository.findAllByOrderByIdDesc();
        Map<String, Object> dataMap = new HashMap<>(2);
        dataMap.put("result", JSONArray.fromObject(tags));
        dataMap.put("tagsNum", tags.size());
        return DataMap.success(CodeType.FIND_TAGS_CLOUD).setData(dataMap);
    }

    @Override
    public int countTagsNum() {
        return (int) tagRepository.count();
    }

    @Override
    public int getTagsSizeByTagName(String tagName) {
        return tagRepository.getTagsSizeByTagName(tagName);
    }
}
