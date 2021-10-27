public class SearcherByName implements Visitor{
  private String name;
  private Activity correspondingObj;
  public SearcherByName(String name){this.name = name;correspondingObj=null;}

  public Activity getCorrespondingObj() {
    return correspondingObj;
  }

  @Override
  public void visit(Project project) {
    if(project.getName().equals(name))
    {
      correspondingObj = project;
    }
  }

  @Override
  public void visit(Task task) {
    if(task.getName().equals(name))
    {
      correspondingObj = task;
    }
  }

  @Override
  public void visit(Interval interval) {
    return;
  }
}
