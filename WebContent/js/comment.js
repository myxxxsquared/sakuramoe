$(document).ready(function() {
    $(".btn-post-more").click(function() {
        setUpUrl("detail.jsp?postId=" + $(this).attr("postid"));
    });
    $(".btn-friends-home").click(function() {
        setUpUrl("home.jsp?userId=" + $(this).attr("userid"));
    });
    $(".btn-friends-remove").click(function() {
        setUpUrl("remove_friend.jsp?userId=" + $(this).attr("userid"));
    });
    $(".btn-post-like").click(function() {
        var childspan = $(this).find("span");
        $.post("action_like.jsp", { postId: $(this).attr("postid") }, function(data, status) {
            if (Number(childspan.attr("liked")) == 0) {
                childspan.attr("liked", "1");
                childspan.text(Number(childspan.text()) + 1);
            } else {
                childspan.attr("liked", "0");
                childspan.text(Number(childspan.text()) - 1);
            }
        });
    });
    $(".btn-post-forward").click(function() {
        var childspan = $(this).find("span");
        $.post("action_forward.jsp", { postId: $(this).attr("postid") }, function(data, status) {
            setUpUrl("home.jsp");
        });
    });
    $(".btn-post-comment").click(function() {
        var commentcontent = $(this).prev("input").val();
        var postid = $(this).attr("postid");
        $.post("action_comment.jsp", { postId: postid, parentId: $(this).attr("parentid"), content: commentcontent }, function(data, status) {
            setUpUrl("detail.jsp?postId=" + postid);
        });
    });
});