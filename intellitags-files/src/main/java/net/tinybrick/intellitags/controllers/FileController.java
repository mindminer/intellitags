package net.tinybrick.intellitags.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.tinybrick.intellitags.model.File;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by wangji on 2016/6/15.
 */
@RequestMapping("/v1.0")
@Api(value = "文件管理 API", tags = {"版本v1.0", "文件管理"})
//@ApiDoc
public interface FileController {
    /**
     * Call this method to get an temporary ticket before you upload a big file.
     *
     * With this ticket, you are allowed to try to upload the file from the offset where it broken from the last time.
     *
     * @return
     */
    @ApiOperation(value = "申请临时传送令牌",
            notes = "<p>为防止文件过大，导致传送中断，在发送文件之前可以申请一个零时令牌，系统根据零时令牌的存活状态来判断文件是否已经传送结束" +
                    "如果文件传送过程中出现中断，可以通过临时令牌实现断点续传。"
                    + "<p>输出 ticket")
    @RequestMapping(value = "/ticket",
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.ALL_VALUE})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String[] uploadBegin();

    /**
     * Withdraw the temporary upload ticket when file upload is finished.
     *
     * Once a ticket is withdrawn, it can't be used any more, the file will be frozen also.
     *
     * @param ticket
     */
    @ApiOperation(value = "销毁令牌",
            notes = "<p>文件发送完成后只需要销毁零时令牌即可冻结文件。请及时销毁零时令牌，系统有可能不定期清除未冻结的临时文件导致数据丢失。"
                    + "<p>输入 ticket")
    @RequestMapping(value = {"/ticket/{ticket}"},
            method = {RequestMethod.DELETE})
    @ResponseStatus(HttpStatus.OK)
    public void uploadEnd(@PathVariable("ticket") String ticket);

    /**
     * @param ticket
     * @return
     */
    @ApiOperation(value = "检查文件偏移量",
            notes = "<p>如果发生文件传送中断，可以通过这个方法查寻文件当前的偏移量，程序将返回已接收文件的长度。"
                    + "<p>输入 ticket"
                    + "<p>输出 offset")
    @RequestMapping(value = {"/file/offset/{ticket}"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public Integer queryOffset(@PathVariable("ticket") String ticket);

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
            notes = "<p>这个接口一次只接受一个文件。ticket参数为可选，如果不提供ticket，系统将不支持断点续传。"
                    + "<p>输入 file"
                    + "<br>输入 ticket（可选）如果上载的时候提供了 ticket，并且文件上载成功，没有发生意外，零时ticket将自动销毁"
                    + "<p>输出 上载成功后返回文件ID。")
    @RequestMapping(value = "/file",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseBody
    public String uploadSingleFile(@RequestParam(value="file") MultipartFile files, @RequestParam(value="ticket",required = false) String ticket)
            throws IllegalStateException, IOException;

    /**
     *
     * @param files
     * @param ticket
     * @return
     */
    @ApiOperation(value = "追加上载单个文件",
            notes = "<p>这个接口一次只接受对一个文件的追加上载，并且ticket参数为必须，"
                    + "<p>输入 file"
                    + "<br>输入 ticket 如果上载结束前没有发生意外，零时ticket将自动销毁"
                    + "<p>输出 上载成功后返回文件ID。")
    @RequestMapping(value = "/file/{ticket}",
            method = RequestMethod.PATCH,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseBody
    public String patchSingleFile(@RequestParam("file") MultipartFile files, @PathVariable String ticket);

    /**
     *
     * @param id
     */
    @ApiOperation(value = "删除单个文件",
            notes = "<p>这个接口一次只接受对一个文件的删除"
                    + "<p>输入 id")
    @RequestMapping(value = "/file/{id}",
            method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteFile( @PathVariable("id") String id);

    /**
     * 同时上载多个文件
     *
     * @param files
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @ApiOperation(value = "同时上载多个文件",
            notes = "<p>这个接口接受同时上载多个文件，但不支持断点续传。"
                    + "<p>输入 file"
                    + "<p>输出 上载成功后返回文件ID和文件名的对照表。")
    @RequestMapping(value = "/files",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseBody
    public Map<String, String> uploadMultipleFiles(@RequestParam("file") MultipartFile[] files)
            throws IllegalStateException, IOException;

    @ApiOperation(value = "获得文件列表",
            notes = "<p>获得指定标签（可选）下的指定（可选文件名或模式的）文件列表，可以指定多个，如果没有指定标签则返回最近上载的文件列表。"
                    + "<br>输入 patterns (可选) 文件名，或模式"
                    + "<br>输入 tags (可选)"
                    + "<p>输入 page 页号 (可选)"
                    + "<p>输出 指定标签下的文件列表。")
    @RequestMapping(value = "/list",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public List<File> list(@RequestParam(value="tags", required = false) String[] patterns,
                           @RequestParam(value="tags", required = false) String[] tags,
                           @RequestParam(value="page", required = false) int page);

    @ApiOperation(value = "修改文件名",
            notes = "<p>修改文件的名称"
                    + "<p>输入 id 文件ID"
                    + "<br>输入 newName 新文件名")
    @RequestMapping(value = "/file/name/{id}/{name}",
            method = RequestMethod.PUT)
    public void rename(@PathVariable String id, @PathVariable String newName);


    @ApiOperation(value = "下载文件",
            notes = "<p>下载一个文件"
                    + "<p>输入 id 文件ID"
                    + "<br>输入 offset 偏移量（可选）。用于支持断点续传，如果没有提供则从头下载"
                    + "<br>输入 length 下载长度（可选）。用于支持断点续传，如果没有提供则一直下载到文件结束")
    @RequestMapping(value = "/file/{id}", method = RequestMethod.GET)
    public void download(@PathVariable String id,
                         @RequestParam(value="offset", required = false)  int offset,
                         @RequestParam(value="length", required = false)  int length,
                         HttpServletResponse response);
}
