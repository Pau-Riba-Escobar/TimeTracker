import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <h2>Clase {@code JSONWriter}</h2>
 * Implementación de la interfaz {@code Visitor} que permite construir un {@code JSONArray}, guardado en el atributo
 * {@code jsonArray}, que nos permite almacenar la información de la jerarquía actual. Los métodos {@code visit()} construiran
 * segun el caso({@code Task, Project o Interval}) un objeto {@code JSONObject} y lo meteran en {@code jsonArray}
 */
public class JSONWriter implements Visitor{
  static {
    boolean assertsEnabled = false;
    assert assertsEnabled = true; // Intentional side effect!!!
    if (!assertsEnabled)
      throw new RuntimeException("Asserts must be enabled!!!");
  }
  private JSONArray jsonArray=new JSONArray();
  public JSONArray getJsonArray(){return jsonArray;}
  private JSONObject buildActivity(JSONObject JAct, Activity activity)
  {
    assert activity != null: " null activity";
    assert activity.getName() != null: "null name";
    assert JAct != null: "null JAct";

    JAct.put("name", activity.getName() );
    JAct.put("tag",activity.getTag());
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    JAct.put("initial_data",activity.getInitialDateTime().format(formatter));
    JAct.put("final_data", activity.getFinalDateTime().format(formatter));
    JAct.put("duration", activity.getDuration().toString());
    JAct.put("parentname", (activity.getParentProject()==null)?"null":activity.getParentProject().getName());

    assert JAct.get("name").toString() != null:"name null";
    assert JAct.get("tag").toString() != null: "tag null";
    assert JAct.get("initial_data").toString() != null: "initial_data null";
    assert LocalDateTime.parse(JAct.get("initial_data").toString()).getYear() >= 0: "yaer negative";
    assert JAct.get("final_data").toString() != null: "initial_data null";
    assert LocalDateTime.parse(JAct.get("final_data").toString()).getYear() >= 0: "yaer negative";
    assert JAct.get("duration").toString() != null: "initial_data null";
    //assert !Duration.parse(JAct.get("duration").toString()).isNegative(): "duration negative";

    return JAct;
  }
  @Override
  public void visit(Project project) {
    if(project == null){
      throw new IllegalArgumentException("project null");
    }
    JSONObject Jproject = new JSONObject();
    Jproject.put("type","Project");
    Jproject = buildActivity(Jproject,project);
    jsonArray.put(Jproject);

    assert Jproject.get("type").toString() == "Project": "type not correct";
    assert !jsonArray.isEmpty(): "jsonArray empty later of putting a task";
  }

  @Override
  public void visit(Task task) {
    if(task == null){
      throw new IllegalArgumentException("task null");
    }
    JSONObject Jtask = new JSONObject();
    Jtask.put("type","Task");
    Jtask = buildActivity(Jtask,task);
    jsonArray.put(Jtask);

    assert Jtask.get("type").toString() == "Task": "type not correct";
    assert !jsonArray.isEmpty(): "jsonArray empty later of putting a task";
  }

  @Override
  public void visit(Interval interval) {
    if(interval == null){
      throw new IllegalArgumentException("interval null");
    }
    JSONObject JInterval = new JSONObject();
    JInterval.put("start",interval.getStart());
    JInterval.put("end",interval.getEnd());
    JInterval.put("duration",interval.getDuration());
    JInterval.put("parentname", interval.getParentTask().getName());
    JInterval.put("type","Interval");
    jsonArray.put(JInterval);

    assert JInterval.get("start") != null:"Start null";
    assert LocalDateTime.parse(JInterval.get("start").toString()).getYear() >= 0: "Year negative";
    assert JInterval.get("end") != null:"end null";
    assert LocalDateTime.parse(JInterval.get("end").toString()).getYear() >= 0: "Year negative";
    assert JInterval.get("duration").toString() != null: "duration null";
    //assert Duration.parse(JInterval.get("duration").toString()).isNegative();
    assert !jsonArray.isEmpty(): "jsonArray empty later of putting an interval";
  }
}
