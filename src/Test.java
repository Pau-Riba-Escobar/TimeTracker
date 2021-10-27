public class Test {
  public void testCreateTask()
  {

  }
  public void testCreateProject(){
    Project p=new Project("root");
    assert p.getParentProject()==null:"Error, this project should be the root project";
    p.changeState();
    assert !p.isActive():"Error, project should be active after being created";
    assert p.getName()=="root":"Error, project name should be root";
    assert p.getFinalDateTime()==null: "Error, a brand new project shouldn't have finished";
    assert p.getDuration()==null:"Error, the project has just started. If the project hasn't finished it shouldn't have duration";
    assert p.getActivities().isEmpty()==true:"Error, activity list shall be empty";
    System.out.print("Root project created succesfully");
  }

  public void testAddActivity(){
    // We first create project and then we add to that project an Activity
    Project p = new Project("test");
    Task t = new Task("testtask");
    p.addActivity(t);
    if(p.getActivities().isEmpty())
    {
      System.out.print("activities array is empty, the activity hasn't been added");
      return;
    }
    System.out.print(" testAddActivity CORRECT. task parent is "+t.getParentProject().getName());
  }
}
