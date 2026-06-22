class Logger
{
    private static Logger instance;

    private Logger(){
        System.out.println("Instance created successfully");
    }

    public static Logger getInstance()
    {
        if(instance==null)
        {
            instance=new Logger();
        }
        return instance;
    }

    public void log(String message)
    {
        System.out.println(message);
    }
}

public class SingletonExample {
    public static void main(String[]args)
    {
        Logger l1=Logger.getInstance();
        Logger l2=Logger.getInstance();
        
        System.out.print("Logger 1 : ");
        l1.log("Hey This is Logger 1");
        System.out.print("Logger 2 : ");
        l2.log("Hey This is Logger 2");

        System.out.print("Are logger1 and logger2 same ? "+(l1==l2));
    }
}


// Output:
// Instance created successfully
// Logger 1 : Hey This is Logger 1
// Logger 2 : Hey This is Logger 2
// Are logger1 and logger2 same ? true