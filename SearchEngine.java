import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {

    ArrayList<String> strings = new ArrayList<>();

    public String handleRequest(URI url) {

        if (url.getPath().equals("/")) {
            String result = "";
            for (int i = 0; i < strings.size(); i++) {
                if (result == "") {
                    result = result + strings.get(i);
                } else {
                    result = result + " ," + result;
                }
            }
            return "List of strings stored: " + result;
        }

        if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                strings.add(parameters[1]);
                return String.format("%s has been added to the list of strings!", parameters[1]);
            }
        }

        if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                String result = "";
                for (int i = 0; i < strings.size(); i++) {
                    if (strings.get(i).contains(parameters[1])) {
                        if (result == "") {
                            result = result + strings.get(i);
                        } else {
                            result = result + ", " + strings.get(i);
                        }
                    }
                }
                return "Search results: " + result;
            }
        }

        return "404 Not Found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
