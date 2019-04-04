package ie.my353.inis.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Component
//@Proxy(lazy = false)
@Table(name = "comment")
public class PostComment implements Serializable {
    private static final long serialVersionUID = 4465884610826278371L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    private Long id;

    @NotEmpty(message = "comment can not be empty")
    @Size(max=500, message = "max words 500")
    @Column(nullable = false)
    private String content;
    private String ip;
    private Date createTime;

    private String nikeName;

    @Size(max=500, message = "max words 50")
    private String title;

    private String parentId;

    @OneToMany(fetch = FetchType.EAGER,cascade = { CascadeType.ALL })
    @ElementCollection
    @CollectionTable(name = "comment_child_list")
    private List<PostComment> childCommentList;


    protected PostComment() {// JPA 的规范要求无参构造函数；设为 protected 防止直接使用

    }

    public PostComment(@NotEmpty(message = "comment can not be empty") @Size(max = 500, message = "max words 500") String content,
                       String nikeName, String title, String ip) {
        this.content = content;
        this.nikeName = nikeName;
        this.title = title;
        this.ip = ip;
    }

    public PostComment(@NotEmpty(message = "comment can not be empty") @Size(max = 500, message = "max words 500") String content,
                       Date createTime, String nikeName, String title, String parentId, String ip) {
        this.content = content;
        this.createTime = createTime;
        this.nikeName = nikeName;
        this.title = title;
        this.parentId = parentId;
        this.ip = ip;
    }

    public PostComment(@NotEmpty(message = "comment can not be empty") @Size(max = 500, message = "max words 500") String content,
                       Date createTime, String nikeName, String title, String parentId, List<PostComment> childCommentList, String ip){
        this.content = content;
        this.createTime = createTime;
        this.nikeName = nikeName;
        this.title = title;
        this.parentId = parentId;
        this.childCommentList = childCommentList;
        this.ip = ip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNikeName() {
        return nikeName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<PostComment> getChildCommentList() {
        return childCommentList;
    }

    public void setChildCommentList(List<PostComment> childCommentList) {
        this.childCommentList = childCommentList;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", nikeName='" + nikeName + '\'' +
                ", title='" + title + '\'' +
                ", parentId='" + parentId + '\'' +
                ", childCommentList=" + childCommentList +
                '}';
    }

}
