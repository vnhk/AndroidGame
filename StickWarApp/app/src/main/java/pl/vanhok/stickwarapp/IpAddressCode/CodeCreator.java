package pl.vanhok.stickwarapp.IpAddressCode;


enum Code{
    x,s,t,w,y,i,o,d,h,l,a;
}

public class CodeCreator{
    public static String convertToString(String message){
        for (Code it:Code.values()) {
            message = message.replaceAll(String.valueOf(it.ordinal()),it.name());
        }
        return message.replaceAll("\\.",Code.a.name());
    }

    public static String convertToIpAddress(String message){
        for (Code it:Code.values()) {
            if(it.equals(Code.a))
                break;
            message = message.replaceAll(it.name(),String.valueOf(it.ordinal()));
        }
        return message.replaceAll(Code.a.name(),"\\.");
    }
}
