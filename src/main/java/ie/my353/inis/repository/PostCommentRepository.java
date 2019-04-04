package ie.my353.inis.repository;


import ie.my353.inis.entity.PostComment;
import ie.my353.inis.entity.ReturnPostComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PostCommentRepository extends PagingAndSortingRepository<PostComment, Long> {

    PostComment save(PostComment postComment);
    /**
     @Modifying 此时在@Query注解中定义，必须加上@Modify,告诉spring data 这是一个update/delete操作。
     @Transactional //不加会出现错误     javax.persistence.TransactionRequiredException: Executing an update/delete query
     @Query(value = "select * from comment where create_time between fromDate and toDate",nativeQuery = true)
     List<Comment> findByDate(Date fromDate, Date toDate);
     **/

    Page<PostComment> findAll(Pageable pageable);

    @Transactional
    @Query(value = "select * from comment where id = ?", nativeQuery = true)
    PostComment findCommentByParentId(String parentId);


    PostComment findAllById(Long id);


    @Transactional
    @Query(value = "select * from comment  where parent_id ='0' order by id DESC" , nativeQuery = true)
    List<PostComment> findAllComments();



    List<ReturnPostComment> findAllByParentIdOrderByIdDesc(String id);
/**
    @Transactional
    @Query(value = "select * from comment  where parent_id ='0' order by id DESC" , nativeQuery = true)
    List<ReturnPostComment> findAllComments();
**/

    List<ReturnPostComment> findAllBy();





}
