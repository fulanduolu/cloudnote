/**alert.js 封装对话框处理**/

//弹出创建笔记对话框
function alertAddNoteWndow(){
	//如果没有选中笔记本，提示
	var $a=$("#book_ul a.checked");
	if($a.length==0){
		alert("请选择笔记本")
	}else{     //有选中的笔记本.再弹出创建笔记的对话框
		$("#can").load("alert/alert_note.html");   //给其加div样式
		$(".opacity_bg").show();    //让其背景显现	
	}
};


//弹出重命名笔记本对话框
function alertRenameBookWindow(){
	$("#can").load("alert/alert_rename.html");   //给其加div样式
	$(".opacity_bg").show();    //让其背景显现	
};


//弹出创建笔记本对话框
function alertAddBookWindow(){
	//弹出对话框alert_notebook.html
	$("#can").load("alert/alert_notebook.html");   //给其加div样式
	$(".opacity_bg").show();    //让其背景显现	
};

//弹出删除笔记确认对话框
function alertDeleteNoteWindow(){
	$("#can").load("alert/alert_delete_note.html");   //给其加div样式
	$(".opacity_bg").show();    //让其背景显现	
};

//弹出转移笔记对话框
function alertMoveNoteWindow(){
	$(".opacity_bg").show();    //让其背景显现	
	//加载alert_move.html
	$("#can").load("alert/alert_move.html",function(){
		//为alert_move.html中<select>加载数据
		var books=$("#book_ul li");    //book列表
		//循环book列表数据
		for(var i=0;i<books.length;i++){
			var $li=$(books[i]);   //获取li元素并转为jQuerycf
			var bookId=$li.data("bookId");    //获取笔记本的bookId
			var bookName=$li.text().trim();          //获取笔记本的名称
			//创建一个option元素
			var sopt='';
			sopt+='<option value="'+bookId+'">';
			sopt+=bookName;	
			sopt+=' </option>';
			//将其添加到select中
			$("#moveSelect").append(sopt);

		}
	});   //因为需要给其中的select中添加<option>，所以必须将其逻辑写到回调函数中

		

		
}

function alertLikeNoteWindow(){
	$(".opacity_bg").show();    //让其背景显现	
	//加载alert_like.html
	$("#can").load("alert/alert_like.html");
}

//删除收藏笔记的对话框
function alertDeleteLike(){
	$(".opacity_bg").show();    //让其背景显现	
	//加载alert_like.html
	$("#can").load("alert/alert_delete_like.html");
}




//关闭对话框
function closeAlertWindow(){    //用到了动态绑定 
	//关闭操作
	$("#can").empty();     //清空对话框内容
	$(".opacity_bg").hide();    //影藏背景
};