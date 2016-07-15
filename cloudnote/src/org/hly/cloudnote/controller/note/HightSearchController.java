package org.hly.cloudnote.controller.note;

import javax.annotation.Resource;

import org.hly.cloudnote.service.NoteService;
import org.hly.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/note")
public class HightSearchController {
  @Resource
  private NoteService noteService;

  @RequestMapping("/hightSearch.do")
  @ResponseBody
  public NoteResult execute(String title, String status, String begin, String end) {
    NoteResult result = noteService.hightSearch(title, status, begin, end);
    return result;
  }
}
