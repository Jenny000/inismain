package ie.my353.inis.repository;



import ie.my353.inis.entity.PostComment;
import ie.my353.inis.entity.ReturnPostComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostCommentRepositoryImpl {

    @Autowired
    private PostCommentRepository commentRepository;


    public void saveComment(PostComment c) {

        if(null != c){
            commentRepository.save(c);
        }

    }
    public PostComment findCommentByParentId (String parentId){
        return commentRepository.findCommentByParentId(parentId);
    }



    public Page<PostComment> findComment(int page, int size) {

        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = new PageRequest(0,5,sort);
        return commentRepository.findAll(pageable);
    }





    public List<ReturnPostComment> getReturnCommentsList() {

       return commentRepository.findAllByParentIdOrderByIdDesc("0");


    }

}
