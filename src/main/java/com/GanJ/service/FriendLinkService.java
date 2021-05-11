package com.GanJ.service;

import com.GanJ.entity.FriendLink;
import com.GanJ.utils.DataMap;

/**
 * @author: GanJ
 * @Date: 2021/2/16 17:08
 * Describe:
 */
public interface FriendLinkService {

    DataMap addFriendLink(FriendLink friendLink);

    DataMap getAllFriendLink();

    DataMap updateFriendLink(FriendLink friendLink, int id);

    DataMap deleteFriendLink(int id);

    DataMap getFriendLink();
}
