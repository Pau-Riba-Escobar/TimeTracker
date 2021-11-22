import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class Searcher implements Visitor {
  private List<Activity> objList;
  public Searcher(){
    objList = new LinkedList<Activity>();
  }
  public List<Activity> getObjList() {
    return objList;
  }

}
