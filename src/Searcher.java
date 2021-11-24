import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe padre de las classe SearcherByTag y SearcherByName
 */
public abstract class Searcher implements Visitor {
  private List<Activity> objList;

  public Searcher() {
    objList = new LinkedList<Activity>();
  }

  public List<Activity> getObjList() {
    return objList;
  }

}
