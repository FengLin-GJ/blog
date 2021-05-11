package com.GanJ.utils;

import com.aliyun.oss.OSSClient;
import com.GanJ.constant.OSSClientConstants;
import com.aliyun.oss.model.GetObjectRequest;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;

/**
 * @author: GanJ
 * @Date: 2021/2/13 11:29
 * Describe: 文件工具
 */
public class FileUtil {

    /**
     * 上传文件到阿里云OSS
     *
     * @param file 文件流
     * @return 返回文件URL
     */
    public String uploadFile(File file, String subCatalog) {

        //初始化OSSClient
        OSSClient ossClient = AliYunOSSClientUtil.getOSSClient();

        String md5Key = AliYunOSSClientUtil.uploadObject2OSS(ossClient, file, OSSClientConstants.BACKET_NAME,
                OSSClientConstants.FOLDER + subCatalog + "/");
        String url = AliYunOSSClientUtil.getUrl(ossClient, md5Key);
        String picUrl = "https://" + OSSClientConstants.BACKET_NAME + "." + OSSClientConstants.ENDPOINT +
                "/" + OSSClientConstants.FOLDER + subCatalog + "/" + file.getName();

        //删除临时生成的文件
        File deleteFile = new File(file.toURI());
        deleteFile.delete();

        return picUrl;

    }

    /**
     * 下载文件
     */
    public String downloadFile(String username, String subCatalog) throws FileNotFoundException {

        //初始化OSSClient
        OSSClient ossClient = AliYunOSSClientUtil.getOSSClient();

        // 填写Bucket名称。
        String bucketName = OSSClientConstants.BACKET_NAME;
        // 填写不包含Bucket名称在内的Object完整路径，例如testfolder/exampleobject.txt。
        String objectName = OSSClientConstants.FOLDER + subCatalog + "/" + username + ".txt";

        String path = ResourceUtils.getURL("classpath:").getPath() + username + ".txt";

        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(path));

        // 关闭OSSClient。
        ossClient.shutdown();

        return path;
    }

    /**
     * base64字符转换成file
     *
     * @param destPath 保存的文件路径
     * @param base64   图片字符串
     * @param fileName 保存的文件名
     * @return file
     */
    public File base64ToFile(String destPath, String base64, String fileName) {
        File file = null;
        //创建文件目录
        String filePath = destPath;
        File dir = new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            file = new File(filePath + "/" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 将file转换成base64字符串
     *
     * @param path
     * @return
     */
    public String fileToBase64(String path) {
        String base64 = null;
        InputStream in = null;
        try {
            File file = new File(path);
            in = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            in.read(bytes);
            base64 = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

    /**
     * MultipartFile类型文件转File
     *
     * @return File类型文件
     */
    public File multipartFileToFile(MultipartFile multipartFile, String filePath, String fileName) {
        File f = null;
        File dir = new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        if (StringUtil.BLANK.equals(multipartFile) || multipartFile.getSize() <= 0) {
            multipartFile = null;
        } else {
            try {
                InputStream ins = multipartFile.getInputStream();
                f = new File(filePath + fileName);
                OutputStream os = new FileOutputStream(f);
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

}
