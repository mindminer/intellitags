package net.tinybrick.intellitags.controllers;

import net.tinybrick.intellitags.model.Tag;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by wangji on 2016/6/17.
 */
@RestController
public class TagControllerImpl implements TagController{
    private static Logger logger = Logger.getLogger(TagController.class);

    @Override
    public String addTag( String name){
        return null;
    }

    @Override
    public void rename( String id, String newName) {
        ;
    }

    @Override
    public void delete( String id) {
        ;
    }

    @Override
    public List<Tag> list( Integer page) {
        return null;
    }

    @Override
    public List<Tag> listRelatives( String[] tagIds, Integer page) {
        return null;
    }

    @Override
    public List<Tag> listAssociation( String fileId, Integer page) {
        return null;
    }

    @Override
    public void associate( String fileId, String tagId) {
        ;
    }

    @Override
    public void disconnection( String fileId, String tagId) {
        ;
    }
}
