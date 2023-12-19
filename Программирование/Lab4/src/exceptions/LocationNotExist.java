package exceptions;
public class LocationNotExist extends Exception{
    private Object location;
    public LocationNotExist(Object location){
        super(location + " не является местом");
        this.location = location;

    }
    @Override
    public String getMessage(){
        return "Объект " + this.location.toString() + " не является местом";
    }

}