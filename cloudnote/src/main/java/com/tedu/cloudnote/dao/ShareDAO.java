package com.tedu.cloudnote.dao;

import java.util.List;
import java.util.Map;

import com.tedu.cloudnote.entity.Share;

public interface ShareDAO {

	public void save(Share share);
	
	public List<Share> findLikeTitle(Map<String,Object> params);
	
	public Share findById(String shareId);
	
	public String findNidBySid(String shareId);
}
