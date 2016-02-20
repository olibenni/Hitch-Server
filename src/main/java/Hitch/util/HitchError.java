package Hitch.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by olafurma on 20.2.2016.
 */
public class HitchError extends Exception {
  private Logger logger = Logger.getLogger(HitchError.class.getName());

  public HitchError(String message){
    super(message);
  }

  public HitchError(Throwable cause){
    super(cause);
  }

  public HitchError(String message, Throwable cause){
    super(message, cause);
  }

  public HitchError(String message, Level severity, Exception exception)
  {
    super(message);
    logger.log(severity, message, exception);
  }
}
