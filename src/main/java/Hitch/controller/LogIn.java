package Hitch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;

import javax.websocket.server.PathParam;
import java.io.IOException;

/**
 * Created by olafurma on 6.2.2016.
 */
@Controller
@RequestMapping("/login")
public class LogIn {

  @RequestMapping(method= RequestMethod.GET)
  public @ResponseBody String handleLogIn() throws IOException
  {
    System.out.println(RequestContextHolder.currentRequestAttributes().getSessionId());
    return RequestContextHolder.currentRequestAttributes().getSessionId();
  }
}
