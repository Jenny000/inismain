package ie.my353.inis.service;



import ie.my353.inis.entity.PostComment;
import ie.my353.inis.entity.ReturnPostComment;
import ie.my353.inis.repository.PostCommentRepositoryImpl;
import ie.my353.inis.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PostCommentService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PostCommentRepositoryImpl postComment;
    @Autowired
    private IPUtils ipUtils;
    @Autowired
    private HttpServletRequest request;
//redis 序列化

    @PostConstruct
    private void init() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
    }


    public List<ReturnPostComment> getComment(){
        List<ReturnPostComment> allComments;
        //先读redis. 如果为空，读取数据库并存入redis

        if(redisTemplate.hasKey("comments")){
            allComments= (List<ReturnPostComment>) redisTemplate.opsForValue().get("comments");
            log.info("read comments from redis");

        }else {
            allComments=postComment.getReturnCommentsList();
            log.info("read comments from mysql");
            redisTemplate.opsForValue().set("comments",allComments);

        }

        return allComments;
    }


    public void addComment(PostComment c){

        c.setCreateTime(new Date());
        c.setIp(ipUtils.getIpAddr(request));
        if(c.getParentId().equals("0")){
            postComment.saveComment(c);
        }else {
            String parentId = c.getParentId();
            PostComment co = postComment.findCommentByParentId(parentId);
            List<PostComment> postCommentList = co.getChildCommentList();
            postCommentList.add(c);
            co.setChildCommentList(postCommentList);
            postComment.saveComment(co);
        }

        List<ReturnPostComment> allComments = postComment.getReturnCommentsList();

         redisTemplate.opsForValue().set("comments",allComments);
         log.info("new comment saved");



    }


}
