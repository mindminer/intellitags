package net.tinybrick.intellitags.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.tinybrick.intellitags.model.File;
import net.tinybrick.intellitags.model.Tag;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by wangji on 2016/6/16.
 */
@RestController
@RequestMapping("/v1.0")
@Api(value = "标签管理 API", tags = {"版本v1.0", "标签管理"})
public class TagController {
    private static Logger logger = Logger.getLogger(TagController.class);

    @ApiOperation(value = "添加标签",
            notes = "添加新的标签<p>"
                    + "<br>输入 name 标签名"
                    + "<p>输出 新的标签id")
    @RequestMapping(value = "/tag/{name}",
            method = {RequestMethod.POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public String addTag(@PathVariable String name){
        return null;
    }

    @ApiOperation(value = "重命名",
            notes = "重命名标签<p>"
                    + "<br>输入 id 标签编号"
                    + "<br>输入 newName 新标签名")
    @RequestMapping(value = "/tag/{id}/{newName}",
            method = {RequestMethod.PUT})
    public void rename(@PathVariable String id, @PathVariable String newName) {
        ;
    }

    @ApiOperation(value = "删除标签",
            notes = "删除标签<p>"
                    + "<br>输入 id 标签编号")
    @RequestMapping(value = "/tag/{id}",
            method = {RequestMethod.DELETE})
    public void delete(@PathVariable String id) {
        ;
    }

    @ApiOperation(value = "罗列标签",
            notes = "简单罗列出所有的标签<p>"
                    + "<br>输入 page 页号"
                    + "<p>输出 标签列表。")
    @RequestMapping(value = "/tag",
            method = {RequestMethod.GET})
    @ResponseBody
    public List<Tag> list(@RequestParam(value="page",required = false) Integer page) {
        return null;
    }

    @ApiOperation(value = "列出相关标签",
            notes = "罗列出与给定的标签有关联的其他标签<p>"
                    + "<br>输入 tagId 标签ID。如果输入多个标签，则返回交集"
                    + "<br>输入 page 页号(可选，缺省第一页)"
                    + "<p>输出 标签列表。")
    @RequestMapping(value = "/tag/{tagId}/relatives",
            method = {RequestMethod.GET})
    @ResponseBody
    public List<Tag> listRelatives(@PathVariable String[] tagIds, @RequestParam(value="page",required = false)Integer page) {
        return null;
    }

    @ApiOperation(value = "列出相关标签",
            notes = "根据给定的文件，列出其所关联的标签<p>"
                    + "<br>输入 fileId 文件ID"
                    + "<br>输入 page 页号(可选，缺省第一页)"
                    + "<p>输出 标签列表。")
    @RequestMapping(value = "/tag/{fileId}/association",
            method = {RequestMethod.GET})
    @ResponseBody
    public List<Tag> listAssociation(@PathVariable String fileId, @RequestParam(value="page",required = false)Integer page) {
        return null;
    }

    @ApiOperation(value = "贴标签",
            notes = "为文件贴上标签<p>"
                    + "<br>输入 fileId 文件编号"
                    + "<br>输入 tagId 标签编号")
    @RequestMapping(value = "/tag/{tagId}/{fileId}",
            method = {RequestMethod.POST})
    public void associate(@PathVariable String fileId, @PathVariable String tagId) {
        ;
    }

    @ApiOperation(value = "取消标签",
            notes = "为文件取消一个标签<p>"
                    + "<br>输入 fileId 文件编号"
                    + "<br>输入 tagId 标签编号")
    @RequestMapping(value = "/tag/{tagId}/{fileId}",
            method = {RequestMethod.DELETE})
    public void disconnection(@PathVariable String fileId, @PathVariable String tagId) {
        ;
    }
}
