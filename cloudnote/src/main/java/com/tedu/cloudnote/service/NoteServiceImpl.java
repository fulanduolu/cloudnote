package com.tedu.cloudnote.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tedu.cloudnote.dao.NoteDAO;
import com.tedu.cloudnote.dao.ShareDAO;
import com.tedu.cloudnote.entity.Note;
import com.tedu.cloudnote.entity.Share;
import com.tedu.cloudnote.util.NoteResult;
import com.tedu.cloudnote.util.NoteUtil;

@Service("noteService")
@Transactional
public class NoteServiceImpl implements NoteService{

	@Resource
	private NoteDAO dao;
	
	@Resource
	private ShareDAO shareDao;
	
	public NoteResult loadBookNotes(String bookId) {
		//按照笔记本ID查询
		List<Map> list=dao.findByBookId(bookId);
		//构建返回结果
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMsg("查询结果");
		result.setData(list);
		return result;
	}

	public NoteResult loadNote(String noteId) {
		//按照笔记ID的查询笔记的信息
		Note note=dao.findById(noteId);
		//创建返回结果
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMsg("查询完毕");
		result.setData(note);
		return result;
	}

	public NoteResult updateNote(String noteId, String title, String body) {
		Note note=new Note();
		note.setCn_note_id(noteId);  //设置笔记Id
		note.setCn_note_title(title);   //设置标题
		note.setCn_note_body(body);     //设置内容
		Long time=System.currentTimeMillis();
		note.setCn_note_last_modify_time(time);      //设置修改时间	
		int rows=dao.updateNote(note);    //更新列表
		NoteResult result=new NoteResult();
		
		
		if(rows==1){   //成功    
			result.setStatus(0);
			result.setMsg("保存笔记成功");
		}else{    //失败
			result.setStatus(1);
			result.setMsg("保存笔记失败");
		}
		return result;
	}

	public NoteResult addNote(String userId, String bookId, String bookName) {
		Note note=new Note();
		note.setCn_user_id(userId);
		note.setCn_notebook_id(bookId);
		note.setCn_note_title(bookName);
		String noteId=NoteUtil.createId();
		note.setCn_note_id(noteId);
		note.setCn_note_create_time(System.currentTimeMillis());
		note.setCn_note_last_modify_time(System.currentTimeMillis());
		dao.save(note);
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMsg("创建笔记成功");
		result.setData(note);
		return result;
	}

	public NoteResult deleteNote(String noteId) {
		int rows=dao.updateStatus(noteId);
		//创建返回结果
		NoteResult result=new NoteResult();
		if(rows>=1){
			result.setStatus(0);
			result.setMsg("删除笔记成功");
		}else{
			result.setStatus(1);
			result.setMsg("删除笔记失败");
		}
		return result;
	}

	public NoteResult moveNote(String noteId, String bookId) {
		Note note=new Note();
		note.setCn_note_id(noteId);
		note.setCn_notebook_id(bookId);
		int rows=dao.updateBookId(note);
		NoteResult result=new NoteResult();
		if(rows>=1){
			result.setStatus(0);
			result.setMsg("转移笔记本成功");		
		}else{
			result.setStatus(1);
			result.setMsg("转移笔记异常");
		}
		return result;
	}

	//分享笔记
	public NoteResult shareNote(String noteId) {
		//获取笔记信息
		Note note=dao.findById(noteId);
		String title=note.getCn_note_title();
		String body=note.getCn_note_body();
		
		NoteResult result=new NoteResult();
		
		//TODO 检查cn_note_type_id是否为分享状态,如果以分享直接退出,不执行下面的逻辑。
		if("2".equals(note.getCn_note_type_id())){
			//此时表示为已经分享过了
			result.setStatus(1);
			result.setMsg("该笔记已经分享过了");
			return result;
		}
		
	
		//TODO 更新笔记的cn_note_type_id为分享状态
		int rowns=dao.updateType(noteId);
		if(rowns>=1){
			Share share=new Share();
			share.setCn_note_id(noteId);    //设置笔记ID
			share.setCn_share_id(NoteUtil.createId());
			share.setCn_share_title(title);
			share.setCn_share_body(body);
			shareDao.save(share);       //传入share  

			result.setStatus(0);
			result.setMsg("分享笔记成功");
			return result;
		}else{
			result.setStatus(2);
			result.setMsg("分享笔记失败");
			return result;
		}	
		
	}

	public NoteResult showTrash(String userId) {
		List<Note> list=dao.findTrashById(userId);
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMsg("查询回收站成功");
		result.setData(list);
		return result;
	}

	public NoteResult searchShareNote(String noteTitle,int page) {
		String title="%";
		//处理查询条件值
		if(noteTitle!=null){
			title="%"+noteTitle+"%";
		}
		//计算查询的抓取起点
		if(page<1){
			page=1;
		}
		int begin=(page-1)*5;
		//封装成Map参数
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("begin", begin);
		params.put("noteTitle", title);
		
		List<Share> list=shareDao.findLikeTitle(params);
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMsg("查询成功");
		result.setData(list);
		return result;
	}

	public NoteResult loadShareNote(String shareId) {
		Share share=shareDao.findById(shareId);
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMsg("加载笔记本成功");
		result.setData(share);
		return result;
	}

	@Transactional
	public NoteResult shareLike(String userId, String shareId) {
		
		
		Note note2=new Note();
		String id=NoteUtil.createId();
		note2.setCn_note_id(id);
		String noteId=shareDao.findNidBySid(shareId);
		
		
		Note note1=dao.findById(noteId);
		String title=note1.getCn_note_title();
		
		Note note3=new Note();
		note3.setCn_user_id(userId);
		note3.setCn_note_title(title);
		
		//1.首先在cn_note表中查找有没有ID一样且type是3的
		Note note=dao.findByIdAndTypeId(note3);
		NoteResult result=new NoteResult();
		if(note!=null){
			//证明表里面已经存在
			result.setStatus(1);
			result.setMsg("已经收藏过啦!");
			return result;
		}
		//这里表示原来表里面没有,查到noteId
		String body=note1.getCn_note_body();
		
		
		note2.setCn_user_id(userId);
		note2.setCn_note_title(title);
		note2.setCn_note_body(body);
		dao.insertLike(note2);
		result.setStatus(0);
		result.setMsg("收藏笔记成功");
		return result;
	}

	public NoteResult showLikes(String userId) {
		//查询所有type_id='3'的
		List<Note> list=dao.findByLike(userId);
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMsg("查询收藏笔记成功");
		result.setData(list);
		return result;
	}

	public NoteResult DeleteLikes(String userId, String noteId) {
		Note note=new Note();
		note.setCn_user_id(userId);
		note.setCn_note_id(noteId);
		dao.deleteById(note);
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMsg("删除成功");
		return result;
	}

}
