package vv;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.*;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFileFormat.*;


public class v
{


   Voice freettsVoice;
   private static Voice voice;
   
   public static void main(String[] args) {

	   VoiceManager vv = VoiceManager.getInstance();
	   System.setProperty(
               "freetts.voices",
               "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");  
	   Voice[] voices = vv.getVoices();
	   System.out.println(voices.length);
	    for (int i = 0; i < voices.length; i++) {
	        System.out.println("    " + voices[i].getName() + " (" + voices[i].getDomain() + " domain)");
	    }
	    
	    
       voice = vv.getVoice("kevin16");
        voice.allocate();
       voice.speak("With Chrome profiles you can separate all your Chrome stuff. Create profiles for friends and family, or split between work and fun.");

      //dumpAudio("C:\\Users\\david\\Documents\\Reddit_Project\\dumpAudio.wav"); 
              
       voice.deallocate();
    
   }
}
