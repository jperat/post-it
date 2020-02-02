package com.jperat.postit.dao;

import com.jperat.postit.model.File;

public interface FileDao {
	File findByName(String name);
	
	void save(File file);
	
	void remove(File file);
}
