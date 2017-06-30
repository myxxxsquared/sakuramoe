$(document).ready(function() {
    $(".btn-post-more").click(function() {
        setUpUrl("detail.jsp?postId=" + $(this).attr("postid"));
    });
    $(".btn-friends-home").click(function() {
        setUpUrl("home.jsp?userId=" + $(this).attr("userid"));
    });
    $(".btn-friends-remove").click(function() {
    	if(confirm("Do you really want to remove?"))
    	{
    		$.post("actions/friend_remove.jsp", { friendId: $(this).attr("userid") }, function(data, status) {
    			setUpUrl("friends.jsp");
            });
    	}
    });
    $(".btn-post-like").click(function() {
        var childspan = $(this).find("span");
        $.post("actions/post_like.jsp", { postId: $(this).attr("postid") }, function(data, status) {
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
        $.post("actions/post_forward.jsp", { postId: $(this).attr("postid") }, function(data, status) {
            setUpUrl("home.jsp");
        });
    });
    $(".btn-post-comment").click(function() {
        var commentcontent = $(this).parent().prev("input").val();
        var postid = $(this).attr("postid");
        $.post("actions/post_comment.jsp", { postId: postid, parentId: $(this).attr("parentid"), content: commentcontent }, function(data, status) {
            setUpUrl("detail.jsp?postId=" + postid);
        });
    });
    $("#friend_add_button").click(function(){
    	var friendname = $("#friend_add_id").val();
    	$.post("actions/friend_add.jsp", { friend_id : friendname }, function(data, status) {
            setUpUrl("friends.jsp");
        });
    });
});