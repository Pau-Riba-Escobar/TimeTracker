import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Project extends Activity{
  private List<Activity> activities = new ArrayList<Activity>();
  /*
    public Project(String name, Project parentProject){
    this.setName(name);
    this.setParentProject(parentProject);
  }*/
  public Project(String name)
  {
    this.setName(name);
  }
  public void addActivity(Activity act)
  {
    activities.add(act);
    act.setParentProject(this);
  }
  public List<Activity> getActivities(){return activities;}
  @Override
  public Duration TotalTimeSpent(timePeriods period) {
    Duration totalTime = Duration.ZERO;
    for (Activity act:activities) {
      totalTime.plus(act.TotalTimeSpent(period));
    }
    return totalTime;
  }
  public void recalculateTimes()
  {
    LocalDateTime finalDate = getFinalDateTime();
    for(Activity a:activities)
    {
      if(finalDate.isBefore(a.getFinalDateTime()))
      {
        finalDate = a.getFinalDateTime();
      }
    }
    setFinalDateTime(finalDate);
    setDuration(Duration.between(getInitialDateTime(),finalDate));
    Project parent = getParentProject();
    if(parent!=null)
    {
      parent.recalculateTimes();
    }
    //printInfo();
  }
  @Override
  public void accept(Visitor visitor){
    visitor.visit(this);
    for(Activity a:activities)
    {
      a.accept(visitor);
    }
  }
  @Override
  public void printInfo() {
    System.out.print("Project "+getName()+" child of "+((getParentProject()!=null)?getParentProject().getName():"null")+"  "+getInitialDateTime()+"  "+getFinalDateTime()+"  "+getDuration().toSeconds()+"\n");
  }
}
