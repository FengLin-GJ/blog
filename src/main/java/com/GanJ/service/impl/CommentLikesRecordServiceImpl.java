package com.GanJ.service.impl;

import com.GanJ.dao.CommentLikesRepository;
import com.GanJ.entity.CommentLikesRecord;
import com.GanJ.service.CommentLikesRecordService;
import com.GanJ.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: GanJ
 * @Date: 2021/2/12 13:47
 * Describe:
 */
@Service
public class CommentLikesRecordServiceImpl implements CommentLikesRecordService {

    @Autowired
    CommentLikesRepository commentLikesRepository;
    @Autowired
    UserService userService;

    @Override
    public boolean isLiked(long articleId, long pId, String username) {
        return commentLikesRepository.isLiked(articleId, pId, userService.findIdByUsername(username)) != null;
    }

    @Override
    public void insertCommentLikesRecord(CommentLikesRecord commentLikesRecord) {
        commentLikesRepository.save(commentLikesRecord);
    }

    @Override
    public void deleteCommentLikesRecordByArticleId(long articleId) {
        commentLikesRepository.deleteByArticleId(articleId);
    }
}
