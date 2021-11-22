import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.lang.Thread;

/**
 * <h2>Clase Principal({@code Application})</h2>
 * El TimeTracker es un tipo de aplicación que nos permite crear una jerarquía de actividades( proyectos o tareas)
 * y controlar el tiempo que dedicamos a cada una de estas actividades.
 *
 * Esta es la clases que controla al programa. Aquí podemos encontrar los tests para las
 * diferentes funcionalidades y la función main para que será el núcleo de ejecución de la
 * aplicación. También encontramos métodos para la persistencia de la sesión del TimeTracker.
 *
 */

public class Application {
  /**
   * {@code rootProject} es el proyecto principal a partir del cual construiremos la jerarquía
   * de proyectos y tareas
   */
  private static Project rootProject=new Project("root");

  /**
   * La función principal que ejecutará las diferentes funcionalidades del proyecto así como los tests
   * @param args este parámetro actualmente no se usa
   */
  public static void main(String[] args)
  {
    // Clock c = Clock.getInstance();
    // c.startTimer();
    rootProject.setTag("ROOT");
    testCreateHierarchy();
    List<Activity> matchingActivities = SearchByTag("ROOT");
    System.out.print("DONE");
    // c.stopTimer();
    //writeJSON();
    //buildTreeFromJSON();
    // testCreateHierarchy();
  }

