/**
 * book.js  封装笔记本的相关处理
 */



//修改笔记本的名字
function renameBook(){
			//1.获取请求参数
			var bookId=$("#book_ul a.checked").parent().data("bookId");
			var bookName=$("#input_notebook_rename").val();
			var ok=true;
			//2.参数检查
			if(bookId==null){
				window.location.hreaf="loh_in.html";
				ok=false;
			}
			if(bookName==""){
				$("#rename_book").html("名字为空");
				ok=false;
			}
			//3.发送ajax请求
			if(ok==true){
				$.ajax({
					url:base_path+"/book/updateName.do",
					type:"post",
					dataType:"json",
					data:{"bookId":bookId,"bookName":bookName},
					success:function(result){
						if(result.status==0){
							//执行没有问题,此时执行回调函数
							//1.关闭对话框
							closeAlertWindow();
							//2.修改，并刷新列表
								//首先清空元素
							$("#book_ul").empty();
							loadUserBooks();
							//3.提示
							alert("修改成功");
						}
					},
					error:function(){
						alert("修改名称异常");
					}
				});
			}
		}

//增加笔记本
function add_cnBook(){
			//获取请求参数
			var name=$("#input_notebook").val();
			var userId=getCookie("uid");
			var ok=true;
			//格式检查
			if(name==""){
				ok=false;
				$("#notebook_span").html("名称为空");
			}
			if(userId==null){
				ok=false;
				window.location.href="log_in.html";
			}
			//发送Ajax请求
			if(ok){
				//表明通过了检测
				$.ajax({
					url:base_path+"/book/add.do",
					type:"post",
					data:{"userId":userId,"name":name},
					dataType:"json",
					success:function(result){
						if(result.status==0){
							//表示状态正确，没问题
							//关闭对话框
							closeAlertWindow();
							//生成笔记本li元素
							var bookId=result.data.cn_notebook_id;
							var bookName=result.data.cn_notebook_name;
							createBookLi(bookName,bookId);   //调用生成li的方法。(提取出来以便于重用)
							//提示成功
							alert(result.msg);
						}
					},
					error:function(){
						alert("创建笔记本异常!");
					}
				})
				
			}
		}


//加载用户笔记本列表
function loadUserBooks(){
	//获取请求参数
	var userId=getCookie("uid");
	//检查格式
	if(userId==null){
		//cookie失效，没有取到，让用户自己去登陆。
		window.location.href="log_in.html";
	}else{
		//发送ajax请求
		$.ajax({
			url:base_path+"/book/loadbooks.do",
			type:"post",
			data:{"userId":userId},
			dataType:"json",
			success:function(result){
				//成功,回调函数
				if(result.status==0){
					
					//获取返回的笔记本集合
					var books = result.data;
					//循环生成列表元素
					for(var i=0;i<books.length;i++){
						//获取笔记本的Id，与名称。
						var bookId=books[i].cn_notebook_id;
						var bookName=books[i].cn_notebook_name;
						//创建笔记本li
						createBookLi(bookName,bookId);
					}
				}
			},
			error:function(){
				alert("加载笔记本列表异常");
			}
		});
		
	}	
};




//创建笔记本列表的li元素
function createBookLi(bookName,bookId){
	//构建li列表元素
	var sli="";
	sli+='<li class="online">';
	sli+='	<a >';
	sli+='	<i class="fa fa-book" title="online" rel="tooltip-bottom">';
	sli+='		</i> '+bookName;
	sli+='	</a>';
	sli+='</li>';
	//将bookId绑定到li元素上
	var $li=$(sli);
	$li.data("bookId",bookId);
	//将li元素添加到url列表中 
	$("#book_ul").append($li);
}