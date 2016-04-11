package org.hly.cloudnote.dao;

import java.util.List;

import org.hly.cloudnote.entity.Share;

public interface ShareDao {
 public void add(Share share);
 public Share findByNoteId(String noteId);
 public void update(String shareTitile,String shareBody,String noteId);
 public List<Share> load(String search);
 public Share findByShareId(String shareId);
}