  /**
   * test para comprobar si el reloj funciona. Creamos una jerarquía simple con un proyecto y una tarea
   * y imprimimos ese arbol cada 2 segundos para ver como se va actualizando
   */
  public static void testClock()
  {
    Clock c = Clock.getInstance();
    c.startTimer();
    Timer timer = c.getTimer();
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        synchronized (this) {
          printTree();
        }
      }
    },Date.from(Instant.now()),2000);
    Project p=new Project("root");
    Task t = new Task("task");
    p.addActivity(t);
    t.startTask();
    try {
      Thread.currentThread().sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    t.stopTask();
    try {
      Thread.currentThread().sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    t.startTask();
    try {
      Thread.currentThread().sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    t.stopTask();
    Clock.getInstance().stopTimer();
  }

  /**
   * En este test creamos una jerarquía de proyectos y tareas partiendo del proyecto {@code rootProject} y simulamos
   * el funcionamiento real del proyecto poniendo tareas en marcha y parandolas. Así pues podemos observar
   * también el correcto funcionamiento del reloj.
   *
   * A partir de {@code rootProject} podremos comprobar si la jerarquía se ha construido correctamente
   * y al igual que en {@code testClock()} planificamos la impresión del árbol cada 2 segundos y ver como se van
   * sucediendo las actualizaciones( como se crean nuevos intervalos y como se actualiza el tiempo de Intervalos, Tareas
   * y Proyectos)
   */
  public static void testCreateHierarchy()
  {
    Clock c = Clock.getInstance();
    c.startTimer();
    Timer t = c.getTimer();
    t.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        synchronized (this) {
          printTree();
        }
      }
    },Date.from(Instant.now()),2000);
    // TOP LEVEL(0)
    Project softwareDesign = new Project("software design");
    Project softwareTesting = new Project("software testing");
    Project databases = new Project("databases");
    Task tasktransportation = new Task("task transportation");
    rootProject.addActivity(softwareDesign);
    rootProject.addActivity(softwareTesting);
    rootProject.addActivity(databases);
    rootProject.addActivity(tasktransportation);

    // LEVEL 1
    Project problems = new Project("problems");
    Project projectTimeTracker = new Project("project time tracker");
    softwareDesign.addActivity(problems);
    softwareDesign.addActivity(projectTimeTracker);

    // LEVEL 2
    Task firstList = new Task("first list");
    Task secondList = new Task("second list");
    problems.addActivity(firstList);
    problems.addActivity(secondList);
    Task readHandout = new Task("read handout");
    Task firstMilestone = new Task("first milestone");
    projectTimeTracker.addActivity(readHandout);
    projectTimeTracker.addActivity(firstMilestone);

    tasktransportation.startTask();
    try {
      Thread.currentThread().sleep(4000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    tasktransportation.stopTask();
    try {
      Thread.currentThread().sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    firstList.startTask();
    try {
      Thread.currentThread().sleep(6000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    secondList.startTask();
    try {
      Thread.currentThread().sleep(4000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    firstList.stopTask();
    try {
      Thread.currentThread().sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    secondList.stopTask();
    try {
      Thread.currentThread().sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    tasktransportation.startTask();
    try {
      Thread.currentThread().sleep(4000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    tasktransportation.stopTask();
    c.stopTimer();
  }

  /**
   * Esta función implementa la persistencia de los datos de nuestra jerarquía. A partir de un objeto
   * de la clase {@code JSONWriter} ,que implementa la interfaz {@code Visitor}, guardamos en su atributo
   * {@code jsonArray} un {@code JSONArray}, que contendrá {@code JSONObjects} con información de Proyectos,
   * Tareas o Intervalos, que luego vamos a escribir como {@code String} en un archivo txt. Este
   * archivo txt nos permitirá posteriormente recuperar la sesión.
   */
  public static void writeJSON()
  {
    JSONWriter writer = new JSONWriter();
    rootProject.accept(writer);
    try{
      FileWriter fw = new FileWriter("jsonfile.txt");
      fw.write(writer.getJsonArray().toString());
      fw.close();
    }catch(IOException e){e.printStackTrace();}
  }

  /**
   * Este método nos permite recuperar la sesión guardada en el archivo {@code jsonfile.txt}. De
   * este modo construimos la jerarquía que tenemos guardada en el archivo sobre el proyecto raíz {@code rootProject}. Obtendremos
   * la información de los {@code JSONObjects} guardados y los reconstruiremos como los objetos correspondientes
   * a partir de su tipo({@code Project, Task, o Interval})
   */
  public static void buildTreeFromJSON()
  {
    String rawdata="";
    try{
      File file = new File("jsonfile.txt");
      Scanner scanner = new Scanner(file);
      rawdata = scanner.nextLine();
      scanner.close();
    }catch(FileNotFoundException e){e.printStackTrace();}
    JSONArray data = new JSONArray(rawdata);
    JSONObject jsonObj = data.getJSONObject(0);
    rootProject.setName(jsonObj.get("name").toString());
    rootProject.setInitialDateTime(LocalDateTime.parse(jsonObj.get("initial_data").toString()));
    rootProject.setFinalDateTime(LocalDateTime.parse(jsonObj.get("final_data").toString()));
    rootProject.setDuration(Duration.parse(jsonObj.get("duration").toString()));
    data.remove(0);
    for(int i=0;i< data.length();i++)
    {
      JSONObject jsonObject = data.getJSONObject(i);
      switch(jsonObject.get("type").toString())
      {
        case "Project":
          Project project = new Project(jsonObject.get("name").toString());
          project.setTag(jsonObject.get("tag").toString());
          project.setInitialDateTime(LocalDateTime.parse(jsonObject.get("initial_data").toString()));
          project.setFinalDateTime(LocalDateTime.parse(jsonObject.get("final_data").toString()));
          project.setDuration(Duration.parse(jsonObject.get("duration").toString()));
          SearcherByName psearcher = new SearcherByName(jsonObject.get("parentname").toString());
          rootProject.accept(psearcher);
          Project parent = (Project)(psearcher.getObjList().get(0));
          project.setParentProject(parent);
          parent.addActivity(project);
          break;
        case "Task":
          Task  task = new Task(jsonObject.get("name").toString());
          task.setTag(jsonObject.get("tag").toString());
          task.setInitialDateTime(LocalDateTime.parse(jsonObject.get("initial_data").toString()));
          task.setFinalDateTime(LocalDateTime.parse(jsonObject.get("final_data").toString()));
          task.setDuration(Duration.parse(jsonObject.get("duration").toString()));
          SearcherByName tsearcher = new SearcherByName(jsonObject.get("parentname").toString());
          rootProject.accept(tsearcher);
          Project parentProject = (Project)(tsearcher.getObjList().get(0));
          task.setParentProject(parentProject);
          parentProject.addActivity(task);
          break;
        case "Interval":
          Interval interval = new Interval();
          interval.setStart(LocalDateTime.parse(jsonObject.get("start").toString()));
          interval.setEnd(LocalDateTime.parse(jsonObject.get("start").toString()));
          interval.setDuration(Duration.parse(jsonObject.get("duration").toString()));
          SearcherByName isearcher = new SearcherByName(jsonObject.get("parentname").toString());
          rootProject.accept(isearcher);
          Task parentTask = (Task)(isearcher.getObjList().get(0));
          interval.setParentTask(parentTask);
          parentTask.addInterval(interval);
          break;

      }
    }
    System.out.print("DONE");
  }
  public static List<Activity> SearchByTag(String tag)
  {
    SearcherByTag searcher = new SearcherByTag(tag);
    rootProject.accept(searcher);
    return searcher.getObjList();
  }
  /**
   * Este método nos permite imprimir la información del árbol por pantalla a partir de un
   * objeto de la clase {@code InfoPrinter} que implementa la interfaz {@code Visitor}
   */
  public static void printTree()
  {
    InfoPrinter printer=new InfoPrinter();
    rootProject.accept(printer);

  }

  public Duration TotalTimeSpent(Activity activity, timePeriods period)
  {
     return activity.TotalTimeSpent(period);
  }
}
