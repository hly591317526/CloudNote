package org.hly.cloudnote.service;

import org.hly.cloudnote.util.NoteResult;

public interface ShareService {
   public NoteResult add(String noteId);
   public NoteResult load(String search);
   public NoteResult loadShare(String shareId);
}
