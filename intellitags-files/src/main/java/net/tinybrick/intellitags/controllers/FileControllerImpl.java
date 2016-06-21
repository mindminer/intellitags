package net.tinybrick.intellitags.controllers;

import net.tinybrick.intellitags.model.File;
import net.tinybrick.utils.crypto.MD5;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangji on 2016/6/17.
 */
@RestController
public class FileControllerImpl implements FileController{
    private static Logger logger = Logger.getLogger(FileController.class);

    /**
     * Call this method to get an temporary ticket before you upload a big file.
     *
     * With this ticket, you are allowed to try to upload the file from the offset where it broken from the last time.
     *
     * @return
     */
    @Override
    public String[] uploadBegin() {
        return new String[] {MD5.hash(String.valueOf(new Date()))};
    }

    /**
     * Withdraw the temporary upload ticket when file upload is finished.
     *
     * Once a ticket is withdrawn, it can't be used any more, the file will be frozen also.
     *
     * @param ticket
     */
    @Override
    public void uploadEnd(String ticket) {
        logger.debug(String.format("Thicket %s is withdrawn", ticket));
    }

    /**
     * @param ticket
     * @return
     */
    @Override
    public Integer queryOffset( String ticket){
        return 1024;
    }

    /**
     * 上载单个文件
     *
     * @param files
     * @param ticket
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @Override
    public String uploadSingleFile( MultipartFile files, String ticket)
            throws IllegalStateException, IOException {
        return null;
    }

    /**
     *
     * @param files
     * @param ticket
     * @return
     */
    @Override
    public String patchSingleFile( MultipartFile files,  String ticket) {
        return null;
    }

    /**
     *
     * @param id
     */
    @Override
    public void deleteFile( String id) {
    }

    /**
     * 同时上载多个文件
     *
     * @param files
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @Override
    public Map<String, String> uploadMultipleFiles( MultipartFile[] files)
            throws IllegalStateException, IOException {
        Map<String, String> fileMap = new HashMap<String, String>();
        return fileMap;
    }

    @Override
    public List<File> list( String[] patterns,
                            String[] tags,
                            Integer page) {
        return null;
    }

    @Override
    public void rename( String id,  String newName) {
    }


    public void download( String id,
                          Integer offset,
                          Integer length,
                         HttpServletResponse response) {
    }
}
