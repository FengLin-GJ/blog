package com.GanJ.service.impl;

import com.GanJ.constant.CodeType;
import com.GanJ.dao.FriendLinkRepository;
import com.GanJ.entity.FriendLink;
import com.GanJ.service.FriendLinkService;
import com.GanJ.utils.DataMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: GanJ
 * @Date: 2021/2/16 17:09
 * Describe:
 */
@Service
@Slf4j
public class FriendLinkServiceImpl implements FriendLinkService {

    @Autowired
    FriendLinkRepository friendLinkRepository;

    @Override
    public DataMap addFriendLink(FriendLink friendLink) {
        int id = friendLinkRepository.findIsExistByBlogger(friendLink.getBlogger());
        if(id == 0){
            friendLinkRepository.save(friendLink);
            return DataMap.success(CodeType.ADD_FRIEND_LINK_SUCCESS)
                    .setData(friendLink.getId());
        } else {
            return DataMap.fail(CodeType.FRIEND_LINK_EXIST);
        }
    }

    @Override
    public DataMap getAllFriendLink() {
        List<FriendLink> links = friendLinkRepository.findAll();
        return DataMap.success().setData(links);
    }

    @Override
    public DataMap updateFriendLink(FriendLink friendLink, int id) {
        friendLinkRepository.updateFriendLink(id,friendLink);
        return DataMap.success(CodeType.UPDATE_FRIEND_LINK_SUCCESS);
    }

    @Override
    public DataMap deleteFriendLink(int id) {
        friendLinkRepository.deleteById(id);
        return DataMap.success(CodeType.DELETE_FRIEND_LINK_SUCCESS);
    }

    @Override
    public DataMap getFriendLink() {
        List<FriendLink> links = friendLinkRepository.findAll();
        return DataMap.success().setData(links);
    }
}
