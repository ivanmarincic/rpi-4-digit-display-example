import com.pi4j.io.gpio.*;

import java.text.SimpleDateFormat;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        final GpioController gpio = GpioFactory.getInstance();
        List<Pin> segmentsPins = new ArrayList(Arrays.asList(RaspiPin.GPIO_25, RaspiPin.GPIO_02, RaspiPin.GPIO_24, RaspiPin.GPIO_23, RaspiPin.GPIO_22, RaspiPin.GPIO_21, RaspiPin.GPIO_00, RaspiPin.GPIO_07));
        List<GpioPinDigitalOutput> segments = new ArrayList<>();
        for (int i =0; i<segmentsPins.size(); i++){
            segments.add(gpio.provisionDigitalOutputPin(segmentsPins.get(i), PinState.LOW));
        }
        List<Pin> digitsPin = new ArrayList(Arrays.asList(RaspiPin.GPIO_26, RaspiPin.GPIO_05, RaspiPin.GPIO_03, RaspiPin.GPIO_01));
        List<GpioPinDigitalOutput> digits = new ArrayList<>();
        for (int i =0; i<digitsPin.size(); i++){
            digits.add(gpio.provisionDigitalOutputPin(digitsPin.get(i), PinState.LOW));
        }
        int[][] numbers = {
            {1,1,1,1,1,1,0}, //0
            {0,1,1,0,0,0,0}, //1
            {1,1,0,1,1,0,1}, //2
            {1,1,1,1,0,0,1}, //3
            {0,1,1,0,0,1,1}, //4
            {1,0,1,1,0,1,1}, //5
            {1,0,1,1,1,1,1}, //6
            {1,1,1,0,0,0,0}, //7
            {1,1,1,1,1,1,1}, //8
            {1,1,1,1,0,1,1}, //9
        };
        try{
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("Europe/Sarajevo"));
            String time;
            while (true){
                calendar = Calendar.getInstance();
                time = new SimpleDateFormat("mmss").format(calendar.getTime());
                for(int i=0; i<digits.size(); i++){
                    for(int j=0; j<segments.size()-1; j++){
                        segments.get(j).low();
                        if(numbers[Integer.parseInt(String.valueOf(time.charAt(i)))][j] == 1){
                            segments.get(j).high();
                        }
                        if(i==1){
                            segments.get(7).high();
                        }else{
                            segments.get(7).low();
                        }
                    }
                    digits.get(i).low();
                    digits.get(i).high();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}