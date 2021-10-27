public interface Visitor {
  void visit(Task task);
  void visit(Project project);
  void visit(Interval interval);
}
