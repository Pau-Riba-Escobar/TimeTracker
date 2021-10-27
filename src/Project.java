import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** <h2> Clase Project</h2>
 * Esta clase representa los proyectos que tendremos en nuestra aplicación.
 * Los proyectos contienen todos los atributos de Activity, y una lista de actividades.
 */
public class Project extends Activity{
  private List<Activity> activities = new ArrayList<Activity>();
  /*
    public Project(String name, Project parentProject){
    this.setName(name);
    this.setParentProject(parentProject);
  }*/
  /**<h2>Constructor de la clase</h2>
   * @param name -> nombre del proyecto (string).
   */
  public Project(String name)
  {
    this.setName(name);
  }
  /**<h2>Función para añadir una actividad</h2>
      *Añadimos una actividad hijo al proyecto actual
   * *
       */
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
  /**<h2> Funcion para recalcular tiempos</h2>
   * Miramos para cada actividad que contiene el proyecto
   * que actividad ha terminado ultima (finalDate mayor).
   * Esa fecha se pasa a la fecha final del proyecto
   * Calculamos la duracion del proyecto:
   *  -. ( fecha final - fecha incial )
   *  Si el proyecto actual tiene un proyecto padre,
   *  hacemos lo mismo para el.
   */
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
  /** <h2> accept </h2>
   * En esta función estamos acceptando la clase visitante
   * para visitarla y que aplique nuevas funcionalidades
   * externas a la clase proyecto. Aplicamos el patron de
   * diseño visitor.
   * @param visitor - objeto visitor para aplicar patrón.
   */
  @Override
  public void accept(Visitor visitor){
    visitor.visit(this);
    for(Activity a:activities)
    {
      a.accept(visitor);
    }
  }
  /**
   * La información mostrada consiste en:
   *  .- nombre del proyecto
   *  .- nombre del proyecto padre
   *  .- fecha inicial, final y duración.
   *  @deprecated
   */
  @Override
  public void printInfo() {
    System.out.print("Project "+getName()+" child of "+((getParentProject()!=null)?getParentProject().getName():"null")+"  "+getInitialDateTime()+"  "+getFinalDateTime()+"  "+getDuration().toSeconds()+"\n");
  }
}
