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
  @Override
  public void visit(Project project) {
    JSONObject Jproject = new JSONObject();
    Jproject.put("name", project.getName() );
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    Jproject.put("initial_data",project.getInitialDateTime().format(formatter));
    Jproject.put("final_data", project.getFinalDateTime().format(formatter));
    Jproject.put("duration", project.getDuration().toString());
    Jproject.put("parentname", (project.getParentProject()==null)?"null":project.getParentProject().getName());
    Jproject.put("type","Project");
    jsonArray.put(Jproject);
  }

  @Override
  public void visit(Task task) {
    JSONObject Jtask = new JSONObject();
    Jtask.put("name", task.getName() );
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    Jtask.put("initial_data",task.getInitialDateTime().format(formatter));
    Jtask.put("final_data", task.getFinalDateTime().format(formatter));
    Jtask.put("duration", task.getDuration().toString());
    Jtask.put("parentname", task.getParentProject().getName());
    Jtask.put("type","Task");
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
