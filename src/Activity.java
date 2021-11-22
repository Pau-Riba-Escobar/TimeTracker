import java.time.Duration;
import java.time.LocalDateTime;

/**
 * <h2>Clase Actividad({@code Activity})</h2>
 * Esta clase contiene toda la información(atributos) y métodos que una Actividad(proyecto o tarea) debería tener. De una
 * actividad necesitamos saber su nombre({@code name}), las fechas inicial({@code initialDateTime}) y final({@code finalDateTime}), su duración({@code duration}), si está activa() y cual
 * es su proyecto padre({@code parentProject})
 */
public abstract class Activity {

  private String name;
  private String tag;
  private  LocalDateTime initialDateTime;
  private  LocalDateTime finalDateTime;
  private Duration duration;
  private boolean active;
  private Project parentProject;
  public String getName(){ return name;}

  public String getTag() {return tag;}

  public LocalDateTime getInitialDateTime(){return initialDateTime;}

  public LocalDateTime getFinalDateTime(){return finalDateTime;}

  public Duration getDuration() {return duration;}

  public Project getParentProject(){return parentProject;}

  public boolean isActive() {return active;}

  // Setters for name, finalDateTime and parentProject
  public void setName(String name){this.name=name;}

  public void setTag(String tag) {this.tag = tag;}

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
    tag = "NO TAG";
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

  /**
   * Método que nos permite implementar el patrón visitor en actividades. Es abstracta porque se va a redefinir de
   * forma distinta en {@code Project} o {@code Task}
   * @param visitor instancia de la clase que implementa al visitor
   */
  public abstract void accept(Visitor visitor);

  /**
   * Función que nos permite imprimir la información de una Actividad. Redefinida en {@code Project} y {@code Task}
   * @deprecated
   */
  public abstract void printInfo();
  public abstract Duration TotalTimeSpent(timePeriods period);

  /**
   * Método que recalcula los parámetros de tiempo({@code initialDateTime,finalDateTime y duration}) de una actividad
   * cuando sucede una actualización en el tiempo. Será diferente en el caso de {@code Task}  que en el de {@code Project}
   */
  public abstract void recalculateTimes();
}
