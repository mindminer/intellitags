package net.tinybrick.intellitags.controllers;

import io.swagger.annotations.ApiOperation;
import net.tinybrick.intellitags.model.Tag;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by wangji on 2016/6/17.
 */
@RestController
public class TagControllerImpl implements TagController{
    private static Logger logger = Logger.getLogger(TagController.class);

    public String addTag( String name){
        return null;
    }

    public void rename( String id, String newName) {
        ;
    }

    public void delete( String id) {
        ;
    }

    public List<Tag> list( Integer page) {
        return null;
    }

    public List<Tag> listRelatives( String[] tagIds, Integer page) {
        return null;
    }

    public List<Tag> listAssociation( String fileId, Integer page) {
        return null;
    }

    public void associate( String fileId, String tagId) {
        ;
    }

    public void disconnection( String fileId, String tagId) {
        ;
    }
}
