//导入
function loginShare(){
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
	for(var i=1;i<9;i++){
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
										 li+=result.data[i].cn_share_title;
										 li+='</button></a></li>';
                                         var $li=$(li);
										 $li.data("shareId",result.data[i].cn_share_id);
										 $("#search_ul").append($li);
                                		}
                                	},
                                	error:function(){alert("查询笔记失败");}
                                });
                    }
			}