/**login.js  封装登录和注册处理**/
//log_in.html主处理
$(function(){   //页面载入完毕
		//给登录按钮绑定单击事件
		$("#login").click(checkLogin);
		//给注册按钮绑定单机事件
		$("#regist_button").click(registUser);
});
function checkLogin(){
			//获取请求参数，
			var name=$("#count").val().trim();
			var password=$("#password").val().trim();
			//检测参数格式，
			//先清空之前的提示信息
			$("#count_span").html("");
			$("#password_span").html("");
			var ok=true;   //是否通过检测
			if(name==""){
				$("#count_span").html("用户名为空");
				ok=false;
			}
			if(password==""){
				$("#password_span").html("密码为空");
				ok=false;
			}
			//发送Ajax请求
			if(ok==true){
				//检测通过可以发送请求
				$.ajax({
					url:base_path+"/user/login.do",
					type:"post",
					data:{"name":name,"password":password},
					dataType:"json",
					success:function(result){
						//                                  状态v      信息v      v对象（登陆成功时）
						//result的值为服务器返回的JSON结果{"status":xx,"msg":xx,"data":xx}
						
						if(result.status==0){     //表示登陆成功
							var user=result.data;     //获取返回的user信息
							//写入Cookie
							addCookie("uid",user.cn_user_id,2);                  //已经封装好在cookie_util.js中
							addCookie("uname",user.cn_user_name,2);
							
							window.location.href="edit.html";
						}else if(result.status==1){   //用户名错误
							$("#count_span").html(result.msg);
						}else if(result.status==2){    //密码错误
							$("#password_span").html(result.msg);
						}
					},
					error:function(){
						alert("登录异常");
					}
				});
			}
			
		}


/**注册功能**/
function registUser(){
	//获取请求参数 
	var name=$("#regist_username").val().trim();
	var nick=$("#nickname").val().trim();
	var password=$("#regist_password").val().trim();
	var f_password=$("#final_password").val().trim();
	//格式检查
	//清空提示消息
	$("#warning_1 span").html("");
	$("#warning_2 span").html("");
	$("#warning_3 span").html("");
	
	
	var ok=true;   //是否通过验证
	
	if(name==""){
		$("#warning_1").show();
		$("#warning_1 span").html("用户名为空");
		ok=false;
	}
	if(password == ""){
		$("#warning_2").show();
		$("#warning_2 span").html("密码为空");
		ok=false;
	}else if(password.length<6){
		$("#warning_2").show();
		$("#warning_2 span").html("密码长度太短");
		ok=false;
	}
	
	if(f_password==""){
		$("#warning_3").show();
		$("#warning_3 span").html("确认密码为空");
		ok=false;
	}else if(f_password!=password){
		$("#warning_3").show();
		$("#warning_3 span").html("密码输入不一致");
		ok=false;
	}
	
	//发送AJAX请求
	if(ok){
		$.ajax({
			url:base_path+"/user/add.do",
			type:"post",
			data:{"name":name,"nick":nick,"password":password},
			dataType:"json",
			success:function(result){
				//正常返回了json数据
				if(result.status==0){   //成功
					alert(result.msg);
					$("#back").click();
				}else if(result.status==1){   //用户名被占用
					$("#warning_1").show();
					$("#warning_1 span").html(result.msg);
				}
			},
			error:function(){
				//服务器端抛出异常
				alert("注册异常 ");
			}
		});
	}
}