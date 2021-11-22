import java.util.List;

public class SearcherByTag extends Searcher{
  private String tag;
  public SearcherByTag(String tag){this.tag = tag;}
  @Override
  public void visit(Project project) {
    if(project.getTag().equals(tag))
    {
      List<Activity> objectList = getObjList();
      objectList.add(project);
    }
  }

  @Override
  public void visit(Task task) {
    if(task.getName().equals(tag))
    {
      List<Activity> objectList = getObjList();
      objectList.add(task);
    }
  }

  @Override
  public void visit(Interval interval) {
    return;
  }
}
