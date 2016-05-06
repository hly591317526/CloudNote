//取消笔记的赞
function shareNoteDown(){
				var shareId=$(this).parents("li").data("shareId");
			    $.ajax({
			    	url:"share/down.do",
			    	type:"post",
			    	data:{
			    		"userId":userId,
			    		"shareId":shareId
			    	},
			    	dataType:"json",
				  success:function(result){
		    			$("#search_ul a.checked").parents("li").find("b.like_sum").html(result.data);
			    		alert(result.msg);
				  },
				error:function(){
                 alert("取消点赞失败！");					
				}
			    });
			}
//给笔记点赞
function shareNoteUp(){
				var shareId=$(this).parents("li").data("shareId");
			    $.ajax({
			    	url:"share/up.do",
			    	type:"post",
			    	data:{
			    		"userId":userId,
			    		"shareId":shareId
			    	},
			    	dataType:"json",
			    	success:function(result){
			    		if(result.status==0){
			    			$("#search_ul a.checked").parents("li").find("b.like_sum").html(result.data);
			    		}
			    		alert(result.msg);
			    	},
			    	error:function(){
			    		alert("点赞失败。");
			    	}
			    });
			}


//导入
function loginShare(){
	 $("#search_ul").find(".checked").removeClass("checked");
	$(this).find("a").addClass("checked");
	 $("#search_ul").find("button").css("visibility","hidden");
	        $(this).find("button").css("visibility","visible")
              //获取请求参数
            	var shareId=$(this).data("shareId");
              //发送ajax请求
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
//切换隐藏显示
function showhide(arr){
	for(var i=1;i<=9;i++){
		$("#pc_part_"+i+"").hide();
	}
	for(var i=0;i<arr.length;i++){
		$("#pc_part_"+arr[i]+"").show();
	}
};
//搜索分享笔记
function search(event){
				   var e=event||window.event||arguments.callee.caller.arguments[0];
                    	//回车键
                    if(e&&e.keyCode==13){
                    	 $("#search_ul").empty();
                                //获取请求参数
                                var search=$("#search_note").val().trim();
                                //发送ajax请求
                                $.ajax({
                                	url:"share/load.do",
                                	type:"get",
                                	data:{
                                		"search":search
                                	},
                                	dataType:"json",
                                	success:function(result){
                                		showhide([1,5,6]);
                                		for(var i=0;i<result.data.length;i++){
                                		var li='<li class="online"><a> <i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
										 li+=result.data[i].cn_share_title+'(<b class="like_sum">'+result.data[i].cn_like_sum+'</b>个赞)';
										 li+='</a>';
										 li+=	'<button type="button" class="btn btn-default btn-xs btn_position_3 btn_up" >';
											li+='<i class="fa fa-thumbs-o-up"></i></button>'
											li+='<button type="button"  class="btn btn-default btn-xs btn_position_2 btn_down"><i class="fa fa-thumbs-o-down"></i>';
											li+='</button><button type="button" class="btn btn-default btn-xs btn_position btn_like"><i class="fa fa-star-o"></i>';
											li+='</button></li>';
                                         var $li=$(li);
										 $li.data("shareId",result.data[i].cn_share_id);
										 $("#search_ul").append($li);
                                		}
                                	},
                                	error:function(){alert("查询笔记失败");}
                                });
                    }
			}