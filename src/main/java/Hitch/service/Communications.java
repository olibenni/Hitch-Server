package Hitch.service;

import Hitch.service.dto.DriverMessage;
import Hitch.service.dto.PassengerInfo;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by olafurma on 9.11.2015.
 */

@Controller
@RequestMapping("/fromPassenger")
public class Communications {

  // Our "database", cheaper, faster and more suitable for this applications need (at this stage)
  private List<PassengerInfo> passengersDB = new ArrayList<>();


  // Handles rest get, not used at the current moment.
  @RequestMapping(method=RequestMethod.GET)
  public @ResponseBody Object newPassenger(@RequestParam(value="info") Object info) throws IOException{
    System.out.println("SOMEONE CALLED ME");
    return info;
  }

  // Broadcasts a new driver event
  @MessageMapping("/newDriver")
  @SendTo("/broadcast/message")
  public List<PassengerInfo> broadCast(Object empty) throws Exception {
    Thread.sleep(1000); // simulated delay
    return this.passengersDB;
  }

  // Broadcasts a new passenger event
  @MessageMapping("/fromPassenger")
  @SendTo("/broadcast/message")
  public PassengerInfo broadCast(PassengerInfo info) throws Exception {
    if(info.getType().equals("newPassenger")) {
      passengersDB.add(info);
    }else{
      this.removeFromDb(info.getPassenger());
    }
    Thread.sleep(1000); // simulated delay
    return info;
  }

  // Broadcasts a drivers respond
  @MessageMapping("/fromDriver")
  @SendTo("/broadcast/message")
  public DriverMessage broadCast(DriverMessage info) throws Exception {
    Thread.sleep(1000); // simulated delay
    return info;
  }


  // Removes all instances of a passengers requests from our "database".
  public void removeFromDb(String passenger){
    passengersDB = passengersDB.stream().filter(x -> !x.getPassenger().equals(passenger)).collect(Collectors.toList());
  }
}