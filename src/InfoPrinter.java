import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * <h2>Clase {@code InfoPrinter}</h2>
 * Implementación de la interfaz {@code Visitor} que nos permite imprimir la información de los objetos de
 * las clases {@code Task, Project y Interval}
 */
public class InfoPrinter implements Visitor{
  private static final Logger logger = LoggerFactory.getLogger("InfoPrinter");
  private static final Marker fita1 = MarkerFactory.getMarker("F1");
  private static final Marker fita2 = MarkerFactory.getMarker("F2");
  @Override
  public void visit(Task task) {
    logger.debug(fita2,"Task "+task.getName()+" child of "+task.getParentProject().getName()+" created correctly \n");
    logger.info(fita1,"Task "+task.getName()+" child of "+task.getParentProject().getName()+"  "+task.getInitialDateTime()+"  "+task.getFinalDateTime()+"  "+task.getDuration().toSeconds()+"\n");
  }

  @Override
  public void visit(Project project) {
    logger.debug(fita2,"Project "+project.getName()+" child of "+((project.getParentProject()!=null)?project.getParentProject().getName():"null")+" created correctly \n");
    logger.info(fita1,"Project "+project.getName()+" child of "+((project.getParentProject()!=null)?project.getParentProject().getName():"null")+"  "+project.getInitialDateTime()+"  "+project.getFinalDateTime()+"  "+project.getDuration().toSeconds()+"\n");
  }

  @Override
  public void visit(Interval interval) {
    logger.debug(fita2,"Interval child of "+interval.getParentTask().getName()+"  "+interval.getStart()+"  "+interval.getEnd()+"  "+interval.getDuration().toSeconds()+" created correctly \n");
    logger.info(fita1,"Interval child of "+interval.getParentTask().getName()+"  "+interval.getStart()+"  "+interval.getEnd()+"  "+interval.getDuration().toSeconds()+"\n");
  }
}
