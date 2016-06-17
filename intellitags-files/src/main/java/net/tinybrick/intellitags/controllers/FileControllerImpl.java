package net.tinybrick.intellitags.controllers;

import io.swagger.annotations.ApiOperation;
import net.tinybrick.intellitags.model.File;
import net.tinybrick.utils.crypto.MD5;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public void uploadEnd(String ticket) {
        logger.debug(String.format("Thicket %s is withdrawn", ticket));
    }

    /**
     * @param ticket
     * @return
     */
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
    public String patchSingleFile( MultipartFile files,  String ticket) {
        return null;
    }

    /**
     *
     * @param id
     */
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
    public Map<String, String> uploadMultipleFiles( MultipartFile[] files)
            throws IllegalStateException, IOException {
        Map<String, String> fileMap = new HashMap<String, String>();
        return fileMap;
    }

    public List<File> list( String[] patterns,
                            String[] tags,
                            int page) {
        return null;
    }


    public void rename( String id,  String newName) {
    }


    public void download( String id,
                           int offset,
                           int length,
                         HttpServletResponse response) {
    }
}
