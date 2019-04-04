package ie.my353.inis.entity;

import java.util.List;

public interface ReturnPostComment {

    Long getId();
    String getContent();
    String getNikeName();
    List<ReturnPostComment> getChildCommentList();
}
