import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;



public class Clock extends Observable {
  private static Timer timer;
  private static Clock clock= new Clock();
  // hacer el constructor privado
  private Clock()
  {
    timer=new Timer();
  }
  public void startTimer()
  {
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        synchronized (this)
        {
          tick();
        }
      }
    };
    timer.scheduleAtFixedRate(task,Date.from(Instant.now()),2000);
  }
  public Timer getTimer(){return timer;}
  public void stopTimer()
  {
    timer.cancel();
  }
  public void tick()
  {
    setChanged();
    notifyObservers(LocalDateTime.now());
  }
  public static Clock getInstance(){
    return clock;
  }
}
