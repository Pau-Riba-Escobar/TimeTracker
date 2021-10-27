import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

/**
 * <h2>Clase Intervalo({@code Interval})</h2>
 * Esta clase representa un intervalo. Un intervalo es el periodo en que una tarea({@code Task}) se está
 * ejecutando. De este periodo necesitamos saber el inicio(), el fin(), y la duración(). Además estos intervalos
 * serán los objetos a partir de los cuales actualicemos los tiempos del resto de actividades de la jerarquía. Esta clase
 * implementa la clase {@code Observer} que en este caso recibirá de la clase {@code Observable Clock} información sobre
 * el tiempo para realizar las actualizaciones.
 * <br>
 * Para poder actualizar los tiempos de las diferentes actividades del árbol necesitamos propagar la actualización de los
 * intervalos a niveles superiores y para eso necesitamos saber a que {@code Task} pertenece cada {@code Interval}. Esa información
 * se almacena en el atributo {@code parentTask}.
 */
public class Interval implements Observer {
  private Task parentTask;
  private LocalDateTime start;
  private LocalDateTime end;
  private Duration duration;

  public Interval()
  {
    start=LocalDateTime.now();
    end=LocalDateTime.now();
    duration=Duration.ZERO;
    parentTask=null;
    Clock.getInstance().addObserver(this);
  }
  public Duration getDuration(){return duration;}
  public LocalDateTime getStart(){return  start;}
  public LocalDateTime getEnd(){return end;}
  public Task getParentTask(){return parentTask;}
  public void setParentTask(Task parent){parentTask=parent;}
  public void setStart(LocalDateTime start) {this.start = start;}
  public void setDuration(Duration duration) {this.duration = duration;}
  public void setEnd(LocalDateTime end) {this.end = end;}
  public void accept(Visitor visitor)
  {
    visitor.visit(this);
  }

  /**
   * Método que nos permite imprimir toda la información de un intervalo
   * @deprecated
   */
  public void printInfo()
  {
    System.out.print("Interval child of "+parentTask.getName()+"  "+start+"  "+end+"  "+getDuration().toSeconds()+"\n");
  }

  /**
   * Implementación de la función update de la interfaz {@code Observer}. Actualizaremos
   * los parámetros {@code end} y {@code duration} del intervalo a partir del parámetro {@code arg}
   * que realmente es una fecha({@code LocalDateTime}). Después de haber actualizado el intervalo llamamos
   * desde la tarea padre {@code parentTask} al metodo {@code recalculateTimes()} para propagar la actualización del
   * intervalo a esta.
   * @param o {@code Observable} que notifica
   * @param arg parámetro que nos sirve para actualizar el intervalo. En este caso del tipo ({@code LocalDateTime})
   */
  @Override
  public void update(Observable o, Object arg) {
    end = (LocalDateTime)arg;
    duration = Duration.between(start,end);
    parentTask.recalculateTimes();
  }
}
