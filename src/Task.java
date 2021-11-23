import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/** <h2> Clase Task </h2>
 * Esta clase representa una tarea.
 * Las tareas heredan metodos y atributos de actividad.
 * Una Tarea contiene ademas:
 *    - Lista de intervalos (intervals)
 *    - Numeros de intervalos (nIntervals)
 *    - Su utlima duración actualizada (lastUpdateDuration)
 */
public class Task extends Activity{
  static {
    boolean assertsEnabled = false;
    assert assertsEnabled = true; // Intentional side effect!!!
    if (!assertsEnabled)
      throw new RuntimeException("Asserts must be enabled!!!");
  }
  private List<Interval> intervals;
  private int nIntervals;
  private Duration lastUpdateDuration;

  /*
  public Task(String name, Project parent){
     this.setName(name);
     this.setParentProject(parent);
  }
  */
  /**<h2>constructor de Task</h2>
   * @param name -> nombre de la Tarea (string)
   *             es necsario para construir una tarea.
   */
  public Task(String name)
  {
    //assert name != null: "null name";
    if(name == null){
      throw new IllegalArgumentException("name null");
    }
    this.setName(name);
    nIntervals=0;
    intervals=new ArrayList<Interval>();
    lastUpdateDuration=Duration.ZERO;

    assert this.getName() != null: "name null";
    assert this.nIntervals >= 0: "nIntervals negative";
    assert !this.lastUpdateDuration.isNegative(): "duration negative";

  }
  public int getnIntervals(){return nIntervals;}
  public Duration TotalTimeSpent(timePeriods period) {
    //  this should return the sum of the durations of all the intervals of the task that fit the period
    switch(period)
    {
      case ALL -> {

      }
    }
    /*
    Duration totalDuration=Duration.ZERO;
    * for(Interval interval:intervals)
    * {
    *   totalDuration.plus(interval.getDuration());
    * }*/
    return Duration.ZERO;
  }
/**<h2> Start Task</h2>
 * En esta función empezariamos o activaremos
 * una tarea (no crearla). Añadimos un intervalo
 * a la lista de intervalos de la tarea. Ese inter-
 * valo se añade al reloj (como observador), donde en
 * paralelo contaremos su duracion (Observer).
 * Si queremos activar la tarea y ya esta previamente
 * activada, mostraremos un mensaje.
 * */
  public void startTask()
  {
    if(!isActive())
    {
      Interval i = new Interval();
      addInterval(i);
      changeState();
      System.out.print(getName()+" starts\n");
    }
    else{
      assert isActive(): "isActive not positive";
      System.out.print("Cannot start task because it is already active");
    }
    assert isActive(): "isActive not positive";
  }
/**
 * <h2>Stop task</h2>
 * En esta función paramos la activación de
 * una tarea (ya no la estamos realizando).
 * Seleccionamos el ultimo intervalo de la lista
 * de intervalos de la tarea y hacemos que deje de actualizar
 * sus tiempos con el reloj (observer)
 */
  public void stopTask(){
    if(isActive())
    {
      Interval intervalToDelete = intervals.get(nIntervals-1);
      Clock.getInstance().deleteObserver(intervalToDelete);
      changeState();
      lastUpdateDuration = Duration.ZERO;
      System.out.print(getName()+" stops\n");
    }
    else{
      assert !isActive(): "isActive true";
      System.out.print("Cannot start task because it is already unactive");
    }
    assert !isActive(): "isActive true";
    assert !lastUpdateDuration.isNegative(): "duration not negative";
  }
  /** <h2>Eliminar un intervalo</h2>
   * actualmente no tiene uso, puede servir
   * para el destructor. Elimina un intervalo.
   */
  private void deleteInterval(Interval i)
  {
    assert !intervals.isEmpty():"list intervals empty";
    assert i != null: "interval null";
    assert nIntervals > 0: "nIntervals 0 or negative";
    intervals.remove(i);
    nIntervals--;
    assert nIntervals >= 0: "nIntervals negative";
  }
  public void addInterval(Interval interval)
  {
    if(interval == null){
      throw new IllegalArgumentException("interval null");
    }
    interval.setParentTask(this);
    intervals.add(interval);
    nIntervals++;
    assert nIntervals > 0: "nIntervals 0 or negative" + nIntervals;
    assert !intervals.isEmpty(): "intervals empty";
    assert interval.getParentTask() != null: "interval parent null";
  }
  /**
   *  Actualmente gracias al patron visitor
   *  no es necesaria. La implementación InfoPrinter ya
   *  permite imprimir la información de la clase a través
   *  del método visit()
   *  @deprecated
   */
  @Override
  public void printInfo() {
    System.out.print("Task "+getName()+" child of "+getParentProject().getName()+"  "+getInitialDateTime()+"  "+getFinalDateTime()+"  "+getDuration().toSeconds()+"\n");
  }
  /** <h2> accept </h2>
   * En esta función estamos acceptando la clase visitante
   * para visitarla y que aplique nuevas funcionalidades
   * externas a la clase Task. Aplicamos el patron de
   * diseño visitor.
   * @param visitor - objeto visitor para aplicar patrón.
   */
  @Override
  public void accept(Visitor visitor){
    visitor.visit(this);
    for(Interval i:intervals)
    {
      i.accept(visitor);
    }
  }
  /**<h2> Recalculate times</h2>
   * En esta función actualizamos fechas y duración de
   * la tarea. Fecha final = Fecha final del ultimo
   * intervalo de la lista de intervalos.
   * La duracion = duración - ultima actualización de duracion
   * + la duración del ultimo intervalo de la lista de intervalos.
   */
  public void recalculateTimes(){
    // sumar la duración del último intervalo a la tarea
    Duration currentDuration = getDuration();
    currentDuration = currentDuration.minus(lastUpdateDuration);
    Interval lastInterval = intervals.get(nIntervals-1);
    lastUpdateDuration = lastInterval.getDuration();
    currentDuration = currentDuration.plus(lastInterval.getDuration());
    setDuration(currentDuration);
    Project parent = getParentProject();
    assert parent != null: "parent null";
    assert parent.getName() != null: "parent name null";
    setFinalDateTime(lastInterval.getEnd());
    parent.recalculateTimes();
    //printInfo();
    assert !lastUpdateDuration.isNegative(): "LastUpdateDuratin negative";
    assert !this.getDuration().isNegative(): "Update negative";
  }
}
