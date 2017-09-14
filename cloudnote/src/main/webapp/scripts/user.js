
//修改密码检查原始密码
function check_password(){
	//鼠标移出，发送ajax请求，来判断密码输入的是否正确
	//获取请求参数
	var userId=getCookie("uid");
	var last_password=$("#last_password").val();
	var ok=true;
	//格式检验
	if(userId==null){
		ok=false;
		window.location.href="log_in.html";
	}
	if(ok==true){
		//通过格式检验,发送Ajax请求。
		$.ajax({
			url:base_path+"/user/check.do",
			type:"post",
			datatype:"json",
			data:{"userId":userId,"last_password":last_password},
			success:function(result){
					$("#warning_1").html(result.msg).show();
			},
			error:function(){
				alert("出现异常");
			}
		});
	}
}