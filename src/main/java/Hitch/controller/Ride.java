package Hitch.controller;

//import Hitch.persistance.DataBase;
import Hitch.persistance.SQLHelper2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.crypto.Data;
import java.io.IOException;

/**
 * Created by olafurma on 6.2.2016.
 */
@Controller
@RequestMapping("/newRide")
public class Ride {

  // Does nothing at the moment but concat the input together and send it back
  // This function should, eventually, send to a database the ride with id.
  // If database code is uncommented it will crash ... Don't know why.
  @RequestMapping(method= RequestMethod.GET)
  public @ResponseBody String handleNewRide(@RequestParam(value="from") String from, @RequestParam(value="to") String to, @RequestParam(value="phone") String phone) throws IOException
  {
//    DataBase base = new DataBase();
//    base.test();
    int pickup = Integer.parseInt(from);
    int dropoff = Integer.parseInt(to);
    int phonenr = Integer.parseInt(phone);

    SQLHelper2 db = new SQLHelper2();
    db.insertRide(pickup, dropoff, phonenr);
    db.printMatrix(db.getRideArray());

    String response = "From: " + from + " To: " + to;
    return response;
  }
}
