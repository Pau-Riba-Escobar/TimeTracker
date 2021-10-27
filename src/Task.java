import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task extends Activity{
  private List<Interval> intervals;
  private int nIntervals;
  private Duration lastUpdateDuration;

  /*
  public Task(String name, Project parent){
     this.setName(name);
     this.setParentProject(parent);
  }
  */

  public Task(String name)
  {
    this.setName(name);
    nIntervals=0;
    intervals=new ArrayList<Interval>();
    lastUpdateDuration=Duration.ZERO;
  }
  public int getnIntervals(){return nIntervals;}
  public Duration TotalTimeSpent(timePeriods period) {
    //  this should return the sum of the durations of all the intervals of the task that fit the period
    switch(period)
    {
      case ALL -> {

      }
    }
    /*
    Duration totalDuration=Duration.ZERO;
    * for(Interval interval:intervals)
    * {
    *   totalDuration.plus(interval.getDuration());
    * }*/
    return Duration.ZERO;
  }
  public void startTask(Clock clock)
  {
    if(!isActive())
    {
      Interval i = new Interval();
      addInterval(i);
      clock.addObserver(i);
      changeState();
      System.out.print(getName()+" starts\n");
    }
    else{
      System.out.print("Cannot start task because it is already active");
    }

  }
  public void stopTask(Clock clock){
    if(isActive())
    {
      Interval intervalToDelete = intervals.get(nIntervals-1);
      clock.deleteObserver(intervalToDelete);
      changeState();
      lastUpdateDuration = Duration.ZERO;
      System.out.print(getName()+" stops\n");
    }
    else{
      System.out.print("Cannot start task because it is already unactive");
    }
  }
  //deleteInterval has currently no use but it can be used as a destructor if we want to delete a task
  public void deleteInterval(Interval i)
  {
    intervals.remove(i);
    nIntervals--;
  }
  public void addInterval(Interval interval)
  {
    interval.setParentTask(this);
    intervals.add(interval);
    nIntervals++;
  }

  @Override
  public void printInfo() {
    System.out.print("Task "+getName()+" child of "+getParentProject().getName()+"  "+getInitialDateTime()+"  "+getFinalDateTime()+"  "+getDuration().toSeconds()+"\n");
  }
  @Override
  public void accept(Visitor visitor){
    visitor.visit(this);
    for(Interval i:intervals)
    {
      i.accept(visitor);
    }
  }
  public void recalculateTimes(){
    // sumar la duración del último intervalo a la tarea
    Duration currentDuration = getDuration();
    currentDuration = currentDuration.minus(lastUpdateDuration);
    Interval lastInterval = intervals.get(nIntervals-1);
    lastUpdateDuration = lastInterval.getDuration();
    currentDuration = currentDuration.plus(lastInterval.getDuration());
    setDuration(currentDuration);
    Project parent = getParentProject();
    setFinalDateTime(lastInterval.getEnd());
    parent.recalculateTimes();
    //printInfo();
  }
}
