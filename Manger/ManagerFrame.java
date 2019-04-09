public class ManagerFrame {

  private FTPConnection conn;
  private int width, height;
  private String title;
  
  public ManagerFram(FTPConnection conn, int width, int height, String title) {
    this.conn = conn;
    this.width = width;
    this.height = height;
    this.title = title;
  }
  public void setFiles() {
    FTPConverter convert = new FTPConverter(conn);
    String [] allFiles = null;
    String [] fileNames = convert.getFileNames();
    String [] dirNames = convert.getDirNames();
    int tempLength = fileNames.length + dirName.length;
    int index = -1;
    
    for(int i = 0; i < fileNames.length; i++) {
     for(int j = 0; j < dirName.length; j++) {
      if(fileName[i].equals(dirName[j])) {
        System.out.println("Remove " + dirName[j]);
        index = i;
        allFiles = removeElement(fileNames, index);
      }
     }
    }
    
  }
  
  private String [] removeElement(String [] arr, int index) {
    if(arr == null || index < 0 || index >= arr.length) {
     return arr; 
    }
    
    String [] temp = new String[arr.length - 1];
    
    for(int i = 0, k = 0; i < arr.length; i++) {
      if(i == index) {
       
     }
      temp[k++] = arr[i];
    }
    
    return temp;
    
  }
    
}
