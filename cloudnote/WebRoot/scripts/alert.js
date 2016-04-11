//弹出删除笔记本框
function alertDeleteNoteBook(){
                $("#can").load("alert/alert_delete_notebook.html");
                $(this).parents("li").find("a").addClass("checked");
            	$(".opacity_bg").show();
            	return false;
            }

//弹出提示移动笔记本
function alertMoveNote() {
	var $bookli = $("#book_ul li a[class!=checked]").parent();
	// 加载alert_notebook.html内容
	$("#can").load(
			"alert/alert_move.html",
			function() {
				for (var i = 0; i < $bookli.length; i++) {
					var $bookId = $($bookli[i]).data("bookId");
					var li = "<option value='" + $bookId + "'>- "
							+ $($bookli[i]).text() + " -</option>";
					$("#moveSelect").append(li);
				}
			});
	// 背景色div显示
	$(".opacity_bg").show();
}

// 4.弹出添加“笔记对话框”
function alertAddNoteWindow() {
	// 检测是否选中笔记本
	// 同功能不同实现方法。
	var $li = $("#book_ul li a.checked");
	if ($li.length == 0) {
		alert("请选择笔记本！");
		return;
	}
	// 加载alert_note.html内容
	$("#can").load("alert/alert_note.html");
	// 背景色div显示
	$(".opacity_bg").show();
};
// 弹出添加“笔记本对话框”
function alertAddBookWindow() {
	// 加载alert_notebook.html内容
	$("#can").load(
			"alert/alert_notebook.html");
	// 背景色div显示
	$(".opacity_bg").show();
};
// 关闭弹出对话框
function closeAlertWindow() {
	$("#can").empty(); // 清空
	$(".opacity_bg").hide(); // 隐藏
}
// 弹出确认删除对话框
function alertRecycleNoteWindow() {
	$("#can").load("alert/alert_delete_note.html");
	$(".opacity_bg").show();
}
