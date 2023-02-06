package persistentie;

import java.io.File;
import java.util.Locale;
import java.util.Random;

public class SpelbordMapper {

    private static final String template= "src/persistentie/%s_%d.txt";

    public File getRandomFile(String difficulty){
        int variatie = (int)Math.floor(Math.random()*(2)+1);
        return new File(String.format(template, difficulty.toUpperCase(), variatie));
    }
}
