import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Activity {

  private String name;
  private  LocalDateTime initialDateTime;
  private  LocalDateTime finalDateTime;
  private Duration duration;
  private boolean active;
  private Project parentProject;
  public String getName(){ return name;}

  public LocalDateTime getInitialDateTime(){return initialDateTime;}

  public LocalDateTime getFinalDateTime(){return finalDateTime;}

  public Duration getDuration() {return duration;}

  public Project getParentProject(){return parentProject;}

  public boolean isActive() {return active;}

  // Setters for name, finalDateTime and parentProject
  public void setName(String name){this.name=name;}
  // When we set a finalDateTime we also calculate the Duration of the Activity. Because we should only use this method
  //when we finish an Activity we also calculate its duration
  public  void setInitialDateTime(LocalDateTime time){initialDateTime=time;}
  public void setFinalDateTime(LocalDateTime time){finalDateTime = time;}//duration=Duration.between(initialDateTime,finalDateTime);}
  public void setDuration(Duration d){duration=d;}
  public void setParentProject(Project p){parentProject=p;}
  // if the Activity is currently active, now will be deactivated, and if it is deactivated this method will activate it.
  public void changeState(){active=(!active);}


  public Activity(){
    name = "No name has been assigned to the activity";
    initialDateTime = LocalDateTime.now();
    finalDateTime = LocalDateTime.now();
    duration = Duration.ZERO;
    active = false;
    parentProject=null;
  }

  /*
  public Activity(String name)
  {
    this.name = name;
    initialDateTime = LocalDateTime.now();
    finalDateTime = null;
    duration = Duration.ZERO;
    active = false;
  }*/
  public abstract void accept(Visitor visitor);
  public abstract void printInfo();
  public abstract Duration TotalTimeSpent(timePeriods period);
  public abstract void recalculateTimes();
}
