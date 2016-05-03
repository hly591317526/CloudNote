function enterDeleteBook(event) {
	var e = event || window.event || arguments.callee.caller.arguments[0];
	if (e && e.keyCode == 13) {
		deleteBook();
	}
}
function deleteBook() {
	// 获取参数
	var bookId = $("#book_ul .checked").parents("li").data("bookId");
	// 发送ajax请求
	$.ajax({
		url : "notebook/delete.do",
		type : "post",
		data : {
			"bookId" : bookId
		},
		dataType : "json",
		success : function(result) {
			alert(result.msg);
			$("#book_ul .checked").parents("li").remove();
			$("#note_ul").empty();
		},
		error : function() {
			alert("删除笔记本失败");
		}
	});
}

function enterBook(event) {
	var e = event || window.event || arguments.callee.caller.arguments[0];
	// 回车键
	if (e && e.keyCode == 13) {
		newBook();
	}
}
// 加载用户笔记本列表
function loadUserBooks() {
	$.ajax({
		url : url_path + "/notebook/loadbooks.do",
		type : "post",
		data : {
			"userId" : userId
		},
		dataType : "json",
		success : function(result) {
			if (result.status == 0) {
				var books = result.data;
				// 循环解析生成li
				for (var i = 0; i < books.length; i++) {
					var bookName = books[i].cn_notebook_name;
					var bookId = books[i].cn_notebook_id;
					// 创建笔记本项目Li
					createBookLi(bookId, bookName);
				}
			}
		},
		error : function() {
			alert("加载笔记本列表失败！");
		}
	});

}

// 创建添加一个笔记本项目Li
function createBookLi(bookId, bookName) {
	var sli = '	<li class="online" >';
	sli += '<a > ';
	sli += '<i class="fa fa-book" title="online" rel="tooltip-bottom"> </i>';
	sli += bookName
			+ '<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down book_delete"> <i class="fa fa-times"></i></button></i>';
	sli += '</a>';
	sli += '</li>';
	// 将sli转换成Jquery对象
	var $li = $(sli);
	$li.data("bookId", bookId);// 绑定BookId的值
	// 添加到ul元素中
	$("#book_ul").append($li);
}

// 添加一个新的Book
function newBook() {
	$("#warning_newBook").html("");
	// 获取请求参数，
	var bookName = $("#input_notebook").val().trim();
	// 非空检查检查格式
	var ischecked = true;
	if (bookName == "") {
		$("#warning_newBook").html("名字不能为空");
		ischecked = false;
	}
	if (ischecked) {
		// 发送Ajax请求
		$.ajax({
			url : "notebook/add.do",
			type : "post",
			data : {
				"userId" : userId,
				"bookName" : bookName
			},
			dataType : "json",
			success : function(result) {
				// 成功回调用
				if (result.status == 0) {
					// 关闭对话框
					closeAlertWindow();
					// 添加列表项
					var bookId = result.data;// 返回笔记Id
					createBookLi(bookId, bookName)
					// 提示成功
					alert(result.msg);
				} else {
					$("#warning_newBook").html(result.msg);
				}
			},
			error : function() {
				alert("创建笔记本失败");
			}
		});
	}
}
