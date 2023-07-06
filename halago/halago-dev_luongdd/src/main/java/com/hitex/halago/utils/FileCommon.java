package com.hitex.halago.utils;

import com.hitex.halago.config.Constant;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class FileCommon {
    public static String saveFile(byte[] bytes, String fileName) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String fd = simpleDateFormat.format(new Date());
        String pathFile = fd + "/" + fileName.replaceAll(" ", "_");
        String locationPath = Constant.IMAGE_CAMPAIGN_DIRECTION + pathFile;
        String locationUrl = Constant.IMAGE_CAMPAIGN_DOMAIN + pathFile;
        FileOutputStream fileOutputStream = FileUtils.openOutputStream(new File(locationPath));
        IOUtils.write(bytes, fileOutputStream);
        IOUtils.closeQuietly(fileOutputStream);
        return locationUrl;
    }
    public static String saveFileBase64(byte[] bytes, String fileData, String fileName) throws IOException {
        bytes = Base64.getDecoder().decode(fileData);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String fd = simpleDateFormat.format(new Date());
        String pathFile = fd + "/" + fileName.replaceAll(" ", "_");
        String locationPath = Constant.IMAGE_CAMPAIGN_DIRECTION + pathFile;
        String locationUrl = Constant.IMAGE_CAMPAIGN_DOMAIN + pathFile;
        FileOutputStream fileOutputStream = FileUtils.openOutputStream(new File(locationPath));
        IOUtils.write(bytes, fileOutputStream);
        IOUtils.closeQuietly(fileOutputStream);
        return locationUrl;
    }
}
