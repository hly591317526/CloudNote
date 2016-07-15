//家在好友分享分笔记
function loadFriendShare(){
				var shareId=$(this).parents("h4").data("shareId");
				showhide([1,8,5]);
	              $.ajax({
	             	 url:"share/loadShare.do",
	             	 type:"post",
	             	 data:{"shareId":shareId},
	             	 dataType:"json",
	             	 success:function(result){
	             		 $("#noput_note_title").html(result.data.cn_share_title);            		 
	             		 $("#share_body").html(result.data.cn_share_body);
	             	 },
	             	 error:function(){alert("预览失败！")}
	               });
			}


// 下载好友的文件
function downLoadFriendFile(){
				// 获取参数
				var fileId=$(this).parents("h4").data("fileId");
                  window.location.href="file/downfile.do?fileId="+fileId;					
			}

// 载入好友的文件
function loadFriendFile(){
	showhide([1,8,9]);
				$("#friend_file").empty();
				$("#friend_ul li a").removeClass("checked");
				$(this).find("a").addClass("checked");
				var friendId=$(this).data("friendId");
				$.ajax({
					url:"user/loadfriendfile.do",
					type:"post",
					data:{
					"friendId":friendId
					},
					dataType:"json",
					success:function(result){
						var shares=result.data[0];
						var files=result.data[1];
						for(var i=0;i<shares.length;i++){
							var li = "<h4 class='sharesNote'><strong>笔记："
								+ shares[i].cn_share_title
								+ " </strong>"
					    	  li += '<button type="button" class="btn btn-default btn-xs btn_plus share_info">查看</button></h4>';
							var $li=$(li);
							$li.data("shareId",shares[i].cn_share_id);
							$("#friend_file").append($li);
						}
						for(var i=0;i<files.length;i++){
							var li = "<h4><strong>文件："
								+ files[i].cn_file_name
								+ " </strong>--------来自"
								+ files[i].cn_user_name
								+ "的上传&nbsp;  &nbsp;";
						li += '<button type="button" class="btn btn-default btn-xs btn_plus file_down">下载</button></h4>';
						var $li=$(li);
						$li.data("fileId",files[i].cn_file_id);
						$("#friend_file").append($li);
						}
					},
					error:function(){
						alert("载入好友文件失败！");
					}
				});
			}
// 删除好友
function deletefriend(){
	var friendId=$(this).parents("li").data("friendId");
	$.ajax({
		url:"user/deletefriend.do",
		type:"post",
		data:{
			"userId":userId,
			"friendId":friendId
		},
		dataType:"json",
		success:function(result){
			alert(result.msg);
			setTimeout(function(){
				$("#action_button").click();
			},500)
		},
		error:function(){
			alert("删除好友失败");
		}
	});
	return false;
}
// 添加好友
function addfriend(){
	var name=$("#input_notebook_rename").val();
	if(name==""){
		alert("请输入名字！");
	}else{
		$.ajax({
			url:"user/addfriend.do",
			type:"post",
			data:{
				"userId":userId,
				"name":name
			},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					var li='<li class="offline"><a >昵称：'+result.data.cn_user_nick+'('+result.data.cn_user_name+')<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down book_delete"> <i class="fa fa-times"></i></button></a></li>';
					var $li=$(li);
					$li.data("friendId",result.data.cn_user_id);
					$("#friend_ul").append($li);				
				$("#can .close").click();
				}else{
					alert(result.msg);
				}
			},
			error:function(){
				alert("添加好友失败！");
			}
		});
	}
}
// 加载好友
function loadfriends(){
	showhide([1,8,9]);
	$("#friend_file").empty();
	$("#friend_ul").empty();
	// 记载好友列表
	$.ajax({
		url:"user/loadfriends.do",
		type:"post",
		data:{
			"userId":userId
		},
		dataType:"json",
		success:function(result){
			for (var i = 0; i < result.data.length; i++) {
			var li='<li class="offline"><a >昵称：'+result.data[i].cn_user_nick+'('+result.data[i].cn_user_name+')<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down book_delete"> <i class="fa fa-times"></i></button></a></li>';
			var $li=$(li);
			$li.data("friendId",result.data[i].cn_user_id);
			$("#friend_ul").append($li);
			}
		},
		error:function(){
			alert("加载用户好友失败！");
		}
	});
}
