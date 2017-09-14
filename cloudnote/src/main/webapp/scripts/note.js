/**封装笔记的相关操作**/


//显示收藏的笔记
function show_likes(){
	//获取请求参数
	var userId=getCookie("uid");
	$("#load_likes").empty();
	//发送ajax请求
	$.ajax({
		url:base_path+"/note/load_likes.do",
		type:"post",
		data:{"userId":userId},
		datatype:"json",
		success:function(result){
			if(result.status==0){
				$(".col-xs-3").hide();
				$("#pc_part_7").show();
				var likes=result.data;
				for(var i=0;i<likes.length;i++){
					//循环给其中加入笔记的信息
					var noteTitle=likes[i].cn_note_title;
					var noteId=likes[i].cn_note_id;
					var sli="";
					sli+='<li class="idle"><a ><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>' +noteTitle+'<button type="button" class="btn btn-default btn-xs btn_position btn_delete"><i class="fa fa-times"></i></button></a></li>';
					$li=$(sli);
					$li.data("noteId",noteId);
					$("#load_likes").append($li);
					
				}
			}
		},
		error:function(){
			alert("查看我的收藏失败!!");
		}
	});
}



//收藏笔记
function sure_likes(){
	//获取请求参数
	var userId=getCookie("uid");
	var shareId=$("#sreach_ul a.checked").parent().data("shareId");
	//发送Ajax请求
	$.ajax({
		url:base_path+"/note/share_like.do",
		type:"post",
		data:{"userId":userId,"shareId":shareId},
		datatype:"json",	
		success:function(result){
			if(result.status==0){
				//提示收藏成功
				alert(result.msg);
			}else{
				//提示收藏失败
				alert(result.msg);
			}
		},
		error:function(){
			alert("收藏笔记异常");
		}
	});
}



//查看分享笔记信息
function loadShareNotes(){
	//清楚全部选中状态
	$("#pc_part_6 ul").find("a").removeClass("checked");
	$(this).find("a").addClass("checked");
	
	
	//获取请求参数
	var shareId=$(this).data("shareId");
	//发送ajax请求
	$.ajax({
		url:base_path+"/note/load_share.do",
		type:"post",
		data:{"shareId":shareId},
		datatype:"json",
		success:function(result){
			if(result.status==0){
				var title=result.data.cn_share_title;
				var body=result.data.cn_share_body;
				//设置
				$("#noput_note_title").html(title);
				$("#noput_note_title").next().html(body);
				//切换标题
				$("#pc_part_3").hide();
				$("#pc_part_5").show();
			}
		},
		error:function(){
			alert("预览分享笔记异常");
		}
	});
	
}



function searchSharePage(noteTitle,page){
	//发送Ajax请求
	$.ajax({
		url:base_path+"/note/search_share.do",
		type:"post",
		data:{"noteTitle":noteTitle,"page":page},
		datatype:"json",
		success:function(result){
			if(result.status==0){
				//获取服务器返回的搜索结果
				var shares=result.data;							
				//循环解析生成列表的li元素
				for(var i=0;i<shares.length;i++){
					var shareId=shares[i].cn_share_id;   //获取分享Id
					var shareTitle=shares[i].cn_share_title;     //分享标题
					//生成一个li
					var sli="";
					sli+='<li class="online">';
					sli+='<a>';
					sli+='<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> '+shareTitle+'<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-star" style="font-size:20px;line-height:31px;" id="share_star"></i></button>';
					sli+='</a>';
					sli+='</li>';
					var $li=$(sli);
					$li.data("shareId",shareId);
					//添加到搜索结果ul中。
					$("#sreach_ul").append($li);
						
				}
			}
		},
		error:function(){
			alert("搜索异常");
		}
	});
}



//搜索分享笔记
function shareNotes(event){
	var code=event.keyCode;
	if(code==13){  //回车键
		//清除列表
		$("#pc_part_6").find("ul").empty();
		//显示搜索结果列表,其他列表隐藏  , 切换显示
		$("#pc_part_2").hide();
		$("#pc_part_4").hide();
		$("#pc_part_6").show();
		$("#pc_part_7").hide();
		$("#pc_part_8").hide();
		//获取请求参数
		noteTitle=$("#search_note").val();
		page=1;     //设置初始之1
		searchSharePage(noteTitle,page);
	}
}


