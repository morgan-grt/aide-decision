
import java.io.IOException;
import extraction.*;
import planning.*;
import representations.*;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 0){
            switch(args[0]){
                case "0":
                    new InitExtraction().init(args);
                    break;
                case "1":
                    new InitPlanning().init(args);
                    break;
                case "2":
                    new InitRepresentations().init(args);
                    break;
            }
        }
        else{
            new InitRepresentations().init(args);
        }
    }
}
