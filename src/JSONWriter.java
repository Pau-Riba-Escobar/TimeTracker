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
  private JSONArray jsonArray=new JSONArray();
  public JSONArray getJsonArray(){return jsonArray;}
  private JSONObject buildActivity(JSONObject JAct, Activity activity)
  {
    JAct.put("name", activity.getName() );
    JAct.put("tag",activity.getTag());
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    JAct.put("initial_data",activity.getInitialDateTime().format(formatter));
    JAct.put("final_data", activity.getFinalDateTime().format(formatter));
    JAct.put("duration", activity.getDuration().toString());
    JAct.put("parentname", (activity.getParentProject()==null)?"null":activity.getParentProject().getName());
    return JAct;
  }
  @Override
  public void visit(Project project) {
    JSONObject Jproject = new JSONObject();
    Jproject.put("type","Project");
    Jproject = buildActivity(Jproject,project);
    jsonArray.put(Jproject);
  }

  @Override
  public void visit(Task task) {
    JSONObject Jtask = new JSONObject();
    Jtask.put("type","Task");
    Jtask = buildActivity(Jtask,task);
    jsonArray.put(Jtask);
  }

  @Override
  public void visit(Interval interval) {
    JSONObject JInterval = new JSONObject();
    JInterval.put("start",interval.getStart());
    JInterval.put("end",interval.getEnd());
    JInterval.put("duration",interval.getDuration());
    JInterval.put("parentname", interval.getParentTask().getName());
    JInterval.put("type","Interval");
    jsonArray.put(JInterval);
  }
}
