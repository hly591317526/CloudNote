//确定移动笔记
function SureMoveNote() {
						//获取参数
                       var $noteTitle = $("#input_note_title").val().trim();			
						var $bookId = $("#can #moveSelect").val();
						var $noteId = $("#note_ul .checked").parents("li").data("noteId");
						//判断参数
						if ($bookId == "") {
							alert("未选择笔记本");
							return;
						}
						//发送ajax
						$.ajax({
							url : "note/move.do",
							type : "post",
							data : {
								"userId":userId,
								"noteTitle":$noteTitle,
								"bookId" : $bookId,
								"noteId" : $noteId
							},
							dataType : "json",
							success : function(result) {
								if(result.status==0){
									alert(result.msg);
									$("#note_ul .checked").parents("li").remove();
								}
								else{
									alert(result.msg);
								}
							},
							error : function() {
								alert("转移笔记失败")
							}
						});

					}


//分享笔记
function share(){
				$("#save_note").click();
				//获取参数
              var noteId=$("#note_ul .checked").parents("li").data("noteId");				
				//发送Ajax
                $.ajax({
                  url:"share/add.do",	
                  type:"post",
                  data:{"noteId":noteId},
                  dataType:"json",
                  success:function(result){
                	  alert(result.msg);
                  },
                  error:function(){alert("分享笔记失败！")}
                });
			 
			}
function enterSureRecycle(event){
	var e=event||window.event||arguments.callee.caller.arguments[0];
	//回车键
if(e&&e.keyCode==13){
	sureRecycle();
}
}
//确定删除  送到回收站
function sureRecycle() {
	var $noteId = $("#note_ul .checked").parents("li").data("noteId");
	$.ajax({
		url : "note/recycle.do",
		type : "post",
		data : {
			"noteId" : $noteId
		},
		dataType : "json",
		success : function(result) {
			alert(result.msg);
			$("#book_ul .checked").parent("li").click();
			// 清空编辑区
			$("#input_note_title").val("");
			um.setContent("");
		},
		error : function() {
			alert("删除失败");
		}
	});
}
// 笔记菜单的控制
function showNoteMenu() {
	$("#note_ul").on("click", ".btn_slide_down", function() {
		// 隐藏所有的菜单
		$("#note_ul .note_menu").hide();
		// 显示单机笔记的菜单div
		var $li = $(this).parents("li");
		var $menu = $li.find("div.note_menu");
		$menu.slideDown(200);// 显示菜单
		// 设置当前li选中模式
		$("#note_ul li a").removeClass("checked");
		$li.find("a").addClass("checked");
		// 阻止冒泡到body
		return false;
	});
	// 当鼠标在div菜单移动保持显示
	$("#note_ul").on("mouseover", ".note_menu", function() {
		$(this).show();
	});
	// 当鼠标在div菜单离开时隐藏
	$("#note_ul").on("mouseout", ".note_menu", function() {
		$(this).hide();
	});
	// 在点击body仍和位置，将菜单隐藏
	$("body").click(function() {
		$("#note_ul .note_menu").hide();
	});
}

// 确认保存笔记
function update() {
	// 获取参数
	var noteTitle = $("#input_note_title").val().trim();
	var noteBody = um.getContent();
	var bookId = $("#book_ul .checked").parent("li").data("bookId");
	var noteId = $("#note_ul .checked").parent("li").data("noteId");
	var note = $("#note_ul .checked").parent("li");
	// 判断参数
	var ischecked = true;
	if (noteTitle == "") {
		alert("笔记名称不能为空！");
		ischecked = false;
	}
	if (noteId == undefined) {
		alert("请选择笔记！");
		ischecked = false;
	}
	if (ischecked) {
		// 发送ajax
		$
				.ajax({
					url : "note/update.do",
					type : "post",
					data : {
						"userId" : userId,
						"bookId" : bookId,
						"noteId" : noteId,
						"noteTitle" : noteTitle,
						"noteBody" : noteBody
					},
					dataType : "json",
					success : function(result) {
						// 成功回掉
						if (result.status == 0) {
							// 刷新列表标题
							var sli = '<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
							sli += noteTitle;
							sli += '<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"> <i class="fa fa-chevron-down"></i></button>';
							// 替换a中的内容
							$("#note_ul .checked").html(sli);
							// 提示成功
							alert(result.msg);
						} else if (result.status == 1) {
							alert(result.msg);
						}
					},
					error : function() {
						alert("更新笔记失败");
					}
				});
	}
}

