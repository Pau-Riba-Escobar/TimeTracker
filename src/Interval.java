import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {
  private Task parentTask;
  private LocalDateTime start;
  private LocalDateTime end;
  private Duration duration;

  public Interval()
  {
    start=LocalDateTime.now();
    end=LocalDateTime.now();
    duration=Duration.ZERO;
    parentTask=null;
  }
  public Duration getDuration(){return duration;}
  public LocalDateTime getStart(){return  start;}
  public LocalDateTime getEnd(){return end;}
  public Task getParentTask(){return parentTask;}
  public void setParentTask(Task parent){parentTask=parent;}
  public void setStart(LocalDateTime start) {this.start = start;}
  public void setDuration(Duration duration) {this.duration = duration;}
  public void setEnd(LocalDateTime end) {this.end = end;}
  public void accept(Visitor visitor)
  {
    visitor.visit(this);
  }
  public void printInfo()
  {
    System.out.print("Interval child of "+parentTask.getName()+"  "+start+"  "+end+"  "+getDuration().toSeconds()+"\n");
  }

  @Override
  public void update(Observable o, Object arg) {
    end = (LocalDateTime)arg;
    duration = Duration.between(start,end);
    parentTask.recalculateTimes();
    //printInfo();
  }
}
