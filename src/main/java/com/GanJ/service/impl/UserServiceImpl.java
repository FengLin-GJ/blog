package com.GanJ.service.impl;

import com.GanJ.constant.CodeType;
import com.GanJ.constant.RoleConstant;
import com.GanJ.dao.ArticleRepository;
import com.GanJ.dao.UserRepository;
import com.GanJ.entity.User;
import com.GanJ.service.UserService;
import com.GanJ.utils.AliYunOSSClientUtil;
import com.GanJ.utils.DataMap;
import com.GanJ.utils.FileUtil;
import com.GanJ.utils.StringUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.List;

/**
 * @author: GanJ
 * @Date: 2021/2/13 15:56
 * Describe: user表接口具体业务逻辑
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Override
    public User findUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    public String findUsernameById(int id) {
        return userRepository.findUsernameById(id);
    }

    @Override
    public DataMap insert(User user) {

        user.setUsername(user.getUsername().trim().replaceAll(" ", StringUtil.BLANK));
        String username = user.getUsername();

        if (username.length() > 35 || StringUtil.BLANK.equals(username)) {
            return DataMap.fail(CodeType.USERNAME_FORMAT_ERROR);
        }
        if (userIsExist(user.getPhone())) {
            return DataMap.fail(CodeType.PHONE_EXIST);
        }
        user.setAvatarImgUrl("https://ganj-blog.oss-cn-chengdu.aliyuncs.com/public/user/avatar/" + username + "/" + username + ".png");
        userRepository.save(user);
        int userId = userRepository.findUserIdByPhone(user.getPhone());
        insertRole(userId, RoleConstant.ROLE_USER);
        return DataMap.success();
    }

    @Override
    public int findUserIdByPhone(String phone) {
        return 0;
    }

    @Override
    public void updatePasswordByPhone(String phone, String password) {
        userRepository.updatePassword(phone, password);
//        密码修改成功后注销当前用户
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Override
    public String findPhoneByUsername(String username) {
        return userRepository.findPhoneByUsername(username);
    }

    @Override
    public int findIdByUsername(String username) {
        return userRepository.findIdByUsername(username);
    }

    @Override
    public String findUsernameByPhone(String phone) {
        return userRepository.findUsernameByPhone(phone);
    }

    @Override
    public void updateRecentlyLanded(String username, String recentlyLanded) {
        String phone = userRepository.findPhoneByUsername(username);
        userRepository.updateRecentlyLanded(phone, recentlyLanded);
    }

    @Override
    public boolean usernameIsExist(String username) {
        User user = userRepository.findByUsername(username);
        return user != null;
    }

    @Override
    public boolean isSuperAdmin(String phone) {
        int userId = userRepository.findUserIdByPhone(phone);
        List<Object> roleIds = userRepository.findRoleIdByUserId(userId);
        for (Object i : roleIds) {
            if ((int) i == 3) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateAvatarImgUrlById(String avatarImgUrl, int id) {
        userRepository.updateAvatarImgUrlById(avatarImgUrl, id);
    }

    @Override
    public DataMap getHeadPortraitUrl(int id) {
        String avatarImgUrl = userRepository.getHeadPortraitUrl(id);
        return DataMap.success().setData(avatarImgUrl);
    }

    @Override
    public DataMap getUserPersonalInfoByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return DataMap.success().setData(user);
    }

    @Override
    public DataMap savePersonalDate(User user, String username) {

        user.setUsername(user.getUsername().trim().replaceAll(" ", StringUtil.BLANK));
        String newName = user.getUsername();
        if (newName.length() > StringUtil.USERNAME_MAX_LENGTH) {
            return DataMap.fail(CodeType.USERNAME_TOO_LANG);
        } else if (StringUtil.BLANK.equals(newName)) {
            return DataMap.fail(CodeType.USERNAME_BLANK);
        }

        int status;
        //改了昵称
        if (!newName.equals(username)) {
            if (usernameIsExist(newName)) {
                return DataMap.fail(CodeType.USERNAME_EXIST);
            }
            status = CodeType.HAS_MODIFY_USERNAME.getCode();
            //注销当前登录用户
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        //没改昵称
        else {
            status = CodeType.NOT_MODIFY_USERNAME.getCode();
        }
        userRepository.savePersonalDate(username, user);

        return DataMap.success(status);
    }

    @Override
    public String getHeadPortraitUrlByUserId(int userId) {
        return userRepository.getHeadPortraitUrl(userId);
    }

    @Override
    public int countUserNum() {
        return (int) userRepository.count();
    }

    /**
     * 增加用户权限
     *
     * @param userId 用户id
     * @param roleId 权限id
     */
    private void insertRole(int userId, int roleId) {
        userRepository.saveRole(userId, roleId);
    }

    /**
     * 通过手机号判断用户是否存在
     *
     * @param phone 手机号
     * @return true--存在  false--不存在
     */
    private boolean userIsExist(String phone) {
        User user = userRepository.findByPhone(phone);
        return user != null;
    }

    @Override
    public void uploadTxt(String url, String name) throws IOException {
        String path = ResourceUtils.getURL("classpath:").getPath() + name + ".txt";
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileUtil fileUtil = new FileUtil();
        fileUtil.uploadFile(file, url);
        file.delete();
    }

    public void uploadPng(String name) throws IOException {
        String path= ClassUtils.getDefaultClassLoader().getResource("static/img").getPath()+"/logo_icon.png";
        //初始化OSSClient
        OSSClient ossClient = AliYunOSSClientUtil.getOSSClient();
        // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        InputStream inputStream = new FileInputStream(path);
        // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
        ossClient.putObject("ganj-blog", "public/user/avatar/"+name+"/"+name+".png", inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
