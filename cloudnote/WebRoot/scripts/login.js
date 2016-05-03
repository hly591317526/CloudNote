$(function() {
	//回车按键监听登陆
	 document.onkeydown=function(event){
         var e=event||window.event||arguments.callee.caller.arguments[0];
         //监听回收事件
         if(e&&e.keyCode==13){
        	 $("#login").click();
         }
	 }
	// body载入完毕
	$("#login").click(function() {
		// 清除原有提示信息
		$("#count_span").html("");
		$("#password_span").html("");
		// 获取请求参数
		var name = $("#count").val().trim();
		var password = $("#password").val().trim();
		// 检查参数格式
		var ischeck = true;
		if (name == "") {
			$("#count_span").html("用户名为空");
			ischeck = false;
		}
		if (password == "") {
			$("#password_span").html("密码为空");
			ischeck = false;
		}
		// 没问题就发送Ajax请求
		if (ischeck) {
			$.ajax({
				url : "user/login.do", 
				type : "post",
				data : {
					"name" : name,
					"password" : password
				},
				dataType : "json",
				success : function(result) {
					// 正常回调
					if (result.status == 0) {
						// 获取用户ID，写入cookie
						var userId = result.data;
                      addCookie("userId",userId,2);						
                      addCookie("userName",name,2);	
						window.location.href = "edit.html";// 成功
					} else if (result.status == 1) {// 用户名错
						$("#count_span").html(result.msg);
					} else if (result.status == 2) {// 密码错
						$("#password_span").html(result.msg);
					}
				},
				error : function() {
					alert("登录失败");
				}
			});
		}
	});
});