package ie.my353.inis.controller;



import ie.my353.inis.entity.PostComment;
import ie.my353.inis.entity.ReturnPostComment;
import ie.my353.inis.service.PostCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/comment")
public class CommentController {


    @Autowired
    private PostCommentService postCommentService;


    @RequestMapping(value = "/addComment")
    //@CrossOrigin(origins = "*")
    public void addComment(PostComment c){
        postCommentService.addComment(c);

    }


    @RequestMapping(value = "/getComment",method = RequestMethod.GET)
    //@CrossOrigin(origins = "*")
    public List<ReturnPostComment> getComment(){

        return postCommentService.getComment();

    }




}