function loadBookNotes() {
   showhide([1,2,3]);
	// 将当前li设置成选中状态
	$("#book_ul li a").removeClass("checked");
	$(this).find("a").addClass("checked");
	// 获取请求参数
	var bookId = $(this).data("bookId");
	// 发送Ajax请求
	$.ajax({
		url : url_path + "/note/loadnotes.do",
		type : "post",
		data : {
			"bookId" : bookId
		},
		dataType : "json",
		success : function(result) {
			var notes = result.data;
			// 清除原有列表
			$("#note_ul").empty();
			// 循环笔记信息，生成相应列表
			for (var i = 0; i < notes.length; i++) {
				var noteTitle = notes[i].cn_note_title;
				var noteId = notes[i].cn_note_id;
				// 拼一个笔记li
				createNoteLi(noteTitle, noteId)
			}
		},
		error : function() {
			alert("记载笔记列表失败");
		}
	})
};

function createNoteLi(noteTitle, noteId) {
	var sli = '<li class="online"><a> <i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
	sli += noteTitle;
	sli += '<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"> <i class="fa fa-chevron-down"></i></button>';
	sli += '</a>';
	sli += '<div class="note_menu" tabindex="-1">';
	sli += '<dl>';
	sli += '<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>';
	sli += '<dt>';
	sli += '<button type="button"class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i>	</button>';
	sli += '</dt>';
	sli += '<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button>';
	sli += '</dt>';
	sli += '</dl>';
	sli += '</div></li>';
	// 绑定一个noteId
	var $li = $(sli);
	$li.data("noteId", noteId);
	// 添加到笔记ul中
	$("#note_ul").append($li);
}
function enterNote(event){
	var e=event||window.event||arguments.callee.caller.arguments[0];
	//回车键
if(e&&e.keyCode==13){
	newNote();
}
}
function newNote() {
	$("#warning_newNote").html("");
	// 获取请求参数，
	var noteTitle = $("#input_note").val().trim();
	var bookId = $("#book_ul .checked").parent("li").data("bookId");
	// 非空检查检查格式
	var ischecked = true;
	if (noteTitle == "") {
		$("#warning_newNote").html("名字不能为空");
		ischecked = false;
	}
	if (bookId == undefined) {
		$("#warning_newNote").html("没有选择笔记本");
		ischecked = false;
	}
	if (ischecked) {
		// 发送Ajax请求
		$.ajax({
			url : "note/add.do",
			type : "post",
			data : {
				"userId" : userId,
				"bookId" : bookId,
				"noteTitle" : noteTitle
			},
			dataType : "json",
			success : function(result) {
				// 成功回调用
				if (result.status == 0) {
					// 关闭对话框
					closeAlertWindow();
					// 添加列表项
					var noteId = result.data;// 返回笔记Id
					createNoteLi(noteTitle, noteId);
					// 提示成功
					alert(result.msg);
				} else {
					$("#warning_newNote").html(result.msg);
				}
			},
			error : function() {
				alert("创建笔记失败");
			}
		});
	}
}

function loadNoteContent() {
	// 将当前li设置成选中
	$("#note_ul li a").removeClass("checked");
	$(this).find("a").addClass("checked");
	// 获取noteId
	var noteId = $(this).data("noteId");
	// 发送Ajax请求
	$.ajax({
		url : "note/loadnotebody.do",
		type : "post",
		data : {
			"noteId" : noteId
		},
		dataType : "json",
		success : function(result) {
			if (result.status == 0) {
				var noteTitle = result.data.cn_note_title;
				var noteBody = result.data.cn_note_body;
				// 设置到编辑区
				$("#input_note_title").val(noteTitle);
				um.setContent(noteBody);// 设置编辑器内部文件
			}
		},
		error : function() {
			alert("加载笔记内容失败!")
		}
	});
}
