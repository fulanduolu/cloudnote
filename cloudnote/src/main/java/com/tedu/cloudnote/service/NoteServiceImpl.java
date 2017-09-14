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
		//���ձʼǱ�ID��ѯ
		List<Map> list=dao.findByBookId(bookId);
		//�������ؽ��
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMsg("��ѯ���");
		result.setData(list);
		return result;
	}

	public NoteResult loadNote(String noteId) {
		//���ձʼ�ID�Ĳ�ѯ�ʼǵ���Ϣ
		Note note=dao.findById(noteId);
		//�������ؽ��
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMsg("��ѯ���");
		result.setData(note);
		return result;
	}

	public NoteResult updateNote(String noteId, String title, String body) {
		Note note=new Note();
		note.setCn_note_id(noteId);  //���ñʼ�Id
		note.setCn_note_title(title);   //���ñ���
		note.setCn_note_body(body);     //��������
		Long time=System.currentTimeMillis();
		note.setCn_note_last_modify_time(time);      //�����޸�ʱ��	
		int rows=dao.updateNote(note);    //�����б�
		NoteResult result=new NoteResult();
		
		
		if(rows==1){   //�ɹ�    
			result.setStatus(0);
			result.setMsg("����ʼǳɹ�");
		}else{    //ʧ��
			result.setStatus(1);
			result.setMsg("����ʼ�ʧ��");
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
		result.setMsg("�����ʼǳɹ�");
		result.setData(note);
		return result;
	}

	public NoteResult deleteNote(String noteId) {
		int rows=dao.updateStatus(noteId);
		//�������ؽ��
		NoteResult result=new NoteResult();
		if(rows>=1){
			result.setStatus(0);
			result.setMsg("ɾ���ʼǳɹ�");
		}else{
			result.setStatus(1);
			result.setMsg("ɾ���ʼ�ʧ��");
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
			result.setMsg("ת�ƱʼǱ��ɹ�");		
		}else{
			result.setStatus(1);
			result.setMsg("ת�Ʊʼ��쳣");
		}
		return result;
	}

	//����ʼ�
	public NoteResult shareNote(String noteId) {
		//��ȡ�ʼ���Ϣ
		Note note=dao.findById(noteId);
		String title=note.getCn_note_title();
		String body=note.getCn_note_body();
		
		NoteResult result=new NoteResult();
		
		//TODO ���cn_note_type_id�Ƿ�Ϊ����״̬,����Է���ֱ���˳�,��ִ��������߼���
		if("2".equals(note.getCn_note_type_id())){
			//��ʱ��ʾΪ�Ѿ��������
			result.setStatus(1);
			result.setMsg("�ñʼ��Ѿ��������");
			return result;
		}
		
	
		//TODO ���±ʼǵ�cn_note_type_idΪ����״̬
		int rowns=dao.updateType(noteId);
		if(rowns>=1){
			Share share=new Share();
			share.setCn_note_id(noteId);    //���ñʼ�ID
			share.setCn_share_id(NoteUtil.createId());
			share.setCn_share_title(title);
			share.setCn_share_body(body);
			shareDao.save(share);       //����share  

			result.setStatus(0);
			result.setMsg("����ʼǳɹ�");
			return result;
		}else{
			result.setStatus(2);
			result.setMsg("����ʼ�ʧ��");
			return result;
		}	
		
	}

	public NoteResult showTrash(String userId) {
		List<Note> list=dao.findTrashById(userId);
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMsg("��ѯ����վ�ɹ�");
		result.setData(list);
		return result;
	}

	public NoteResult searchShareNote(String noteTitle,int page) {
		String title="%";
		//�����ѯ����ֵ
		if(noteTitle!=null){
			title="%"+noteTitle+"%";
		}
		//�����ѯ��ץȡ���
		if(page<1){
			page=1;
		}
		int begin=(page-1)*5;
		//��װ��Map����
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("begin", begin);
		params.put("noteTitle", title);
		
		List<Share> list=shareDao.findLikeTitle(params);
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMsg("��ѯ�ɹ�");
		result.setData(list);
		return result;
	}

	public NoteResult loadShareNote(String shareId) {
		Share share=shareDao.findById(shareId);
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMsg("���رʼǱ��ɹ�");
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
		
		//1.������cn_note���в�����û��IDһ����type��3��
		Note note=dao.findByIdAndTypeId(note3);
		NoteResult result=new NoteResult();
		if(note!=null){
			//֤���������Ѿ�����
			result.setStatus(1);
			result.setMsg("�Ѿ��ղع���!");
			return result;
		}
		//�����ʾԭ��������û��,�鵽noteId
		String body=note1.getCn_note_body();
		
		
		note2.setCn_user_id(userId);
		note2.setCn_note_title(title);
		note2.setCn_note_body(body);
		dao.insertLike(note2);
		result.setStatus(0);
		result.setMsg("�ղرʼǳɹ�");
		return result;
	}

	public NoteResult showLikes(String userId) {
		//��ѯ����type_id='3'��
		List<Note> list=dao.findByLike(userId);
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMsg("��ѯ�ղرʼǳɹ�");
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
		result.setMsg("ɾ���ɹ�");
		return result;
	}

}
