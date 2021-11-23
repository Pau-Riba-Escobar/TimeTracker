import java.util.List;

/**
 * <h2>Clase {@code SearcherByName}</h2>
 * Implementación de la interfaz visitor que nos permite encontrar una actividad según su nombre. Se usará a la
 * hora de construir el árbol a partir del atributo en los {@code JSONObject parentname} que nos indicará el nombre
 * del padre de una actividad o intervalo y al construir el objeto {@code SearcherByName} será almacenado en el
 * atributo {@code name}. Las funciones {@code visit()} en el caso de {@code Task y Project} comprobaran que las tareas y proyectos
 * visitados tengan un nombre coincidente con {@code name}. En tal caso copiaremos esos objetos al otro atributo de la clase
 * {@code correspondingObj} que almacenará la {@code Activity} cuyo nombre es {@code name}
 */
public class SearcherByName extends Searcher{
  private String name;
  public SearcherByName(String name){
    if(name == null){
      throw new IllegalArgumentException("name null");
    }
    this.name = name;
    assert this.name != null:"name null";
  }



  @Override
  public void visit(Project project) {
    if(project.getName().equals(name))
    {
      List<Activity> objectList = getObjList();
      objectList.add(project);
    }
  }

  @Override
  public void visit(Task task) {
    if(task.getName().equals(name))
    {
      List<Activity> objectList = getObjList();
      objectList.add(task);
    }
  }

  @Override
  public void visit(Interval interval) {
    return;
  }
}
