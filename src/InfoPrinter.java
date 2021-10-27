public class InfoPrinter implements Visitor{
  @Override
  public void visit(Task task) {
    System.out.print("Task "+task.getName()+" child of "+task.getParentProject().getName()+"  "+task.getInitialDateTime()+"  "+task.getFinalDateTime()+"  "+task.getDuration().toSeconds()+"\n");
  }

  @Override
  public void visit(Project project) {
    System.out.print("Project "+project.getName()+" child of "+((project.getParentProject()!=null)?project.getParentProject().getName():"null")+"  "+project.getInitialDateTime()+"  "+project.getFinalDateTime()+"  "+project.getDuration().toSeconds()+"\n");
  }

  @Override
  public void visit(Interval interval) {
    System.out.print("Interval child of "+interval.getParentTask().getName()+"  "+interval.getStart()+"  "+interval.getEnd()+"  "+interval.getDuration().toSeconds()+"\n");
  }
}
