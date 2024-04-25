package FileType;

abstract class Document {
    public String path;
    public abstract void open();
    public abstract void close();

    public String getPath(){
        return this.path;
    }
}  
