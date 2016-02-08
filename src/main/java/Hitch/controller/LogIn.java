package Hitch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by olafurma on 6.2.2016.
 */
@Controller
@RequestMapping("/login")
public class LogIn {
  private int id = 0;

  @RequestMapping(method= RequestMethod.GET)
  public @ResponseBody int handleLogIn() throws IOException
  {
    this.id++;
    return id;
  }
}