//回收站
function NoteTrash(){
	//获取请求参数
	var userId=getCookie("uid");
	//格式验证
	//发送ajax请求
	$.ajax({
		url:base_path+"/note/trash.do",
		type:"post",
		data:{"userId":userId},
		datatype:"json",
		success:function(result){
			if(result.status==0){
				$("#trash_ul").empty();
				var notes=result.data;//获得全部的笔记
				$(".col-xs-3").hide();
				$("#pc_part_4").show();
				for(var i=0;i<notes.length;i++){
					var noteId=notes[i].cn_note_id;
					var noteTitle=notes[i].cn_note_title;
					var str='';
					str+='<li class="disable"><a ><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> '+noteTitle+'<button type="button" class="btn btn-default btn-xs btn_position btn_delete"><i class="fa fa-times"></i></button><button type="button" class="btn btn-default btn-xs btn_position_2 btn_replay"><i class="fa fa-reply"></i></button></a></li>';
					$li=$(str);
					$li.data("noteId",noteId);
					$("#trash_ul").append($li);
				}
				
			}
		},
		error:function(){
			alert("发送ajax请求异常");	
		}
	});
	
}

//笔记分享
function shareNote(){
	//获取请求参数noteId
	var $li=$(this).parents("li");
	var noteId=$li.data("noteId");
	//发送ajax请求
	$.ajax({
		url:base_path+"/note/share.do",
		type:"post",
		data:{"noteId":noteId},
		datatype:"json",
		success:function(result){
			if(result.status==0){
				//TODO 添加分享图标   //复杂的还要让其可以保持,修改其状态cn_note_type_id
				var img='<i class="fa fa-sitemap"></i>';
				$li.find(".btn_slide_down").before(img);	
				//提示分享成功
				alert(result.msg);
			}else if(result.status==1){
				//提示已经
				alert(result.msg);
			}else{
				alert(result.msg);
			}
		},
		error:function(){
			alert("分享笔记异常");
		}
	});
	
}


//移动笔记
function MoveNote(){
	//获取请求参数
	//1.获取要转移的笔记ID
	var $li=$("#note_ul a.checked").parent();
	var noteId=$li.data("noteId");
	//2.获取要转入的笔记本Id
	var bookId=$("#moveSelect").val();
	
	//发送Ajax请求
	$.ajax({
		url:base_path+"/note/move.do",
		type:"post",
		data:{"noteId":noteId,"bookId":bookId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//关闭对话框
				closeAlertWindow();
				//移除笔记li
				$li.remove();
				//提示成功
				alert(result.msg);
			}
		},
		error:function(){
			alert("转移笔记本异常");
		}
	});
}


//笔记删除
function deleteNote(){
	//获取请求参数
	var noteId=$("#note_ul a.checked").parent().data("noteId");
	//发送Ajax请求
	$.ajax({
		url:base_path+"/note/delete.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//删除li
				$("#note_ul a.checked").parent().remove();
				//提示成功
				alert(result.msg);
				//关闭窗口
				closeAlertWindow();
			}
		},
		error:function(){
			alert("删除笔记异常");
		}
	});
}


//点击body隐藏菜单
function hideNote(){
			$("#note_ul div").hide();
}


//弹出笔记菜单操作
function popNoteMenu(){
			//隐藏所有笔记菜单
			$("#note_ul div").hide();
			//显示点击的笔记菜单
			var $menu=$(this).parent().next();
			$menu.slideDown(500);
			//设置点击笔记选中效果
			$("#note_ul a").removeClass("checked");
			$(this).parent().addClass("checked");
			
			
			//阻止冒泡机制（为了能在body上点击时关闭弹出菜单）,阻止li向body上冒泡
			return false;   //可以阻止事件冒泡
}

//创建笔记
function add_cnNote(){
			//获取请求
			var bookName=$("#input_note").val();
			var userId=getCookie("uid");
			var bookId=$("#book_ul a.checked").parent().data("bookId");
			var ok=true;
			//格式检验
			if(userId==null){
				ok=false;
				window.location.href="log_in.html";
			}
			if(bookId==null){
				ok=false;
				alert("没有选择笔记本");
			}
			if(bookName==""){
				$("#note_span").html("笔记名为空");
				ok=false;
			}
			//发送ajax请求
			if(ok){
				$.ajax({
					url:base_path+"/note/add.do",
					type:"post",
					data:{"userId":userId,"bookId":bookId,"bookName":bookName},
					dataType:"json",
					success:function(result){
						if(result.status==0){
							//关闭对话框
							closeAlertWindow();
							//创建笔记本列表元素li
							addnoteLi(result.data.cn_note_title,result.data.cn_note_id);
							//提示创建成功
							alert("创建成功");
						}
					},
					error:function(){
						alert("创建笔记异常");
					}
					
				});
			}
			
		}



