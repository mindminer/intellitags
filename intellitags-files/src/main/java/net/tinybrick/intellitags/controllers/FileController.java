package net.tinybrick.intellitags.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.tinybrick.utils.crypto.MD5;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangji on 2016/6/15.
 */
@RestController
@RequestMapping("/v1.0")
@Api(value = "有关传送文件的API", tags = {"版本v1.0"}, basePath= "/v1.0")
//@ApiDoc
public class FileController {
    private static Logger logger = Logger.getLogger(FileController.class);

    /**
     * Call this method to get an temporary ticket before you upload a big file.
     *
     * With this ticket, you are allowed to try to upload the file from the offset where it broken from the last time.
     *
     * @return
     */
    @ApiOperation(value = "申请临时传送令牌",
            notes = "为防止文件过大，导致传送中断，在发送文件之前可以申请一个零时令牌，系统根据零时令牌的存活状态来判断文件是否已经传送结束" +
                    "如果文件传送过程中出现中断，可以通过临时令牌实现断点续传。<p>"
                    + "<br>输入 ticket")
    @RequestMapping(value = "/ticket",
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.ALL_VALUE})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
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
    @ApiOperation(value = "销毁令牌",
            notes = "文件发送完成后只需要销毁零时令牌即可冻结文件。请及时销毁零时令牌，系统有可能不定期清除未冻结的临时文件导致数据丢失。<p>"
                    + "<br>输入 ticket")
    @RequestMapping(value = {"/ticket/{ticket}"},
            method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void uploadEnd(@PathVariable("ticket") String ticket) {
        logger.debug(String.format("Thicket %s is withdrawn", ticket));
    }

    /**
     * @param ticket
     * @return
     */
    @ApiOperation(value = "检查文件偏移量",
            notes = "如果发生文件传送中断，可以通过这个方法查寻文件当前的偏移量，程序将返回已接收文件的长度。<p>"
                    + "<br>输入 ticket"
                    + "<p>输出 offset")
    @RequestMapping(value = {"/file/offset/{ticket}"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public Integer queryOffset(@PathVariable("ticket") String ticket){
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
    @ApiOperation(value = "上载单个文件",
            notes = "这个接口一次只接受一个文件。ticket参数为可选，如果不提供ticket，系统将不支持断点续传。<p>"
                    + "<br>输入 file"
                    + "<br>输入 ticket（可选）如果上载的时候提供了 ticket，并且文件上载成功，没有发生意外，零时ticket将自动销毁"
                    + "<p>输出 上载成功后返回文件ID。")
    @RequestMapping(value = "/file",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseBody
    public String uploadSingleFile(@RequestParam(value="file") MultipartFile files, @RequestParam(value="ticket",required = false) String ticket)
            throws IllegalStateException, IOException {
        return null;
    }

    /**
     *
     * @param files
     * @param ticket
     * @return
     */
    @ApiOperation(value = "追加上载单个文件",
            notes = "这个接口一次只接受对一个文件的追加上载，并且ticket参数为必须，<p>"
                    + "<br>输入 file"
                    + "<br>输入 ticket 如果上载结束前没有发生意外，零时ticket将自动销毁"
                    + "<p>输出 上载成功后返回文件ID。")
    @RequestMapping(value = "/file/{ticket}",
            method = RequestMethod.PATCH,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseBody
    public String patchSingleFile(@RequestParam("file") MultipartFile files, @PathVariable String ticket) {
        return null;
    }

    /**
     *
     * @param id
     */
    @ApiOperation(value = "删除单个文件",
            notes = "这个接口一次只接受对一个文件的删除<p>"
                    + "<br>输入 id")
    @RequestMapping(value = "/file/{id}",
            method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteFile( @PathVariable("id") String id) {
        ;
    }

    /**
     * 同时上载多个文件
     *
     * @param files
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @ApiOperation(value = "同时上载多个文件",
            notes = "这个接口接受同时上载多个文件，但不支持断点续传。<p>"
                    + "<br>输入 file"
                    + "<p>输出 上载成功后返回文件ID和文件名的对照表。")
    @RequestMapping(value = "/files",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseBody
    public Map<String, String> uploadFiles(@RequestParam("file") MultipartFile[] files)
            throws IllegalStateException, IOException {
        Map<String, String> fileMap = new HashMap<String, String>();
        return fileMap;
    }
}