//"保存笔记"按钮处理
function updateNote(){
			//获取表单请求参数
			var title=$("#input_note_title").val().trim();
			var body=um.getContent();
			var $li=$("#note_ul a.checked").parent();    //获取选中的li元素
			var noteId=$li.data("noteId");
			//清除上次的提示信息
			$("#note_title_span").html("");
			
			//检查格式
			if($li.length==0){
				alert("请选择要保存的笔记");
			}else if(title==""){
				$("#note_title_span").html("<font color='red'>标题不能为空</font>");
			}else{
				//发送Ajax请求
				$.ajax({
					url:base_path+"/note/update.do",
					type:"post",
					data:{"noteId":noteId,"title":title,"body":body},
					dataType:"json",
					success:function(result){
						//成功回调
						if(result.status==0){
							//更新列表li中的标题(会更新标题)
							var sli="";
							sli+='<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> '+title+'<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
							//将选中li元素的a内容替换掉
							$li.find('a').html(sli);
							//表示成功
							alert(result.msg);
							
						}
					},
					error:function(){
						alert("保存笔记异常");
					}
				});
			}
		
		}


//根据笔记Id加载笔记信息
function loadNote(){
	
			//切换显示
			$("#pc_part_3").show();
			$("#pc_part_5").hide();
			
			
			//设置笔记选中状态
			$("#note_ul a").removeClass("checked");
			$(this).find("a").addClass("checked");
			
			
			//获取请求参数
			var noteId=$(this).data("noteId");
			//发送Ajax请求
			$.ajax({
				url:base_path+"/note/load.do",
				type:"post",
				data:{"noteId":noteId},
				dataType:"json",
				success:function(result){
					//回调处理
					if(result.status==0){
						var title=result.data.cn_note_title;     //获取笔记标题
						var body=result.data.cn_note_body;      //获取笔记内容
						//设置到编辑区域
						$("#input_note_title").val(title);
						um.setContent(body);
					}
					
				},
				error:function(){
					alert("打开笔记内容异常");
				}
			});
			return false;
		}



//根基笔记本Id加载笔记信息
function loadBookNotes(){
			$("#pc_part_2").show();
			$("#pc_part_4").hide();
			$("#pc_part_6").hide();
			$("#pc_part_7").hide();
			$("#pc_part_8").hide();
				
			//给li添加选中状态
			$(this).parent().find("a").removeClass("checked");
			$(this).find("a").addClass("checked");
			
		//	$("#book_ul li").children().removeClass("checked");
		//	$(this).children().eq(0).addClass("checked");
			//获取请求参数
			var bookId=$(this).data("bookId");
			//发送Ajax请求
			$.ajax({
				url:base_path+"/note/loadnotes.do",
				type:"post",
				data:{"bookId":bookId},
				dataType:"json",
				success:function(result){
					//发送成功时的回调函数
					if(result.status==0){
						//清空原有的笔记列表
						$("#note_ul li").remove();
						//获取服务器返回的笔记集合信息
						var notes=result.data;
						//循环生成笔记的li元素
						for(var i=0;i<notes.length;i++){
							//获取笔记ID和笔记标题
							var noteId=notes[i].cn_note_id;
							var noteTitle=notes[i].cn_note_title;
							
							//创建一个笔记li元素
							addnoteLi(noteTitle,noteId);
							//给其加上标记
							var typeId=notes[i].cn_note_type_id;
							if(typeId=='2'){
								//给其最后生成的加分享图标
								var img='<i class="fa fa-sitemap"></i>';
								$li=$("#note_ul li:last");   //找到其新生成的。
								$li.find(".btn_slide_down").before(img);
							}
						}
					}
					
				},
				error:function(){
					alert("加载笔记列表异常");
				}
			});
		}


function addnoteLi(noteTitle,noteId){
	var sli="";
	sli+='<li class="online">';
	sli+='<a>';
	sli+='<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> '+noteTitle+'<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
	sli+='</a>';
	sli+='<div class="note_menu" tabindex="-1">';
	sli+='<dl>';
	sli+='<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>';
	sli+='<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>';
	sli+='	<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>';
	sli+='</dl>';
	sli+='</div>';
	sli+='</li>';
	
	//将noteId绑定到li元素上
	var $li=$(sli);
	$li.data("noteId",noteId);
	//将li元素添加到笔记列表ul中
	$("#note_ul").append($li);
}







